package me.pieking1215.game.level.tiles;

import java.util.List;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
		this(id, x, y, tileColor, levelColor, null);
	}
	
	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor, List<TileEffect> effects) {
		super(id, x, y, tileColor, levelColor, false, effects);
		this.solid = true;
	}

}
