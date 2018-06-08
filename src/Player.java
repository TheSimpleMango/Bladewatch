import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends gameObject {
	private final float PLAYERSPEED = 15;
	private boolean jumping = true;
	private final float GRAVITY = 1f;
	private float velX;
	private float velY;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean facesLeft;
	private final int XTILE_SIZE = 32;
	private final int YTILE_SIZE = 32;
	private BufferedImage viewImage;
	private BufferedImage healthImage;
	private BufferedImage viewHealthImage;
	private int health;
	private Game g;

	/**
	 * constructor for both player objects
	 * @param x
	 * @param y
	 * @param screenWidth
	 * @param screenHeight
	 * @param imageName
	 * @param facesLeft
	 * @param g
	 * @param healthImage
	 */
	public Player(float x, float y, int screenWidth, int screenHeight, String imageName, boolean facesLeft, Game g,
			String healthImage) {
		super(x, y, screenWidth, screenHeight, imageName);

		setHealth(5);

		this.setFacesLeft(facesLeft);

		this.setG(g);
		standImage();

		this.width = getViewImage().getWidth();
		this.height = getViewImage().getHeight();

		setVelX(0);
		setVelY(0);
		try {
			this.setHealthImage(ImageIO.read(this.getClass().getResourceAsStream(healthImage)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setViewHealthImage(this.getHealthImage());
	}

	/* (non-Javadoc)
	 * @see gameObject#paint(java.awt.Graphics)
	 * paints the player object and the health bar
	 */
	public void paint(Graphics g) {
		if (isFacesLeft()) {
			g.drawImage(getViewImage(), (int) x + getViewImage().getWidth(), (int) y, -getViewImage().getWidth(),
					getViewImage().getHeight(), null);
		} else {
			g.drawImage(getViewImage(), (int) x, (int) y, getViewImage().getWidth(), getViewImage().getHeight(), null);
		}
		if (getHealth() != 0) {
			g.drawImage(getViewHealthImage(), (int) x - 15, (int) y - 10, getViewHealthImage().getWidth(),
					getViewHealthImage().getHeight(), null);
		}
	}

	/* (non-Javadoc)
	 * @see gameObject#update(java.util.ArrayList)
	 * updates player location and velocity
	 */
	public void update(ArrayList<gameObject> object) {
		if (jumping) {
			setVelY(getVelY() + getGRAVITY());
			if (getVelY() > getPLAYERSPEED()) {
				setVelY(getPLAYERSPEED());
			}
			if (getVelY() < -1 * getPLAYERSPEED()) {
				setVelY(-1 * getPLAYERSPEED());
			}
		}
		x += (int) getVelX();
		y += (int) getVelY();
	}

	/**
	 * @param x
	 * @param y
	 * resets player when game resets
	 */
	public void reset(int x, int y) {
		setHealth(5);

		standImage();

		this.width = getViewImage().getWidth();
		this.height = getViewImage().getHeight();

		setVelX(0);
		setVelY(5);

		this.x = x;
		this.y = y;

		setViewHealthImage(this.getHealthImage());
	}

	/**
	 * collision checkers
	 */
	public void checkTop(gameObject temp) {
		// if the player's top is hitting the object
		if (getBoundsTop().intersects(temp.getBounds())) {
			y = temp.getY() + height;
			setVelY(0);
		}
	}

	public void checkBottom(gameObject temp) {
		// if bottom is hitting the object
		if ((getBoundsBot().intersects(temp.getBounds()) && !(getBoundsRight().intersects(temp.getBounds()))
				&& !(getBoundsLeft().intersects(temp.getBounds()))) || (y + height) > getScreenHeight()) {
			y = temp.getY() - height;
			setVelY(0);
			setJumping(false);
		}
	}

	public void checkRight(gameObject temp) {
		// if right side is hitting the object
		if (getBoundsRight().intersects(temp.getBounds())) {
			x = temp.getX() - width;
			setVelX(0);
		}
	}

	public void checkLeft(gameObject temp) {
		// if left side is hitting the object
		if (getBoundsLeft().intersects(temp.getBounds())) {
			x = temp.getX() + width;
			setVelX(0);
		}
	}

	public void checkFire(Fireball temp) {
		System.out.println("checkFire");
		System.out.println(temp.x + "; " + temp.y);
		if (contains(temp.x, temp.y)) {
			System.out.println("checkintersect");
			decreaseHealth();
			temp.reset();
		}
	}

	public boolean contains(float x1, float y1) {
		y1 += 30;
		if (x1 >= x && x1 <= x + width) {
			if (y1 >= y && y1 <= (y + height)) {
				return true;
			}
		}
		return false;
	}

	// collisions. divided into 4 parts: left/right/above/below collision.
	// left/right collisions checked w/ rectangle along respective side and 1/4
	// total length
	// top/bottom collisions checked w/ rectangle half of total length centered
	// along
	// middle from respective side to half height

	// collision checker bottom half
	public Rectangle getBoundsBot() {
		return new Rectangle((int) (x + width / 4), (int) y + (height + 1) / 2, (width + 1) / 2, (height + 1) / 2);
	}

	// collision checker upper half
	public Rectangle getBoundsTop() {
		return new Rectangle((int) (x + width / 4), (int) y, (width + 1) / 2, (height + 1) / 2);
	}

	// collision checker left side
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + height / 10, width / 4, height * 8 / 10);
	}

	// collision checker right side
	public Rectangle getBoundsRight() {
		return new Rectangle((int) (x + width * 3 / 4), (int) y + height / 10, width / 4, height * 8 / 10);
	}

	/**
	 * switches to sword sprite
	 */
	public void swordSprite() {
		setViewImage(getObjImage().getSubimage(81, 0, 95, 78));
		this.width = getViewImage().getWidth();
		this.height = getViewImage().getHeight();
	}

	/**
	 * @param temp
	 * @return
	 * checks for containment
	 */
	public boolean contains(gameObject temp) {
		// if the player's top is hitting the object
		if (getBoundsTop().intersects(temp.getBounds())) {
			return true;
			// System.out.println("hit top");
		}

		// if bottom is hitting the object
		if (getBoundsBot().intersects(temp.getBounds()) && !(getBoundsRight().intersects(temp.getBounds()))
				&& !(getBoundsLeft().intersects(temp.getBounds()))) {
			return true;
			// System.out.println("hit bot");
		}

		// if right side is hitting the object
		if (getBoundsRight().intersects(temp.getBounds())) {
			return true;
			// s.out.println(temp + " hit right");
		}

		// if left side is hitting the object
		if (getBoundsLeft().intersects(temp.getBounds())) {
			return true;
			// System.out.println(temp + " hit left");
		}

		return false;
	}

	/**
	 * decreases health and healthbar image
	 */
	public void decreaseHealth() {
		setHealth(getHealth() - 1);
		if (getHealth() > 0) {
			setViewHealthImage(getHealthImage().getSubimage(0, 0, (getHealthImage().getWidth() / 5) * getHealth(),
					getHealthImage().getHeight()));
		} else {
			getG().endGame(this);
		}
	}

	/**
	 * changes to standing sprite
	 */
	public void standImage() {
		this.setViewImage(getObjImage().getSubimage(0, 0, 58, 78));
		this.width = getViewImage().getWidth();
		this.height = getViewImage().getHeight();
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	float getPLAYERSPEED() {
		return PLAYERSPEED;
	}

	float getGRAVITY() {
		return GRAVITY;
	}

	boolean isUp() {
		return up;
	}

	void setUp(boolean up) {
		this.up = up;
	}

	boolean isDown() {
		return down;
	}

	void setDown(boolean down) {
		this.down = down;
	}

	boolean isLeft() {
		return left;
	}

	void setLeft(boolean left) {
		this.left = left;
	}

	boolean isRight() {
		return right;
	}

	void setRight(boolean right) {
		this.right = right;
	}

	boolean isFacesLeft() {
		return facesLeft;
	}

	void setFacesLeft(boolean facesLeft) {
		this.facesLeft = facesLeft;
	}

	int getXTILE_SIZE() {
		return XTILE_SIZE;
	}

	int getYTILE_SIZE() {
		return YTILE_SIZE;
	}

	BufferedImage getViewImage() {
		return viewImage;
	}

	void setViewImage(BufferedImage viewImage) {
		this.viewImage = viewImage;
	}

	BufferedImage getHealthImage() {
		return healthImage;
	}

	void setHealthImage(BufferedImage healthImage) {
		this.healthImage = healthImage;
	}

	BufferedImage getViewHealthImage() {
		return viewHealthImage;
	}

	void setViewHealthImage(BufferedImage viewHealthImage) {
		this.viewHealthImage = viewHealthImage;
	}

	int getHealth() {
		return health;
	}

	void setHealth(int health) {
		this.health = health;
	}

	Game getG() {
		return g;
	}

	void setG(Game g) {
		this.g = g;
	}
}
