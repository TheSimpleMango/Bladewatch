import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends gameObject {
	final float PLAYERSPEED = 15;
	private boolean falling = true;
	private boolean jumping = true;
	final float GRAVITY = 1f;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean facesLeft;
	final int XTILE_SIZE = 32;
	final int YTILE_SIZE = 32;
	BufferedImage viewImage;
	BufferedImage healthImage;
	int health = 5;
	Game g;

	private Handler handler;

	public Player(float x, float y, int screenWidth, int screenHeight, String imageName, Handler handler,
			boolean facesLeft, Game g, String healthImage) {
		super(x, y, screenWidth, screenHeight, imageName);
		this.width = objImage.getWidth();
		this.height = objImage.getHeight();

		this.handler = handler;
		this.facesLeft = facesLeft;
		
		this.g = g;

		objType = "Player";
		viewImage = objImage.getSubimage(0, 147, 189, 146);

		velX = 0;
		velY = 0;
		try {
			this.healthImage = ImageIO.read(this.getClass().getResourceAsStream(healthImage));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(ArrayList<gameObject> object) {
		x += (int) velX;
		y += (int) velY;

		// System.out.println("velX = " + velX);
		// System.out.println("velY = " + velY);
		// System.out.println("new location: " + x + ", " + y);

		if (jumping || falling) {
			falling = true;
			velY += GRAVITY;
			if (velY > PLAYERSPEED) {
				velY = PLAYERSPEED;
			}
			if (velY < -1 * PLAYERSPEED) {
				velY = -1 * PLAYERSPEED;
			}
		}

		// hard check to not sink below bottom, can replace after collisions
		// fixed
		/*
		 * if (y > screenHeight - height - height / 2) { y = screenHeight -
		 * height - height / 2; velY = 0; System.out.println("oof"); jumping =
		 * false; falling = false; up = false; }
		 */
		// commented out b/c buggy
		checkCollisions(handler.object);
	}

	public void checkCollisions(ArrayList<gameObject> object) {
		// if not colliding with a floor, then it must be falling
		boolean noFloor = true;
		// for each game object
		for (int i = 0; i < handler.object.size(); i++) {
			// assign temporary variable to store the object
			gameObject temp = handler.object.get(i);
			// if the object isn't the player itself
			if (!objType.equals(temp.objType)) {
				// if the player's top is hitting the object
				if (getBoundsTop().intersects(temp.getBounds())) {
					y = temp.getY() + height;
					velY = 0;
					// System.out.println("hit top");
				}

				// if bottom is hitting the object
				if (getBoundsBot().intersects(temp.getBounds()) && !(getBoundsRight().intersects(temp.getBounds()))
						&& !(getBoundsLeft().intersects(temp.getBounds()))) {
					y = temp.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
					noFloor = false;
					// System.out.println("hit bot");
				}

				// if right side is hitting the object
				if (getBoundsRight().intersects(temp.getBounds())) {
					x = temp.getX() - width;
					velX = 0;
					// s.out.println(temp + " hit right");
				}

				// if left side is hitting the object
				if (getBoundsLeft().intersects(temp.getBounds())) {
					x = temp.getX() + temp.width;
					velX = 0;
					// System.out.println(temp + " hit left");
				}
				if (noFloor) {
					falling = true;
				}
			}
		}
	}

	public boolean onFloor(ArrayList<gameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {

			gameObject temp = handler.object.get(i);

			if (!objType.equals(temp.objType)) {
				if (getBoundsBot().intersects(temp.getBounds())) {
					return true;
				}
			}
		}
		return false;
	}

	public void setJumping(boolean bool) {
		jumping = bool;
	}

	public boolean isJumping() {
		return jumping;
	}

	public boolean isFalling() {
		return falling;
	}

	public void paint(Graphics g) {
		g.drawImage(viewImage, (int) x, (int) y, width, height, null);
		g.drawImage(healthImage, (int) x-15, (int) y-20, 40, 10, null);
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

	public void swordSprite() {
		viewImage = objImage.getSubimage(3 * XTILE_SIZE, 3 * YTILE_SIZE, XTILE_SIZE, YTILE_SIZE);
	}

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
	
	public void decreaseHealth(){
		health--;
		healthImage = healthImage.getSubimage(0, 0, healthImage.getWidth()-20, healthImage.getHeight());
		
		if (health == 0) {
			g.endGame(this);
		}
	}
	
	public void standImage(){
		
	}
}
