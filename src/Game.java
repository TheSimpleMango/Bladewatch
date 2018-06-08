import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.Timer;

import org.omg.Messaging.SyncScopeHelper;

//ftp://ecs.csus.edu/clevengr/133/handouts/UsingJavaKeyBindings.pdf
//look into key bindings as an alternative to keylistener

@SuppressWarnings("serial")
public class Game extends JPanel implements MouseListener, KeyListener, ActionListener {
	private final int WIDTH;
	private final int HEIGHT;

	Timer t;
	BufferedImage backgroundImg;

	Handler handler;

	Player p1;
	Player p2;

	vertical leftEdge, rightEdge;
	floor ground, ceiling;
	
	Player win = null;

	public Game(int WIDTH, int HEIGHT) {

		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;

		t = new Timer(20, this);

		handler = new Handler();

		p1 = new Player(100, 300, WIDTH, HEIGHT, "player.png", handler, false, this, "p1health.png");
		p2 = new Player(600, 300, WIDTH, HEIGHT, "player.png", handler, true, this, "p2health.png");

		initGame();

		loadImages();

		t.start();
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);
		p1.paint(g);
		p2.paint(g);
		if (win != null) {
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			if (win.equals(p2)) {
				g.drawString("player 1 wins!", 200, 200);
			}
			else {
				g.drawString("player 2 wins!", 200, 200);
			}
		}
	}

	private void initGame() {
		ground = new floor(0, HEIGHT - 20, WIDTH, WIDTH, HEIGHT);
		ceiling = new floor(0, 0, WIDTH, WIDTH, HEIGHT);
		leftEdge = new vertical(0, 0, HEIGHT, WIDTH, HEIGHT);
		rightEdge = new vertical(WIDTH - 20, 0, HEIGHT, WIDTH, HEIGHT);
		handler.addObj(p1);
		handler.addObj(p2);
		handler.addObj(ground);
		handler.addObj(ceiling);
		handler.addObj(leftEdge);
		handler.addObj(rightEdge);
	}

	private void loadImages() {
		try {
			backgroundImg = ImageIO.read(MainMenu.class.getResourceAsStream("Future.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(p1.getX() + "/ " + p1.getY());
		tick(handler.object);
	}

	private void tick(ArrayList<gameObject> object) {
		System.out.println(p1.getX() + "/ " + p1.getY());
		p1.update(object);
		p2.update(object);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent keyPressed) {
		switch (keyPressed.getKeyCode()) {
		case KeyEvent.VK_W:
			if (!p1.isFalling()) {
				p1.up = true;
				p1.setJumping(true);
				p1.setVelY(-1 * p1.PLAYERSPEED);
			}
			break;
		case KeyEvent.VK_A:
			p1.left = true;
			p1.setVelX(-1 * p1.PLAYERSPEED / 3);
			p1.facesLeft = true;
			break;
		case KeyEvent.VK_D:
			p1.right = true;
			p1.setVelX(p1.PLAYERSPEED / 3);
			p1.facesLeft = false;
			break;
		case KeyEvent.VK_UP:
			if (!p2.isFalling()) {
				p2.up = true;
				p2.setJumping(true);
				p2.setVelY(-1 * p2.PLAYERSPEED);
			}
			break;
		case KeyEvent.VK_LEFT:
			p2.left = true;
			p2.setVelX(-1 * p1.PLAYERSPEED / 3);
			p2.facesLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			p2.right = true;
			p2.setVelX(p1.PLAYERSPEED / 3);
			p2.facesLeft = false;
			break;
		case KeyEvent.VK_Q:
			p1.swordSprite();
			repaint();
			if (p1.contains(p2)) {
				p2.decreaseHealth();
			}
			break;
		case KeyEvent.VK_E:
			System.out.println("Fireball!");
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent keyReleased) {
		switch (keyReleased.getKeyCode()) {
		case KeyEvent.VK_W:
			p1.up = false;
			break;
		case KeyEvent.VK_A:
			p1.left = false;
			if (!p1.right) {
				p1.setVelX(0);
			}
			break;
		case KeyEvent.VK_D:
			p1.right = false;
			if (!p1.left) {
				p1.setVelX(0);
			}
			break;
		case KeyEvent.VK_UP:
			p2.up = false;
			break;
		case KeyEvent.VK_LEFT:
			p2.left = false;
			if (!p2.right) {
				p2.setVelX(0);
			}
			break;
		case KeyEvent.VK_RIGHT:
			p2.right = false;
			if (!p2.left) {
				p2.setVelX(0);
			}
			break;
		case KeyEvent.VK_Q:
			p2.standImage();
			break;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
			
		}
		repaint();
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
	
	public void endGame(Player p){
		win = p;
		repaint();
	}

}