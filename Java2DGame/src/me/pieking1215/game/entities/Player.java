package me.pieking1215.game.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.Random;

import javax.swing.JOptionPane;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.InputHandler;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Colors;
import me.pieking1215.game.gfx.HUD;
import me.pieking1215.game.gfx.Particle;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.gui.LevelSelect;
import me.pieking1215.game.gui.OptionsMenu;
import me.pieking1215.game.inventory.Inventory;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.Location;
import me.pieking1215.game.level.tiles.Tile;
import me.pieking1215.game.level.tiles.TileEffect.TileEffectType;
import me.pieking1215.game.net.packets.Packet02Move;
import me.pieking1215.game.net.sound.Sound;

public class Player extends Mob {

	public static Screen screen;
	
	public static boolean shouldClip=true;
	
	public static int[] hpOfs= new int[24];
	
    private InputHandler input;
    private int color = Colors.get(-1, 111, 145, 543);
    private int scale = 1;
    protected boolean isSwimming = false;
    private int tickCount = 0;
    private String username;
    private static int playerX;
    private static int playerY;
    private static int xOffset;
    private static int yOffset;
    private static int xp;
    private static int maxHp=24;
    private static int hp = maxHp;
    public static boolean spacePress;
    public static int damTimer = 0;
    public static int colorChange;
	private int atkCooldown=0;
	private int bombCooldown=0;
    public Game game;
	private int arwCooldown = 0;
	private int half=0;
	public static int tileChangeId=0;
	public static int colTimer=0;
	public static int randCol=200;
	public static int color2=0;
	public static int arwCt;
	public static boolean win=false;
	public static String restart=null;
	
	public static int winTimer=0;
	
	public static boolean restartE = false;
	public static boolean restartE2 = false;
	public static boolean restartEE = false;
	
	public static double fireTime=0;
	
	public static int xplevel=0;
	
	private static boolean slide=false;
	
	public static Level level2;

	public static int xpToLevel =10;
	
	private static String[] chat = new String[8];
	private static int[] chatTime = new int[chat.length];
	
    public Player(Level level, int x, int y, InputHandler input, String username) {
        super(level, "Player", x, y, 1);
        this.input = input;
        this.username = username;
        Player.level2=level;
        for(int i=0;i<chat.length;i++){
        	if(chat[i]==null){
        		chat[i]="";
        		chatTime[i]=0;
        	}
        }
    }

    public static void restart(boolean edit) {
    	restartE=true;
    	restartEE=edit;
    }
    
