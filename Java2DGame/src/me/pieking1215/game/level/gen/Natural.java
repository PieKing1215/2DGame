package me.pieking1215.game.level.gen;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Rand;
import me.pieking1215.game.entities.BasicEnemy;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;

public class Natural {
	
	Structure[] structures;
	Random rand = Rand.getRand();
	private int wave = 0;
	
	public Natural(){
		wave=0;
		structures=new Structure[Level.width * Level.height];
		Game.spawnX=Level.width*4;
		Game.spawnY=Level.height*4;
		for (int y = 5; y < Level.height-5; y++) {
    		for (int x = 5; x < Level.width-5; x++) {
    			
    			int randomNum = rand.nextInt((4 - 1) + 1) + 1;
    			if (randomNum == 1) {
    				Level.tiles[x + y * Level.width] = Tile.GRASS_1.getId();
    				if(Game.oneIn(100)){
    					structures[x + y * Level.width]=new Structure(x, y, 5);
    				}else if(Game.oneIn(200)){
    					structures[x + y * Level.width]=new Structure(x, y, 3);
    				}else if(Game.oneIn(100)){
    					structures[x + y * Level.width]=new Structure(x, y, 2);
    				}else if(Game.oneIn(1000)){
    					structures[x + y * Level.width]=new Structure(x, y, 4);
    				}else if(Game.oneIn(25)){
    					structures[x + y * Level.width]=new Structure(x, y, 1);
    				}
    				if(Game.oneIn(50)) {
    					if(Game.oneIn(20)){
    						int randomNum2 = rand.nextInt(3);
    						randomNum2=0;
    						Level.level.spawn(new BasicEnemy(Level.level, x*8, y*8, 1, randomNum2+1, 320, true, 1));
    					}
    				}
    			} else if (randomNum == 2) {
    				Level.tiles[x + y * Level.width] = Tile.GRASS_2.getId();
    			} else if (randomNum == 3){
    				Level.tiles[x + y * Level.width] = Tile.GRASS_3.getId();
    			} else {
    				Level.tiles[x + y * Level.width] = Tile.GRASS_4.getId();
    			}
    		}
	}
		for(Structure s : structures){
			if(s!=null){
			s.generate();
			}
		}
		//<-----------------= Gens Border =------------------------>
		for(int y=0;y<Level.height;y++){
			for(int x=0;x<4;x++){
			Level.tiles[x + y * Level.width] = Tile.STONE.getId();
			}}
		for(int y=0;y<Level.height;y++){
			for(int x=Level.width-4;x<Level.width;x++){
			Level.tiles[x + y * Level.width] = Tile.STONE.getId();
			}}
		for(int x=0;x<Level.width;x++){
			for(int y=0;y<4;y++){
			Level.tiles[x + y * Level.width] = Tile.STONE.getId();
			}}
		for(int x=0;x<Level.width;x++){
			for(int y=Level.height-4;y<Level.height;y++){
			Level.tiles[x + y * Level.width] = Tile.STONE.getId();
			}}
		for(int y=4;y<Level.height-4;y++){
			Level.tiles[4 + y * Level.width] = Tile.STONE_WALL_E.getId();
		}
		for(int y=4;y<Level.height-4;y++){
			Level.tiles[(Level.width-5) + y * Level.width] = Tile.STONE_WALL_W.getId();
		}
		for(int x=4;x<Level.width-4;x++){
			Level.tiles[x + 4 * Level.width] = Tile.STONE_WALL_S.getId();
		}
		for(int x=4;x<Level.width-4;x++){
			Level.tiles[x + (Level.height-5) * Level.width] = Tile.STONE_WALL_N.getId();
		}
		Level.tiles[(Level.width-5) + (Level.height-5) * Level.width] = Tile.STONE_WALL_IN_NW.getId();
		Level.tiles[4 + 4 * Level.width] = Tile.STONE_WALL_IN_SE.getId();
		Level.tiles[4 + (Level.height-5) * Level.width] = Tile.STONE_WALL_IN_NE.getId();
		Level.tiles[(Level.width-5) + 4 * Level.width] = Tile.STONE_WALL_IN_SW.getId();
		//<-----------------= Stops Gens Border =------------------------>
	}
	
	public void tick(){
		if(Level.level.zombieCt()==0){
			wave++;
			Level.level.spawn(new Particle(Level.level, Player.getLocation().getX(), Player.getLocation().getY(), "Wave", 1, 500, wave+""));
			for(int ct=0; ct<10+(wave*2); ct++){
			for (int y = 5; y < Level.height-5; y++) {
	    		for (int x = 5; x < Level.width-5; x++) {
	    			if(Game.oneIn(50)) {
    					if(Game.oneIn(500)){
    						int randomNum2 = rand.nextInt(3);
    						randomNum2=0;
    						Level.level.spawn(new BasicEnemy(Level.level, x*8, y*8, 1, randomNum2+1, 320, true, 1));
    					}
    				}
	    		}
			}
			}
		}
	}
	
}
