import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.util.*;
public class Platform{
	int x;
	int y;
	int width;
	int height;
	protected String imageName;
	BufferedImage playerImage;
	protected Player player1;
	protected Player player2;
	public Platform(){}
	public Platform(int x, int y, int width, int height, String imageName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imageName = imageName;
		
		try {
			playerImage = ImageIO.read(this.getClass().getResourceAsStream(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void paint(Graphics g){
		g.drawImage(playerImage, x, y, width, height, null);
	}
}