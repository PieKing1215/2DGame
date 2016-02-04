package me.pieking1215.game.level.tiles;

import java.awt.Color;

public class TileLight {

	private int x;
	private int y;
	private Color col;
	
	public TileLight(int x, int y, Color col){
		this.x=x;
		this.y=y;
		this.col=col;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Color getCol(){
		return col;
	}
	
}
