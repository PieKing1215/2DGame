package me.pieking1215.game.entities;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.net.sound.Sound;

public class Spawner extends Mob{

	private String type;
	private int num1;
	private int tickCount = 0;
	private int spawnSpeed;
	private int spawnCnt;
	private boolean bool1;
	private int spawnRadius;
	private int hp;
    Player player = Game.game.player;

	public Spawner(Level level, int x, int y, String type, int spawnSpeed, int spawnCnt, int spawnRadius, int hp, int num1, boolean bool1) {
		super(level, "Spawner", x, y, 1);
		this.type = type;
		this.num1 = num1;
		this.spawnSpeed = spawnSpeed;
		this.spawnCnt = spawnCnt;
		this.bool1 = bool1;
		this.spawnRadius = spawnRadius;
		this.hp = hp;
	}
	
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public void tick() {
		int xa2 = Player.getLocation().getX();
        int ya2 = Player.getLocation().getY();
		if(Player.spacePress && hp > 0) {
        	if(Math.abs(x-xa2)<12 && Math.abs(y-ya2)<12) { 
        		damage(1);
        	}
        }
        
        if(hp <= 0 && hp >= -2) {
        	level.spawn(new Particle(level, x, y, "Poof",2,511,null));
            Random rand = Rand.getRand();
            int randomX = rand.nextInt((10 - -10) + 1) + -10;
            int randomY = rand.nextInt((10 - -10) + 1) + -10;
            int randomXp = rand.nextInt((5 - 2) + 1) + 2;
            int randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
        	randomX = rand.nextInt((10 - -10) + 1) + -10;
            randomY = rand.nextInt((10 - -10) + 1) + -10;
            randomXp = rand.nextInt((5 - 2) + 1) + 2;
            randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
            randomX = rand.nextInt((10 - -10) + 1) + -10;
            randomY = rand.nextInt((10 - -10) + 1) + -10;
            randomXp = rand.nextInt((5 - 2) + 1) + 2;
            randomCnt = rand.nextInt((2 - 1) + 1) + 1;
            if(randomCnt==1) level.spawn(new XpOrb(level,x + randomX,y +randomY,randomXp));
            int hitR = rand.nextInt((2 - 1) + 1) + 1;
    		if(hitR==1) Sound.playSound("SpawnerKill1.wav", 0);
    		if(hitR==2) Sound.playSound("SpawnerKill2.wav", 0);
        	level.removeEntity(this);
        }
        if(Math.abs(x-xa2)<80 && Math.abs(y-ya2)<80) {
		if(tickCount>=100){
			Random rand = Rand.getRand();
			int randCnt = rand.nextInt((3 - 0) + 1) + 0;
			while(randCnt>0) {
				int randX = rand.nextInt((10 - (-10)) + 1) + (-10);
				int randY = rand.nextInt((10 - (-10)) + 1) + (-10);
				level.spawn(new Particle(level, x+randX, y+randY, "Flame", 1, 530,null));
				randCnt--;
			}
			
		//System.out.println("tickCount");
        int spawn = rand.nextInt((spawnSpeed - 1) + 1) + 0;
        //System.out.println(""+spawn);
        if(spawn==1) {
        	//System.out.println("spawnSpeed");
        	int spawnCt = rand.nextInt((spawnCnt - 1) + 1) + 1;
        	while(spawnCt>0) {
        		//System.out.println(""+spawnCt);
        	switch(type) {
        		case "Skeleton":
        			if(level.skeletonCt()<27){
        				int randomX = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            int randomY = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            while(!level.getTile(randomX, randomY).isSolid()){
        	            	randomX = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
            	            randomY = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            }
        	            
        	            if(level.getTile(randomX, randomY).isSolid()){
        	            	//System.out.println(""+randomX+" "+randomY+" "+level.getTile(randomX, randomY).isSolid());
        	            	level.spawn(new Particle(level, this.x+randomX, this.y+randomY, "Poof",2,555,null));
        	            	level.spawn(new BasicEnemy(level, this.x +randomX, this.y+randomY, 1, num1, 30, bool1, 0));
        	            }
        			}
        			break;
        		case "XpOrb":
        			if(level.xpOrbCt()<10){
        				int randomX = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            int randomY = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            level.spawn(new Particle(level, this.x+randomX, this.y+randomY, "Poof",2,555,null));
        	            level.spawn(new XpOrb(level, this.x+randomX, this.y+randomY, num1));
        			}
        			break;
        		case "Drop":
        			if(level.dropCt()<10){
        				int randomX = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            int randomY = rand.nextInt((spawnRadius -(-spawnRadius)) + 1) + (-spawnRadius);
        	            level.spawn(new Particle(level, this.x+randomX, this.y+randomY, "Poof",2,555,null));
        	            level.spawn(new Drop(level, this.x+randomX, this.y+randomY, num1, bool1,true));
        			}
        			break;
        		default:
        			level.removeEntity(this);
        			break;
        	}
        	spawnCt--;
        	}
        }
        tickCount = 0;
		}
        tickCount ++;
        }
	}

	public void render(Screen screen) {
		if(hp<=-1){
		screen.render(x, y, 30 + 28 * 32, Colors.get(-1, 001, 223, 222), 0x00, 1);
		} else {
			screen.render(x, y, 30 + 28 * 32, Colors.get(-1, 000, 222, 222), 0x00, 1);	
		}
	}
	public void damage(int damage) {
		hp = hp-damage;
		Random rand = Rand.getRand();
        int randomX = rand.nextInt((5 - -5) + 1) + -5;
        int randomY = rand.nextInt((5 - -5) + 1) + -5;
        //System.out.println(""+randomX+" "+randomY);
        level.spawn(new Particle(level, x+randomX, y+randomY-3, "Poof",1,533,null));
        int hitR = rand.nextInt((2 - 1) + 1) + 1;
		if(hitR==1) Sound.playSound("Hit1.wav", 0);
		if(hitR==2) Sound.playSound("Hit4.wav", 0);
	}
}
