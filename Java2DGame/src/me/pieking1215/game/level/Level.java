package me.pieking1215.game.level;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.Rand;
import me.pieking1215.game.entities.BasicEnemy;
import me.pieking1215.game.entities.Drop;
import me.pieking1215.game.entities.Entity;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.entities.PlayerMP;
import me.pieking1215.game.entities.Sign;
import me.pieking1215.game.entities.XpOrb;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.Font;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.gui.OptionsMenu;
import me.pieking1215.game.level.gen.Dungeon;
import me.pieking1215.game.level.gen.Natural;
import me.pieking1215.game.level.gen.Snowy;
import me.pieking1215.game.level.tiles.Tile;
import me.pieking1215.game.net.sound.Sound;

public class Level {

    public static byte[] tiles;
    public static byte[] tileR;
    public static int width;
    public static int height;
    private List<Entity> entities = new CopyOnWriteArrayList<Entity>();
    
    public static final byte NONE = 0;
    public static final byte RAIN = 1;
    public static final byte SNOW = 2;
    
    public byte weather=NONE;
    private URL url;
    public String imagePath;
    private BufferedImage image;
	public boolean pause = false;
	public static Level level;
	private Natural natural=null;
	public Snowy snowy=null;
	public static String levelsaves;
	public static boolean underground = false; 
	
    public Level(String imagePath, boolean custom) {
    	Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Loading Level: "+imagePath);
    	//System.out.println(custom);
    	level=this;
        if (imagePath != null) {
            this.imagePath = imagePath;
            if(imagePath.equals("Gen_Natural")){
            	imagePath="none";
            	this.generateLevel("Natural");
            }else if(imagePath.equals("Gen_Dungeon")){
            	imagePath="none";
            	this.generateLevel("Dungeon");
            }else if(imagePath.equals("Gen_Snowy")){
            	imagePath="none";
            	this.generateLevel("Snowy");
            }else{
            this.loadLevelFromFile(custom);
            }
        } else {
            Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "No LevelPath Specified");
            Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "The Game Will Load The Fallback Level");
            
