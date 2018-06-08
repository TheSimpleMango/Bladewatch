import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class gameObject {
	protected Rectangle objBounds;

	protected String objType;

	public float x, y, velX, velY;
	public int width, height;
	private int screenWidth;

	private int screenHeight;

	private BufferedImage objImage;

	/**
	 * first constructor for those with images
	 * @param initx
	 * @param inity
	 * @param screenWidth
	 * @param screenHeight
	 * @param imgName
	 */
	public gameObject(float initx, float inity, int screenWidth, int screenHeight, String imgName) {
		try {
			setObjImage(ImageIO.read(this.getClass().getResourceAsStream(imgName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = initx;
		this.y = inity;
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);

		objBounds = new Rectangle((int) x, (int) y, width, height);

		this.setVelX(0);
		this.setVelY(0);
	}

	/**
	 * second constructor
	 * @param initx
	 * @param inity
	 * @param width
	 * @param height
	 * @param screenWidth
	 * @param screenHeight
	 */
	public gameObject(float initx, float inity, int width, int height, int screenWidth, int screenHeight) {
		this.x = initx;
		this.y = inity;
		this.width = width;
		this.height = height;
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);
	}

	// collision checkers
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void paint(Graphics g) {
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public void update(ArrayList<gameObject> object) {
		// TODO Auto-generated method stub

	}

	int getScreenWidth() {
		return screenWidth;
	}

	void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	int getScreenHeight() {
		return screenHeight;
	}

	void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	BufferedImage getObjImage() {
		return objImage;
	}

	void setObjImage(BufferedImage objImage) {
		this.objImage = objImage;
	}
}