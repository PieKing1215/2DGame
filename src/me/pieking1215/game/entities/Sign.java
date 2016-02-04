package me.pieking1215.game.entities;

import me.pieking1215.game.Game;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Font;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;

public class Sign extends Mob {
    private String[] msg;
    Player player = Game.game.player;
    
	public Sign(Level level, int x, int y, String... msg) {
		super(level, "Sign", x-8, y-8, 1);
		this.msg=msg;
	}
	
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for(Entity e: level.getEntities()) {
        	int xE = e.x;
        	int yE = e.y;
        	if(Math.abs(x-xE)<9 && Math.abs(y-yE)<9) {
        		
        	}
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }

	public void tick() {
	}
	
        public void render(Screen screen) {
            screen.render(x, y, 18 + 27 * 32, Colors.get(-1, 210, 110, 221), 0x00, 1);
        }
        
        public void renderOver(Screen screen){
        	//for(PlayerMP player: Game.level.getOnlinePlayers()){ //TODO: Work On This
                int xa2 = player.getLoc().getX();
                int ya2 = player.getLoc().getY();
                if(Math.abs(x-xa2)<8 && Math.abs(y-ya2)<8) {
                	for(int i=0;i<msg.length;i++){
                		Font.render(msg[i], Player.screen, Player.screen.xOffset+Game.game.image.getWidth()/2-(msg[i].length()*4), Player.screen.yOffset +30+(8*i),Colors.get(210, -1, -1, 443), 1, 100);
                	}
                }
            //}
        }

}

