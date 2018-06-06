import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.util.*;
public class Player{
	private final int PLAYERSPEED = 19;
	private final int GRAVITY_EFFECT = 15;
	int x;
	int y;
	int width;
	int height;
	int screenWidth;
	int screenHeight;
	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	protected boolean onGround;
	protected boolean hasJumped;
	protected boolean onPlatform;
	protected final int JUMP_HEIGHT=200;
	protected String imageName;
	protected ArrayList<Platform> platformList;
	BufferedImage playerImage;
	public Player(){}
	public Player(int x, int y, int width, int height, int screenWidth, int screenHeight, String imageName, ArrayList<Platform> platformList){
		onPlatform = true;
		this.platformList = platformList;
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
			y-=PLAYERSPEED*1.5;
		}
		else if (down && y < screenHeight) {
			y+=PLAYERSPEED*1.5;
		}
		else if (left && x > 0) {
			x-=PLAYERSPEED;
		}
		else if (right && x < screenWidth) {
			x+=PLAYERSPEED;
		}		
	}
	
	public void onPlatform(){
		for(int i=0;i<platformList.size();i++) {
			Platform plat = platformList.get(i);
			if((y + height <= plat.y && y + height >= plat.y - PLAYERSPEED) && (x >= plat.x && x <= plat.x+ plat.width)) {
				onPlatform = true;
				return;
			}
		}
		onPlatform = false;
	}
	

	public void paint(Graphics g){
		g.drawImage(playerImage, x, y, width, height, null);
	}
}