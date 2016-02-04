package me.pieking1215.game.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Disp extends Canvas{
	
	private static final long serialVersionUID = 1L;

	public int width;
	public int height;
	public int rwidth;
	public int rheight;
	
	public BufferedImage image;
    private int[] pixels;
    
    private static BufferStrategy bs;
    
    public Disp(int width, int height, int rwidth, int rheight){
    	this.width=width;
    	this.height=height;
    	this.rwidth=rwidth;
    	this.rheight=rheight;
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }
    
    public void init(){
		bs=this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(2);
		}
	}
    
	@Override
	public void paint(Graphics g){
		g.drawImage(image, 0, 0, rwidth, rheight, null);
	}

	public void setPixel(int x, int y, int color){
		try{
			pixels[x+y*image.getWidth()]=color;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
	
	public void fill(int x, int y, int width, int height, int color){
		for(int w=0; w<width; w++){
			for(int h=0; h<height; h++){
				setPixel(x+w, y+h, color);
			}
		}
	}
	
	public int getPixel(int x, int y){
		try{
			return pixels[x+y*image.getWidth()];
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		return 0;
	}
	
	public static int getIntFromColor(int Red, int Green, int Blue/*, int Alpha*/){
	    Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
	    Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
	    Blue = Blue & 0x000000FF; //Mask out anything not blue.
	    //Alpha = Alpha & 0xFF000000;
	    
	    
	    return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
}

