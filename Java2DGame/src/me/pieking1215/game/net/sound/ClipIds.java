package me.pieking1215.game.net.sound;

import javax.sound.sampled.Clip;

public class ClipIds {

	private Clip clip;
	private long pos;
	private String url;
	
	public ClipIds(Clip clip, long pos, String url){
		this.clip=clip;
		this.pos=pos;
		this.url=url;
	}
	
	public Clip getClip(){
		return clip;
	}
	
	public long getPos(){
		return pos;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setPos(long pos2){
		pos=pos2;
	}
	
}
