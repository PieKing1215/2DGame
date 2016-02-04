package me.pieking1215.game.entities;

import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;

public class MovableRock extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    public static int x2;
    public static int y2;
    int xp;
    Player player = new Player(null, 0, 0, null, "");
	int animCount = 0;
	public String toLevel;
	public boolean resetToLevel=false;
    
	public MovableRock(Level level, int x, int y) {
		super(level, "MovableRock", x, y, 1);
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
		
		for (Entity e : level.getEntities()) {
    		if(e instanceof Player){
    			if(Math.abs((this.x-4)-e.x)<=4 && Math.abs((this.y-4)-e.y)<=4) {
            	
            	}
            }
        }
	}

	
	
    public void render(Screen screen) {
        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        screen.render(xOffset, yOffset + 3, 8 + 27 * 32, Colors.get(-1, 222, 333, 444), 0x00, 1);
        numSteps++;
        for (Entity e : level.getEntities()) {
    		if(e instanceof Player){
            	if((Math.abs((this.x)-e.x)<=10 && Math.abs((this.y)-e.y)<=10)) {
            	Player pl = (Player) e;
            	if(pl.x>this.x){
            		System.out.println("Up");
            	}else if(pl.x>this.x) {
            		System.out.println("Down");
            	}else if(pl.y>this.y){
            		System.out.println("Left");
            	}else if(pl.y<this.y){
            		System.out.println("Right");
            	}
            	}
            	//screen2.fillRect(0, 0, 1000, 1000, 143);
            }
        }
    }
}
