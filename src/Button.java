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

	/**constructs Button
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param img1
	 * @param img2
	 */
	public Button(int x, int y, int width, int height, BufferedImage img1, BufferedImage img2) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;

		setup();
		
		setDefaultButton(img1);
		hoverButton = img2;
		drawButton = getDefaultButton();
	}

	/**
	 * sets up the button
	 */
	public void setup() {
		super.setSize(width, height);
		super.setLocation(x, y);
	}

	/**
	 * @param x1
	 * @param y1
	 * @return
	 * checks for button contains mouse
	 */
	public boolean contains(double x1, double y1) {
		y1 -= 20;
		if (x1 >= x && x1 <= x + width) {
			if (y1 >= y && y1 <= (y + height)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param g
	 * draws button
	 */
	public void draw(Graphics g) {
		g.drawImage(drawButton, x, y, width, height, null);
	}

	/**
	 * hover button image
	 */
	public void hoverButton() {
		drawButton = hoverButton;
	}

	/**
	 * default button image
	 */
	public void defaultButton() {
		drawButton = getDefaultButton();
	}

	/**
	 * @param a
	 * adds an action listener
	 */
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