import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener, MouseListener {
	//comment
	private BufferedImage backgroundImg;
	private BufferedImage creditsPage;
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
	private BufferedImage creditsBDImg;
	private BufferedImage creditsBHImg;
	Button creditsButton;
	
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
		this.setLayout(null);
		panel.add(this, "MainMenu");
		
		game = new Game(WIDTH, HEIGHT);
		game.addKeyListener(game);
		game.addMouseListener(game);
		this.setLayout(null);
		panel.add(game, "Game");
		

		((CardLayout)(panel.getLayout())).show(panel, "MainMenu");
		
		t = new Timer(100, this);
		gameState = "MainMenu";
		
		loadImages();
		
		startButton = new Button(402, 200, 96, 72, startBDImg, startBHImg);
		startButton.addAL(this);
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		this.add(startButton);

		helpButton = new Button(402, 275, 96, 72, helpBDImg, helpBHImg);
		helpButton.addAL(this);
		helpButton.setOpaque(false);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorderPainted(false);
		this.add(helpButton);

		creditsButton = new Button(402, 350, 96, 72, creditsBDImg, creditsBHImg);
		creditsButton.addAL(this);
		creditsButton.setOpaque(false);
		creditsButton.setContentAreaFilled(false);
		creditsButton.setBorderPainted(false);
		this.add(creditsButton);

		startButton.addMouseListener(this);
		helpButton.addMouseListener(this);
		creditsButton.addMouseListener(this);

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
			creditsBDImg = ImageIO.read(MainMenu.class.getResourceAsStream("creditsButtonD.png"));
			creditsBHImg = ImageIO.read(MainMenu.class.getResourceAsStream("creditsButtonH.png"));
			backgroundImg = ImageIO.read(MainMenu.class.getResourceAsStream("titlePage.jpg"));
			creditsPage = ImageIO.read(MainMenu.class.getResourceAsStream("Credits Page.jpg")); //get credits page image
			System.out.println("all images were loaded");
		} catch (IOException e) {
			System.out.println("could not load some images");
			e.printStackTrace();
		}
	}

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
	
	public void drawMainMenu(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);
		startButton.draw(g);
		helpButton.draw(g);
		creditsButton.draw(g);
	}

	public void drawHelp(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);
	}
	
	public void drawCredits(Graphics g) {
		g.drawImage(creditsPage, 0,0,null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (e.getSource().equals(startButton)) {
			System.out.println("Pressed Start");
			gameState = "Game";
			((CardLayout)(panel.getLayout())).show(panel, "Game");
			game.requestFocusInWindow();
			t.stop();
		} else if (e.getSource().equals(helpButton)) {
			System.out.println("Pressed Help");
			gameState = "Help";
		} else if(e.getSource().equals(creditsButton)) {
			System.out.println("Pressed Credits");
			gameState = "Credits";
		}
		// quit t.stop()
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getSource().equals(startButton)) 
			startButton.hoverButton();
		else if(arg0.getSource().equals(helpButton)) 
			helpButton.hoverButton();
		else if(arg0.getSource().equals(creditsButton))
			creditsButton.hoverButton();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(arg0.getSource().equals(startButton)) 
			startButton.defaultButton();
		else if(arg0.getSource().equals(helpButton))
			helpButton.defaultButton();
		else if(arg0.getSource().equals(creditsButton))
			creditsButton.defaultButton();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
