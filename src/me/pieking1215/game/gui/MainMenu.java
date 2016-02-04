package me.pieking1215.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.Game.ExitState;
import me.pieking1215.game.Rand;
import me.pieking1215.game.gfx.Images;
import me.pieking1215.game.net.sound.Sound;

public class MainMenu extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Disp display = new Disp(150,100,600,400);
	
    private Timer timer;
    
    private Rectangle rPlay = new Rectangle(85*4, 43*4, 55*4, 17*4);
    private Rectangle rOptions = new Rectangle(85*4, 63*4, 55*4, 17*4);
    private Rectangle rExit = new Rectangle(85*4, 83*4, 55*4, 17*4);
    
    //private Rectangle rPlay2 = new Rectangle(85, 43, 55, 17);
    //private Rectangle rOptions2 = new Rectangle(85, 63, 55, 17);
    //private Rectangle rExit2 = new Rectangle(85, 83, 55, 17);
    
    private static List<String> splashes = readSplashes()/*new ArrayList<String>(Arrays.asList("Awesome!", "2 Cool!", "Spooky!", "Scary", "Colorful!"))*/;
    
    private String splash = getRandomSplash();
    
    public static List<String> loadSplashes = readLoadSplashes()/*new ArrayList<String>(Arrays.asList("Awesome!", "2 Cool!", "Spooky!", "Scary", "Colorful!"))*/;
    
    int r=0;
    int g=255;
    int b=0;
    
    private int time=0;

	private int color = 0;

	private int timer2 = 0;
    
	@SuppressWarnings("unused")
	public MainMenu(){
		Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Loading main menu...");
		if(Game.activationKey!=null && !Game.INDEV){
			Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "You must input the key to continue.");
			new PlayKey();
		}else{
			Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Loaded Main Menu!");
			start();
		}
	}
	
	private void start(){

		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(590,390));
		getContentPane().add(jp);
		pack();
		jp.setVisible(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(Game.NAME);
		getContentPane().add(display);
		
		setResizable(false);
        
		getContentPane().add(display);
		
		//display.fill(75, 77, 54, 17, 0xcccc00);
		
		//display.fill(75, 57, 54, 17, 0xcccc00);
		
		//display.fill(75, 37, 54, 17, 0xcccc00);
		
		repaint();
		
		display.addMouseListener(this);
		
		timer = new Timer(15, this);
		timer.setRepeats(true);
		timer.start();
		
	}

		
	public void go(){
		timer.stop();
		Sound.playSound("Select2.wav", 0.5f);
		new LevelSelect();
		dispose();
	}
	
	public void options(){
		timer.stop();
		Sound.playSound("Select2.wav", 0.5f);
		new OptionsMenu();
		dispose();
	}
	
	public void exit(){
		timer.stop();
		dispose();
		Game.exit(ExitState.NORMAL);
	}
	
	public void actionPerformed(ActionEvent e) {
		tick();
	}

	private void tick() {
		
		Graphics2D g2d = (Graphics2D) display.image.getGraphics();
		
		g2d.drawImage(Images.player, 16, 35, 52, 60, null);
		
		g2d.setColor(new Color(r,g,b,191));
		
		g2d.setFont(new Font("Verdana", 1, 16));
		g2d.drawString("Java 2D Game", 10, 20);
		
		int col2=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/7)+1)*127.5);
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(11, 22, 62, 10);
		
		g2d.setColor(new Color(255, 255, 0, Math.round((float)(col2*0.8+51))));
		g2d.setFont(new Font("Verdana", 0, 9));
		g2d.drawString("v"+Game.version, 10, 30);
		
		int sx = 80;
		int sy = 10/*+Math.round((float)(Math.sin(time/10)))*/;
		//int sy2 = 10+Math.round((float)(Math.sin((time-1)/10)));
		
		g2d.rotate(0.5,sx,sy);
		
		/*g2d.setColor(new Color(0, 0, 0, 255));
		g2d.setFont(new Font("Dialoge.plain", 1, 12));
		g2d.drawString(splash, sx, sy);*/
		
		col2=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/15)+1)*127.5);
		
		g2d.setColor(new Color(col2/2+127, col2/2+127, 0, 255));
		g2d.setFont(new Font("Berlin Sans FB", 1, 15));
		g2d.drawString(splash, sx, sy);
		
		g2d.rotate(-0.5,sx,sy);
		
		if(time%4==0){
			if(color==0){
				r++;
				g--;
				b=0;
			}else if(color==1){
				r--;
				g=0;
				b++;
			}else if(color==2){
				r=0;
				g++;
				b--;
			}
		
			if(r>255){
				r=255;
			}else if(r<0){
				r=0;
			}
		
			if(g>255){
				g=255;
			}else if(g<0){
				g=0;
			}
		
			if(b>255){
				b=255;
			}else if(b<0){
				b=0;
			}
		
			if(timer2>255){
				if(color==2){
					color=0;
				}else{
					color++;
				}
					timer2=0;
			}else{
				timer2++;
			}
		}
		
		int col=191;
		
		Polygon p = Game.RectangleToPolygon(rPlay);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(Math.round(col/2),col,Math.round(col/2),col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));
		g2d.drawString("Play", 90, 50);
		
		col=191;
		
		p = Game.RectangleToPolygon(rOptions);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));
		g2d.drawString("Options", 90, 70);
		
		col=191;
		
		p = Game.RectangleToPolygon(rExit);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(Color.BLACK);
		
		g2d.setColor(new Color(col,Math.round(col/2),Math.round(col/2),col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));
		g2d.drawString("Exit", 90, 90);
		
		display.paint(display.getGraphics());
		
		time++;
	}
	
	private static String getRandomSplash() {
		String splash = "MISSINGNO.";

		int index = Rand.range(0, splashes.size()-1);
		
		splash = splashes.get(index);
		
		return splash;
	}
	
	public static String getRandomLoadSplash() {
		String splash = "MISSINGNO.";

		int index = Rand.range(0, loadSplashes.size()-1);
		
		splash = loadSplashes.get(index);
		
		return splash;
	}

	private static List<String> readSplashes(){
		List<String> spl = new ArrayList<String>();
		
		URL splURL = MainMenu.class.getClassLoader().getResource("splashes.txt");
		
		try {
			Scanner x = new Scanner(splURL.openStream());
			while(x.hasNextLine()){
				spl.add(x.nextLine());
			}
			x.close();
		} catch (IOException e) {
			//e.printStackTrace();
			Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Error reading splashes.txt file.");
			spl.add("GG NO RE");
		} catch (NullPointerException e){
			Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Could not find splashes.txt file.");
			spl.add("GG NO RE");
		}
		
		return spl;
	}
	
	private static List<String> readLoadSplashes(){
		List<String> spl = new ArrayList<String>();
		
		URL splURL = MainMenu.class.getClassLoader().getResource("loadSplashes.txt");
		
		try {
			Scanner x = new Scanner(splURL.openStream());
			while(x.hasNextLine()){
				spl.add(x.nextLine());
			}
			x.close();
		} catch (IOException e) {
			//e.printStackTrace();
			Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Error reading splashes.txt file.");
			spl.add("GG NO RE");
		} catch (NullPointerException e){
			Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Could not find splashes.txt file.");
			spl.add("GG NO RE");
		}
		
		return spl;
	}
	
	public void click(){
		Polygon p = Game.RectangleToPolygon(rExit);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			exit();
		}
		
		p = Game.RectangleToPolygon(rOptions);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			options();
		}
		
		p = Game.RectangleToPolygon(rPlay);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			go();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		click();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
