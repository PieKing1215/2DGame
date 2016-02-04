package me.pieking1215.game.entities;

import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.Location;

public abstract class Entity {

	public int x,y;
	protected Level level;
	private Location loc;
	
	public Entity() {
	}
	public Entity(Level level){
		loc=new Location(0, 0);
		init(level);
		
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public void tickL(){
		getLoc().set(x, y);
		tick();
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
	
	public Location getLoc(){
		return loc;
	}
}
