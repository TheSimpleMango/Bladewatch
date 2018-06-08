import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener, MouseListener, KeyListener {
	private BufferedImage backgroundImg;
	private BufferedImage creditsPage;
	private BufferedImage helpPage;
	private JFrame frame;
	private JPanel panel;
	private final int WIDTH = 900;
	private final int HEIGHT = 500;

	private Game game;

	private Timer t;

	private BufferedImage startBDImg;
	private BufferedImage startBHImg;
	private Button startButton;
	private BufferedImage helpBDImg;
	private BufferedImage helpBHImg;
	private Button helpButton;
	private BufferedImage creditsBDImg;
	private BufferedImage creditsBHImg;
	private Button creditsButton;
	private Button backButton;

	protected String gameState;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

	/**
	 * constructs main menu
	 */
	public MainMenu() {
		setFrame(new JFrame("BladeWatch"));
		getFrame().setVisible(true);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setPanel(new JPanel());
		getFrame().add(getPanel());
		getPanel().setLayout(new CardLayout());
		this.setLayout(null);
		getPanel().add(this, "MainMenu");

		setGame(new Game(WIDTH, HEIGHT, this));
		getGame().addKeyListener(getGame());
		getGame().addMouseListener(getGame());
		this.setLayout(null);
		getPanel().add(getGame(), "Game");

		((CardLayout) (getPanel().getLayout())).show(getPanel(), "MainMenu");

		setT(new Timer(100, this));
		gameState = "MainMenu";

		loadImages();

		setStartButton(new Button(402, 200, 96, 72, startBDImg, startBHImg));
		getStartButton().addAL(this);
		getStartButton().setOpaque(false);
		getStartButton().setContentAreaFilled(false);
		getStartButton().setBorderPainted(false);
		this.add(getStartButton());

		setHelpButton(new Button(402, 275, 96, 72, helpBDImg, helpBHImg));
		getHelpButton().addAL(this);
		getHelpButton().setOpaque(false);
		getHelpButton().setContentAreaFilled(false);
		getHelpButton().setBorderPainted(false);
		this.add(getHelpButton());

		setCreditsButton(new Button(402, 350, 96, 72, creditsBDImg, creditsBHImg));
		getCreditsButton().addAL(this);
		getCreditsButton().setOpaque(false);
		getCreditsButton().setContentAreaFilled(false);
		getCreditsButton().setBorderPainted(false);
		this.add(getCreditsButton());

		setBackButton(new Button(0, 0, 100, 60, creditsBDImg, creditsBHImg));
		getBackButton().addAL(this);
		getBackButton().setOpaque(false);
		getBackButton().setContentAreaFilled(false);
		getBackButton().setBorderPainted(false);
		this.add(getBackButton());

		getStartButton().addMouseListener(this);
		getHelpButton().addMouseListener(this);
		getCreditsButton().addMouseListener(this);

		getT().start();
		getFrame().setSize(WIDTH, HEIGHT);
		repaint();
	}

	/**loads images
	 * 
	 */
	public void loadImages() {
		try {
			startBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("startButtonD.png"));
			startBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("startButtonH.png"));
			helpBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("helpButtonD.png"));
			helpBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("helpButtonH.png"));
			creditsBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("creditsButtonD.png"));
			creditsBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("creditsButtonH.png"));
			backgroundImg = ImageIO.read(MainMenu.class.getResourceAsStream("titlePage.jpg"));
			creditsPage = ImageIO.read(MainMenu.class.getResourceAsStream("Credits Page.jpg"));
			helpPage = ImageIO.read(MainMenu.class.getResourceAsStream("Help page.jpg"));
			System.out.println("all images were loaded");
		} catch (IOException e) {
			System.out.println("could not load some images");
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * paints everything
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameState == "MainMenu") {
			drawMainMenu(g);
		} else if (gameState == "Help") {
			drawHelp(g);
		} else if (gameState == "Credits") {
			drawCredits(g);
		}
	}

	/**
	 * @param g next three methods draw pages
	 */
	public void drawMainMenu(Graphics g) {
		getFrame().setSize(900, 500);
		g.drawImage(backgroundImg, 0, 0, null);
		getStartButton().draw(g);
		getHelpButton().draw(g);
		getCreditsButton().draw(g);
	}

	public void drawHelp(Graphics g) {
		getFrame().setSize(960, 607);
		g.drawImage(helpPage, 0, 0, null);
	}

	public void drawCredits(Graphics g) {
		getFrame().setSize(927, 579);
		g.drawImage(creditsPage, 0, 0, null);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * button actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (e.getSource().equals(getStartButton())) {
			System.out.println("Pressed Start");
			gameState = "Game";
			((CardLayout) (getPanel().getLayout())).show(getPanel(), "Game");
			getGame().requestFocusInWindow();
			getGame().getT().start();
			getT().stop();
		} else if (e.getSource().equals(getHelpButton())) {
			System.out.println("Pressed Help");
			gameState = "Help";
		} else if (e.getSource().equals(getCreditsButton())) {
			System.out.println("Pressed Credits");
			gameState = "Credits";
		} else if (e.getSource().equals(getBackButton())) {
			gameState = "MainMenu";
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * hover functionality
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getSource().equals(getStartButton()))
			getStartButton().hoverButton();
		else if (arg0.getSource().equals(getHelpButton()))
			getHelpButton().hoverButton();
		else if (arg0.getSource().equals(getCreditsButton()))
			getCreditsButton().hoverButton();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource().equals(getStartButton()))
			getStartButton().defaultButton();
		else if (arg0.getSource().equals(getHelpButton()))
			getHelpButton().defaultButton();
		else if (arg0.getSource().equals(getCreditsButton()))
			getCreditsButton().defaultButton();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	JFrame getFrame() {
		return frame;
	}

	void setFrame(JFrame frame) {
		this.frame = frame;
	}

	JPanel getPanel() {
		return panel;
	}

	void setPanel(JPanel panel) {
		this.panel = panel;
	}

	Game getGame() {
		return game;
	}

	void setGame(Game game) {
		this.game = game;
	}

	Timer getT() {
		return t;
	}

	void setT(Timer t) {
		this.t = t;
	}

	Button getStartButton() {
		return startButton;
	}

	void setStartButton(Button startButton) {
		this.startButton = startButton;
	}

	Button getHelpButton() {
		return helpButton;
	}

	void setHelpButton(Button helpButton) {
		this.helpButton = helpButton;
	}

	Button getCreditsButton() {
		return creditsButton;
	}

	void setCreditsButton(Button creditsButton) {
		this.creditsButton = creditsButton;
	}

	Button getBackButton() {
		return backButton;
	}

	void setBackButton(Button backButton) {
		this.backButton = backButton;
	}
}
