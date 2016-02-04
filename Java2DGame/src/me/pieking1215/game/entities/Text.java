package me.pieking1215.game.entities;

import me.pieking1215.game.Game;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Font;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;

public class Text extends Mob {
    private int scale = 1;
    protected boolean isSwimming = false;
    public static int x2;
    public static int y2;
    int xp;
    Player player = Game.game.player;
	private int color;
	private String text;
	public Text(Level level, int x, int y, String text, int color) {
		super(level, "Text", x, y, 1);
		this.color = color;
		this.text=text;
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
	}

    public void render(Screen screen) {
    	if(color>555){
    		System.out.println("[Severe] Please Refrain From Using Color Values Greater Than 555");
    		color=555;
    	//Random rand = Rand.getRand();
        //if(colTimer==10){
        //	if(color>=557) randCol++;
        //	if(randCol==556){
        //		randCol=1;
        //	}
    	//colTimer=0;
        //} else {
        //	colTimer++;
        //}
        }
    	Font.render(text, screen, x, y, Colors.get(-1, -1, -1, color), scale, 0);
        numSteps++;
    }
}
