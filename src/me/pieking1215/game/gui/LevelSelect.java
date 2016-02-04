package me.pieking1215.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import me.pieking1215.game.Game;
import me.pieking1215.game.GameLauncher;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.level.ReadFile;
import me.pieking1215.game.net.sound.Sound;

public class LevelSelect extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JButton backButton,leftButton,rightButton,goButton,createButton,loadButton;
	
	private String[] levels = { "Tutorial","DungeonDemo", "Gen_Natural", "Gen_Snowy", "Gen_Dungeon" };
	
	private JLabel title, lSelect, lLevel, lLevelIndex, lPreview, lDesc;
	
	public static boolean optLight=false;
	public static boolean optSound=true;
	public static boolean optMusic=true;

	private int levelIndex=1;
	
	public boolean ready=false;

	private static boolean first=true;

	public static boolean inEditMode;
	
	public static boolean debugMenu=false;

	public static boolean paused=false;
	
	public LevelSelect(){
		start();
	}
	
	public void start(){

		int offset=14;
		
		setVisible(true);
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Level Select");
		
		setLayout(null);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		title=new JLabel("Level Select");
		title.setFont(new Font("Verdana", 0, 50));
		title.setBounds(getWidth()/2 - 160, -10, 1000, 120);
		
		lSelect=new JLabel("Select A Level:");
		lSelect.setFont(new Font("Arial", 0, 16));
		lSelect.setBounds(140, 40, 1000+offset, 120);
		
		backButton= new JButton();
		backButton.setBounds(5, 325, 100, 30);
		backButton.setText("Back");
		
		goButton= new JButton();
		goButton.setBounds(getWidth()-112, 325, 100, 30);
		goButton.setText("Go!");
		
		createButton= new JButton();
		createButton.setBounds(110, 325, 85, 30);
		createButton.setText("Create");
		
		loadButton= new JButton();
		loadButton.setBounds(198, 325, 85, 30);
		loadButton.setText("Load");
		
		rightButton= new JButton();
		rightButton.setBounds(getWidth()-80, 130, 50, 50);
		rightButton.setText(">");
		
		leftButton= new JButton();
		leftButton.setBounds(25, 130, 50, 50);
		leftButton.setText("<");
		
		lLevel=new JLabel(levels[levelIndex-1]);
		lLevel.setFont(new Font("Verdana", 0, 20));
		lLevel.setBounds(100, 95, 175+offset, 120);
		lLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lLevel.setVerticalAlignment(SwingConstants.CENTER);
		lLevel.setVisible(true);
		
		lLevelIndex=new JLabel("Level: "+(levelIndex));
		lLevelIndex.setFont(new Font("Verdana", 0, 16));
		lLevelIndex.setBounds(100, 70, 175+offset, 120);
		lLevelIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lLevelIndex.setVerticalAlignment(SwingConstants.CENTER);
		lLevelIndex.setVisible(true);
		
		Scanner x=null;
		
		try{
			URL url = ReadFile.class.getClassLoader().getResource("textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			x=new Scanner(url.openStream());
		} catch(NullPointerException e) {
			Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "Could not find file at /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			//e.printStackTrace();
		} catch(IOException e){
			Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "Error reading file at /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			e.printStackTrace();
		}
		
		String desc="(No Description Provided)";
		if(x!=null){
			try{
				desc=x.nextLine();
			}catch(NoSuchElementException e){
				Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "No text found in /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
		
			}
		}
		
		lDesc=new JLabel(desc);
		lDesc.setFont(new Font("Verdana", 0, 14));
		lDesc.setBounds(0, 250, 390, 120);
		lDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lDesc.setVerticalAlignment(SwingConstants.CENTER);
		lDesc.setVisible(true);
		
		ImageIcon myImage = new ImageIcon(LevelSelect.class.getClassLoader().getResource("textures/levels/default.png"));
		
		try{
			myImage = new ImageIcon(LevelSelect.class.getClassLoader().getResource("textures/levels/"+levels[levelIndex-1]+"/preview.png"));
		}catch(NullPointerException e){
			
		}
		
		Image img = myImage.getImage();
		Image newimg = img.getScaledInstance(125, 125,  java.awt.Image.SCALE_AREA_AVERAGING);
		myImage = new ImageIcon(newimg);
		
		lPreview=new JLabel(myImage);
		lPreview.setBounds(25, 135, getWidth()-75+offset, 200);
		lPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lPreview.setVerticalAlignment(SwingConstants.CENTER);
		lPreview.setVisible(true);
		
		add(goButton);
		add(leftButton);
		add(rightButton);
		add(backButton);
		add(createButton);
		add(loadButton);
		add(title);
		add(lSelect);
		add(lLevel);
		add(lLevelIndex);
		add(lPreview);
		add(lDesc);
		
		repaint();
		
		goButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				Sound.playSound("Select2.wav", 0.5f);
				
				go();
				dispose();
			}
		});
		loadButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				Sound.playSound("Select3.wav", 0.5f);
				
				/*while(cmd.trim().equals("")){
					try{
						cmd = new String(JOptionPane.showInputDialog("Load what level?"));
					}catch(NullPointerException e){
						return;
					}
					if(cmd.trim().equals("")){
						JOptionPane.showMessageDialog(null, "Level name must not be nothing.", Game.NAME, 0);
					}
				}*/
				
				final JFileChooser fc = new JFileChooser();
	        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        	int returnVal = fc.showOpenDialog(null);
	        	if(returnVal==JFileChooser.CANCEL_OPTION||returnVal==JFileChooser.ERROR_OPTION){
	        		return;
	        	}
	        	String s = fc.getSelectedFile().toString();
				
				createButton.setText(s);
				
				goCreate();
				dispose();
			}
		});
		createButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				Sound.playSound("Select3.wav", 0.5f);
				
				long time = System.currentTimeMillis();
	        	String cmd = time+"";

				/*try{
					cmd = new String(JOptionPane.showInputDialog("What Do You Want It's Filename To Be?"));
				}catch(NullPointerException e){
					return;
				}*/

				int width=-10;
				int height=-10;
				
				while(width<=0||height<=0||width>1000||height>1000){
	        		try{
	        			width = Integer.parseInt(((new String(JOptionPane.showInputDialog("What Do You Want It's Width To Be?")))));
	        			
	        		}catch(NumberFormatException e){
	        			
	        		}catch(NullPointerException e){
	        			return;
	        		}
	        		try{
	        			height = Integer.parseInt(((new String(JOptionPane.showInputDialog("What Do You Want It's Height To Be?")))));
	        			
	        		}catch(NumberFormatException e){
	        			
	        		}catch(NullPointerException e){
	        			return;
	        		}
	        		
	        		if(width<=0||height<=0||width>1000||height>1000){
        				JOptionPane.showMessageDialog(null, "Width and height must be numbers between 1-1000.", Game.NAME, 0);
        			}
					}
				
				
				/*if(cmd==null||cmd.equals("")){
					cmd = time+"";
				}
				
				createButton.setText(cmd);
				
				String urlStr = InputHandler.class.getClassLoader().getResource("levels/")+""+cmd+"";
	        	File f=new File(urlStr.replace("file:/", "").replace("%20", " "));*/
				
				final JFileChooser fc = new JFileChooser();
	        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        	int returnVal = fc.showOpenDialog(null);
	        	if(returnVal==JFileChooser.CANCEL_OPTION||returnVal==JFileChooser.ERROR_OPTION){
	        		return;
	        	}
	        	File f = fc.getSelectedFile();
	        	
            	if(f!=null) f.mkdirs();
            	
            	/*urlStr = InputHandler.class.getClassLoader().getResource("levels/")+""+cmd+"/data.txt";
	        	f=new File(urlStr.replace("file:/", "").replace("%20", " "));*/
            	
            	f = new File(fc.getSelectedFile()+"\\data.txt");
	        	
	        	if(!f.exists()){
	        		try {
						f.createNewFile();
						BufferedWriter out = new BufferedWriter(new FileWriter(f));
						String nl = System.getProperty("line.separator");
						out.write("Name:"+nl+""+cmd+""+nl+"Song:"+nl+"null"+nl+"Player:"+nl+"32"+nl+"32"+nl+"0"+nl+"0"+nl+"0");
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
            	
            	
	        	f = new File(fc.getSelectedFile()+"\\level.png");
	        	
            	if (!f.exists()) {
            		try {
						f.createNewFile();
						if(f!=null){
							
							
			        		
			        		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			        		
			        		Graphics g = image.getGraphics();
			        		
			        		g.setColor(new Color(85, 85, 85));
			        		g.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
			        		
			        		try {
			                    ImageIO.write(image, "png", f);
			                } catch (IOException e1) {
			                    e1.printStackTrace();
			                }
			        		
			        		inEditMode=true;
			        		goCreate();
			        		dispose();
			        		
			        	}
						Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Created New Level: "+cmd+"!");
					} catch (IOException e) {
						Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Failed To Create Level.");
						e.printStackTrace();
					}
            	}else{
            		createButton.setText("Level Exists");
            	}
            	
            	
            	
			}
		});
		backButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Sound.playSound("Select1.wav", 0.5f);
				new MainMenu();
				dispose();
			}
		});
		rightButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Sound.hardStopSounds();
				Sound.playSound("Select2.wav", 0.5f);
				if(levelIndex<levels.length){
					levelIndex++;
					
				}else{
					levelIndex=1;
				}
				
				updateThings();
				
			}
		});
		leftButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Sound.hardStopSounds();
				Sound.playSound("Select2.wav", 0.5f);
				if(levelIndex>1){
					levelIndex--;
				}else{
					levelIndex=levels.length;
				}
				updateThings();
					
			}
		});
	}
	
	public static void reload(){
		paused=false;
		debugMenu=false;
	}
	
	private void go(){
		if(first){
			Game.toLevel=levels[levelIndex-1];
			ready=true;
			GameLauncher.menuReady();
		}else{
			Game.toLevel=levels[levelIndex-1];
			Game.game.frame.setVisible(true);
			Game.game.restart(Game.toLevel, false, false);
		}
		first=false;
	}
	
	private void goCreate(){
		if(first){
			Game.toLevel=createButton.getText();
			Game.custom=true;
			ready=true;
			GameLauncher.menuReady();
		}else{
			Game.toLevel=createButton.getText();
			Game.game.frame.setVisible(true);
			Game.game.restart(Game.toLevel, false, true);
		}
		first=false;
	}
	
	public void updateThings(){
		lLevel.setText(levels[levelIndex-1]);
		lLevelIndex.setText("Level: "+(levelIndex));
		
		Sound.stopSounds();
		//Sound.playSoundPath("textures/levels/"+levels[levelIndex-1]+"/sound.wav", 0.5f);
		
		ImageIcon myImage = new ImageIcon(LevelSelect.class.getClassLoader().getResource("textures/levels/default.png"));
		try{
			myImage = new ImageIcon(LevelSelect.class.getClassLoader().getResource("textures/levels/"+levels[levelIndex-1]+"/preview.png"));
		}catch(NullPointerException e){
			
		}
		
		Image img = myImage.getImage();
		Image newimg = img.getScaledInstance(125, 125,  java.awt.Image.SCALE_AREA_AVERAGING);
		myImage = new ImageIcon(newimg);
		lPreview.setIcon(myImage);
		
		
		Scanner x=null;
		
		try{
			URL url = ReadFile.class.getClassLoader().getResource("textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			x=new Scanner(url.openStream());
		} catch(NullPointerException e) {
			Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "Could not find file at /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			//e.printStackTrace();
		} catch(IOException e){
			Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "Error reading file at /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
			e.printStackTrace();
		}
		
		String desc="(No Description Provided)";
		if(x!=null){
			try{
				desc=x.nextLine();
			}catch(NoSuchElementException e){
				Game.debug(Game.DebugLevel.INFO, Game.DebugPriority.NORMAL,  "No text found in /textures/levels/"+levels[levelIndex-1]+"/desc.txt");
		
			}
		}
		
		lDesc.setText(desc);
	}
	
}
