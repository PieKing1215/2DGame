package me.pieking1215.game.level.gen;

import me.pieking1215.game.Game;
import me.pieking1215.game.entities.Spawner;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;

public class Structure {
	int x,y,id;
	
	public Structure(int x, int y, int id){
		this.x=x;
		this.y=y;
		this.id=id;
	}
	
	public void generate(){
		if(id==1){            //Small Boulder
			try{Level.tiles[(x+0) + (y+0) * Level.width] = Tile.STONE_WALL_OUT_SE.getId();}catch(ArrayIndexOutOfBoundsException e){};
			try{Level.tiles[(x+1) + (y+0) * Level.width] = Tile.STONE_WALL_OUT_SW.getId();}catch(ArrayIndexOutOfBoundsException e){};
			try{Level.tiles[(x+0) + (y+1) * Level.width] = Tile.STONE_WALL_OUT_NE.getId();}catch(ArrayIndexOutOfBoundsException e){};
			try{Level.tiles[(x+1) + (y+1) * Level.width] = Tile.STONE_WALL_OUT_NW.getId();}catch(ArrayIndexOutOfBoundsException e){};
		} else if(id==2){     //Small Water
			for(int xi=0;xi<3;xi++){for(int yi=0;yi<3;yi++){
				if(Game.oneIn(3)) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.WATER.getId();}catch(ArrayIndexOutOfBoundsException e){};
				}}
		} else if(id==3){     //Medium Water
			for(int xi=0;xi<4;xi++){for(int yi=0;yi<4;yi++){
			if(Game.oneIn(3)) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.WATER.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}
		} else if(id==4){     //Big Water
			for(int xi=-25;xi<25;xi++){for(int yi=-25;yi<25;yi++){
				byte b = Tile.GRASS_1.getId();
				try{b=Level.tiles[(x+xi)+(y+yi)*Level.width];}catch(ArrayIndexOutOfBoundsException e){};
				if(b==Tile.GRASS_1.getId()||b==Tile.GRASS_2.getId()||b==Tile.GRASS_3.getId()||b==Tile.GRASS_4.getId()){
				if(!(Math.pow(xi,2)+Math.pow(yi,2)>=250)) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.WATER.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}}
		} else if(id==5){     //Big Grass
			for(int xi=-10;xi<10;xi++){for(int yi=-10;yi<10;yi++){
				byte b = Tile.GRASS_1.getId();
				try{b=Level.tiles[(x+xi)+(y+yi)*Level.width];}catch(ArrayIndexOutOfBoundsException e){};
				if(b==Tile.GRASS_1.getId()||b==Tile.GRASS_2.getId()||b==Tile.GRASS_3.getId()||b==Tile.GRASS_4.getId()){
				if(!(Math.pow(xi,2)+Math.pow(yi,2)>=100)&&(Game.oneIn(3)||Game.oneIn(3))){
					try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.GRASS_4.getId();}catch(ArrayIndexOutOfBoundsException e){};
				}
				}
			}}
		} else if(id==6){     //Small Dungeon Hole
			boolean gt = true;
			for(int xi2=-1;xi2<5;xi2++){for(int yi2=-1;yi2<5;yi2++){
				try{
					if(Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_10.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_11.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_12.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_5.getId()){
					} else {
						gt=false;
						//System.out.println(Level.tiles[(x+xi2) + (y+yi2) * Level.width]); //TODO: DELETE THIS
					}
				}catch(ArrayIndexOutOfBoundsException e){};
			}}
			if(gt){
			for(int xi=0;xi<3;xi++){for(int yi=0;yi<3;yi++){
				try{Level.tiles[(x+xi+1) + (y+yi) * Level.width] = Tile.VOID.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}
			for(int yi=0;yi<3;yi++){
				try{Level.tiles[(x) + (y+yi) * Level.width] = Tile.DUNGEON_6.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+4) + (y+yi) * Level.width] = Tile.DUNGEON_4.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}
			for(int xi=0;xi<3;xi++){
				try{Level.tiles[(x+xi+1) + (y-1) * Level.width] = Tile.DUNGEON_8.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+xi+1) + (y+3) * Level.width] = Tile.DUNGEON_2.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}
			}
		} else if(id==7){     //Star For Pentagram 
			for(int xi=-10;xi<20;xi++){for(int yi=-10;yi<10;yi++){
				if(-2*xi==yi) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				if(-2*xi+1==yi) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};

				if(2*xi==yi+20) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				if(2*xi+1==yi+20) try{Level.tiles[(x+xi+1) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				
				if(0.5*xi==yi-1) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				if(0.5*xi+1==yi-1) try{Level.tiles[(x+xi+1) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};

				if(-0.5*xi==yi-4&&xi-6<10) try{Level.tiles[(x+xi+5) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				if(-0.5*xi==yi-4&&xi-6<10) try{Level.tiles[(x+xi+1+5) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
			
				if(yi==-4) try{Level.tiles[(x+xi+1) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				
			}}
			id=8;
			generate();
		} else if(id==8){     //Circle For Pentagram
			for(int xi=-20;xi<20;xi++){for(int yi=-20;yi<20;yi++){
				for(int ci=0;ci<25;ci++){
			if(Math.pow(xi, 2)+Math.pow(yi, 2)==140+(ci*1)) try{Level.tiles[(x+xi+5) + (y+yi) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				}
				}}
		} else if(id==9){     //Large Dungeon Hole
			boolean gt = true;
			for(int xi2=-1;xi2<7;xi2++){for(int yi2=-1;yi2<7;yi2++){
				try{
					if(Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_10.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_11.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_12.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_5.getId()){
					} else {
						gt=false;
						//System.out.println(Level.tiles[(x+xi2) + (y+yi2) * Level.width]); //TODO: DELETE THIS
					}
				}catch(ArrayIndexOutOfBoundsException e){};
			}}
			if(gt){
			for(int xi=0;xi<5;xi++){for(int yi=0;yi<5;yi++){
				try{Level.tiles[(x+xi+1) + (y+yi) * Level.width] = Tile.VOID.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}
			for(int yi=0;yi<5;yi++){
				try{Level.tiles[(x) + (y+yi) * Level.width] = Tile.DUNGEON_6.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+6) + (y+yi) * Level.width] = Tile.DUNGEON_4.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}
			for(int xi=0;xi<5;xi++){
				try{Level.tiles[(x+xi+1) + (y-1) * Level.width] = Tile.DUNGEON_8.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+xi+1) + (y+5) * Level.width] = Tile.DUNGEON_2.getId();}catch(ArrayIndexOutOfBoundsException e){};
			
			}
			}
		} else if(id==10){
			boolean gt = true;
			for(int xi2=0;xi2<=5;xi2++){for(int yi2=0;yi2<=5;yi2++){
				try{
					if(Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_10.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_11.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_12.getId()||Level.tiles[(x+xi2) + (y+yi2) * Level.width] == Tile.DUNGEON_5.getId()){
					} else {
						gt=false;
						//System.out.println(Level.tiles[(x+xi2) + (y+yi2) * Level.width]);
					}
				}catch(ArrayIndexOutOfBoundsException e){};
			}}
			if(gt){
				try{Level.tiles[(x) + (y) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+5) + (y) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x) + (y+5) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				try{Level.tiles[(x+5) + (y+5) * Level.width] = Tile.STONE.getId();}catch(ArrayIndexOutOfBoundsException e){};
				
				Level.level.spawn(new Spawner(Level.level, (x*8)+20, (y*8)+20, "Skeleton", 2, 1, 16, 10, 1, true));
			}
		} else if(id==11){     //Medium Ice
			for(int xi=0;xi<4;xi++){for(int yi=0;yi<4;yi++){
					if(Level.tiles[(x+xi) + (y+yi) * Level.width]==Tile.SNOW_1.getId()) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.ICE.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}
		} else if(id==12){     //Big Ice
			for(int xi=-25;xi<25;xi++){for(int yi=-25;yi<25;yi++){
				byte b = Tile.GRASS_1.getId();
				try{b=Level.tiles[(x+xi)+(y+yi)*Level.width];}catch(ArrayIndexOutOfBoundsException e){};
				if(b==Tile.SNOW_1.getId()||(b==Tile.SNOW_2.getId()&&Game.oneIn(3))||(b==Tile.SNOW_3.getId()&&Game.oneIn(3))){
				if(!(Math.pow(xi,2)+Math.pow(yi,2)>=250)) try{Level.tiles[(x+xi) + (y+yi) * Level.width] = Tile.ICE.getId();}catch(ArrayIndexOutOfBoundsException e){};
			}}}
		}
	}
}
