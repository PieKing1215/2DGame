package me.pieking1215.game.inventory;

import me.pieking1215.game.Game;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Font;

public class Inventory {

	private static final int maxItems = 5;
	private static int itemIndex=0;
	private static Item[] items = new Item[maxItems];
	
	/**
	 * Adds An Item To The Inventory.<br>
	 * If There Is No Space, Returns False.
	 */
	public static boolean addItem(Item item){
		
		for(Item i: items){
			if(i!=null){
			if(i.getName()==item.getName()){
				if(i.getCount()+item.getCount()<=i.getMaxStack()){
					i.setCount(i.getCount()+item.getCount());
					return true;
				}
			}
			}
		}
		
		items[itemIndex+1] = item;
		itemIndex++;
		
		return false;
	}
	
	public static Item getItemByName(String name){
		for(Item i: items){
			if(i!=null){
			if(i.getName()==name){
				return i;
			}
			}
		}
		return null;
	}
	
	public static void render(){
		
		for(int x=0; x<items.length; x++) {
			Item i =items[x];
			if(i!=null) {
				//System.out.println("ad");
				int length = String.valueOf(i.getCount()).length()-1;
				
				Player.screen.render(Player.screen.xOffset +8+(16*(x-1)), Player.screen.yOffset +5, i.getSpriteY() + i.getSpriteX() * 32, i.getCol(), 0x00, 1);
				if(Game.level.snowy==null){
					Font.renderSmall(""+i.getCount(), Player.screen, Player.screen.xOffset +8+(16*(x-1))-(length*2), Player.screen.yOffset +12,Colors.get(-1, -1, -1, 555), 1, 1000);
				}else{
					Font.renderSmall(""+i.getCount(), Player.screen, Player.screen.xOffset +8+(16*(x-1))-(length*2), Player.screen.yOffset +12,Colors.get(-1, -1, -1, 000), 1, 1000);
				}
			}
		}
		
	}
	
	public static void clear(){
		for(int x=0; x<items.length; x++) {
			items[x]=null;
		}
		itemIndex=0;
	}
	
}
