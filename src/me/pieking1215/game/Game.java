package me.pieking1215.game;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import me.pieking1215.game.entities.BasicEnemy;
import me.pieking1215.game.entities.Entity;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.entities.PlayerMP;
import me.pieking1215.game.entities.RainEntity;
import me.pieking1215.game.entities.SnowEntity;
import me.pieking1215.game.entities.Spawner;
import me.pieking1215.game.entities.WinPad;
import me.pieking1215.game.entities.XpOrb;
import me.pieking1215.game.gfx.HUD;
import me.pieking1215.game.gfx.Images;
import me.pieking1215.game.gfx.Screen;
import me.pieking1215.game.gfx.SpriteSheet;
import me.pieking1215.game.gui.LevelSelect;
import me.pieking1215.game.gui.OptionsMenu;
import me.pieking1215.game.inventory.Inventory;
import me.pieking1215.game.inventory.Item;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.TileLight;
import me.pieking1215.game.net.GameClient;
import me.pieking1215.game.net.GameServer;
import me.pieking1215.game.net.sound.Sound;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    /*
     * Version 0.2.6 (Dev) <Loading...>
     */
    
    public static String version="0.2.6 (Dev)";
    
    public static String activationKey= "COLORFUL";
    
    public static final boolean INDEV=true;
    
    public static final boolean PLAYTESTING=false;
    
    public static List<RainEntity> rain = new ArrayList<RainEntity>();
    public static List<RainEntity> rainToRemove = new ArrayList<RainEntity>();
    
    public static List<SnowEntity> snow = new ArrayList<SnowEntity>();
    public static List<SnowEntity> snowToRemove = new ArrayList<SnowEntity>();
    
    
    private static int zoomOut=1;
    public static final int WIDTH = 160*zoomOut;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 5-(zoomOut-1);
    public static final String NAME = "2D Game v"+version;
    public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    public static Game game;
    public int xp = 0;
    public static String toLevel="Tutorial";
    public static boolean custom = false;
    private static Random rand = Rand.getRand();
    
    public static Graphics g;
    
    public static Graphics2D gshot = null;
    
    public JFrame frame, loadFrame;

    private Thread thread;

    public boolean running = false;
    public int tickCount = 0;

    public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colors = new int[6 * 6 * 6 * 6];

    public static TileLight[] tileLights;
    
    public static Screen screen;
    public InputHandler input;
    public WindowHandler windowHandler;
    public static Level level;
    public Player player;
    public BasicEnemy enemy;
    public static XpOrb xpOrb;
    public static Spawner drop;
    public static boolean isPlayerAlive = true;

	public static boolean inEditMode;
    
    public GameClient socketClient;
    public GameServer socketServer;

    public static boolean debug = false;
    public static boolean isApplet = false;

	private int dbgFPS=0;
	private int dbgTPS=0;

    public static boolean host=false;
    
	public static int xp2;
	public static int bombCt2;
	public static int arwCt2;
	public static String song="null";
	
	public static int spawnX=0;
	public static int spawnY=0;

	public static String songContinues = "new";

	public static boolean shouldPlay= true;

	public static boolean spawnOverride= false;

	public static Graphics gi;
	
	private static int colp1=191;
	private static int colp2=191;
	
    public static void main(String args[]) {

    	debug(DebugLevel.INFO, DebugPriority.DEV, "Main ran from Game class. Passing to GameLauncher.mainTwo().");
    	GameLauncher.mainTwo(null);
    }
    
    public void restart(String toLevel, boolean keepCoords, boolean custom) {
    	for(KeyListener k:getKeyListeners()){
    		removeKeyListener(k);
    	}
    	for(MouseListener m:getMouseListeners()){
    		removeMouseListener(m);
    	}
    	
    	for(MouseWheelListener m:getMouseWheelListeners()){
    		removeMouseWheelListener(m);
    	}
    	OptionsMenu.reload();
    	shouldPlay=false;
    	Sound.stopSounds();
    	isPlayerAlive=true;
    	Player.win=false;
    	song="null";
    	songContinues="new";
    	//System.out.println(song);
    	//System.out.println(toLevel+" "+level.getWorld());
    	Game.toLevel=toLevel;
    	//System.out.println(toLevel+" "+level.getWorld());
    	level.removeAll();
    	Player.setXp(0);
    	Player.fireTime=0;
    	spawnOverride=true;
    	if(keepCoords){
    		spawnX=Player.getLocation().getX();
    		spawnY=Player.getLocation().getY();
    	}
    	Game.custom=custom;
    	if(Player.restartE2){
    		Player.restartE2=false;
    		game.player.tick();
    		game.player.tick();
    		game.player.tick();
    	}
    	
    	Player.restartE=false;
    	Player.restartEE=false;
    	
    	run();
    }
    
    public void init() {
    	inEditMode=LevelSelect.inEditMode;
        game = this;
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colors[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/spritesheet.png"));
        input = new InputHandler(this);
        level = new Level(toLevel, custom);
        tileLights=new TileLight[(Level.width+1)*(Level.height+1)];
        //String user = JOptionPane.showInputDialog(this, "Please enter a username");
        String user="poop";
        player = new PlayerMP(level, spawnX, spawnY, input, user, null, -1);
        level.addEntity(player);
        level.levelInit();
        //level.addEntity(new Projectile(level, 100, 400, 0, 0, 12, 27, Colors.get(-1, 333, 444, 555)));
        //level.addEntity(new RangedMob(level, 100, 300, 0, 10, 80, 48, false, 2));
        //level.addEntity(new RangedMob(level, 150, 300, 0, 10, 80, 48, false, 2));
        //level.addEntity(new RangedMob(level, 200, 300, 0, 10, 80, 48, false, 2));
        //level.addEntity(new RangedMob(level, 250, 300, 0, 10, 80, 48, false, 2));
        Player.setHp(Player.getMaxHp());
        Player.shouldClip=true;
        Player.setXp(xp2);
        
        Inventory.clear();
        Inventory.addItem(Item.ARROW);
        Inventory.addItem(Item.BOMB);
        if(Inventory.getItemByName("BOMB")!=null){
        	Inventory.getItemByName("BOMB").setCount(bombCt2);
        }
        if(Inventory.getItemByName("ARROW")!=null){
        	Inventory.getItemByName("ARROW").setCount(arwCt2);
        }
        
        /*if (!isApplet) {
            Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
            if (socketServer != null) {
                socketServer.addConnection((PlayerMP) player, loginPacket);
            }
            loginPacket.writeData(socketClient);
        }*/
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        if(!isApplet) frame.getContentPane().setCursor(blankCursor);
        if(!(song.equals("null"))) {
        	Sound.playSound(song+".wav", -5);
        }
    }
    
    public synchronized void start() {
    	//System.out.println("poop"+Game.class.getResourceAsStream("/levels/"));
        running = true;

        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Starting thread...");
        thread = new Thread(this, NAME + "_main");
        thread.start();
        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Started thread!");
        /*if (!isApplet) {
            if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
                socketServer = new GameServer(this);
                socketServer.start();
                host=true;
            }
        	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Starting Client...");
            socketClient = new GameClient(this, "localhost");
            socketClient.start();
            Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Finished Starting Client!");
        }*/
    }

    public synchronized void stop() {
    	System.out.println("[" + NAME + "] [SEVERE] The game will force stop..."); //To not infini-call
    	Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Stopping...");
    	Sound.stopSounds();
    	OptionsMenu.paused=false;
    	LevelSelect.inEditMode=false;
    	image=null;
    	input=null;
        running = false;
        /*if(thread.isAlive()){
        try {
        	thread.join();
        } catch (InterruptedException e) {
        	System.out.println("[" + NAME + "] [SEVERE] Could not properly stop the thread, there is a serious problem."); //TODO: Why isn't this working?
        	System.out.println("[" + NAME + "] [SEVERE] Kill stopping..."); 
        	
            e.printStackTrace();
            exit(ExitState.FORCED);
        }
        }*/
        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Stopped.");
        exit(ExitState.CONTROLLED);
    }

    public void run() {
    	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Running As Applet...");
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        //Sound.playSound("Select2.wav", 0.5f);
        
        
        init();
        if(!Game.isApplet) {
        	loadFrame.dispose();
        	frame.setVisible(true);
        }
        //Sound.playSound("Select2.wav", 0.5f);
        while (running) {
        	
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //debug(DebugLevel.INFO, DebugPriority.NORMAL, ticks + " ticks, " + frames + " frames");
                dbgFPS=frames;
                dbgTPS=ticks;
                if(!isApplet) {
                	if(host){
                		frame.setTitle("2D Game v"+version+" (Hosting)");
                	}else{
                		frame.setTitle("2D Game v"+version);
                	}
                }
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
    	
    	if(!OptionsMenu.paused&&Level.level.weather==Level.RAIN) {
    		int rx = rand.nextInt((getWidth()*2))-getWidth();
        	rain.add(new RainEntity(rx, 0,rand.nextInt(2)+3,rand.nextInt(2)+3));
    	}
    	if(game.tickCount%3==0&&!OptionsMenu.paused&&Level.level.weather==Level.SNOW) {
    		int rx = rand.nextInt((getWidth()*2))-getWidth();
        	snow.add(new SnowEntity(rx, 0,rand.nextInt(20)+10,rand.nextInt(2)+1,rand.nextInt(3)+2));
    	}
    	//System.out.println(OptionsMenu.paused);
        tickCount++;
        level.tick();
        if(Player.restart!=null){
        	Sound.stopSounds();
        	restart(Player.restart, false, custom);
        	Player.restart=null;
        }
        if(input.r.isPressed()) {
        	Sound.stopSounds();
            restart(level.getWorld(), false, custom);
            Sound.stopSounds();
            //System.out.println("Player");
        } else {
        	for(Entity e :level.getEntities()) {
        		if(e instanceof WinPad) {
        			WinPad p = (WinPad) e;
        			if (p.resetToLevel){
        				restart(p.toLevel, false, custom);
        			}
        		}
        	}
        }
    }

    public void render() {
    	
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        try{
        	g = bs.getDrawGraphics();
        }catch(IllegalStateException e){
        	
        }

        
        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);
        
        
        level.renderTiles(screen, xOffset, yOffset);
        
        level.renderEntities(screen);

        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colorCode = screen.pixels[x + y * screen.width];
                if (colorCode < 255){
                	double dx = Math.abs((x+screen.xOffset-4)-Player.getLocation().getX());
                	double dy = Math.abs((y+screen.yOffset)-Player.getLocation().getY());
                	if(Level.underground){
                		//System.out.println(colors[colorCode]+" "+colorCode);
                		int color = (int) (Math.sqrt(Math.abs(Math.pow(dx, 2)+Math.pow(dy, 2)))*18);
                		if(Math.abs(Math.pow(dx, 2)+Math.pow(dy, 2))<64){
                			color = 128;
                		}
                		color = 255-color;
                		color = getIntFromColor(color, color, color);
                		color = (color | colors[colorCode]);
                		//System.out.println(color);
                		if(color>0 || Math.abs(Math.pow(dx, 2)+Math.pow(dy, 2))>=196){
                			color=0;
                		}
                		pixels[x + y * WIDTH] = color;
                	}else{
                		pixels[x + y * WIDTH] = colorCode;
                	}
                }
            }
        }

        

        gi= image.getGraphics();
        
        if(!OptionsMenu.hideGUI) {
        	Player.renderChat(gi);
        	HUD.renderHUDgi(gi);
        }
        
        
        switch(Level.level.weather){
    	case Level.RAIN:
    		gi.setColor(new Color(127, 127, 191, 127));
    		gi.fillRect(0, 0, image.getWidth(),image.getHeight());
    		break;
    	case Level.SNOW:
    		gi.setColor(new Color(255,255,255, 63));
    		gi.fillRect(0, 0, image.getWidth(),image.getHeight());
    	default:
    		break;
        }
        
        if(OptionsMenu.paused){
            gi.setColor(new Color(0,0,0, 191));
        	gi.fillRect(0, 0, getWidth(), getHeight());

            gi.setColor(new Color(HUD.r,HUD.g,HUD.b,191));
            gi.setFont(new Font("Seguoe UI", 1, 24));
        	gi.drawString("Paused", image.getWidth()/2-40, 45);
        		
        	gi.setFont(new Font("Seguoe", 1, 9));
        	colp1=191;
        	colp2=191;
        	
        	Rectangle r = new Rectangle(Game.game.getWidth()/2-70, 270, 205, 50);
        	Polygon p =RectangleToPolygon(r);
        	if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-Game.game.frame.getX(), MouseInfo.getPointerInfo().getLocation().getY()-Game.game.frame.getY())){
        		colp1=223;
        	}

        	r = new Rectangle(Game.game.getWidth()/2-98, 330, 245, 50);
        	p =RectangleToPolygon(r);
        	if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-Game.game.frame.getX(), MouseInfo.getPointerInfo().getLocation().getY()-Game.game.frame.getY())){
        		colp2=223;
        	}
        		
        	if(colp1==223){
        		colp1=((short) Math.round((float)(Math.sin((game.tickCount-(50*Math.PI))/5)+3)*63.75));
        	}
        		
        	if(colp2==223){
        		colp2=((short) Math.round((float)(Math.sin((game.tickCount-(50*Math.PI))/5)+3)*63.75));
        	}
        		
        	gi.setColor(new Color(colp1,colp1,colp1,191));
        	gi.drawString("Resume", image.getWidth()/2-13, 57);
        	gi.setColor(new Color(191, 191, 191, 191));
        		
        	gi.setColor(new Color(colp2,colp2,colp2,191));
        	gi.drawString("Quit Game", image.getWidth()/2-19, 68);
        		
        }
        
        if(!OptionsMenu.hideGUI){
        if(OptionsMenu.debugMenu){
        	gi.setColor(new Color(127, 127, 127, 191));
        	gi.fillRect(image.getWidth()-40, image.getHeight()-61, 200, 300);
        }else{
        	gi.setColor(new Color(127, 127, 127, 191));
        	gi.fillRect(image.getWidth()-40, image.getHeight()-6, 200, 30);
        }
        }
        
        //gi.setFont(new Font("Verdana", 0, 20));
    	//gi.drawString("FPS: "+dbgFPS, 20 ,20);
        
        
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
        if(INDEV){
        	g.drawImage(Images.devIcon, 10, game.frame.getContentPane().getHeight()-60, 100, 50, null);
        }else if(PLAYTESTING){
        	g.drawImage(Images.playtestIcon, 10, game.frame.getContentPane().getHeight()-60, 100, 50, null);
        }
        
        if(OptionsMenu.optLight){
        	for(TileLight t: tileLights){
        	//System.out.println(t);
        	if(t==null){
        		g.setColor(Color.BLACK);
        		g.fillRect(0, 0, 8, 8);
        	}else{
        		g.setColor(t.getCol());
        		g.fillRect((t.getX()*8)+screen.xOffset, (t.getY()*8)-screen.yOffset, 8, 8);
        	}
        	
        }
        }
        
        switch(Level.level.weather){
        	case Level.RAIN:
        		g.setColor(new Color(63, 63, 79, 127));
        		for(RainEntity r : rain){
        			r.render(g);
        		}
        		rain.removeAll(rainToRemove);
        		
        		break;
        	case Level.SNOW:
        		g.setColor(new Color(255, 255, 255, 191));
        		for(SnowEntity s : snow){
        			s.render(g);
        		}
        		rain.removeAll(rainToRemove);
        		break;
        	default:
        		break;
        }
        
        if(!OptionsMenu.hideGUI){
        
        if(OptionsMenu.debugMenu){
        	g.setColor(new Color(0, 0, 0, 255));
        	g.setFont(new Font("Verdana", 1, 25));
        	g.drawString("Debug Menu", getWidth()-185, getHeight()-275);
        	g.drawLine(getWidth()-185, getHeight()-270, getWidth()-10, getHeight()-270);
        	g.setFont(new Font("Verdana", 0, 20));
        	g.drawString("FPS: "+dbgFPS, getWidth()-190, getHeight()-245);
        	g.drawString("TPS: "+dbgTPS, getWidth()-190, getHeight()-220);
        	g.drawString("X Coords: "+Player.getLocation().getX(), getWidth()-190, getHeight()-195);
        	g.drawString("Y Coords: "+Player.getLocation().getY(), getWidth()-190, getHeight()-170);
        	g.drawString("# Entities: "+Level.level.getEntities().size(), getWidth()-190, getHeight()-145);
        	g.drawString("Level:", getWidth()-190, getHeight()-120);
        	g.setFont(new Font("AgencyFB", 1, 16));
        	g.drawString(""+Level.level.imagePath, getWidth()-125, getHeight()-120);
        	g.setFont(new Font("Verdana", 0, 20));
        	g.drawString("Mouse X:"+(MouseInfo.getPointerInfo().getLocation().getX()-frame.getX()), getWidth()-190, getHeight()-95);
        	g.drawString("Mouse Y:"+(MouseInfo.getPointerInfo().getLocation().getY()-frame.getY()), getWidth()-190, getHeight()-70);
        	g.drawString("Seed:", getWidth()-190, getHeight()-45);
        	g.setFont(new Font("AgencyFB", 1, 16));
        	g.drawString(""+(Rand.getSeed()), getWidth()-130, getHeight()-45);
        }else{
        	g.setColor(new Color(0, 0, 0, 255));
        	g.setFont(new Font("Verdana", 1, 15));
        	try{
        		g.drawString("[F3] Open Debug Menu", getWidth()-195, getHeight()-10);
        	}catch(ClassCastException e){
        		
        	}
        	
        }
        }
        gshot=(Graphics2D)g;
        g.dispose();
        gi.dispose();
        try{
        	bs.show();
        }catch(IllegalStateException e){
        	
        }
    }

    public static Polygon RectangleToPolygon(Rectangle rect) { Polygon result = new Polygon(); result.addPoint(rect.x, rect.y); result.addPoint(rect.x + rect.width, rect.y); result.addPoint(rect.x + rect.width, rect.y + rect.height); result.addPoint(rect.x, rect.y + rect.height); return result; } //- See more at: http://wikicode.wikidot.com/convert-rectangle-to-polygon#sthash.TGD9pLei.dpuf
    
    public static void click(){
    	if(OptionsMenu.paused){
    		Rectangle r = new Rectangle(Game.game.getWidth()/2-70, 270, 205, 50);
    		Polygon p =RectangleToPolygon(r);
    		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-Game.game.frame.getX(), MouseInfo.getPointerInfo().getLocation().getY()-Game.game.frame.getY())){
    			togglePause();
    		}
    	
    		r = new Rectangle(Game.game.getWidth()/2-98, 330, 245, 50);
    		p =RectangleToPolygon(r);
    		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-Game.game.frame.getX(), MouseInfo.getPointerInfo().getLocation().getY()-Game.game.frame.getY())){
    			//exit(ExitState.NORMAL);
				//new MainMenu();
				//game.frame.dispose();
				//GameLauncher.game=null;
				exit(ExitState.NORMAL);
    		}
    	}
    }
    
    public static long fact(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * fact(n - 1);
        }
    }

    public static void debug(DebugLevel level, DebugPriority priority, String msg) {
    	if((priority==DebugPriority.DEV&&INDEV)||priority==DebugPriority.NORMAL){
        switch (level) {
        default:
        case INFO:
            System.out.println("[" + NAME + "] [INFO] " + msg);
            break;
        case WARNING:
            System.out.println("[" + NAME + "] [WARNING] " + msg);
            break;
        case SEVERE:
            System.out.println("[" + NAME + "] [SEVERE] " + msg);
            game.stop();
            break;
        }
    	}
    }

    public static enum DebugLevel {
        INFO, WARNING, SEVERE;
    }
    
    public static enum DebugPriority {
        DEV, NORMAL;
    }
    
    public static enum ExitState {
        NORMAL, CONTROLLED, FORCED;
    }
    
    public static synchronized void exit(ExitState e){
    	int n=0;
    	
    	switch (e) {
		case NORMAL:
			 n=0;
			break;
		case CONTROLLED:
			 n=1;
			break;
		case FORCED:
			 n=2;
			break;

		default:
			break;
		}
    	
    	debug(DebugLevel.INFO, DebugPriority.NORMAL, "Exited with state: "+e);
    	System.exit(n);
    }
    
    public static boolean oneIn(int in){
    	
    	if(in>1){
    		return Rand.oneIn(in);
    	} else {
    		debug(DebugLevel.WARNING, DebugPriority.NORMAL, "x Must Be Greater Than 1 For Game.oneIn(x)");
    		debug(DebugLevel.WARNING, DebugPriority.NORMAL, "The Game Will Try To Continue.");
    		return true;
    	}
    }

	public static void togglePause() {
		OptionsMenu.paused=!OptionsMenu.paused;
    	Level.level.pause=!Level.level.pause;
    	if(OptionsMenu.paused){
    		Sound.pause();
    		Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException r) {
				r.printStackTrace();
			}
	        robot.mouseMove(Game.game.frame.getX()+(Game.game.frame.getWidth()/2)+8, Game.game.frame.getY()+(Game.game.frame.getHeight()/2)+23);
    		if(!Game.isApplet) Game.game.frame.getContentPane().setCursor(null);
    	}else{
    		Sound.unpause();
    		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
    		if(!Game.isApplet) Game.game.frame.getContentPane().setCursor(blankCursor);
    	}		
	}

	public int getIntFromColor(int Red, int Green, int Blue/*, int Alpha*/){
	    Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
	    Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
	    Blue = Blue & 0x000000FF; //Mask out anything not blue.
	    //Alpha = Alpha & 0xFF000000;
	    
	    
	    return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
	
	public static double divide(double a, double b) {
		return a/b;
	}
}
