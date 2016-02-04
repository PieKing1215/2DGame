package me.pieking1215.game.net.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.entities.Player;
import me.pieking1215.game.gui.OptionsMenu;


public class Sound {
	
	public static Clip clip=null;
	public static List<ClipIds> clips = new ArrayList<ClipIds>();
	public static List<Sound> sounds = new ArrayList<Sound>();
	private String url=null;
	public static ClipIds stayInMe;
	
	public static synchronized void playSound(final String url, float volume) {
		boolean loop=false;
		if(url.equals("Back2k.wav")||url.equals("ChipsetSunset.wav")||url.equals("FuckDaRipper.wav")||url.equals("SleighRide.wav")||url.equals("Impact.wav")||url.equals("StayInsideMe.wav")){
			loop=true;
			Player.sendMessage("Playing: "+(url.replace("wav", "")));
			if(!OptionsMenu.optMusic){
				return;
			}
		}else if(!OptionsMenu.optSound){
			return;
		}
		
			// The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.=
		      try {
		    	clip = AudioSystem.getClip();
		    	//URL url2 = new URL("http://www.thepierealm.coffeecup.com/java/2D/sound/"+url);
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource("sound/"+url));
		        clip.open(inputStream);
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
				
				
				
				if(loop){
					clip.setLoopPoints(0, -1);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
		        clip.start(); 
		        ClipIds cl= new ClipIds(clip,0,url);
		        clips.add(cl);
		        if(url.equals("RainLoop.wav")){
					clip.setLoopPoints(0, -1);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
		        
		        if(url.equals("StayInsideMe.wav")){
		    		if(stayInMe==null){
		    			stayInMe=new ClipIds(clip, 0,url);
		    		}else{
		    			clip.stop();
		    			clip.close();
		    			clips.remove(cl);
		    			return;
		    		}
				}
		        
		      } catch (Exception e) {
		        //System.err.println(e.getMessage());
		    	  Game.debug(DebugLevel.WARNING, DebugPriority.DEV, "Invalid sound at /sound/"+url);
		      }
	}

	public static synchronized void playSoundPath(final String url, float volume) {
		if(!OptionsMenu.optSound){
			return;
		}
		      try {
		    	  clip = AudioSystem.getClip();
		    	  //URL url2 = new URL("http://www.thepierealm.coffeecup.com/java/2D/sound/"+url);
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(url));
		        clip.open(inputStream);
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
		        clip.start(); 
		        clips.add(new ClipIds(clip,0,url));
		        if(url.equals("RainLoop.wav")){
					clip.setLoopPoints(0, -1);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
		      } catch (Exception e) {
		        //System.err.println(e.getMessage());
		    	  Game.debug(DebugLevel.WARNING, DebugPriority.DEV, "Invalid sound at "+url);
		      }
	}
	
	public static void stopSounds() {
		for(ClipIds clip:clips){
			if(clip.getClip().isActive()){
				if(stayInMe!=null&&clip.getUrl()==stayInMe.getUrl()){
					
				}else{
					clip.getClip().stop();
				}
			}
		}
		//for(ClipIds clip:clips){
		//	clip.getClip().close();
		//}
		clips.clear();
	}
	
	public static void hardStopSounds() {
		for(ClipIds clip:clips){
			if(clip.getClip().isActive()){
				clip.getClip().stop();
			}
		}
		if(stayInMe!=null){
			stayInMe.getClip().stop();
			stayInMe=null;
		}
		clips.clear();
	}
	
	public static synchronized void stopSound(String url) {
		for(Sound sound:sounds){
			if(sound.getUrl().equals(url)){
				clip.stop();
			}
		}
	}
	public String getUrl(){
		return url;
	}
	
	public static void pause(){
		for(ClipIds clip:clips){
			if(stayInMe==null||clip.getUrl()!=stayInMe.getUrl()){
				clip.setPos(clip.getClip().getMicrosecondPosition());
				clip.getClip().stop();
			}
		}
	}
	
	public static void unpause(){
		for(ClipIds clip:clips){
			if(clip!=stayInMe){
				clip.getClip().setMicrosecondPosition(clip.getPos());
				clip.getClip().start();
			}
		}
	}
	
}

