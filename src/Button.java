import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton {

	private int x;
	private int y;
	private int width;
	private int height;

	private BufferedImage defaultButton;
	private BufferedImage hoverButton;
	private BufferedImage drawButton;

	Button(int x, int y, int width, int height, BufferedImage img1, BufferedImage img2) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;

		setup();
		
		setDefaultButton(img1);
		hoverButton = img2;
		drawButton = getDefaultButton();
	}

	public void setup() {
		super.setSize(width, height);
		super.setLocation(x, y);
	}

	public boolean contains(double x1, double y1) {
		y1 -= 20;
		if (x1 >= x && x1 <= x + width) {
			if (y1 >= y && y1 <= (y + height)) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		g.drawImage(drawButton, x, y, width, height, null);
	}

	public void hoverButton() {
		drawButton = hoverButton;
	}

	public void defaultButton() {
		drawButton = getDefaultButton();
	}

	public void addAL(ActionListener a) {
		this.addActionListener(a);
	}

	public BufferedImage getDefaultButton() {
		return defaultButton;
	}

	public void setDefaultButton(BufferedImage defaultButton) {
		this.defaultButton = defaultButton;
	}

}
