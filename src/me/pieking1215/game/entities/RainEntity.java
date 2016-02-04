package me.pieking1215.game.entities;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import me.pieking1215.game.Game;
import me.pieking1215.game.gui.OptionsMenu;

public class RainEntity {

	private int x;
	private int y;
	private int xa,ya;

	public RainEntity(int x, int y, int xa, int ya){
		this.x=x;
		this.y=y;
		this.xa=xa;
		this.ya=ya;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setStroke(new BasicStroke(Math.round((float)(Game.divide(xa,10)*10))));
		g2d.drawLine(x, y, x+(xa*4), y+(ya*4));
		
		if(!OptionsMenu.paused) {
			x+=xa;
			y+=ya;
		}
		if(x>Game.game.getWidth()||y>=Game.game.getHeight()){
			Game.rainToRemove.add(this);
		}
	}

}