	public void tick() {
		/*System.out.println(winTimer);
		if(win){
			if(winTimer==500){
				Game.game.frame.setVisible(false);
				new LevelSelect();
				win=false;
				winTimer=0;
			}
			winTimer++;
		}else{
			if(winTimer>0){
				winTimer=0;
			}
		}*/
		
		if(Player.getHp()<=0) {
        	if(Player.getHp() >-100){
        	Random rand = Rand.getRand();
        	int hitR = rand.nextInt((2 - 1) + 1) + 1;
        	Sound.stopSounds();
        	if(hitR==1) {
        		Sound.playSound("Fail1.wav", 0);
        	}else{
        		Sound.playSound("Fail2.wav", 0);
        	}
    		Player.setHp(-200);
        	}
        	
        }
		
		if(xp>=xpToLevel){
			xpToLevel*=1.5;
			xp=0;
			xplevel++;
			Sound.playSound("Levelup.wav", 0.5f);
		}
		
		if(half==4){
			half=0;
		} else {
			half++;
		}
		
    	//System.out.println(""+getX()+" "+getY());
        int xa = 0;
        int ya = 0;
        playerX = x;
        playerY = y;
        
        if (input != null) {
        	Tile t=Tile.STONE;
        	try{
        		t = Tile.getById(Level.tiles[((int) Math.floor(((x-2) +  (double)8 / 2) / 8))+((int) Math.floor(((y) +  (double)8 / 2) / 8)) *Level.width]);
        	}catch(ArrayIndexOutOfBoundsException e){
        		
        	}catch(NullPointerException e){
        		
        	}
            slide=false;
        	if(t.getId()==Tile.ICE.getId()&&!Game.inEditMode){
            	if(movingDir==0){
            		if(!Game.inEditMode) ya--;
            		if(hasCollided(xa, ya)){
            		}else{
                	slide=true;
            		}
            	}else if(movingDir==1){
            		if(!Game.inEditMode) ya++;
            		if(hasCollided(xa, ya)){
            		}else{
                	slide=true;
            		}
            	}else if(movingDir==2){
            		if(!Game.inEditMode)xa--;
            		if(hasCollided(xa, ya)){
            		}else{
                	slide=true;
            		}
            	}else if(movingDir==3){
            		if(!Game.inEditMode)xa++;
            		if(hasCollided(xa, ya)){
            		}else{
                	slide=true;
            		}
            	}
            }
            if(!slide){
            	if((getLoc().add(4, 4).getTile().hasEffect(TileEffectType.SLOW)&&Game.game.tickCount%2==0)||!getLoc().add(4, 4).getTile().hasEffect(TileEffectType.SLOW)||(Game.inEditMode||win)){
            		if (input.up.isPressed()) { //NSEW -> 0123
            			if(!Game.inEditMode) ya--;
            			if(Game.inEditMode&&half==1) ya=ya-8;
            		}
            		if (input.down.isPressed()) {
            			if(!Game.inEditMode) ya++;
            			if(Game.inEditMode&&half==1) ya=ya+8;
            		}
            		if (input.left.isPressed()) {
            			if(!Game.inEditMode)xa--;
            			if(Game.inEditMode&&half==1) xa=xa-8;
            		}
            		if (input.right.isPressed()) {
            			if(!Game.inEditMode)xa++;
            			if(Game.inEditMode&&half==1)xa=xa+8;
            		}
            	}
            }
            
            //System.out.println(""+input.enterPressed+" "+input.spacePressed+" "+tileChangeId);
            if (input.shiftPressed == 1 && atkCooldown==0) {
            	//this.color2++;
            	//System.out.println(""+color2);
            	if(Game.inEditMode && atkCooldown==0){
            		tileChangeId--;
            		if(tileChangeId==-1) {
            			tileChangeId=Tile.tileMax;
            		}
            		atkCooldown=20;
            		//System.out.println(""+xR+" "+yR+" "+tileChangeId);
            	}else{
            	if(atkCooldown==0) {
            		input.shiftPressed = 2;
                    atkCooldown=10;
            	}
            	}
            } else {
            }
            if (input.enterPressed == 1 && atkCooldown==0) {
            	//this.color2++;
            	//System.out.println(""+color2);
            	if(Game.inEditMode && atkCooldown==0){
            		tileChangeId++;
            		if(tileChangeId==Tile.tileMax+1) {
            			tileChangeId=0;
            		}
            		atkCooldown=20;
            		//System.out.println(""+xR+" "+yR+" "+tileChangeId);
            	}else{
            	if(atkCooldown==0) {
            		input.enterPressed = 2;
                    atkCooldown=10;
            	}
            	}
            }
            
            if(input.mousePressed && LevelSelect.inEditMode){
            	doEdit(x, y, level);
            }
            
            if (input.spacePressed == 1) {
            	
            	doEdit(x, y, level);
            	
            	if(atkCooldown==0){
            		spacePress=true;
            		atkCooldown=40;
            	} else {
            		spacePress = false;
            	}
            } else {
            	spacePress = false;
            }
            if (input.qPressed == 1) {
            	if(bombCooldown==0) {
                    input.qPressed = 2;
                    if(Inventory.getItemByName("BOMB")!=null){
                    	if((Inventory.getItemByName("BOMB").getCount())>0){
                    		level.spawn(new Drop(level, x, y, 3, false, false));
                    		Inventory.getItemByName("BOMB").addCount(-1);
                    		bombCooldown=20;
                    	}
                    }
            	}
            }
            //System.out.println(""+atkCooldown+" "+spacePress);
            if (input.ePressed == 1) {
            	if(arwCooldown==0) {
                    input.ePressed = 2;
                    if(Inventory.getItemByName("ARROW")!=null){
                    	if(Inventory.getItemByName("ARROW").getCount()>0){
                    		level.spawn(new Arrow(level, x, y, movingDir));
                    		Inventory.getItemByName("ARROW").addCount(-1);
                    		arwCooldown=20;
                    		Random rand = Rand.getRand();
                    		int hitR = rand.nextInt((2 - 1) + 1) + 1;
                    		if(hitR==1) Sound.playSound("ArrowShot1.wav", 0);
                    		if(hitR==2) Sound.playSound("ArrowShot2.wav", 0);
                    	}
                    }
            	}
            }
            //System.out.println("1"+game);
            if (input.rPressed == 1) {
            	//System.out.println("Player");
                    input.tPressed = 2;
                    //System.out.println("1"+game);
                    //game.restart("Testing1");
            }
            if(bombCooldown>0) bombCooldown--;
            if(atkCooldown>0) atkCooldown--;
            if(arwCooldown>0) arwCooldown--;
            if (input.tPressed == 1) {
            	Game.togglePause();
                input.tPressed = 2;
                String cmd="";
                Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Please keep in mind, the command prompt is temporary and will likely be changed, so it may act up.");
                try{
                	cmd = new String(JOptionPane.showInputDialog("Enter Command To Run"));
                }catch(NullPointerException e){
                	cmd="";
                }
                if(cmd.equalsIgnoreCase("editreload")){
                	//Game.game.restart(level.getWorld(), true);
                	Player.restart(true);
                }
                if(cmd.equalsIgnoreCase("reload")){
                	//Game.game.restart(level.getWorld(), true);
                	Player.restart(false);
                }
                if(cmd.equalsIgnoreCase("editload")){
                	cmd = new String(JOptionPane.showInputDialog("Load What Level?"));
                	Game.game.restart(cmd, false, Game.custom);
                }
                if(cmd.equalsIgnoreCase("editnew")){
                	cmd = new String(JOptionPane.showInputDialog("What Do You Want It's Filename To Be?"));
                	File file = new File(Player.class.getResource("levels/"+cmd)+"");
                	boolean exists = new File(Player.class.getResource("levels/"+cmd)+"").exists();
                	System.err.println(exists);
                	if (!file.exists()) {
                			if(file.mkdir()){
                			System.out.println("[Info] Created New Level: "+cmd+"!");
                			}
                		} else {
                			System.out.println("[Severe] A Level Already Exists By The Name: "+cmd+"");
                			int cmd1 = JOptionPane.showConfirmDialog(null, "A Level Already Exists With The Name: "+cmd+". Do You Want To Override It?");
                			if(cmd1==0){
                			if(file.mkdir()){
                        	System.out.println("[Info] Created New Level: "+cmd+"!");
                        	}else{
                        		Game.debug(DebugLevel.SEVERE, DebugPriority.NORMAL, " FAILED TO CREATE LEVEL.");
                        	}
                			} else if(cmd1==1||cmd1==2){
                				
                			}
                	}
                }
                if (cmd.equalsIgnoreCase("Spawn")) {
                	cmd = new String(JOptionPane.showInputDialog("Spawn What?"));
                	if (cmd.equalsIgnoreCase("Skeleton")) {
                		BasicEnemy enemy = new BasicEnemy(level,x,y,1,10,50,true,0);
                        level.addEntity(enemy);
                        System.out.println("[Info] Spawned Skeleton At "+x+", "+y+".");
                    } else if (cmd.equalsIgnoreCase("XpOrb")) {
                    	String valueStr = JOptionPane.showInputDialog("Value?");
                    	if(valueStr.matches("[0-9]+") && valueStr.length()>0 && !(valueStr.equals(null))) {
                        int value = Integer.parseInt(valueStr);
                		XpOrb xpOrb = new XpOrb(level,x,y,value);
                        level.addEntity(xpOrb);
                        System.out.println("[Info] Spawned XpOrb At "+x+", "+y+".");
                    	}
                    }
                } else if (cmd.equalsIgnoreCase("SetHp")) {
                	cmd = JOptionPane.showInputDialog("How Much HP?");
                	if(cmd.matches("[0-9]+") && cmd.length()>0 && !(cmd.equals(null))) {
                	int cmd1 = Integer.parseInt(cmd);
                	hp = cmd1;
                	}
                } else if (cmd.equalsIgnoreCase("EditOn")) {
                	Game.inEditMode=true;
                	LevelSelect.inEditMode=true;
                	Sound.stopSounds();
                } else if (cmd.equalsIgnoreCase("EditOff")) {
                	Game.inEditMode=false;
                	LevelSelect.inEditMode=false;
                } else if (cmd.equalsIgnoreCase("EditSave")) {
                	level.saveLevelToFile();
                } else if (cmd.equalsIgnoreCase("EditNew")) {
                	System.out.println("Working On It!");
                }
                Game.togglePause();
            }
        }
        //System.out.println(restartE);
        if(restartE2){
        	Game.game.restart(Game.toLevel, false, Game.custom);
        	
        }
        if(restartE){
        	Game.game.restart(level.getWorld(), restartEE, Game.custom);
        	
        }
        
        if(hp>0) {
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            isMoving = true;
            Packet02Move packet = new Packet02Move(this.getUsername(), this.x, this.y, this.numSteps, this.isMoving,this.movingDir);
            packet.writeData(Game.game.socketClient);
        } else {
            isMoving = false;
            //if(!Game.game.isApplet){
            Packet02Move packet = new Packet02Move(this.getUsername(), this.x, this.y, this.numSteps, this.isMoving,this.movingDir);
            packet.writeData(Game.game.socketClient);
            //}
        }
        if (getLoc().add(4, 4).getTile().hasEffect(TileEffectType.WATER)) {
            if(!(Game.inEditMode||win)) isSwimming = true;
        }
        if (isSwimming && !(getLoc().add(4, 4).getTile().hasEffect(TileEffectType.WATER))) {
            isSwimming = false;
        }
        tickCount++;
        //addXp(10);=
        if(hp<=0) {
        	isMoving=false;
        	xa=0;
        	ya=0;
        }
        }
        
