package me.pieking1215.game.gfx;

import java.awt.Color;
import java.awt.Graphics;
import me.pieking1215.game.Game;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gui.LevelSelect;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;
import me.pieking1215.game.inventory.Inventory;

public class HUD {

	
	public static short r=0;
	public static short g=255;
	public static short b=0;
	
	private static short timer=0;
	
	private static byte color=0;
	
	public static void renderHUD(){
		
		if(!Game.inEditMode){
			
			Font.renderSmall(""+Player.xplevel, Player.screen, Player.screen.xOffset+104, Player.screen.yOffset -2,Colors.get(-1, -1, -1, 454), 1, 100);
			
			Player.screen.render(Player.screen.xOffset +151, Player.screen.yOffset +1, 11 + 27 * 32, Colors.get(-1, 111, 333, 222), 0x00, 1);
			Player.screen.render(Player.screen.xOffset +119, Player.screen.yOffset +1, 10 + 27 * 32, Colors.get(-1, 111, 333, 222), 0x00, 1);
			Player.screen.render(Player.screen.xOffset +127, Player.screen.yOffset +1, 10 + 27 * 32, Colors.get(-1, 111, 333, 222), 0x00, 1);
			Player.screen.render(Player.screen.xOffset +135, Player.screen.yOffset +1, 10 + 27 * 32, Colors.get(-1, 111, 333, 222), 0x00, 1);
			Player.screen.render(Player.screen.xOffset +143, Player.screen.yOffset +1, 10 + 27 * 32, Colors.get(-1, 111, 333, 222), 0x00, 1);
			Player.screen.render(Player.screen.xOffset +111, Player.screen.yOffset +1, 9 + 27 * 32, Colors.get(-1, 111, 333, -1), 0x00, 1);
			
			
			
			int ofs=2;
			
			Player.screen.render(Player.screen.xOffset +5+ofs, Player.screen.yOffset +5, 15 + 27 * 32, Colors.get(-1, 222, -1, 444), 0x00, 2);
			Player.screen.render(Player.screen.xOffset +21+ofs, Player.screen.yOffset +5, 16 + 27 * 32, Colors.get(-1, 222, -1, 444), 0x00, 2);
			Player.screen.render(Player.screen.xOffset +37+ofs, Player.screen.yOffset +5, 16 + 27 * 32, Colors.get(-1, 222, -1, 444), 0x00, 2);
			Player.screen.render(Player.screen.xOffset +53+ofs, Player.screen.yOffset +5, 16 + 27 * 32, Colors.get(-1, 222, -1, 444), 0x00, 2);
			Player.screen.render(Player.screen.xOffset +69+ofs, Player.screen.yOffset +5, 17 + 27 * 32, Colors.get(-1, 222, -1, 444), 0x00, 2);
	        
	        if(Player.damTimer>0) {
	        	Player.colorChange = 444;
	        	Player.damTimer--;
	        } else {
	        	Player.colorChange=000;
	        	for(int i=0; i<Player.hpOfs.length; i++){
	        		Player.hpOfs[i] = 0;
				}
	        }
	        
	        for(int row=0; row<2; row++){
	        	for(int col=0; col<6; col++){
	        		int heart=col+(6*row);
	        		if(Player.getHp()-heart*2<=0) {
	        			Player.screen.render(Player.screen.xOffset +110+(8*col), Player.screen.yOffset+(Player.hpOfs[heart]) +5+(8*row), 3 + 27 * 32, Colors.get(-1, Player.colorChange, 500, 111), 0x00, 1);
	        		}else if(Player.getHp()-heart*2==1){
	        			Player.screen.render(Player.screen.xOffset +110+(8*col), Player.screen.yOffset+(Player.hpOfs[heart]) +5+(8*row), 2 + 27 * 32, Colors.get(-1, Player.colorChange, 500, 111), 0x00, 1);
	        		}else if(Player.getHp()-heart*2>1){
	        			Player.screen.render(Player.screen.xOffset +110+(8*col), Player.screen.yOffset+(Player.hpOfs[heart]) +5+(8*row), 1 + 27 * 32, Colors.get(-1, Player.colorChange, 500, 111), 0x00, 1);
	        		}
	        	}
	        }
		
	        Inventory.render();
		
		}else{
            Tile.getById(Player.tileChangeId).render(Player.screen, Player.level2, Player.screen.xOffset+8, Player.screen.yOffset+8, 2, false);
		}
		
		if(Player.win) {
        	int shadowOffset = 1;
        	
        	Player.screen.renderAll(Player.screen.xOffset + shadowOffset + ((Player.screen.width/2)-64), Player.screen.yOffset + shadowOffset + ((Player.screen.height/2)-16), 8, 2, 18 ,25, Colors.get(-1, 010,-1, -1), 0x00, 2);
        	
        	//Player.screen.renderAll(Player.screen.xOffset + ((Player.screen.width/2)-64), Player.screen.yOffset + ((Player.screen.height/2)-16), 8, 2, 18 ,25, Colors.get(-1, 050,-1, -1), 0x00, 2);
        	
        	if(Level.level.getWorld().equals("Tutorial")) {
        		Font.render("Please do Survey!", Player.screen, Player.screen.xOffset + ((Player.screen.width/2)) - (70), Player.screen.yOffset + 16+((Player.screen.height/2)),Colors.get(-1, -1, -1, 555), 1, 100);
        	}
        	
        }
        if(Player.getHp()<=0) {
        	
        	int shadowOffset = 1;
        	
        	Player.screen.renderAll(Player.screen.xOffset + shadowOffset + ((Player.screen.width/2)-48), Player.screen.yOffset + shadowOffset + ((Player.screen.height/2)-16), 6, 2, 12, 25, Colors.get(-1, 100, -1, -1), 0x00, 2);
        	
        	//Player.screen.renderAll(Player.screen.xOffset + ((Player.screen.width/2)-48), Player.screen.yOffset + ((Player.screen.height/2)-16), 6, 2, 12, 25, Colors.get(-1, 400, -1, -1), 0x00, 2);
        	
        	Font.render("[R] To Restart", Player.screen, Player.screen.xOffset + ((Player.screen.width/2)) - (60), Player.screen.yOffset + 16+((Player.screen.height/2)),Colors.get(-1, -1, -1, 555), 1, 100);
        	
        }
		
	}

