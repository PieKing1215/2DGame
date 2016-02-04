package me.pieking1215.game.inventory;

import me.pieking1215.game.gfx.Colors;

public class Item {
	
	private String name;
	private int count;
	private int data;
	private int maxStack;
	private int spriteX;
	private int spriteY;
	private int col;
	
	public static Item BOMB = new Item("BOMB", 1, 0, 10, 27, 5, Colors.get(-1, 111, 222, 555));
	public static Item ARROW = new Item("ARROW", 100, 0, 10, 27, 6, Colors.get(-1, 320, 333, 555));
	public static Item SWORD = new Item("SWORD", 1, 0, 1, 27, 19, Colors.get(-1, 320, 333, 555));
	
	public Item(String name, int count, int data, int maxStack, int spriteX, int spriteY, int col){
		this.name=name;
		this.count=count;
		this.data=data;
		this.maxStack=maxStack;
		this.spriteX=spriteX;
		this.spriteY=spriteY;
		this.col=col;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getData(){
		return data;
	}
	
	public int getMaxStack(){
		return maxStack;
	}
	
	public int getSpriteX(){
		return spriteX;
	}
	
	public int getSpriteY(){
		return spriteY;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setCount(int cnt){
		count=cnt;
	}
	
	public void addCount(int cnt){
		count+=cnt;
	}
	
	public void setData(int dat){
		data=dat;
	}

}
