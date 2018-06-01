import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player{
	final int PLAYERSPEED = 18;
	int x;
	int y;
	int width;
	int height;
	int screenWidth;
	int screenHeight;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	String imageName;
	
	BufferedImage playerImage;
	public Player(){}
	public Player(int x, int y, int width, int height, int screenWidth, int screenHeight, String imageName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.imageName = imageName;
		
		try {
			playerImage = ImageIO.read(this.getClass().getResourceAsStream(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void update(){
		if (up && y > 0) {
			y-=PLAYERSPEED;
		}
		else if (down && y < screenHeight) {
			y+=PLAYERSPEED;
		}
		else if (left && x > 0) {
			x-=PLAYERSPEED;
		}
		else if (right && x < screenWidth) {
			x+=PLAYERSPEED;
		}
	}
	public void paint(Graphics g){
		g.drawImage(playerImage, x, y, width, height, null);
	}
}