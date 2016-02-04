package me.pieking1215.game.gfx;

import me.pieking1215.game.Game;
import me.pieking1215.game.entities.Player;

public class Screen {
	 
    public static final int MAP_WIDTH = 64;
    public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

    public static byte BIT_MIRROR_X = 0x01;
    public static byte BIT_MIRROR_Y = 0x02;
    
    public int[] pixels;
    // public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH]; move to level class
    // (next episode?)

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;
    

    public SpriteSheet sheet;
    public void setOffset(int xOffset, int yOffset) { 
    	this.xOffset = xOffset; 
    	this.yOffset = yOffset; 
    }
    
    public Screen(int width, int height, SpriteSheet sheet) {
            this.width = width;
            this.height = height;
            this.sheet = sheet;

            pixels = new int[width * height];

    }
    
    public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
            xPos -= xOffset;
            yPos -= yOffset;

            boolean mirrorX = ( mirrorDir & BIT_MIRROR_X) > 0;
            boolean mirrorY = ( mirrorDir & BIT_MIRROR_Y) > 0;
            
            int scaleMap = scale-1;
            int xTile = tile % 32;
            int yTile = tile / 32;
            int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
            for (int y = 0; y < 8; y++) {
                    int ySheet = y;
                    if(mirrorY) ySheet = 7-y;
                    int yPixel = y + yPos +(y*scaleMap) - ((scaleMap << 3)/2);
                    for (int x = 0; x < 8; x++) {
                            int xSheet = x;
                            if(mirrorX) xSheet = 7-x;
                            int xPixel = x+ xPos + (x*scaleMap) - ((scaleMap<<3)/2);
                            int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
                            if (col != 0) {
                            	for(int yScale = 0; yScale < scale; yScale ++) {
                            		if (yPixel + yScale < 0 ||yPixel + yScale >= height)
                                        continue;
                            		for(int xScale = 0;xScale < scale; xScale++){
                                if (xPixel + xScale < 0 || xPixel + xScale >= width)
                                        	continue;  
                                        pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
                                		}
                            		}
                            }
                    }
            }
    }
    
    public void renderAll(int xPos, int yPos, int width, int height, int xTile, int yTile, int color, int mirrorDir, int scale) {
    	
    	for(int x=xTile; x<xTile+width;x++){
    		for(int y=yTile; y<yTile+height;y++){
    			render(xPos+ ((x-xTile)*(8*scale)), yPos + ((y-yTile)*(8*scale)), x + y * 32, color, mirrorDir, scale);
    		}
    	}
    }
    
    public void renderOver(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
        xPos -= xOffset;
        yPos -= yOffset;

        boolean mirrorX = ( mirrorDir & BIT_MIRROR_X) > 0;
        boolean mirrorY = ( mirrorDir & BIT_MIRROR_Y) > 0;
        
        int scaleMap = scale-1;
        int xTile = tile % 32;
        int yTile = tile / 32;
        int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
        for (int y = 0; y < 8; y++) {
                int ySheet = y;
                if(mirrorY) ySheet = 7-y;
                int yPixel = y + yPos +(y*scaleMap) - ((scaleMap << 3)/2);
                for (int x = 0; x < 8; x++) {
                        int xSheet = x;
                        if(mirrorX) xSheet = 7-x;
                        int xPixel = x+ xPos + (x*scaleMap) - ((scaleMap<<3)/2);
                        int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
                        if ((0xFF & (col >> 24))==255) {
                        	for(int yScale = 0; yScale < scale; yScale ++) {
                        		if (yPixel + yScale < 0 ||yPixel + yScale >= height)
                                    continue;
                        		for(int xScale = 0;xScale < scale; xScale++){
                            if (xPixel + xScale < 0 || xPixel + xScale >= width)
                                    	continue;
                                    pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
                            	}
                        	}
                       }
                }
        }
}
    public void fillRect(int xPos, int yPos, int width, int height, int color) {
		if (xPos > this.width)
			xPos = this.width;
		if (yPos > this.height)
			yPos = this.height;
		if (xPos + width > this.width)
			width = this.width - xPos;
		if (yPos + height > this.height)
			height = this.height - yPos;
		for (int y = yPos; y < yPos + height; y++) {
			for (int x = xPos; x < xPos + width; x++) {
				pixels[x + y * this.width] = color;
			}
		}
	}
    
    public void drawString(int time, String str, int color){
    	for(int ti=0;ti>time;ti++){
    	Font.render(str, Game.screen, Player.getLocation().getX(), Player.getLocation().getY(), color, 1, 0);
    	}
    }
}
    