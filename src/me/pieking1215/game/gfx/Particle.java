package me.pieking1215.game.gfx;

import me.pieking1215.game.entities.Mob;
import me.pieking1215.game.level.Level;

public class Particle extends Mob {

	private String type;
	private int delay = 0;
	private int scaleP;
	private int color1;
	private String str1;
	
	public Particle(Level level, int x, int y, String type, int scale, int color1,String str1){
		super(level, "Spawner", x, y, 1);
		this.scaleP=scale;
		this.type=type;
		this.color1=color1;
		this.str1 = str1;
	}

	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public void tick() {
		delay++;
	}

	public void render(Screen screen) {
		switch(type){
		case "Poof":
			if(delay<10/2) {
				screen.render(x, y, 12 + 28 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=10/2 && delay<20/2) {
				screen.render(x, y, 13 + 28 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=20/2 && delay<30/2) {
				screen.render(x, y, 12 + 29 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=30/2 && delay<40/2) {
				screen.render(x, y, 13 + 29 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=40/2) {
				delay=0;
				level.removeEntity(this);
			}
			break;
		case "ExplodeSmall":
			if(delay<10/2) {
				screen.render(x, y, 15 + 28 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=10/2 && delay<20/2) {
				screen.render(x, y, 16 + 28 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=20/2 && delay<30/2) {
				screen.render(x, y, 15 + 29 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=30/2 && delay<40/2) {
				screen.render(x, y, 16 + 29 * 32, Colors.get(-1, -1, -1, color1), 0x00, scaleP);
			}
			if(delay>=40/2) {
				delay=0;
				level.removeEntity(this);
			}
		case "Flame":
			if(delay<10/2) {
				screen.render(x, y, 17 + 28 * 32, Colors.get(-1, 520, 540, 445), 0x00, scaleP);
			}
			if(delay>=10/2 && delay<20/2) {
				screen.render(x, y, 18 + 28 * 32, Colors.get(-1, 520, 540, 445), 0x00, scaleP);
			}
			if(delay>=20/2 && delay<30/2) {
				screen.render(x, y, 17 + 29 * 32, Colors.get(-1, 520, 540, 445), 0x00, scaleP);
			}
			if(delay>=30/2 && delay<40/2) {
				screen.render(x, y, 18 + 29 * 32, Colors.get(-1, 520, 540, 445), 0x00, scaleP);
			}
			if(delay>=40/2) {
				delay=0;
				level.removeEntity(this);
			}
			break;
		case "Text":
			if(delay<10/2) {
				Font.render(str1,screen, x-(((str1.length()-1)*8)/2), (y)-16, Colors.get(-1, -1, -1, color1), scaleP, 0);
			}
			if(delay>=10/2 && delay<20/2) {
				Font.render(str1,screen, x-(((str1.length()-1)*8)/2), (y-2)-16, Colors.get(-1, -1, -1, color1), scaleP, 0);
			}
			if(delay>=20/2 && delay<30/2) {
				Font.render(str1,screen, x-(((str1.length()-1)*8)/2), (y-4)-16, Colors.get(-1, -1, -1, color1), scaleP, 0);
			}
			if(delay>=30/2 && delay<40/2) {
				Font.render(str1,screen, x-(((str1.length()-1)*8)/2), (y-6)-16, Colors.get(-1, -1, -1, color1), scaleP, 0);
			}
			if(delay>=40/2) {
				delay=0;
				level.removeEntity(this);
			}
			break;
		case "Wave":
			if(delay>=40) {
				delay=0;
				level.removeEntity(this);
			} else {
			}
			break;
		default:
			break;
	}
	}
	
}
