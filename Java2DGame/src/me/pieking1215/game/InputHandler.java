package me.pieking1215.game;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gfx.ScreenImage;
import me.pieking1215.game.gui.LevelSelect;
import me.pieking1215.game.gui.OptionsMenu;
import me.pieking1215.game.level.Level;
import me.pieking1215.game.level.tiles.Tile;
import me.pieking1215.game.net.sound.Sound;

public class InputHandler implements KeyListener , MouseListener, MouseWheelListener{

	private static JPanel panel;
	
    public InputHandler(Game game) {
        game.addKeyListener(this);
        game.addMouseListener(this);
        game.addMouseWheelListener(this);
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) numTimesPressed++;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();
    public Key esc = new Key();
    public Key r = new Key();
    public int spacePressed;
    public int enterPressed;
    public int shiftPressed;
    public int tPressed;
    public int rPressed;
    public int qPressed;
    public int ePressed;
    
    public boolean mousePressed = false;
    
    public void keyPressed(KeyEvent e) {
    	//if(Player.hp>0) {
        toggleKey(e.getKeyCode(), true);
        
        if(!OptionsMenu.paused){
        	
        if (e.getKeyCode()==KeyEvent.VK_F6){
        	Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Reloading...");
        	Player.restart(true);
        	Player.sendMessage("Reloaded Level.");
        }
        
        if (e.getKeyCode()==KeyEvent.VK_F5){
        	Level.level.saveLevelToFile();
        	Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Saved Level.");
        	Player.sendMessage("Saved Level.");
        }
        
        if (e.getKeyCode()==KeyEvent.VK_F4){
        	Game.inEditMode=!Game.inEditMode;
        	LevelSelect.inEditMode=!LevelSelect.inEditMode;
        	Player.sendMessage("Toggled Edit: "+LevelSelect.inEditMode+".");
        	Sound.stopSounds();
        	if(LevelSelect.inEditMode){
        		Sound.playSound("StayInsideMe.wav", 0.5f);
        	}
        }
        
        if (e.getKeyCode()==KeyEvent.VK_F7){
        	Game.screen.sheet.reload();
        	Player.sendMessage("Reloaded Textures.");
        }
        
        } //Ends No Pause Zone
        
        if (e.getKeyCode()==KeyEvent.VK_F2){
        	
        	long time =System.currentTimeMillis();
        	String timeS = (time+"").substring(0, 5)+"-"+(time+"").substring(5, 9)+"-"+(time+"").substring(9, 13);
        	//14520-4430-3251
        	
        	
        	String urlStr = InputHandler.class.getClassLoader().getResource("screenshots/")+""+timeS+".png";
        	File f=new File(urlStr.replace("file:/", "").replace("%20", " "));
        	try {
				f.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
        	
            try {
            	panel=new JPanel();
            	panel.setSize(Game.game.frame.getContentPane().getWidth(),Game.game.frame.getContentPane().getHeight());
            	panel.paint(Game.gshot);
            	
                try {
					ImageIO.write(ScreenImage.createImage(Game.game.frame), "png", f);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Game.debug(DebugLevel.INFO, DebugPriority.NORMAL, "Saved Screenshot as "+timeS+".png");
            Player.sendMessage("Saved Screenshot.");
        }
        
        if (e.getKeyCode()==KeyEvent.VK_F3){
        	OptionsMenu.debugMenu=!OptionsMenu.debugMenu;
        }
        
        if (e.getKeyCode()==KeyEvent.VK_F1){
        	OptionsMenu.hideGUI=!OptionsMenu.hideGUI;
        }
        
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
        	Game.togglePause();
        }
        
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE && spacePressed == 0) {
            spacePressed = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && enterPressed == 0) {
            enterPressed = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT && shiftPressed == 0) {
            shiftPressed = 1;
        }
    	//}
    	//if(Player.hp<0) {
           // toggleKey(e.getKeyCode(), true);
           // if (e.getKeyCode() == KeyEvent.VK_R) {
            //   game.restart();
            //   System.out.println("Player");
            //}
        	//}
        if (e.getKeyCode() == KeyEvent.VK_T && tPressed == 0) {
            //tPressed = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q && qPressed == 0) {
            qPressed = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_E && ePressed == 0) {
            ePressed = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
    	//if(Player.hp>0) {
        toggleKey(e.getKeyCode(), false);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterPressed = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = 0;
        }
        	//}
        if (e.getKeyCode() == KeyEvent.VK_T) {
            tPressed = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            qPressed = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            ePressed = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void toggleKey(int keyCode, boolean isPressed) {
    	//System.out.println(""+keyCode+" "+isPressed);
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            esc.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            space.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_R && !(Game.isPlayerAlive) && !OptionsMenu.paused) {
        Sound.stopSounds();
        r.toggle(isPressed);
        }
    }

	public void mouseClicked(MouseEvent arg0) {
		Game.click();
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		mousePressed=true;
	}

	public void mouseReleased(MouseEvent arg0) {
		mousePressed=false;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println(e.getWheelRotation());
		Player.tileChangeId-=e.getWheelRotation();
		
		if(Player.tileChangeId==-1) {
			Player.tileChangeId=Tile.tileMax;
		}
		if(Player.tileChangeId==Tile.tileMax+1) {
			Player.tileChangeId=0;
		}
	}
}