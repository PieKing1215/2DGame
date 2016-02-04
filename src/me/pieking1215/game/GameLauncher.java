package me.pieking1215.game;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gfx.Images;
import me.pieking1215.game.gui.MainMenu;
import me.pieking1215.game.gui.OptionsMenu;

@SuppressWarnings("serial")
public class GameLauncher extends Applet {

    public static Game game = new Game();
    public static final boolean DEBUG = false;

    public static MainMenu menu;
    
    @Override
    public void init() {
        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        setMaximumSize(Game.DIMENSIONS);
        setMinimumSize(Game.DIMENSIONS);
        setPreferredSize(Game.DIMENSIONS);
        Game.debug = DEBUG;
        Game.isApplet = true;
    }

    @Override
    public void start() {
    	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Starting Applet...");
        game.start();
    }

    @Override
    public void stop() {
    	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Stopping Applet...");
        game.stop();
    }

    public static void main(String[] args) {
    	Game.debug(DebugLevel.WARNING, DebugPriority.DEV, "Main Ran From GameLauncher Class. Not A Problem, But Unusual.");
    	
    	mainTwo(args);
    }
    
    public static void mainTwo(String[] args) {


    	
    	menu = new MainMenu();
    }
    
    public static void menuReady(){
    	menu.dispose();
    	mainThree(null);
    }
    
    public static void mainThree(String[] args) {
    	
    	if(Game.game==null){
    		
    		//game.setMinimumSize(Game.DIMENSIONS);
            //game.setMaximumSize(Game.DIMENSIONS);
            //game.setPreferredSize(Game.DIMENSIONS);

    		game.loadFrame = new JFrame(Game.NAME);
    		
    		game.loadFrame.setUndecorated(true);
    		
    		JPanel jp = new JPanel();
    		jp.setPreferredSize(new Dimension(800,400));
    		game.loadFrame.getContentPane().add(jp);
    		game.loadFrame.pack();
    		jp.setVisible(false);
    		
            game.loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.loadFrame.setLayout(new BorderLayout());

           
            
            JPanel pane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(Images.loading, 0, 0, game.loadFrame.getContentPane().getWidth(), game.loadFrame.getContentPane().getHeight(), null);
                    g.setFont(new Font("Dialog.plain", 1, 52));
                    g.setColor(Color.GRAY);
                    String spl = MainMenu.getRandomLoadSplash();
                    int width = g.getFontMetrics().stringWidth(spl);
                    if(width>800){
                    	g.setFont(new Font("Dialog.plain", 1, 40));
                    	
                    	String h1 = spl.substring(0, spl.length()/2);
                    	String h2 = spl.substring((spl.length()/2)+1, spl.length());
                    	
                    	int width1 = g.getFontMetrics().stringWidth(h1);
                    	
                    	int width2 = g.getFontMetrics().stringWidth(h2);
                    	
                    	g.drawString(h1, 400 - (width1/2), 320);
                    	g.drawString(h2, 400 - (width2/2), 360);
                    }else{
                    	g.drawString(spl, 400 - (width/2), 320);
                    }
                    
                }
            };
            
            game.loadFrame.add(pane);
            
            //game.loadFrame.add(game, BorderLayout.CENTER);
            //game.loadFrame.pack();

            game.loadFrame.setResizable(false);
            game.loadFrame.setLocationRelativeTo(null);
            game.loadFrame.setVisible(true);
            
            //game.loadFrame.getGraphics().drawImage(Images.error, 0, 0, game.loadFrame.getContentPane().getWidth(), game.loadFrame.getContentPane().getHeight(), null);
            
            
    		
    	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Setting Up JFrame...");
    	
    	
        game.setMinimumSize(Game.DIMENSIONS);
        game.setMaximumSize(Game.DIMENSIONS);
        game.setPreferredSize(Game.DIMENSIONS);

        game.frame = new JFrame(Game.NAME);

        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLayout(new BorderLayout());

        game.frame.add(game, BorderLayout.CENTER);
        game.frame.pack();

        game.frame.setResizable(false);
        game.frame.setLocationRelativeTo(null);
        //game.frame.setVisible(true);
        
        game.frame.getGraphics().drawImage(Images.error, 0, 0, game.frame.getContentPane().getWidth(), game.frame.getContentPane().getHeight(), null);
        
        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Finished Setting Up JFrame!");
        
        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Setting Up WindowHandler...");
        game.windowHandler = new WindowHandler(game);
        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Finished Setting Up WindowHandler!");
        Game.debug = DEBUG;

        Game.debug(DebugLevel.INFO, DebugPriority.DEV, "Starting Up Game...");
        
        game.start();
    	}else{
    		Game.game.frame.setVisible(true);
    		Player.restartE2=true;
    		Player.restartEE=false;
    		OptionsMenu.paused=true;
    		//Game.game.player.tick();
    	}
    }
}