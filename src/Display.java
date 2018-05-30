import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Display extends JPanel implements MouseListener, MouseMotionListener, KeyListener, ActionListener{
	JFrame frame;
	Button startButton;
	Button quitButton;
	Button helpButton;
	
	Timer t;

	public static void main(String[] args) {
		Display d = new Display();
	}

	public Display(){
		frame = new JFrame("BladeWatch");
		frame.setSize(800,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);

		this.setLayout(null);
		this.addMouseListener(this);
		
		t = new Timer(100, this);

		BufferedImage startbd = null;
		BufferedImage startbh = null;
		try {
			startbd = ImageIO.read(Display.class.getResourceAsStream("startButtonD.jpg"));
			startbh = ImageIO.read(Display.class.getResourceAsStream("startButtonH.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		startButton = new Button(300, 300, 50, 20, startbd, startbh);
		startButton.addAL(this);
		this.add(startButton);
		
		repaint();
	}
	
	public void paint(Graphics g) {
		System.out.println("paint");
		super.paint(g);
		startButton.draw(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		System.out.println("actionPerformed");
		
		if(e.equals(startButton)) {
			System.out.println("start...");
		}
		else if(e.equals(helpButton)) {
			//display help Screen
		}
		else if(e.equals(quitButton)){
			//quit Game
		}
	}
}