package me.pieking1215.game.entities;

import java.util.Random;

import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.net.sound.Sound;

public class Projectile extends Mob {

	private int xMotion;
	private int yMotion;
	private int xTile;
	private int yTile;
	private static int dir=0;
	private int col;
	private int age=0;
	
	public Projectile(Level level, int x, int y, int xMotion, int yMotion, int xTile, int yTile, int col){
		super(level, "Projectile", x, y, 1);
		this.xMotion=xMotion;
		this.yMotion=yMotion;
		this.xTile=xTile;
		this.yTile=yTile;
		this.col=col;
		this.x=x;
		this.y=y;
		this.canMove=true;
	}
	
	public boolean hasCollided(int xa, int ya) {
        /*int xMin = 0;
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
        }*/
        return false;
    }

	public void tick() {
		
		move(xMotion, yMotion);
		
		if(Math.abs(x-Player.getLocation().getX())<9 && Math.abs(y-(Player.getLocation().getY()-4))<9) {
        	if(Player.damage(0)){
        		Random rand = Rand.getRand();
            	int hitR = rand.nextInt((2 - 1) + 1) + 1;
            	if(hitR==1) Sound.playSound("Hit3.wav", 0);
        		if(hitR==2) Sound.playSound("Hit5.wav", 0);
        	}
        	level.removeEntity(this);
        }
		if(age>=1000){
			level.removeEntity(this);
		}
		age++;
	}

	public void render(Screen screen) {
		
		if(xMotion>0&&yMotion>0){
			if(xMotion>yMotion){
				dir=3;
			}else{
				dir=0;
			}
		}else if(xMotion>0&&yMotion<=0){
			if(xMotion>Math.abs(yMotion)){
				dir=3;
			}else{
				dir=1;
			}
		}else if(xMotion<=0&&yMotion>0){
			if(Math.abs(xMotion)>yMotion){
				dir=2;
			}else{
				dir=0;
			}
		}else if(xMotion<=0&&yMotion<=0){
			if(Math.abs(xMotion)>Math.abs(yMotion)){
				dir=1;
			}else{
				dir=2;
			}
		}
		
		if(dir==1) screen.render(x, y, (xTile+1) + yTile * 32, col, 0x00, 1); //up
        if(dir==0) screen.render(x, y, (xTile+1) + yTile * 32, col, 0x02, 1); //down
        if(dir==2) screen.render(x, y, (xTile) + yTile * 32, col, 0x00, 1); //left
        if(dir==3) screen.render(x, y, (xTile) + yTile * 32, col, 0x01, 1); //right
        
	}

}