            Level.width = 10;
            Level.height = 10;
            this.imagePath = "/levels/Fallback/default";
            this.loadLevelFromFile(false);
        }
    }
    
    public String getWorld() {
    	
    	return imagePath;
    }
    
    public void pause() {
    	pause=true;
    }
    
    public void unPause() {
    	pause=false;
    }
    
    public void spawn(Entity e){
    	this.getEntities().add(e);
    }
    
    public int skeletonCt() {
    	int ct = 0;
    	for (Entity e : entities) {
			if(e instanceof BasicEnemy) {
				BasicEnemy be=(BasicEnemy) e;
				if(be.id==0) ct++;
			}}
    	return ct;
    }
    
    public int zombieCt() {
    	int ct = 0;
    	for (Entity e : entities) {
			if(e instanceof BasicEnemy) {
				BasicEnemy be=(BasicEnemy) e;
				if(be.id==1) ct++;
			}}
    	return ct;
    }
    
    public int xpOrbCt() {
    	int ct = 0;
    	for (Entity e : entities) {
			if(e instanceof XpOrb) {
            ct++;
			}
        }
    	return ct;
    }
    
    public int dropCt() {
    	int ct = 0;
    	for (Entity e : entities) {
			if(e instanceof Drop) {
            ct++;
			}
        }
    	return ct;
    }
    
    public void LevelEnt(String imagePath) {
        if (imagePath != null) {
            this.imagePath = imagePath;
            this.loadLevelFromFile(false);
        } else {
            Font.render("Something Went Wrong", Game.screen, 10, 10, Colors.get(-1, -1, -1, 500), 1, 0);
            Font.render("With Level Loading...", Game.screen, 10, 20, Colors.get(-1, -1, -1, 500), 1, 0);
        }
    }

	private void loadLevelFromFile(boolean custom) {
    	//System.out.println(Level.class.getClassLoader().getResource("levels/"));
    	ReadFile.openFile(this, custom);
    	ReadFile.readFile();
    	ReadFile.closeFile();
    	
        try {
        	boolean online =false;
        	if(online) {
        		url = new URL("http://www.thepierealm.coffeecup.com/java/2D/levels/"+imagePath+"/level.png");
        	}else{
        		url = Level.class.getResource("levels/"+imagePath+"/level.png");
        	}
        	URL u=null;
        	
        	if(!custom){
        		url = Level.class.getResource("/levels/"+imagePath+"/level.png");
        		u = url;
        		//System.out.println(u);
        	}else{
        	
        		/*final JFileChooser fc = new JFileChooser();
        		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		int returnVal = fc.showOpenDialog(null);
        		if(returnVal==JFileChooser.CANCEL_OPTION||returnVal==JFileChooser.ERROR_OPTION){
        			return;
        		}*/
        		//System.out.println("file:\\"+(imagePath+"\\level.png").replace("%20", " "));
        		url = new URL("file:\\"+(imagePath+"\\level.png").replace("%20", " "));
        		u=url;
        	}
        	//System.out.println(u);
        	
        	if(u == null) {
        		Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Could Not Find LevelPath At res"+this.imagePath);
                URL u2 = Level.class.getResource("/levels/Fallback/level.png");
            	if(u2 == null) {
            	Level.width = 10;
                Level.height = 10;
                tiles = new byte[width * height];
                
        		Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Could Not Find Fallback LevelPath At res/levels/Fallback/level.png");
        		Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Crucial files have been moved or deleted. Please redownload the game.");
        		
        		generateLevel("ERROR");
        		
        		Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Generated super error fallback level.");
        		
            	} else {
            		Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Using FallBack Level At res/levels/Fallback/level.png");
            		this.image = ImageIO.read(u2);
                    Level.width = this.image.getWidth();
                    Level.height = this.image.getHeight();
                    tiles = new byte[width * height];
                    this.loadTiles();
            	}
        	} else {
            this.image = ImageIO.read(url);
            Level.width = this.image.getWidth();
            Level.height = this.image.getHeight();
            tiles = new byte[width * height];
            this.loadTiles();
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(weather==RAIN){
			Sound.playSound("RainLoop.wav", 0.5f);
		}
    }

    private void loadTiles() {
        int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
        tileR = new byte[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileCheck: for (Tile t : Tile.tiles) {
                    if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
                        Level.tiles[x + y * width] = t.getId();
                        if(t == Tile.GRASS_1 || t == Tile.GRASS_2 || t == Tile.GRASS_3 || t == Tile.GRASS_4 || t == Tile.STONE || t == Tile.STONE_PATH || t == Tile.GRASS_PATH){
                        	Level.tileR[x + y * width] = (byte) Rand.range(1, 4);
                        }
                        
                        break tileCheck;
                    }
                }
            }
        }
        
    }

    public void levelInit(){
    	if(getWorld().equals("Tutorial")) {
    		
    		spawn(new Drop(this, 328, 144, 2, true, false));
    		spawn(new Drop(this, 336, 148, 1, true, false));
    		
    		spawn(new Drop(this, 471, 187, 2, true, false));
    		spawn(new Drop(this, 476, 194, 1, true, false));
    		
    		spawn(new Drop(this, 528, 136, 5, true, false));
    		spawn(new Drop(this, 530, 139, 5, true, false));
    		spawn(new Drop(this, 533, 133, 5, true, false));
    		spawn(new Drop(this, 531, 136, 5, true, false));
    		spawn(new Drop(this, 531, 136, 1, true, false));
    		spawn(new Drop(this, 533, 133, 1, true, false));
    		
    		spawn(new Sign(this, 40, 32, "Hello There!", "Welcome to my game!"));
    		spawn(new Sign(this, 68, 32, "Use WASD or", "Arrow Keys To move!"));
    		
    		spawn(new Sign(this, 236, 140, "Press space to", "attack enemies!", "They drop XP and", "other useful items."));
    		
    		spawn(new Sign(this, 536, 130, "Press 'E' to", "shoot arrows!"));
    		
    		spawn(new Sign(this, 656, 67, "That's all for", "now! step on the","pad to win!"));
    		
    		spawn(new XpOrb(this, 10, 10, 12));
    		
    		spawn(new XpOrb(this, 10, 15, 5));
    	}else if(getWorld().equals("DungeonDemo")) {
    		//spawnSkeleton(this, 136, 250, 1, 2, 30, false);
    		//spawnSkeleton(this, 210, 404, 1, 3, 30, true);
    		//spawnSkeleton(this, 240, 400, 1, 2, 30, true);
    		//spawnSkeleton(this, 254, 407, 1, 2, 30, true);
    		//spawnSkeleton(this, 390, 130, 1, 5, 30, true);
    		//spawnSkeleton(this, 350, 127, 1, 5, 30, true);
    		//spawnSkeleton(this, 430, 165, 1, 5, 30, true);
    		//spawnSkeleton(this, 434, 205, 1, 5, 30, true);
    		//spawnSkeleton(this, 437, 244, 1, 5, 30, true);
    		//spawnSkeleton(this, 439, 125, 1, 5, 30, true);
    		spawn(new Drop(this, 175, 310, 1, false,false));
    		spawn(new Drop(this, 175, 320, 2, false,false));
    		spawn(new Drop(this, 175, 330, 4, false,false));
    		spawn(new Drop(this, 116, 340, 2, false,false));
    		spawn(new Drop(this, 116, 350, 2, false,false));
    		spawn(new Drop(this, 116, 360, 2, false,false));
    		spawn(new Drop(this, 344, 234, 5, true,false));
    		spawn(new Drop(this, 342, 239, 5, true,false));
    		spawn(new Drop(this, 341, 231, 5, true,false));
    		spawn(new Drop(this, 341, 231, 5, true,false));
    		spawn(new Drop(this, 346, 230, 5, true,false));
    		spawn(new Drop(this, 340, 240, 5, true,false));
    		spawn(new Drop(this, 342, 233, 5, true,false));
    		spawn(new Drop(this, 346, 230, 5, true,false));
    		spawn(new Drop(this, 340, 240, 5, true,false));
    		spawn(new Drop(this, 342, 233, 5, true,false));
    		spawn(new Drop(this, 340, 240, 5, true,false));
    		spawn(new Drop(this, 342, 233, 5, true,false));
    		spawn(new Drop(this, 341, 235, 1, true,false));
    		spawn(new Drop(this, 346, 238, 1, true,false));
    		spawn(new Drop(this, 452, 323, 4, true,false));
    		//spawnSkeleton(this, 470, 400, 1, 4, 30, true);
    		//spawnSkeleton(this, 404, 410, 1, 4, 30, true);
    		//spawnSkeleton(this, 417, 420, 1, 4, 30, true);
    		//spawnSkeleton(this, 459, 390, 1, 4, 30, true);
    		//spawnWinPad(this, 390, 492, false, "DungeonDemo");
    		//spawnSpawner(this, 335, 335, "Skeleton", 3, 2, 24, 10, 2, false);
    		
    	}
    }
    
    //@SuppressWarnings("unused")
    public void saveLevelToFile() {
    	try {
        	final JFileChooser fc = new JFileChooser();
        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        	int returnVal = fc.showOpenDialog(null);
        	if(returnVal==JFileChooser.CANCEL_OPTION||returnVal==JFileChooser.ERROR_OPTION){
        		return;
        	}
        	File file = new File(fc.getSelectedFile()+"\\level.png");
            //ImageIO.write(image, "png", new File(urlStr.replace("file:/", "").replace("%20", " ")));
            ImageIO.write(image, "png", file);
            File f = new File(fc.getSelectedFile()+"\\data.txt");
        	
        	if(!f.exists()){
        		try {
					f.createNewFile();
					BufferedWriter out = new BufferedWriter(new FileWriter(f));
					String nl = System.getProperty("line.separator");
					out.write("Name:"+nl+""+fc.getSelectedFile()+""+nl+"Song:"+nl+"null"+nl+"Player:"+nl+"32"+nl+"32"+nl+"0"+nl+"0"+nl+"0");
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Saved Level To: "+url);
    }

    public void alterTile(int x, int y, Tile newTile) {
    	//System.out.println(newTile.getId());
        Level.tiles[x + y * width] = newTile.getId();
        try{
        	image.setRGB(x, y, newTile.getLevelColor());
        }catch(NullPointerException e){
        		
        }
        //System.out.println(""+x+" "+y+" "+newTile.getId());
    }

    public void generateLevel(String type) {
        switch(type){
        default: 
        	for (int y = 0; y < height; y++) {
            		for (int x = 0; x < width; x++) {
            			Random rand = Rand.getRand();
            			int randomNum = rand.nextInt((4 - 1) + 1) + 1;
            			if (randomNum == 1) {
            				tiles[x + y * width] = Tile.GRASS_1.getId();
            			} else if (randomNum == 2) {
            				tiles[x + y * width] = Tile.GRASS_2.getId();
            			} else if (randomNum == 3){
            				tiles[x + y * width] = Tile.GRASS_3.getId();
            			} else {
            				tiles[x + y * width] = Tile.GRASS_4.getId();
            			}
            		}
        	}
        break;
        case "Natural":
        	width=100;
        	height=100;
        	tiles = new byte[width * height];
        	natural=new Natural();
        break;
        case "Dungeon":
        	width=100;
        	height=100;
        	tiles = new byte[width * height];
        	new Dungeon();
        break;
        case "Snowy":
        	width=100;
        	height=100;
        	tiles = new byte[width * height];
        	snowy=new Snowy();
        break;
        }
        if(weather==RAIN){
			Sound.playSound("RainLoop.wav", 0.5f);
		}
    }

    public synchronized List<Entity> getEntities() {
        return this.entities;
    }

    public void tick() {
        for (Entity e : entities) {
        	if(!pause){
        	if(!((e instanceof Player||(e instanceof PlayerMP)))){
        		if(!Game.inEditMode){
        			e.tickL();
        		}
        	} else {
        		e.tickL();
        	}
        	}
        }
        if(Player.getHp()<0) {
    		for (Entity e : entities) {
    			if(!(e instanceof Player || e instanceof PlayerMP)) {
                entities.remove(e);
    			} else if(e instanceof Player || e instanceof PlayerMP) {
    				Game.isPlayerAlive = false;
    			}
            }
    	}
        if(Player.win){
        	for (Entity e : entities) {
    			if(!(e instanceof Player || e instanceof PlayerMP)) {
                entities.remove(e);
    			}
            }
        }
    	
        for (Tile t : Tile.tiles) {
            if (t == null) {
                break;
            }
            t.tickE();
        }
        if(natural!=null) natural.tick();
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (xOffset < 0)
            xOffset = 0;
        if (xOffset > ((width << 3) - screen.width))
            xOffset = ((width << 3) - screen.width);
        if (yOffset < 0)
            yOffset = 0;
        if (yOffset > ((height << 3) - screen.height))
            yOffset = ((height << 3) - screen.height);
        if(screen.width > width * 8) xOffset = (screen.width - (width * 8)) / 2 * -1; 
        if(screen.height > height * 8) yOffset = (screen.height - (height * 8)) / 2 * -1;
        screen.setOffset(xOffset, yOffset);

        for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
            for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
                getTile(x, y).renderE(screen, this, x << 3, y << 3, 1, true);
            }
        }
        String imgPath = "/levels/"+imagePath+"/level.png";
    	if(imgPath.equals("/levels/DungeonDemo/level.png")&&!OptionsMenu.hideGUI) {
    		Font.render("Welcome To My Game!", Game.screen, 22, 20, Colors.get(-1,-1,-1,353), 1, 0); //TODO: move font renders to DungeonDemo/data.txt
    		Font.render("WASD - Move", Game.screen, 55, 30, Colors.get(-1,-1,-1,353), 1, 0);
    		Font.render("SPACE - ", Game.screen, 155, 180, Colors.get(-1,-1,-1,533), 1, 0);
    		Font.render("Attack", Game.screen, 158, 190, Colors.get(-1,-1,-1,533), 1, 0);
    		Font.render("Enemy", Game.screen, 168, 280, Colors.get(-1,-1,-1,353), 1, 0);
    		Font.render("Drops:", Game.screen, 165, 290, Colors.get(-1,-1,-1,353), 1, 0);
    		Font.render("30%", Game.screen, 180, 305, Colors.get(-1,-1,-1,444), 1, 0);
    		Font.render("10%", Game.screen, 180, 315, Colors.get(-1,-1,-1,444), 1, 0);
    		Font.render("20%", Game.screen, 180, 325, Colors.get(-1,-1,-1,444), 1, 0);
    		Font.render("This Is A", Game.screen, 278, 416, Colors.get(-1,-1,-1,522), 1, 0);
    		Font.render("Challenge", Game.screen, 278, 423, Colors.get(-1,-1,-1,522), 1, 0);
    		Font.render("Room!", Game.screen, 278, 430, Colors.get(-1,-1,-1,522), 1, 0);
    		Font.render("E -Shoot", Game.screen, 290, 270, Colors.get(-1,-1,-1,454), 1, 0);
    		Font.render("Arrow", Game.screen, 314, 278, Colors.get(-1,-1,-1,454), 1, 0);
    		Font.render("Q -", Game.screen, 450, 338, Colors.get(-1,-1,-1,454), 1, 0);
    		Font.render("Place", Game.screen, 450, 345, Colors.get(-1,-1,-1,454), 1, 0);
    		Font.render("Bomb", Game.screen, 450, 352, Colors.get(-1,-1,-1,454), 1, 0);
    	}
    }

    public void renderEntities(Screen screen) {
    	for(int yi=0;yi<height*8;yi++){
    		for (Entity e : getEntities()) {
    			if(e instanceof Player|| e instanceof PlayerMP){
    				
    				if((e.y-1==yi)){
        				if(!OptionsMenu.hideGUI||e instanceof Player||e instanceof PlayerMP ||e instanceof Sign)e.render(screen);
        			}
    				
    			}else if((e.y==yi)){
    				if(!OptionsMenu.hideGUI||e instanceof Player||e instanceof PlayerMP ||e instanceof Sign)e.render(screen);
    			}
    		}
    	}
    	for (Entity e : getEntities()) {
			if(e instanceof Sign){
				if(!OptionsMenu.hideGUI||e instanceof Player||e instanceof PlayerMP){
					((Sign)e).renderOver(screen);
				}
			}
		}
    }

    public Tile getTile(int x, int y) {
        if (0 > x || x >= width || 0 > y || y >= height)
            return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }

    public synchronized void addEntity(Entity entity) {
        this.getEntities().add(entity);
    }
    
    public synchronized void removeEntity(Entity entity) {
        this.getEntities().remove(entity);
    }
    
    public synchronized void removeAll() {
    	for (Entity e : entities) {
    		this.getEntities().remove(e);
        }
        
    }

    public synchronized void removePlayerMP(String username) {
        int index = 0;
        for (Entity e : getEntities()) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        this.getEntities().remove(index);
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (Entity e : getEntities()) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public synchronized void movePlayer(String username, int x, int y, int numSteps, boolean isMoving, int movingDir) {
    	//System.out.println("move player "+ username+" "+isMoving);
        int index = getPlayerMPIndex(username);
        PlayerMP player = (PlayerMP) this.getEntities().get(index);
        player.x = x;
        player.y = y;
        player.setMoving(isMoving);
        player.setNumSteps(numSteps);
        player.setMovingDir(movingDir);
        //System.out.println(player);
    }

	public List<PlayerMP> getOnlinePlayers() {
		List<PlayerMP> list = new ArrayList<PlayerMP>();
		for(Entity e : getEntities()){
			if(e instanceof PlayerMP){
				list.add((PlayerMP) e);
			}
		}
		
		return list;
	}
}