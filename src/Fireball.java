import java.awt.Graphics;
import java.awt.Rectangle;

public class Fireball extends gameObject {
	private boolean left;

	/**
	 * constructor for both fireballs
	 * @param initx
	 * @param inity
	 * @param screenWidth
	 * @param screenHeight
	 * @param imgName
	 */
	public Fireball(float initx, float inity, int screenWidth, int screenHeight, String imgName) {
		super(initx, inity, screenWidth, screenHeight, imgName);
	}

	/* (non-Javadoc)
	 * @see gameObject#paint(java.awt.Graphics)
	 * draws the fireball
	 */
	public void paint(Graphics g) {
		g.drawImage(getObjImage(), (int) x, (int) y, getObjImage().getWidth(), getObjImage().getHeight(), null);
	}

	/**
	 * updates position of fireball
	 */
	public void update() {
		x += (int) getVelX();
		checkLeft();
		checkRight();
	}

	/**
	 * @param x
	 * @param y
	 * @param dir
	 * fires the fireball
	 */
	public void fire(int x, int y, boolean dir) {
		this.x = x;
		this.y = y;
		setLeft(dir);
		if (isLeft()) {
			velX = -15;
		} else {
			velX = 15;
		}
	}

	/**
	 * checks for collisions with walls
	 */
	public void checkRight() {
		if (x > getScreenWidth()) {
			reset();
		}
	}

	public void checkLeft() {
		if (x < 0) {
			reset();
		}
	}

	/**
	 * resets fireball
	 */
	public void reset() {
		x = 1000;
		y = 1000;
		velX = 0;
		velY = 0;
	}

	/**
	 * gets and sets direction of fireball
	 */
	boolean isLeft() {
		return left;
	}

	void setLeft(boolean left) {
		this.left = left;
	}
}
