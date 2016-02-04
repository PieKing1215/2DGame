package me.pieking1215.game.entities;

import me.pieking1215.game.Game;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;

public class Arrow extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    private int tickCount = 0;
    public static int x2;
    public static int y2;
    int xp;
    Player player = Game.game.player;
	private int dir;
    
	public Arrow(Level level, int x, int y, int dir) {
		super(level, "XpOrb", x, y, 1);
		this.dir = dir;
	}
	
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
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
		if(tickCount>1000){
			level.removeEntity(this);
		}
		switch(dir) {
		case 0:
			y--;
			break;
		case 1:
			y++;
			break;
		case 2:
			x--;
			break;
		case 3:
			x++;
			break;
		default:
			break;
		}
		for (Entity e : level.getEntities()) {
    		if(e instanceof BasicEnemy){
            	BasicEnemy en = (BasicEnemy) e;
            	if(Math.abs(x-en.x)<=4 && Math.abs(y-en.y)<=8) {
            	en.damage(2);
            	level.removeEntity(this);
            	}
            }
        }
        tickCount++;
	}

    public void render(Screen screen) {
        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        if(dir==0) screen.render(xOffset, yOffset + 3, 7 + 27 * 32, Colors.get(-1, 320, 333, 555), 0x00, 1);
        if(dir==1) screen.render(xOffset, yOffset + 3, 7 + 27 * 32, Colors.get(-1, 320, 333, 555), 0x02, 1);
        if(dir==2) screen.render(xOffset, yOffset + 3, 6 + 27 * 32, Colors.get(-1, 320, 333, 555), 0x00, 1);
        if(dir==3) screen.render(xOffset, yOffset + 3, 6 + 27 * 32, Colors.get(-1, 320, 333, 555), 0x01, 1);
        numSteps++;
    }
}