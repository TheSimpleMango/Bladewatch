import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener {
	private BufferedImage backgroundImg;
	private JFrame frame;
	private JPanel panel;
	private final int WIDTH = 900;
	private final int HEIGHT = 500;

	Game game;
	
	Timer t;

	private BufferedImage startBDImg;
	private BufferedImage startBHImg;
	Button startButton;
	private BufferedImage helpBDImg;
	private BufferedImage helpBHImg;
	Button helpButton;
	// quit button for ending the timer
	// private BufferedImage helpBDImg;
	// private BufferedImage helpBHImg;
	// Button helpButton;
	// implement credits page
	// private BufferedImage creditsbd;
	// private BufferedImage creditsbh;
	// Button creditsButton;

	private String gameState;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu d = new MainMenu();
	}

	public MainMenu() {
		frame = new JFrame("BladeWatch");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new CardLayout());
		panel.add(this, "MainMenu");
		
		game = new Game();
		game.addKeyListener(game);
		game.addMouseListener(game);
		panel.add(game, "Game");
		

		((CardLayout)(panel.getLayout())).show(panel, "MainMenu");
		
		t = new Timer(100, this);
		gameState = "MainMenu";
		
		loadImages();
		
		startButton = new Button(402, 270, 96, 72, startBDImg, startBHImg);
		startButton.addAL(this);
//		startButton.setOpaque(false);
//		startButton.setContentAreaFilled(false);
//		startButton.setBorderPainted(false);
		this.add(startButton);
		
		helpButton = new Button(402, 350, 96, 72, helpBDImg, helpBHImg);
		helpButton.addAL(this);
//		helpButton.setOpaque(false);
//		helpButton.setContentAreaFilled(false);
//		helpButton.setBorderPainted(false);
		this.add(helpButton);

		t.start();
		frame.setSize(WIDTH, HEIGHT);
		repaint();
	}

	public void loadImages() {
		try {
			startBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("startButtonD.png"));
			startBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("startButtonH.png"));
			helpBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("helpButtonD.png"));
			helpBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("helpButtonH.png"));
			backgroundImg = ImageIO.read(MainMenu.class.getResourceAsStream("Background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameState == "MainMenu") {
			drawMainMenu(g);
		} else if (gameState == "Help") {
			drawHelp(g);
		}
	}
	
	public void drawMainMenu(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);

		// replace this with a picture of the title
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		g.drawString("BladeWatch", 390, 100);

		startButton.draw(g);
		helpButton.draw(g);
	}

	public void drawHelp(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);

		// replace this with a picture of the title
		g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		g.drawString("Help", 390, 100);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		System.out.println(e.getSource());

		if (e.getSource().equals(startButton)) {
			System.out.println("Start...");
			gameState = "Game";
			((CardLayout)(panel.getLayout())).show(panel, "Game");
			game.requestFocusInWindow();
			t.stop();
		} else if (e.getSource().equals(helpButton)) {
			System.out.println("Help...");
			gameState = "Help";
		}
		// quit t.stop()
	}
}
