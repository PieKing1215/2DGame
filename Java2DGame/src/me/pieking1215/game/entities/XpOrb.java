package me.pieking1215.game.entities;

import me.pieking1215.game.Game;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.net.sound.Sound;

public class XpOrb extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    private int value;
    public static int x2;
    public static int y2;
    int xp;
    Player player = Game.game.player;
    
	public XpOrb(Level level, int x, int y, int value) {
		super(level, "XpOrb", x, y, 1);
		this.value = value;
	}
	
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }

	public void tick() {
        int xa = player.getLoc().getX() + 4;
        int ya = player.getLoc().getY() + 4;
        //System.out.println(""+player.xp);
        //System.out.println(""+Math.abs(x-xa)+ " " + Math.abs(y-ya));
        y2 = y;
        x2 = x;
        if(Math.abs(x2-xa)<20 && Math.abs(y2-ya)<20) {
        if((Math.round((double)x2)==Math.round((double)xa) || (Math.round((double)x2)==Math.round((double)xa+1) || (Math.round((double)x2)==Math.round((double)xa-1)) && (Math.round((double)y2)==Math.round((double)ya)) || (Math.round((double)y2)==Math.round((double)ya+1)) || (Math.round((double)y2)==Math.round((double)ya-1))))) {
        	player.addXp(value);
        	if(value<10) {
        	Sound.playSound("Xp1.wav", 0);
        	} else if(value>=10) {
        	Sound.playSound("XpBig1.wav", 0);
        	}
        	level.removeEntity(this);
        } else {
        	 if(x2>xa){
             	x--;
             } 
        	 if(x2<xa){
             	x++;
             } 
             if(y2>ya){
             	y--;
             } 
             if(y2<ya){
             	y++;
             }
        }
		}
	}

    public void render(Screen screen) {
        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        if(value >= 10) {
        	int slowDown = 25;
            if (numSteps < (1 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 31 + 29 * 32, Colors.get(-1, 242, 552, 441), 0x00, 1);
            } else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 31 + 30 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
            } else if (numSteps>=(2 * slowDown) && numSteps<(3 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 31 + 31 * 32, Colors.get(-1, 441, 242, 553), 0x00, 1);
            } else if (numSteps==(3 * slowDown)) {
            	numSteps=0;
            } else {
    			screen.render(xOffset, yOffset + 3, 30 + 29 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		}
        } else if(value < 10) {
        	 int slowDown = 50;
    		if (numSteps < (1 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 30 + 29 * 32, Colors.get(-1, 242, 552, 441), 0x00, 1);
    		} else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 30 + 30 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		} else if (numSteps == (2 * slowDown)) {
    			numSteps=0;
    		} else {
    			screen.render(xOffset, yOffset + 3, 30 + 30 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		}
        }
        numSteps++;
    }
}
