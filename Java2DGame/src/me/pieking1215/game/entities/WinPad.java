package me.pieking1215.game.entities;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.net.sound.Sound;

public class WinPad extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    public static int x2;
    public static int y2;
    int xp;
    Player player = Game.game.player;
	private boolean isEnd;
	int animCount = 0;
	public String toLevel;
	public boolean resetToLevel=false;
    
	public WinPad(Level level, int x, int y, boolean isEnd, String toLevel) {
		super(level, "WinPad", x, y, 1);
		this.toLevel=toLevel;
		this.isEnd=isEnd;
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
		
		for (Entity e : level.getEntities()) {
    		if(e instanceof Player){
    			Player pl = (Player) e;
            	if(Math.abs((this.x-4)-e.x)<=4 && Math.abs((this.y-4)-e.y)<=4) {
            	
            	//System.out.println(""+isEnd);
            	
            	if(isEnd){
            	if(!(Player.win)) {
            		Sound.hardStopSounds();
            		Sound.playSound("Win.wav", 0);
            		Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Please fill out the survey at: http://adf.ly/1VM6es");
            		Player.shouldClip=false;
            	}
            	
            	Player.win=true;
            	} else {
            		if(!(level.pause)) {
            			
            			pl.canMove=false;
            			pl.x=this.x-4;
            			pl.y=this.y-6;
            			//System.out.println(""+pl.canMove);
            			if(animCount>0 && animCount<=20) {
            				pl.movingDir=0;
            			}
            			if(animCount>20 && animCount<=40) {
            				pl.movingDir=3;
            			}
            			if(animCount>40 && animCount<=55) {
            				pl.movingDir=1;
            			}
            			if(animCount>55 && animCount<=70) {
            				pl.movingDir=2;
            			}
            			if(animCount>70 && animCount<=80) {
            				pl.movingDir=0;
            			}
            			if(animCount>80 && animCount<=90) {
            				pl.movingDir=3;
            			}
            			if(animCount>90 && animCount<=95) {
            				pl.movingDir=1;
            			}
            			if(animCount>95 && animCount<=100) {
            				pl.movingDir=2;
            			}
            			if(animCount==101) {
            				animCount=0;
            				resetToLevel=true;
            				level.pause();
            				break;
            			}
            		animCount++;
            		}
            	}
            	}
            }
        }
	}

	
	
    public void render(Screen screen) {
    	int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        int slowDown = 50;
        if(isEnd){
    		if (numSteps < (1 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 242, 552, 441), 0x00, 1);
    		} else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		} else if (numSteps>=(2 * slowDown) && numSteps<(3 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 441, 242, 552), 0x00, 1);
    		} else if (numSteps==(3 * slowDown)) {
    			numSteps=0;
    		} else {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		}
        } else {
        	if (numSteps < (1 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 235, 115, 224), 0x00, 1);
    		} else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 115, 224, 235), 0x00, 1);
    		} else if (numSteps>=(2 * slowDown) && numSteps<(3 * slowDown)) {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 224, 235, 115), 0x00, 1);
    		} else if (numSteps==(3 * slowDown)) {
    			numSteps=0;
    		} else {
    			screen.render(xOffset, yOffset + 3, 31 + 28 * 32, Colors.get(-1, 552, 441, 242), 0x00, 1);
    		}
        }
        numSteps++;
        for (Entity e : level.getEntities()) {
    		if(e instanceof Player){
            	if(Math.abs((this.x-4)-e.x)<=4 && Math.abs((this.y-4)-e.y)<=4) {
            	if(!(isEnd)) {
            		if(!(level.pause)) {
            			Random rand = Rand.getRand();
                    	rand.nextInt((200 - 1) + 1);
            		}
            	}
            	}
            	//screen2.fillRect(0, 0, 1000, 1000, 143);
            }
        }
    }
}
