package me.pieking1215.game.level.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.TileEffect.TileEffectType;

public abstract class Tile {
	private static int tileMakeIndex=0;
	
	public static int tileMax=0;
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(nextIndex(),0,0,Colors.get(000,-1,-1,-1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(nextIndex(),0,4,Colors.get(-1,111,-1,-1), 0xff555555);
	public static final Tile STONE_PATH = new BasicTile(nextIndex(),1,4,Colors.get(-1,111,-1,-1), 0xff414141,false);
	public static final Tile GRASS_PATH = new BasicTile(nextIndex(),2,4,Colors.get(-1,111,-1,-1), 0xff355302,false);
	public static final Tile BUSH = new BasicSolidTile(nextIndex(),3,4,Colors.get(-1,111,-1,-1), 0xff0F3300);
	public static final Tile GRASS_4 = new BasicTile(nextIndex(),5,0,Colors.get(-1,131,141,454), 0xff00cc00,false);
	public static final Tile GRASS_1 = new BasicTile(nextIndex(),2,0,Colors.get(-1,131,141,454), 0xff00ff00,false);
	public static final Tile GRASS_2 = new BasicTile(nextIndex(),3,0,Colors.get(-1,131,141,454), 0xff00ee00,false);
	public static final Tile GRASS_3 = new BasicTile(nextIndex(),4,0,Colors.get(-1,131,141,454), 0xff00dd00,false);
	public static final Tile WATER = new AnimatedTile(nextIndex(), new int[][]{{0,5},{1,5},{2,5},{1,5}},Colors.get(-1,004,115,225), 0xff0000ff,1000,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile BRIDGE_S_NS = new BasicTile(nextIndex(),3,5,Colors.get(-1,131,141,454), 0xff704000,false);
	public static final Tile BRIDGE_S_EW = new BasicTile(nextIndex(),4,5,Colors.get(-1,131,141,454), 0xff704001,false);
	public static final Tile BRIDGE_B_NS_L = new BasicTile(nextIndex(),5,5,Colors.get(-1,131,141,454), 0xff704002,false);
	public static final Tile BRIDGE_B_NS_M = new BasicTile(nextIndex(),8,4,Colors.get(-1,131,141,454), 0xff704003,false);
	public static final Tile BRIDGE_B_NS_R = new BasicTile(nextIndex(),6,5,Colors.get(-1,131,141,454), 0xff704004,false);
	public static final Tile BRIDGE_B_EW_T = new BasicTile(nextIndex(),7,4,Colors.get(-1,131,141,454), 0xff704005,false);
	public static final Tile BRIDGE_B_EW_M = new BasicTile(nextIndex(),8,5,Colors.get(-1,131,141,454), 0xff704006,false);
	public static final Tile BRIDGE_B_EW_B = new BasicTile(nextIndex(),7,5,Colors.get(-1,131,141,454), 0xff704007,false);
	public static final Tile STONE_WALL_N = new BasicSolidTile(nextIndex(),0,1,Colors.get(111,222,333,444), 0xffDDDDDD);
	public static final Tile STONE_WALL_S = new BasicSolidTile(nextIndex(),1,1,Colors.get(111,222,333,444), 0xffCCCCCC);
	public static final Tile STONE_WALL_E = new BasicSolidTile(nextIndex(),2,1,Colors.get(111,222,333,444), 0xffAAAAAA);
	public static final Tile STONE_WALL_W = new BasicSolidTile(nextIndex(),3,1,Colors.get(111,222,333,444), 0xffBBBBBB);
	public static final Tile STONE_WALL_IN_SW = new BasicSolidTile(nextIndex(),0,2,Colors.get(111,222,333,444), 0xff999999);
	public static final Tile STONE_WALL_IN_SE = new BasicSolidTile(nextIndex(),1,2,Colors.get(111,222,333,444), 0xff888888);
	public static final Tile STONE_WALL_IN_NE = new BasicSolidTile(nextIndex(),2,2,Colors.get(111,222,333,444), 0xff777777);
	public static final Tile STONE_WALL_IN_NW = new BasicSolidTile(nextIndex(),3,2,Colors.get(111,222,333,444), 0xff666666);
	public static final Tile STONE_WALL_OUT_SW = new BasicSolidTile(nextIndex(),0,3,Colors.get(444,333,222,111), 0xff565656);
	public static final Tile STONE_WALL_OUT_SE = new BasicSolidTile(nextIndex(),1,3,Colors.get(444,333,222,111), 0xff444444);
	public static final Tile STONE_WALL_OUT_NE = new BasicSolidTile(nextIndex(),2,3,Colors.get(444,333,222,111), 0xff333333);
	public static final Tile STONE_WALL_OUT_NW = new BasicSolidTile(nextIndex(),3,3,Colors.get(444,333,222,111), 0xff222222);
	public static final Tile DUNGEON_1 = new BasicTile(nextIndex(),4,1,Colors.get(-1,112,112,334), 0xff111133,false);
	public static final Tile DUNGEON_2 = new BasicTile(nextIndex(),5,1,Colors.get(-1,112,113,334), 0xff111134,false);
	public static final Tile DUNGEON_3 = new BasicTile(nextIndex(),6,1,Colors.get(-1,112,113,334), 0xff111135,false);
	public static final Tile DUNGEON_4 = new BasicTile(nextIndex(),4,2,Colors.get(-1,112,113,334), 0xff111136,false);
	public static final Tile DUNGEON_5 = new BasicTile(nextIndex(),5,2,Colors.get(-1,112,113,334), 0xff111137,false);
	public static final Tile DUNGEON_6 = new BasicTile(nextIndex(),6,2,Colors.get(-1,112,113,334), 0xff111138,false);
	public static final Tile DUNGEON_7 = new BasicTile(nextIndex(),4,3,Colors.get(-1,112,113,334), 0xff111139,false);
	public static final Tile DUNGEON_8 = new BasicTile(nextIndex(),5,3,Colors.get(-1,112,113,334), 0xff111140,false);
	public static final Tile DUNGEON_9 = new BasicTile(nextIndex(),6,3,Colors.get(-1,112,113,334), 0xff111141,false);
	public static final Tile DUNGEON_10 = new BasicTile(nextIndex(),4,4,Colors.get(-1,112,113,334), 0xff111142,false);
	public static final Tile DUNGEON_11 = new BasicTile(nextIndex(),5,4,Colors.get(-1,112,113,334), 0xff111143,false);
	public static final Tile DUNGEON_12 = new BasicTile(nextIndex(),6,4,Colors.get(-1,112,113,334), 0xff111144,false);
	public static final Tile DUNGEON_13 = new BasicTile(nextIndex(),7,1,Colors.get(004,112,112,334), 0xff111145,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_14 = new BasicTile(nextIndex(),8,1,Colors.get(004,112,113,334), 0xff111146,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_15 = new BasicTile(nextIndex(),9,1,Colors.get(004,112,113,334), 0xff111147,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_16 = new BasicTile(nextIndex(),7,2,Colors.get(004,112,113,334), 0xff111148,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_17 = new BasicTile(nextIndex(),8,2,Colors.get(004,112,113,334), 0xff111149,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_18 = new BasicTile(nextIndex(),9,2,Colors.get(004,112,113,334), 0xff111150,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_19 = new BasicTile(nextIndex(),7,3,Colors.get(004,112,113,334), 0xff111151,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_20 = new BasicTile(nextIndex(),8,3,Colors.get(004,112,113,334), 0xff111152,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile DUNGEON_21 = new BasicTile(nextIndex(),9,3,Colors.get(004,112,113,334), 0xff111153,false,effects(new TileEffect(TileEffectType.WATER),new TileEffect(TileEffectType.SLOW)));
	public static final Tile FIRE = new AnimatedTile(nextIndex(), new int[][]{{0,6},{1,6},{2,6},{3,6},{4,6},{5,6},{6,6},{7,6},{8,6},{9,6},{10,6},{11,6},{12,6}},Colors.get(-1,310,420,530), 0xffff7b00, 100,effects(new TileEffect(TileEffectType.FIRE)));
	public static final Tile SNOW_1 = new BasicTile(nextIndex(),6,0,Colors.get(555,333,444,555), 0xffeeeeee,false);
	public static final Tile SNOW_2 = new BasicTile(nextIndex(),7,0,Colors.get(555,333,444,555), 0xffdddddd,false);
	public static final Tile SNOW_3 = new BasicTile(nextIndex(),8,0,Colors.get(555,222,333,444), 0xffcccccc,true);
	public static final Tile ICE = new BasicTile(nextIndex(),9,0,Colors.get(555,223,334,335), 0xff00ffff,false);
	public static final Tile STOP_ICE = new BasicTile(nextIndex(),9,0,Colors.get(555,223,334,335), 0xff00aaaa,false);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	protected List<TileEffect> effects;
	private int levelColor;
	protected int light;
	
	public Tile(int id,boolean isSolid, boolean isEmitter, int levelColor) {
		this(id, isSolid, isEmitter, levelColor, null);
	}
	
	public Tile(int id,boolean isSolid, boolean isEmitter, int levelColor, List<TileEffect> effects) {
		this.id = (byte) id;
		if(tiles[id] !=null) throw new RuntimeException("Duplicate tile id on" + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id]=this;
		if(isEmitter){
			this.light=9; //0 -> 9
		}else{
			this.light=0;
		}
		if(effects!=null){
			this.effects=effects;
		}
	}
	
	public byte getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	
	public List<TileEffect> getEffects(){
		return effects;
	}
	
	public void addEffect(TileEffect e){
		if(effects!=null){
			effects.add(e);
		}
	}
	
	public void removeEffect(TileEffect e){
		if(effects!=null){
			effects.remove(e);
		}
	}
	
	public boolean hasEffect(TileEffectType e){
		if(effects!=null){
			for(TileEffect effect : effects){
				if(effect.getType()==e){
					return true;
				}
			}
		}
		return false;
	}
	
	public int getLight() {
		return light;
	}
	
	public int getLevelColor() {
		return levelColor;
	}
	
	public static Tile getById(int id) {
		for(Tile t: tiles) {
			if(t.getId()<=tileMax) {
			if(t.getId()==id){
				return t;
			}
			}
		}
		return Tile.VOID;
	}
	
	public int calcLightLevel(int xTile, int yTile){
		
		if(isEmitter()){
			return 9;
		}else{
			int tileN,tileS,tileE,tileW;
			//System.out.println(tileN = getById(Level.tiles[(xTile)+(yTile+1)*Level.width]).getLight());
			//System.out.println("=====");
			//System.out.println(xTile+" "+yTile);
			try{tileN = getById(Level.tiles[(xTile)+(yTile+1)*Level.width]).getLight();}catch(ArrayIndexOutOfBoundsException e){tileN=0;};
			try{tileS = getById(Level.tiles[(xTile)+(yTile-1)*Level.width]).getLight();}catch(ArrayIndexOutOfBoundsException e){tileS=0;};
			try{tileE = getById(Level.tiles[(xTile+1)+(yTile)*Level.width]).getLight();}catch(ArrayIndexOutOfBoundsException e){tileE=0;};
			try{tileW = getById(Level.tiles[(xTile-1)+(yTile)*Level.width]).getLight();}catch(ArrayIndexOutOfBoundsException e){tileW=0;};
			
			int[] myArray = new int[]{tileN,tileS,tileE,tileW};
			Arrays.sort(myArray);
			int max = myArray[myArray.length - 1];
			
			//String s=tileN+" "+tileS+" "+tileE+" "+tileW;
			
			//if(!s.equals("0 0 0 0")) System.out.println(s);
			
			if(max-1<=0){
				return 0;
			}else{
				return max-1;
			}
			
		}
	}
	
	private static int nextIndex(){
		int i = tileMakeIndex;
		tileMax=i;
		tileMakeIndex++;
		return i;
	}
	
	public static List<TileEffect> effects(TileEffect... ef){
		List<TileEffect> effectList = new ArrayList<TileEffect>();
		
		for(TileEffect e : ef){
			effectList.add(e);
		}
		return effectList;
	}
	
	public void tickE(){
		tick();
	}
	
	public abstract void tick();
	
	public void renderE(Screen screen, Level level, int x, int y, int scale, boolean doFlip){
		
		render(screen, level, x, y, scale, doFlip);
		
		if(effects!=null){
			for(TileEffect e : effects){
				e.render(screen,level,x,y);
			}
		}
			
	}
	
	public abstract void render(Screen screen, Level level, int x, int y, int scale, boolean doFlip);
}
