import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Game extends JPanel implements MouseListener, KeyListener, ActionListener {
	private final int WIDTH;
	private final int HEIGHT;
	private MainMenu menu;
	private Platform ground;
	private Platform ceiling;
	private Platform leftEdge;
	private Platform rightEdge;
	public ArrayList<gameObject> object = new ArrayList<gameObject>();
	private boolean isFutureImg;

	private Timer t;
	private BufferedImage backgroundImg;

	private Player p1;
	private Player p2;

	private Fireball f1;
	private Fireball f2;

	private Player win;
	private Player lastWin;

	/*
	 * Constructs the Game Panel
	 */

	/**
	 * @param WIDTH
	 * @param HEIGHT
	 * @param menu
	 */
	public Game(int WIDTH, int HEIGHT, MainMenu menu) {
		setF1(new Fireball(1000, 1000, WIDTH, HEIGHT, "Fireball.png"));
		setF2(new Fireball(1000, 1000, WIDTH, HEIGHT, "Fireball.png"));

		setFutureImg(true);
		setWin(null);

		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.menu = menu;

		setT(new Timer(20, this));
		setP1(new Player(100, 300, WIDTH, HEIGHT, "player.png", false, this, "p1health.png"));
		setP2(new Player(600, 300, WIDTH, HEIGHT, "player.png", true, this, "p2health.png"));

		initGame();

		try {
			setBackgroundImg(ImageIO.read(MainMenu.class.getResourceAsStream("Future.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * paints both players and fireballs as well as the background
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(getBackgroundImg(), 0, 0, null);
		getP1().paint(g);
		getP2().paint(g);
		getF1().paint(g);
		getF2().paint(g);
	}

	/**
	 * @param p
	 * ends the game once a player dies
	 */
	public void endGame(Player p) {
		setWin(p);
		if (getWin() != null) {
			if (getLastWin() == null) {
				if (getWin() == getP1()) {
					JOptionPane.showMessageDialog(null, "player 1 wins this round!");
					setLastWin(getP1());
					setWin(null);
					getT().stop();
				} else if (getWin() == getP2()) {
					JOptionPane.showMessageDialog(null, "player 2 wins this round!");
					setLastWin(getP2());
					setWin(null);
					getT().stop();
				}
				resetGame();
			} else if (getLastWin() != null) {
				if (getWin() == getP1() && getLastWin() == getP1()) {
					JOptionPane.showMessageDialog(null, "player 1 wins the game!");
					getT().stop();
				} else if (getWin() == getP2() && getLastWin() == getP2()) {
					JOptionPane.showMessageDialog(null, "player 2 wins the game!");
					getT().stop();
				} else {
					JOptionPane.showMessageDialog(null, "Tie! Play again...");
					setWin(null);
					setLastWin(null);
				}
				menu.getFrame().dispose();
				@SuppressWarnings("unused")
				MainMenu m = new MainMenu();
			}
		}
	}

	/**
	 * resets game to next or previous level
	 */
	private void resetGame() {
		if (isFutureImg()) {
			try {
				setBackgroundImg(ImageIO.read(MainMenu.class.getResourceAsStream("Jungle.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setFutureImg(false);
		} else {
			try {
				setBackgroundImg(ImageIO.read(MainMenu.class.getResourceAsStream("Future.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setFutureImg(true);
		}
		getP1().reset(100, 300);
		getP2().reset(600, 300);
		repaint();
		getT().start();
	}

	/**
	 * initializes variables
	 */
	private void initGame() {
		setGround(new Platform(0, HEIGHT - 20, WIDTH, 40, WIDTH, HEIGHT));
		setCeiling(new Platform(0, 0, WIDTH, 40, WIDTH, HEIGHT));
		setLeftEdge(new Platform(0, 0, 20, HEIGHT, WIDTH, HEIGHT));
		setRightEdge(new Platform(WIDTH - 20, 0, 20, HEIGHT, WIDTH, HEIGHT));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * updates fireballs and players
	 */
	public void actionPerformed(ActionEvent e) {
		getF1().update();
		getF2().update();

		playerUpdate(getP1());
		playerUpdate(getP2());

		getP1().checkFire(getF2());
		getP2().checkFire(getF1());
		repaint();
	}

	/**
	 * @param p
	 * updates players
	 */
	public void playerUpdate(Player p) {
		p.update(object);
		p.checkTop(getCeiling());
		p.checkBottom(getGround());
		p.checkRight(getRightEdge());
		p.checkLeft(getLeftEdge());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 * checks for key presses for both players
	 */
	@Override
	public void keyPressed(KeyEvent keyPressed) {
		switch (keyPressed.getKeyCode()) {
		case KeyEvent.VK_W:
			if (!getP1().isJumping()) {
				getP1().setUp(true);
				getP1().setJumping(true);
				getP1().setVelY(-1 * getP1().getPLAYERSPEED());
			}
			break;
		case KeyEvent.VK_A:
			getP1().setLeft(true);
			getP1().setVelX(-1 * getP1().getPLAYERSPEED() / 3);
			getP1().setFacesLeft(true);
			break;
		case KeyEvent.VK_D:
			getP1().setRight(true);
			getP1().setVelX(getP1().getPLAYERSPEED() / 3);
			getP1().setFacesLeft(false);
			break;
		case KeyEvent.VK_UP:
			if (!getP2().isJumping()) {
				getP2().setUp(true);
				getP2().setJumping(true);
				getP2().setVelY(-1 * getP2().getPLAYERSPEED());
			}
			break;
		case KeyEvent.VK_LEFT:
			getP2().setLeft(true);
			getP2().setVelX(-1 * getP1().getPLAYERSPEED() / 3);
			getP2().setFacesLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			getP2().setRight(true);
			getP2().setVelX(getP1().getPLAYERSPEED() / 3);
			getP2().setFacesLeft(false);
			break;
		case KeyEvent.VK_Q:
			getP1().swordSprite();
			repaint();
			if (getP1().contains(getP2())) {
				getP2().decreaseHealth();
			}
			break;
		case 46:
			getP2().swordSprite();
			repaint();
			if (getP2().contains(getP1())) {
				getP1().decreaseHealth();
			}
			break;
		case KeyEvent.VK_E:
			getF1().fire((int) getP1().x, (int) getP1().y, getP1().isFacesLeft());
			break;
		case 47:
			getF2().fire((int) getP2().x, (int) getP2().y, getP2().isFacesLeft());
			break;
		}
		repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 * checks for key presses for both players
	 */
	@Override
	public void keyReleased(KeyEvent keyReleased) {
		switch (keyReleased.getKeyCode()) {
		case KeyEvent.VK_W:
			getP1().setUp(false);
			break;
		case KeyEvent.VK_A:
			getP1().setLeft(false);
			if (!getP1().isRight()) {
				getP1().setVelX(0);
			}
			break;
		case KeyEvent.VK_D:
			getP1().setRight(false);
			if (!getP1().isLeft()) {
				getP1().setVelX(0);
			}
			break;
		case KeyEvent.VK_UP:
			getP2().setUp(false);
			break;
		case KeyEvent.VK_LEFT:
			getP2().setLeft(false);
			if (!getP2().isRight()) {
				getP2().setVelX(0);
			}
			break;
		case KeyEvent.VK_RIGHT:
			getP2().setRight(false);
			if (!getP2().isLeft()) {
				getP2().setVelX(0);
			}
			break;
		case KeyEvent.VK_Q:
			getP1().standImage();
			repaint();
			break;
		case 46:
			getP2().standImage();
			repaint();
			break;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	Platform getGround() {
		return ground;
	}

	void setGround(Platform ground) {
		this.ground = ground;
	}

	Platform getCeiling() {
		return ceiling;
	}

	void setCeiling(Platform ceiling) {
		this.ceiling = ceiling;
	}

	Platform getLeftEdge() {
		return leftEdge;
	}

	void setLeftEdge(Platform leftEdge) {
		this.leftEdge = leftEdge;
	}

	Platform getRightEdge() {
		return rightEdge;
	}

	void setRightEdge(Platform rightEdge) {
		this.rightEdge = rightEdge;
	}

	boolean isFutureImg() {
		return isFutureImg;
	}

	void setFutureImg(boolean isFutureImg) {
		this.isFutureImg = isFutureImg;
	}

	Timer getT() {
		return t;
	}

	void setT(Timer t) {
		this.t = t;
	}

	BufferedImage getBackgroundImg() {
		return backgroundImg;
	}

	void setBackgroundImg(BufferedImage backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	Player getP1() {
		return p1;
	}

	void setP1(Player p1) {
		this.p1 = p1;
	}

	Player getP2() {
		return p2;
	}

	void setP2(Player p2) {
		this.p2 = p2;
	}

	Fireball getF1() {
		return f1;
	}

	void setF1(Fireball f1) {
		this.f1 = f1;
	}

	Fireball getF2() {
		return f2;
	}

	void setF2(Fireball f2) {
		this.f2 = f2;
	}

	Player getWin() {
		return win;
	}

	void setWin(Player win) {
		this.win = win;
	}

	Player getLastWin() {
		return lastWin;
	}

	void setLastWin(Player lastWin) {
		this.lastWin = lastWin;
	}

}