package me.pieking1215.game.entities;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.net.sound.Sound;
import me.pieking1215.game.inventory.Inventory;

public class Drop extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    private int id;
    private boolean shouldMove;
    public static int x2;
    public static int y2;
    private int lifeTime = 0;
    int xp;
    public int fuse=1000;
    Player player;
	private boolean shouldDespawn;
    
	/**
	 * IDs:<br>
	 * 1= Small Heart<br>
	 * 2= Large Heart<br>
	 * 3= Lit Bomb<br>
	 * 4= Dropped Bomb<br>
	 * 5= Arrow<br>
	 */
	public Drop(Level level, int x, int y, int id, boolean shouldMove, boolean shouldDespawn) {
		super(level, "Drop", x, y, 1);
		this.id = id;
		this.shouldDespawn=shouldDespawn;
		this.shouldMove = shouldMove;
		fuse = 1000;
		player = Game.game.player;
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
		//System.out.println(""+lifeTime);
		if(lifeTime > 12000 && shouldDespawn) {
			level.removeEntity(this);
		}
        int xa = player.getLoc().getX() + 4;
        int ya = player.getLoc().getY() + 4;
        y2 = y;
        x2 = x;
        if ((id==2||id==1) && Player.getHp()<Player.getMaxHp()){
        	shouldMove=true;
        } else if ((id==2||id==1) && Player.getHp()>=Player.getMaxHp()){
        	shouldMove=false;
        }
        if(Math.abs(x2-xa)<20 && Math.abs(y2-ya)<20) {
        if((Math.round((double)x2)==Math.round((double)xa) || (Math.round((double)x2)==Math.round((double)xa+1) || (Math.round((double)x2)==Math.round((double)xa-1)) && (Math.round((double)y2)==Math.round((double)ya)) || (Math.round((double)y2)==Math.round((double)ya+1)) || (Math.round((double)y2)==Math.round((double)ya-1))))) {
        	pickup(id);
        } else if(shouldMove){
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

    private void pickup(int id) {
    	switch(id) {
    	case 1:
    		if(Player.getHp()<Player.getMaxHp()) {
        		if(Player.getHp()+2<Player.getMaxHp()) {
        			Player.addHp(2);
        			level.spawn(new Particle(level, x, y, "Text", 2, 252,"+2"));
        		} else {
        			level.spawn(new Particle(level, x, y, "Text", 2, 242,"+1"));
        			Player.setHp(Player.getMaxHp());
        		}
        			
    		Sound.playSound("Pop.wav", 0);
    		
    		level.removeEntity(this);
    		}
    		break;
    	case 2:
    		if(Player.getHp()<Player.getMaxHp()) {
    			if(Player.getHp()+5<Player.getMaxHp()) {
    				Player.addHp(5);
    				level.spawn(new Particle(level, x, y, "Text", 2, 252,"+5"));
    			} else {
    				level.spawn(new Particle(level, x, y, "Text", 2, 242,"+"+(Player.getMaxHp()-Player.getHp())));
    				Player.setHp(Player.getMaxHp());
    			}
    			
    		Sound.playSound("Pop.wav", 0);
    		
    		level.removeEntity(this);
    		}
    		break;
    	case 3:
    		break;
    	case 4:
    		Sound.playSound("Pop.wav", 0);
    		level.removeEntity(this);
    		Inventory.getItemByName("BOMB").addCount(1);
    		break;
    	case 5:
    		Sound.playSound("Pop.wav", 0);
    		Inventory.getItemByName("ARROW").addCount(1);
    		level.removeEntity(this);
    		break;
    	default:
    		break;
    	}
	}

	public void render(Screen screen) {
        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        int slowDown = 100;
        if((lifeTime<10000 || lifeTime%2 == 0)||!(shouldDespawn)){
        if(id == 1) {
            if (numSteps < (1 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 4 + 27 * 32, Colors.get(-1, 000, 500, 441), 0x00, 1);
            } else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 4 + 27 * 32, Colors.get(-1, 000, 511, 242), 0x00, 1);
            } else if (numSteps>=(2 * slowDown) && numSteps<(3 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 4 + 27 * 32, Colors.get(-1, 000, 522, 553), 0x00, 1);
            } else if (numSteps==(3 * slowDown)) {
            	numSteps=0;
            } else {
    			screen.render(xOffset, yOffset + 3, 4 + 27 * 32, Colors.get(-1, 555, 500, 242), 0x00, 1);
    		}
        } else if(id == 2) {
            if (numSteps < (1 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 1 + 27 * 32, Colors.get(-1, 000, 500, 441), 0x00, 1);
            } else if (numSteps >= (1 * slowDown) && numSteps < (2 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 1 + 27 * 32, Colors.get(-1, 000, 511, 242), 0x00, 1);
            } else if (numSteps>=(2 * slowDown) && numSteps<(3 * slowDown)) {
            	screen.render(xOffset, yOffset + 3, 1 + 27 * 32, Colors.get(-1, 000, 522, 553), 0x00, 1);
            } else if (numSteps==(3 * slowDown)) {
            	numSteps=0;
            } else {
    			screen.render(xOffset, yOffset + 3, 1 + 27 * 32, Colors.get(-1, 555, 500, 242), 0x00, 1);
    		}
        } else if(id==3) {
        	//System.out.println(""+fuse);
    			if(fuse%4==0 && fuse != 0) {
    				screen.render(xOffset, yOffset + 3, 5 + 27 * 32, Colors.get(-1, 111, 222, 555), 0x00, 1);
    			} else if(fuse==0) {
    				level.removeEntity(this);
    				level.spawn(new Particle(level, x-4, y-4, "ExplodeSmall", 1, 530,null));
    				Random rand = Rand.getRand();
    	        	int hitR = rand.nextInt((2 - 1) + 1) + 1;
    	        	for (Entity e : level.getEntities()) {
    	        		if(e instanceof Player || e instanceof PlayerMP){
    	                	Player pl = (Player) e;
    	                	if(Math.abs(x-pl.x)<=24 && Math.abs(y-pl.y)<=24) {
    	                	Player.damage(7);
    	                	}
    	                } else if(e instanceof BasicEnemy){
    	                	BasicEnemy en = (BasicEnemy) e;
    	                	if(Math.abs(x-en.x)<=24 && Math.abs(y-en.y)<=24) {
    	                	en.damage(4);
    	                	}
    	                }
    	            }
    	        	if(hitR==1) Sound.playSound("ExplodeSmall1.wav", 0);
    	    		if(hitR==2) Sound.playSound("ExplodeSmall2.wav", 0);
    			} else {
    				screen.render(xOffset, yOffset + 3, 5 + 27 * 32, Colors.get(-1, 000, 444, 555), 0x00, 1);
    			}
    		fuse--;
        } else if(id==4) {
    				screen.render(xOffset, yOffset + 3, 5 + 27 * 32, Colors.get(-1, 000, 333, 111), 0x00, 1);
    	} else if(id==5) {
    		screen.render(xOffset, yOffset + 3, 6 + 27 * 32, Colors.get(-1, 320, 333, 555), 0x00, 1);
    	}
        numSteps++;
    }
    	lifeTime++;
	}
}
