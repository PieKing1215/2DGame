package me.pieking1215.game.level.tiles;

import java.awt.Color;
import java.util.List;

import me.pieking1215.game.Game;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.gui.OptionsMenu;
import me.pieking1215.game.level.Level;

public class BasicTile extends Tile{

	protected int tileId;
	protected int tileColor;
	protected int x;
	protected int y;
	
	public BasicTile(int id, int x, int y, int tileColor, int levelColor, boolean isEmitter) {
		this(id, x, y, tileColor, levelColor, isEmitter, null);
	}
	
	public BasicTile(int id, int x, int y, int tileColor, int levelColor, boolean isEmitter, List<TileEffect> effects) {
		super(id, false, isEmitter, levelColor, effects);
		this.tileId = x+y*32;
		this.tileColor = tileColor;
		this.x=0;
		this.y=0;
	}
	
	public void tick(){
		light=calcLightLevel(x/8, y/8);
	}
	public void render(Screen screen, Level level, int x, int y, int scale, boolean doFlip) {
		byte flip = 0x00;
		
		if(this == Tile.GRASS_1 || this == Tile.GRASS_2 || this == Tile.GRASS_3 || this == Tile.GRASS_4 || this == Tile.STONE){
			if(doFlip) flip = Level.tileR[(x/8)+(y/8)*Level.width];
		}
		
		screen.render(x, y, tileId, tileColor, flip, scale);
		this.x=x;
		this.y=y;
		//System.out.println(x/8+" "+y/8+" "+Level.width+" "+Level.height);
		//System.out.println(getLight()+" "+(getLight()/9)*128);
		if(OptionsMenu.optLight) Game.tileLights[(x/8)+(y/8)*(Level.width)]= new TileLight((x/8), (y/8), new Color(0, 0, 0, ((10-getLight())/9)*128));
		
	}
}
