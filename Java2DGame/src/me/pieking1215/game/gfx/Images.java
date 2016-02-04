package me.pieking1215.game.gfx;

import java.awt.Image;

import javax.swing.ImageIcon;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;

public class Images {

	public static final Image error = getImage("error.png");
	public static final Image playtestIcon = getImage("playtestingIcon.png");
	public static final Image devIcon = getImage("devIcon.png");
	public static final Image player = getImage("player.png");
	public static final Image loading = getImage("loadScreen.png");
	
	public static Image getImage(String name){
		Image img=null;
		if(!name.equals("error.png")) {
			img=error;
		}
		try{
			img = new ImageIcon(Images.class.getClassLoader().getResource("textures/"+name)).getImage();
		}catch(NullPointerException e){
			Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Texture not found: textures/"+name);
			if(name.equals("error.png"))img = new ImageIcon(Images.class.getClassLoader().getResource("textures/error.png")).getImage();
		}
		return img;
	}
	
}
