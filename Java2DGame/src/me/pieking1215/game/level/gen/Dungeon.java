package me.pieking1215.game.level.gen;

import java.util.Random;

import me.pieking1215.game.Game;
import me.pieking1215.game.Rand;
import me.pieking1215.game.entities.BasicEnemy;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;

public class Dungeon {
	
	Structure[] structures;
	Random rand = Rand.getRand();
	private int wave = 0;
	
	public Dungeon(){
		wave=0;
		structures=new Structure[Level.width * Level.height];
		Game.spawnX=Level.width*4;
		Game.spawnY=Level.height*4;
		for (int y = 5; y < Level.height-5; y++) {
    		for (int x = 5; x < Level.width-5; x++) {
    			
    			int randomNum = rand.nextInt((4 - 1) + 1) + 1;
    			if (randomNum == 1) {
    				Level.tiles[x + y * Level.width] = Tile.DUNGEON_12.getId();
    				if(Game.oneIn(20)){
    					structures[x + y * Level.width]=new Structure(x, y, 6);
    				}else if(Game.oneIn(100)){
    					structures[x + y * Level.width]=new Structure(x, y, 10);
    				}
    				if(Game.oneIn(50)) {
    					if(Game.oneIn(3)){
    						int randomNum2 = rand.nextInt(3);
    						randomNum2=0;
    						Level.level.spawn(new BasicEnemy(Level.level, x*8, y*8, 1, randomNum2+1, 32, true, 0));
    					}
    				}
    			} else if (randomNum == 2) {
    				if(Game.oneIn(10)){ Level.tiles[x + y * Level.width] = Tile.DUNGEON_10.getId(); }else{ Level.tiles[x + y * Level.width] = Tile.DUNGEON_12.getId();} 
    			} else if (randomNum == 3){
    				if(Game.oneIn(10)){ Level.tiles[x + y * Level.width] = Tile.DUNGEON_11.getId(); }else{ Level.tiles[x + y * Level.width] = Tile.DUNGEON_12.getId();} 
    			} else {
    				if(Game.oneIn(10)){ Level.tiles[x + y * Level.width] = Tile.DUNGEON_5.getId(); }else{ Level.tiles[x + y * Level.width] = Tile.DUNGEON_12.getId();} 
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
			Level.tiles[x + y * Level.width] = Tile.VOID.getId();
			}}
		for(int y=0;y<Level.height;y++){
			for(int x=Level.width-4;x<Level.width;x++){
			Level.tiles[x + y * Level.width] = Tile.VOID.getId();
			}}
		for(int x=0;x<Level.width;x++){
			for(int y=0;y<4;y++){
			Level.tiles[x + y * Level.width] = Tile.VOID.getId();
			}}
		for(int x=0;x<Level.width;x++){
			for(int y=Level.height-4;y<Level.height;y++){
			Level.tiles[x + y * Level.width] = Tile.VOID.getId();
			}}
		for(int y=4;y<Level.height-4;y++){
			Level.tiles[4 + y * Level.width] = Tile.DUNGEON_4.getId();
		}
		for(int y=4;y<Level.height-4;y++){
			Level.tiles[(Level.width-5) + y * Level.width] = Tile.DUNGEON_6.getId();
		}
		for(int x=4;x<Level.width-4;x++){
			Level.tiles[x + 4 * Level.width] = Tile.DUNGEON_2.getId();
		}
		for(int x=4;x<Level.width-4;x++){
			Level.tiles[x + (Level.height-5) * Level.width] = Tile.DUNGEON_8.getId();
		}
		Level.tiles[(Level.width-5) + (Level.height-5) * Level.width] = Tile.DUNGEON_9.getId();
		Level.tiles[4 + 4 * Level.width] = Tile.DUNGEON_1.getId();
		Level.tiles[4 + (Level.height-5) * Level.width] = Tile.DUNGEON_7.getId();
		Level.tiles[(Level.width-5) + 4 * Level.width] = Tile.DUNGEON_3.getId();
		//<-----------------= Stops Gens Border =------------------------>
	}
	
	public void tick(){
		if(Level.level.zombieCt()==0){
			wave++;
			Level.level.spawn(new Particle(Level.level, Player.getLocation().getX(), Player.getLocation().getY(), "Wave", 1, 500, wave+""));
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
