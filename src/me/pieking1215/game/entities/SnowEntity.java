package me.pieking1215.game.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import me.pieking1215.game.Game;
import me.pieking1215.game.gui.OptionsMenu;

public class SnowEntity {

	private int x;
	private int y;
	private int size;
	private int xa,ya;

	public SnowEntity(int x, int y, int size, int xa, int ya){
		this.x=x;
		this.y=y;
		this.size=size;
		this.xa=xa;
		this.ya=ya;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.fillOval(x,y, size, size);
		
		if(!OptionsMenu.paused) {
			x+=xa;
			y+=ya;
		}
		if(x>Game.game.getWidth()||y>=Game.game.getHeight()){
			Game.snowToRemove.add(this);
		}
	}

}
