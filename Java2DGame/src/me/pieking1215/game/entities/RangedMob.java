package me.pieking1215.game.entities;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;
import me.pieking1215.game.net.sound.Sound;

public class RangedMob extends Mob{
    private int scale = 1;
    protected boolean isSwimming = false;
    private int tickCount = 0;
    private int hp;
    public int xOffset;
    public int yOffset;
    private int speed2 = 0;
    private int color = Colors.get(-1, 111, 333, 555);
    public XpOrb xpOrb2;
    Game game=new Game();
    //Player player = new Player(null, 0, 0, null, "");
	private int viewDist;
	private int shootRange;
	private boolean shouldDrop;
	public int id=0;
    
	public RangedMob(Level level, int x, int y, int damage, int hp, int viewDist, int shootRange, boolean shouldDrop, int id) {
		super(level, "RangedMob", x, y, 1);
		this.hp = hp;
    	xpOrb2 = new XpOrb(level,150,120,10);
    	this.viewDist = viewDist;
    	this.shootRange=shootRange;
    	this.shouldDrop = shouldDrop;
    	this.id=id;
    	if(id==1){
    		Random rand=Rand.getRand();
    		int rc1 = rand.nextInt(3);
    		int rc2 = rand.nextInt(3);
    		int rc3 = rand.nextInt(3);
    		//System.out.println(rc1+" "+rc2+" "+rc3);
        	color = Colors.get(-1, 111, (rc1*100)+(rc2*10)+(rc3), 232);
        }else if(id==2){
        	color = Colors.get(-1, 111, 520, 555);
        }
	}
	
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for(Entity e: level.getEntities()) {
        	int xE = e.x;
        	int yE = e.y;
        	if(Math.abs(x-xE)<9 && Math.abs(y-yE)<9) {
        		
        	}
        }
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
		int xa = 0;
        int ya = 0;
        for(PlayerMP player: Game.level.getOnlinePlayers()){ //TODO: Work On This
        int xa2 = player.getLoc().getX();
        int ya2 = player.getLoc().getY();
        if(Math.abs(x-xa2)<viewDist && Math.abs(y-ya2)<viewDist) {
        	if(Math.abs(x-xa2)<shootRange && Math.abs(y-ya2)<shootRange){
        		if(tickCount%60==0){
        			if(x==xa2){
        				if(y<ya2){
        					level.addEntity(new Projectile(level, x, y, 0, 1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}else{
        					level.addEntity(new Projectile(level, x, y, 0, -1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}
        			}else if(y==ya2){
        				if(x<xa2){
        					level.addEntity(new Projectile(level, x, y, 1, 0, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}else{
        					level.addEntity(new Projectile(level, x, y, -1, 0, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}
        			}else if(x<xa2){
        				if(y<ya2){
        					level.addEntity(new Projectile(level, x, y, 1, 1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}else{
        					level.addEntity(new Projectile(level, x, y, 1, -1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}
        			}else{
        				if(y<ya2){
        					level.addEntity(new Projectile(level, x, y, -1, 1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}else{
        					level.addEntity(new Projectile(level, x, y, -1, -1, 12, 27, Colors.get(-1, 333, 444, 555)));
        				}
        			}
        		}
        	}else{
        	
        		if((speed2 == 2&&id==0)||(speed2 == 4&&id==1)||(speed2 == 4&&id==2)){
        			speed2=0;
        			if(x>xa2){
        				xa--;
        			} 
        			if(x<xa2){
        				xa++;
        			} 
        			if(y>ya2){
        				ya--;
        			} 
        			if(y<ya2){
        				ya++;
        			}
        		}else {
        			speed2++;
        		}
        
        	}
        }
        	if(Player.spacePress) {
        		if(Math.abs(x-xa2)<12 && Math.abs(y-ya2)<12) { 
        			damage(1);
        		}
        	}
        }
        if ((xa != 0 || ya != 0) && speed2 == 0) {
            move(xa, ya);
            isMoving = true;
        } else if(speed2 == 0){
            isMoving = false;
        }
        if (level.getTile(this.x >> 3, this.y >> 3).getId() == 6) {
            isSwimming = true;
        }
        if (isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 6) {
            isSwimming = false;
        }
        
        
        
        if(hp <= 0) {
        	level.spawn(new Particle(level, x, y, "Poof",2,511,null));
            Random rand = Rand.getRand();
            int randomX = rand.nextInt((10 - -10) + 1) + -10;
            int randomY = rand.nextInt((10 - -10) + 1) + -10;
            int randomXp = rand.nextInt((4 - 1) + 1) + 1;
            int randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
        	randomX = rand.nextInt((10 - -10) + 1) + -10;
            randomY = rand.nextInt((10 - -10) + 1) + -10;
            randomXp = rand.nextInt((4 - 1) + 1) + 1;
            randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
            randomX = rand.nextInt((10 - -10) + 1) + -10;
            randomY = rand.nextInt((10 - -10) + 1) + -10;
            randomXp = rand.nextInt((4 - 1) + 1) + 1;
            randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
            if(shouldDrop) {
            int randomDrop = rand.nextInt((100 - 1) + 1) + 1;
            if(randomDrop >= 80) {
            	randomX = rand.nextInt((10 - -10) + 1) + -10;
                randomY = rand.nextInt((10 - -10) + 1) + -10;
                level.spawn(new Drop(level,x+randomX,y+randomY,2,true,true));
                //System.out.println("Drop 2");
            } else if(randomDrop <= 30) {
            	randomX = rand.nextInt((10 - -10) + 1) + -10;
                randomY = rand.nextInt((10 - -10) + 1) + -10;
                level.spawn(new Drop(level,x+randomX,y+randomY,1,true,true));
                //System.out.println("Drop 1");
            } else if(randomDrop >= 40 && randomDrop <50) {
            	randomX = rand.nextInt((10 - -10) + 1) + -10;
                randomY = rand.nextInt((10 - -10) + 1) + -10;
                level.spawn(new Drop(level,x+randomX,y+randomY,4,true,true));
            }
            //System.out.println(""+randomDrop);
            }
            Sound.playSound("Kill.wav", 0);
        	level.removeEntity(this);
        }
        
        tickCount++;
        if(Tile.getById(Level.tiles[((int) Math.floor(((x-4) +  (double)8 / 2) / 8))+((int) Math.floor(((y-8) +  (double)8 / 2) / 8)) *Level.width]).isSolid()){
        	x++;
        }
	}
	
	public void damage(int damage) {
		hp = hp-damage;
		Random rand = Rand.getRand();
        int randomX = rand.nextInt((5 - -5) + 1) + -5;
        int randomY = rand.nextInt((5 - -5) + 1) + -5;
        //System.out.println(""+randomX+" "+randomY);
        level.spawn(new Particle(level, x, y, "Text",1,522,"-"+damage));
        level.spawn(new Particle(level, x+randomX, y+randomY-3, "Poof",1,533,null));
        int hitR = rand.nextInt((2 - 1) + 1) + 1;
		if(hitR==1) Sound.playSound("Hit1.wav", 0);
		if(hitR==2) Sound.playSound("Hit4.wav", 0);
	}
	
        public void render(Screen screen) {
            int xTile = 20;
            int yTile = 21-(id*2);
            
            int walkingSpeed = 3;
            int flipTop = (numSteps >> walkingSpeed) & 1;
            int flipBottom = (numSteps >> walkingSpeed) & 1;
            if (movingDir == 1) {
                xTile += 2;
            } else if (movingDir > 1) {
                xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
                flipTop = (movingDir - 1) % 2;
            }

            if(id==2&&movingDir==3){
            	flipBottom=0x00;
            }else if(id==2&&movingDir==2){
            	flipBottom=0x01;
            }
            
            int modifier = 8 * scale;
            xOffset = x - modifier / 2;
            yOffset = y - modifier / 2 - 4;
            //System.out.println("" + xOffset + " " + yOffset);
            if (isSwimming) {
                int waterColor = 0;
                yOffset += 4;
                if (tickCount % 60 < 15) {
                    waterColor = Colors.get(-1, -1, 225, -1);
                } else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
                    yOffset -= 1;
                    waterColor = Colors.get(-1, 225, 115, -1);
                } else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
                    waterColor = Colors.get(-1, 115, -1, 225);
                } else {
                    yOffset -= 1;
                    waterColor = Colors.get(-1, 225, 115, -1);
                }
                screen.render(xOffset, yOffset + 3, 0 + 27 * 32, waterColor, 0x00, 1);
                screen.render(xOffset + 8, yOffset + 3, 0 + 27 * 32, waterColor, 0x01, 1);
            }
            if(!isMoving && movingDir > 1) {
            	xTile=24;
            screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
            screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
            if (!isSwimming) {
                screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                        flipBottom, scale);
                screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                        * 32, color, flipBottom, scale);
            }
            } else if(!isMoving && movingDir == 0) {
            	xTile=20;
                screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
                screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
                if (!isSwimming) {
                    screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                            flipBottom, scale);
                    screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                            * 32, color, flipBottom, scale);
                }
            } else if(!isMoving && movingDir == 1) {
            	xTile=22;
                screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
                screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
                if (!isSwimming) {
                    screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                            flipBottom, scale);
                    screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                            * 32, color, flipBottom, scale);
                }
            } else if(isMoving) {
                screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
                screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
                if (!isSwimming) {
                    screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                            flipBottom, scale);
                    screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                            * 32, color, flipBottom, scale);
                }
            }
        }

}
