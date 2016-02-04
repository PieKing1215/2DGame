package me.pieking1215.game.level.tiles;

import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;

public class TileEffect {
	
	private TileEffectType type;
	
	public TileEffect(TileEffectType type){
		this.type = type;
	}
	
	public void tick(){
		
	}
	
	public static enum TileEffectType {
        FIRE, SLOW, SLIP, WATER;
	}
	
	public TileEffectType getType(){
		return type;
	}

	public void render(Screen screen, Level level, int x, int y) {
		switch (type) {
		case FIRE:
				//if(Game.game.tickCount%20==0){
				//	int xa = Rand.range(0, 8) - 4;
				//	int ya = Rand.range(0, 8) - 4;
				//	Level.level.spawn(new Particle(level, x+xa, y+ya, "Flame", 1, 0, null));
				//}
				Tile.getById(40).render(screen, level, x, y, 1, false);
			break;
			
		case SLOW:
			
			break;
			
		case SLIP:
			
			break;
			
		case WATER:
			
			break;

		default:
			break;
		}
	}
	
}
