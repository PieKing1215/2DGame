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

public class CreditsMenu extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Disp display = new Disp(300,200,600,400);
	
    private Timer timer;
    
    int time = 0;
	
    private Rectangle rBack = new Rectangle(1*4, 89*4, 45*4, 17*4);
    
    //private Rectangle rBack2 = new Rectangle(0, 83, 45, 17);
    
	public CreditsMenu(){
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
		
		repaint();
		
		display.addMouseListener(this);
		
		timer = new Timer(15, this);
		timer.setRepeats(true);
		timer.start();
		
		repaint();
	}
	
	public void back() {
		timer.stop();
		Sound.playSound("Select1.wav", 0.5f);
		new OptionsMenu();
		dispose();
	}
	
	private void tick() {
		
		Graphics2D g2d = (Graphics2D) display.image.getGraphics();
		
		g2d.setColor(new Color(191, 191, 191, 191));
		
		g2d.setFont(new Font("Verdana", 1, 32));
		g2d.drawString("Credits", 20, 40);
		
		int col = 191;
		
		Polygon p = Game.RectangleToPolygon(rBack);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			col=(short) Math.round((float)(Math.sin((time-(50*Math.PI))/10)+2.2)*80);
			col-=1;
		}
		
		g2d.setColor(new Color(col,col,col,col));
		
		g2d.setFont(new Font("Berlin Sans FB", 0, 28));
		g2d.drawString("Back", 10, 192);
		
		g2d.setFont(new Font("Dialog.plain", 0, 14));
		
		g2d.setColor(new Color(191,191,191,191));
		
		g2d.drawString("Back 2k - Skeletonic on SoundCloud", 32, 70);
		
		g2d.drawString("Chipset Sunset - Strobe and Ampli", 39, 85);
		
		g2d.drawString("Fuck Da Ripper - JosSs on SoundCloud", 22, 100);
		
		g2d.drawString("Impact - grande1899 on Youtube/SoundCloud", 5, 115);
		
		g2d.drawString("Stay Inside Me - OcularNebula on Newgrounds", 3, 130);
		
		g2d.drawString("Most other sounds are from bfxr.net", 35, 160);

		
		display.paint(display.getGraphics());
		
		time++;
	}
	
	public void click(){
		Polygon p = Game.RectangleToPolygon(rBack);
		if(p.contains(MouseInfo.getPointerInfo().getLocation().getX()-this.getX(), MouseInfo.getPointerInfo().getLocation().getY()-this.getY())){
			back();
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
