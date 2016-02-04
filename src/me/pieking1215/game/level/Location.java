package me.pieking1215.game.level;

import me.pieking1215.game.level.tiles.Tile;

public class Location {

	private int x,y;
	
	public Location(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getTileX(){
		return x >> 3;
	}
	
	public int getTileY(){
		return y >> 3;
	}
	
	public Tile getTile(){
		return Level.level.getTile(getTileX(), getTileY());
	}
	
	public String getWorld(){
		return Level.level.getWorld();
	}
	
	public double getDistance(Location l){
		double dist = Math.sqrt(Math.pow((l.getX()-getX()), 2)+Math.pow((l.getY()-getY()), 2));
		return Math.abs(dist);
	}
	
	public void set(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public Location add(int x, int y){
		return new Location(getX()+x, getY()+y);
	}
	
}
