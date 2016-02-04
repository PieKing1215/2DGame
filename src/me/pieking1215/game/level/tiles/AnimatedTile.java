package me.pieking1215.game.level.tiles;

import java.util.List;

public class AnimatedTile extends BasicTile {

	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	
	public AnimatedTile(int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay) {
		this(id, animationCoords, tileColor, levelColor, animationSwitchDelay, null);
	}
	
	public AnimatedTile(int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay, List<TileEffect> effects) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, levelColor, false, effects);
	this.animationTileCoords = animationCoords;
	this.currentAnimationIndex = 0;
	this.lastIterationTime = System.currentTimeMillis();
	this.animationSwitchDelay = animationSwitchDelay;
	}

	public void tick() {
		if((System.currentTimeMillis()-lastIterationTime) >= (animationSwitchDelay)) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1]*32));
		}
	}
}