	public static void renderHUDgi(Graphics gi){
		
		
		
		if(Game.game.tickCount%3==0){
			if(color==0){
				r++;
				g--;
				b=0;
			}else if(color==1){
				r--;
				g=0;
				b++;
			}else if(color==2){
				r=0;
				g++;
				b--;
			}
		
			if(r>255){
				r=255;
			}else if(r<0){
				r=0;
			}
		
			if(g>255){
				g=255;
			}else if(g<0){
				g=0;
			}
		
			if(b>255){
				b=255;
			}else if(b<0){
				b=0;
			}
		
			if(timer>255){
				if(color==2){
					color=0;
				}else{
					color++;
				}
					timer=0;
			}else{
				timer++;
			}
		}
		
		if(!Level.underground){
		
		if(LevelSelect.inEditMode){
			
			gi.setColor(Color.BLACK);
			gi.drawRect(2, 2, 19, 19);
	        gi.setColor(new Color(r,g,b));
	        gi.drawRect(3, 3, 17, 17);
	        gi.setColor(Color.WHITE);
	        gi.setFont(new java.awt.Font("AgencyFB", 0, 9));
	        gi.drawString(Player.tileChangeId+"", 12-(gi.getFontMetrics().stringWidth(Player.tileChangeId+"")/2), 28);
		}else{
			for(int i=0; i<Math.round((float) (divide(Player.getXp(),Player.xpToLevel) * 46)); i++){
				int r2=0;
				r2=Math.round((float)(divide(46-i,46)*255));
				if(r2>255){
					r2=255;
				}else if(r2<0){
					r2=0;
				}
				gi.setColor(new Color(r2, 255, 0, 127));
				gi.fillRect(112+i, 2, 1, 1);
			}
			
			gi.setColor(new Color(0, Math.round((float)(Math.sin((Game.game.tickCount-(45*Math.PI))/20)+1)*32)+191, 0, Math.round((float)(Math.sin((Game.game.tickCount-(45*Math.PI))/20)+1)*32)+191));
			
			gi.drawLine(98, 1, 100, 3);
			gi.fillRect(100, 1, 1, 1);
			gi.fillRect(98, 3, 1, 1);
			
			gi.fillRect(102, 1, 2, 2);
			gi.fillRect(102, 3, 1, 1);
			
			gi.fillRect(105, 1, 1, 1);
			gi.fillRect(105, 3, 1, 1);
			
		}
		
		}
		
		
	}
	
	public static float divide(float a, float b){
		return a/b;
	}
	
}