        if(getLoc().add(4, 4).getTile().hasEffect(TileEffectType.FIRE)&&!(Game.inEditMode)&&fireTime<500){
        	fireTime++;
        } else if(fireTime>0){
        	fireTime-=0.25;
        }
        if(fireTime%25==0&&fireTime!=0&&Game.isPlayerAlive&&fireTime!=500){
        	damage(1);
        	Sound.playSound("Hit1.wav", 0.5f);
        }
        if(fireTime%5==0&&fireTime!=0&&Game.isPlayerAlive&&!LevelSelect.inEditMode){
        	int xa2 = Rand.range(0, 8) - 4;
        	int ya2 = Rand.range(0, 16) - 8;
        	level.spawn(new Particle(level, x+xa2, y+ya2, "Flame", 1, 530, ""));
        }
        if(getLoc().add(4, 4).getTile().hasEffect(TileEffectType.WATER) && fireTime>0){
        	Sound.playSound("Extinguish.wav", 0.5f);
        	for(int i=0; i<Rand.range(3, 5); i++){
        		
        		int xa3=Rand.range(0, 8) - 4;
        		int ya3=Rand.range(0, 8) - 4;
        		level.spawn(new Particle(level, x+xa3, y+ya3, "Poof", 1, 444, null));
        	}
        	fireTime=0;
        }
        //System.out.println(fireTime);
    }
	public static void doEdit(int x, int y, Level level) {
		
		if(Game.inEditMode){
    		int yR = (int) Math.floor((y +  (double)8 / 2) / 8) * 8;
    		int xR = (int) Math.floor((x +  (double)8 / 2) / 8) * 8;
    		
    		if(tileChangeId==Tile.tileMax+1) {
    			tileChangeId=0;
    		}
    		try{
    			level.alterTile(xR/8, yR/8, Tile.getById(tileChangeId));
    		}catch(ArrayIndexOutOfBoundsException e){
    			Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "You can only edit tiles inside the map.");
    		}
    		
    	}
		
	}

	public void addXp(int xpToAdd) {
		while(xpToAdd > 0) {
			xp++;
			xpToAdd--;
		}
	}
	
	public static boolean damage(int damage) {
		if(damTimer==0){
			Random rand = Rand.getRand();
			for(int i=0; i<hpOfs.length; i++){
				hpOfs[i] = rand.nextInt(2)-1;
			}
		hp = hp-damage;
		damTimer=50;
		return true;
		}
		return false;
		//System.out.println(""+damTimer);
	}
	
	public static int getXp() {
		return xp;
	}
	
	public static int getHp(){
		return hp;
	}
	
	public static void setHp(int toHp){
		hp=toHp;
	}
	
	public static void addHp(int toHp){
		hp+=toHp;
	}
	
	public static int getMaxHp(){
		return maxHp;
	}
	
	public static void setXp(int toXp){
		xp=toXp;
	}
	 
    public static int getX() {
    	return playerX;
    }
    
    public static int getY() {
    	return playerY;
    }
    
    public static Location getLocation(){
    	return Game.game.player.getLoc();
    }
    
    public void render(Screen screen) { 
    	
    	Player.screen=screen;
        int xTile = 0;
        int yTile = 28;
        int walkingSpeed = 4;
        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;
        
        if(slide){
        	numSteps=100;
        }
        
        if(!Game.inEditMode && shouldClip){
        	
        if (movingDir == 1) {
            xTile += 2;
        } else if (movingDir > 1) {
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = (movingDir - 1) % 2;
        }

        int modifier = 8 * scale;
        xOffset = x - modifier / 2;
        yOffset = y - modifier / 2 - 4;
        //System.out.println("" + xOffset + " " + yOffset);
        if (isSwimming) {
            int waterColor = 0;
            yOffset += 4;
            if (tickCount % 60 < 15) {
                waterColor = Colors.get(-1, -1, 225, -1);
            } else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
                yOffset -= 1;
                waterColor = Colors.get(-1, 225, 115, -1);
            } else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
                waterColor = Colors.get(-1, 115, -1, 225);
            } else {
                yOffset -= 1;
                waterColor = Colors.get(-1, 225, 115, -1);
            }
            screen.renderOver(xOffset, yOffset + 3, 0 + 27 * 32, waterColor, 0x00, 1);
            screen.renderOver(xOffset + 8, yOffset + 3, 0 + 27 * 32, waterColor, 0x01, 1);
        }
        if(!isMoving && movingDir > 1) {
        	xTile=4;
        screen.renderOver(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
        screen.renderOver(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
        if (!isSwimming) {
            screen.renderOver(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                    flipBottom, scale);
            screen.renderOver(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                    * 32, color, flipBottom, scale);
        }
        } else if(!isMoving && movingDir == 0) {
        	xTile=0;
            screen.renderOver(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
            screen.renderOver(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
            if (!isSwimming) {
                screen.renderOver(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                        flipBottom, scale);
                screen.renderOver(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                        * 32, color, flipBottom, scale);
            }
        } else if(!isMoving && movingDir == 1) {
        	xTile=2;
            screen.renderOver(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
            screen.renderOver(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
            if (!isSwimming) {
                screen.renderOver(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                        flipBottom, scale);
                screen.renderOver(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                        * 32, color, flipBottom, scale);
            }
        } else if(isMoving) {
            screen.renderOver(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
            screen.renderOver(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
            if (!isSwimming) {
                screen.renderOver(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color,
                        flipBottom, scale);
                screen.renderOver(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1)
                        * 32, color, flipBottom, scale);
            }
        }
        } else if(Game.inEditMode&&!OptionsMenu.hideGUI){
        	x=(int) (Math.floor((x +  (double)8 / 2) / 8) * 8);
        	y=(int) (Math.floor((y +  (double)8 / 2) / 8) * 8);
        	xTile = 12;
            yTile = 23;
        	int modifier = 8 * scale;
            xOffset = (x - modifier / 2)+1;
            yOffset = (y - modifier / 2 - 4) +13;
            
        	screen.renderOver(xOffset, yOffset, xTile + yTile * 32, Colors.get(-1, 000, 333, 555), 0x00, scale);
            screen.renderOver(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, Colors.get(-1, 000, 333, 555), 0x00, scale);
            screen.renderOver(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, Colors.get(-1, 000, 333, 555),0x00, scale);
            screen.renderOver(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1)* 32, Colors.get(-1, 000, 333, 555), 0x00, scale);
        }
        //if (username != null) {
        //    Font.render(username, screen, xOffset - ((username.length() - 1) / 2 * 8), yOffset - 10,Colors.get(-1, -1, -1, 555), 1, 1);
        //}
        
        if(!OptionsMenu.hideGUI) HUD.renderHUD();

    }

    public boolean hasCollided(int xa, int ya) {
    	if(Game.inEditMode||!shouldClip) {
    		//if((x>=Game.WIDTH||x<=0)||(y>=Game.HEIGHT||y<=0)){
    		//	System.out.println("X "+x+" "+Game.WIDTH);
    		//	System.out.println("Y "+y+" "+Game.);
    		//	return true;
    		//} else {
    			return false;
    		//}
    	}else{
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        if(hp<=0) {
        	return false;
        }
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
    }

    public String getUsername() {
        return this.username;
    }

    public static void sendMessage(String msg){
    	for(int i=chat.length-1; i>=1;i--){
    		chatTime[i]=chatTime[i-1];
    		chat[i]= chat[i-1];
    	}
    	chatTime[0]= 512;
    	chat[0]= msg;
    }
    
	public static void renderChat(Graphics gi) {

		Color col = gi.getColor();
		Font f = gi.getFont();
		
		int fontsize=9;
		
		Font font = new Font("Comic Sans MS", Font.PLAIN,fontsize);
		
		gi.setFont(font);
		for(int i=0; i<chat.length;i++){
			if(chatTime[i]<=231){
				gi.setColor(new Color(255,191,191,chatTime[i]));
			}else{
				gi.setColor(new Color(255,191,191,231));
			}
			gi.drawString(chat[i], 10, 100-(fontsize*i));
			if(chatTime[i]>0 && Game.game.tickCount%2==0){
				chatTime[i]--;
			}
		}
		gi.setColor(col);
		gi.setFont(f);
		
	}
    
}
