package me.pieking1215.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public String path;
	public int width;
	public int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image == null) { 
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

	}
	
	public void reload(){
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image == null) { 
			return;
		}
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}
}