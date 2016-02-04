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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import me.pieking1215.game.Game;
import me.pieking1215.game.net.sound.Sound;

public class OptionsMenu extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Disp display = new Disp(150,100,600,400);
	
    private Timer timer;
    
    private Rectangle rBack = new Rectangle(1*4, 89*4, 45*4, 17*4);
    private Rectangle rCredits = new Rectangle(101*4, 89*4, 50*4, 17*4);
    private Rectangle rLight = new Rectangle(46*4, 29*4, 55*4, 17*4);
    private Rectangle rSound = new Rectangle(46*4, 49*4, 55*4, 17*4);
    private Rectangle rMusic = new Rectangle(46*4, 69*4, 55*4, 17*4);
    /*private Rectangle rZoom = new Rectangle(85*4, 83*4, 55*4, 17*4);
    
    private Rectangle rBack2 = new Rectangle(0, 83, 45, 17);
    private Rectangle rCredits2 = new Rectangle(100, 83, 50, 17);
    private Rectangle rLight2 = new Rectangle(45, 23, 55, 17);
    private Rectangle rSound2 = new Rectangle(45, 43, 55, 17);
    private Rectangle rMusic2 = new Rectangle(45, 63, 55, 17);
    private Rectangle rZoom2 = new Rectangle(85, 83, 55, 17);*/

	private int time = 0;
	
	public static boolean optLight=false;
	public static boolean optSound=true;
	public static boolean optMusic=true;
	public static int optZoomOut=1;
	
	public static boolean debugMenu=false;

	public static boolean paused=false;

	public static boolean hideGUI=false;
	
	public OptionsMenu(){
		start();
	}
	
	public void start(){

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
		
		repaint();
	}
	
	private void tick() {
		
		Graphics2D g2d = (Graphics2D) display.image.getGraphics();
		
		
		
		//g2d.drawImage(Images.player, 16, 35, 52, 60, null);
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(48, 26, 10, 50);
		/*g2d.fill(rLight2);
		g2d.fill(rSound2);
		g2d.fill(rMusic2);*/
		
		
		
		g2d.setColor(new Color(191, 191, 191, 191));
		
		g2d.setFont(new Font("Verdana", 1, 16));
		g2d.drawString("Options", 10, 20);
		
		int col=191;
		
		Polygon p = Game.RectangleToPolygon(rBack);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));
		g2d.drawString("Back", 5, 96);
		
		col=191;
		
		p = Game.RectangleToPolygon(rCredits);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));
		g2d.drawString("Credits", 105, 96);
		
		col=191;
		
		p = Game.RectangleToPolygon(rLight);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));

		g2d.drawString("Light", 60, 36);
		
		g2d.setFont(new Font("Dialog.plain", 0, 14));
		if(!optLight){
			g2d.drawString("\u2610", 46, 36);
		}else{
			g2d.drawString("\u2612", 46, 36);
		}
		
		col=191;
		
		p = Game.RectangleToPolygon(rSound);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));

		g2d.drawString("Sound", 60, 56);
		
		g2d.setFont(new Font("Dialog.plain", 0, 14));
		if(!optSound){
			g2d.drawString("\u2610", 46, 56);
		}else{
			g2d.drawString("\u2612", 46, 56);
		}
		
		col=191;
		
		p = Game.RectangleToPolygon(rMusic);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		g2d.setFont(new Font("Berlin Sans FB", 0, 14));

		g2d.drawString("Music", 60, 76);
		
		g2d.setFont(new Font("Dialog.plain", 0, 14));
		if(!optMusic){
			g2d.drawString("\u2610", 46, 76);
		}else{
			g2d.drawString("\u2612", 46, 76);
		}
		
		
		

		
		display.paint(display.getGraphics());
		
		time++;
	}
	
	public void zoom(){
		Sound.playSound("Select3.wav", 0.5f);
		if(optZoomOut==1){
			optZoomOut=5;
		}else{
			optZoomOut=5;
		}
	}
	
	public void music(){
		Sound.playSound("Select3.wav", 0.5f);
		optMusic=!optMusic;
	}
	
	public void sound(){
		optSound=!optSound;
		Sound.playSound("Select3.wav", 0.5f);
	}
	
	public void light(){
		Sound.playSound("Select3.wav", 0.5f);
		optLight=!optLight;
	}
	
	public void credits(){
		timer.stop();
		Sound.playSound("Select2.wav", 0.5f);
		new CreditsMenu();
		dispose();
	}
	
	public void back(){
		timer.stop();
		Sound.playSound("Select1.wav", 0.5f);
		new MainMenu();
		dispose();
	}
	
	public static void reload(){
		paused=false;
		debugMenu=false;
	}

	public void click(){
		Polygon p = Game.RectangleToPolygon(rBack);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			back();
		}
		
		p = Game.RectangleToPolygon(rCredits);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			credits();
		}
		
		p = Game.RectangleToPolygon(rMusic);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			music();
		}
		
		p = Game.RectangleToPolygon(rSound);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			sound();
		}
		
		p = Game.RectangleToPolygon(rLight);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			light();
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		click();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		tick();
	}
	
}
