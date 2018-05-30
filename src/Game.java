import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//ftp://ecs.csus.edu/clevengr/133/handouts/UsingJavaKeyBindings.pdf
//look into key bindings as an alternative to keylistener

@SuppressWarnings("serial")
public class Game extends JPanel implements MouseListener, KeyListener, ActionListener {
	Timer t;
	BufferedImage backgroundImg;

	Player p1;
	Player p2;

	public Game() {
		
		t = new Timer(20, this);
		
		p1 = new Player(100, 300, 40, 100, "bunny.png");
		p2 = new Player(600, 300, 40, 100, "bunny.png");
		
		loadImages();
		
		t.start();
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, null);
		p1.paint(g);
		p2.paint(g);
	}

	private void loadImages() {
		try {
			backgroundImg = ImageIO.read(MainMenu.class.getResourceAsStream("Future.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		p1.update();
		p2.update();
		repaint();
	}

	public void keyPressed(KeyEvent keyPressed) {
		switch (keyPressed.getKeyCode()) {
		case KeyEvent.VK_W:
			p1.up = true;
			break;
		case KeyEvent.VK_S:
			p1.down = true;
			break;
		case KeyEvent.VK_A:
			p1.left = true;
			break;
		case KeyEvent.VK_D:
			p1.right = true;
			break;
		case KeyEvent.VK_UP:
			p2.up = true;
			break;
		case KeyEvent.VK_DOWN:
			p2.down = true;
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("p2.left");
			p2.left = true;
			break;
		case KeyEvent.VK_RIGHT:
			p2.right = true;
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent keyReleased) {
		System.out.println("keyReleased");
		switch (keyReleased.getKeyCode()) {
		case KeyEvent.VK_W:
			p1.up = false;
			break;
		case KeyEvent.VK_S:
			p1.down = false;
			break;
		case KeyEvent.VK_A:
			p1.left = false;
			break;
		case KeyEvent.VK_D:
			p1.right = false;
			break;
		case KeyEvent.VK_UP:
			p2.up = false;
			break;
		case KeyEvent.VK_DOWN:
			p2.down = false;
			break;
		case KeyEvent.VK_LEFT:
			p2.left = false;
			break;
		case KeyEvent.VK_RIGHT:
			p2.right = false;
			break;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
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
	
}
