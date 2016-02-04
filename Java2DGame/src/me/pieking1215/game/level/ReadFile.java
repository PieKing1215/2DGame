package me.pieking1215.game.level;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import me.pieking1215.game.Game;
import me.pieking1215.game.entities.BasicEnemy;
import me.pieking1215.game.entities.Spawner;
import me.pieking1215.game.entities.Text;
import me.pieking1215.game.entities.WinPad;
import me.pieking1215.game.entities.XpOrb;
import me.pieking1215.game.gfx.Particle;

public class ReadFile {

	private static Scanner x;
	private static Level level;
	
	public static void openFile(Level level2, boolean custom) {
		level=level2;
		//System.out.println(level);
		//System.out.println(ReadFile.class.getResource(level.imagePath+"/data.txt").toString().replace("%20", " ").replace("file:", ""));
		try{
			//System.out.println(res+"/levels/"+level.imagePath+"/data.txt");
			//System.out.println("http://www.thepierealm.coffeecup.com/java/2D/levels/"+level.imagePath+"/data.txt");
			//URL url = new URL("http://www.thepierealm.coffeecup.com/java/2D/levels/"+level.imagePath+"/data.txt");
			String p2="";
			URL url=null;
			if(!custom){
				url = ReadFile.class.getClassLoader().getResource("levels/"+level.imagePath+"/data.txt");
			}else{
				p2 = "file:/"+level2.imagePath.replace("\\", "/").replace(" ", "%20")+"/data.txt";
				//System.out.println(p2);
				url = new URL(p2);
				
			}
			//System.out.println(custom);
			
			//System.out.println(url);
			x=new Scanner(url.openStream());
		} catch(NullPointerException e) {
			Game.debug(Game.DebugLevel.SEVERE, Game.DebugPriority.NORMAL,  "Could not find file at /levels/"+level.imagePath+"/data.txt");
			e.printStackTrace();
		} catch(IOException e){
			Game.debug(Game.DebugLevel.SEVERE, Game.DebugPriority.NORMAL,  "Error reading file at /levels/"+level.imagePath+"/data.txt");
			e.printStackTrace();
		}
	}
	
	public static void readFile(){
		@SuppressWarnings("unused")
		String name = "DEFAULT LEVEL NAME :P";
		String song = "null";
		int spawnX = 0;
		int spawnY = 0;
		int pBombCt = 0;
		int pArwCt = 0;
		int pXp = 0;
		while(x.hasNext()){
			String a = x.nextLine();
			//System.out.println(a);
			switch(a){
			case "Name:":
				name = x.nextLine();
				break;
			case "Song:":
				song = x.nextLine();
				Game.song=song;
				break;
			case "Player:":
					//System.out.println("X:");
				if(!Game.spawnOverride){
					spawnX=Integer.parseInt(x.nextLine());
					Game.spawnX=spawnX;
					
					spawnY=Integer.parseInt(x.nextLine());
					Game.spawnY=spawnY;
					Game.spawnOverride=false;
				}else{
					x.nextLine();
					x.nextLine();
				}
					pBombCt=Integer.parseInt(x.nextLine());
					Game.bombCt2=pBombCt;
					pArwCt=Integer.parseInt(x.nextLine());
					Game.arwCt2=pArwCt;
					pXp=Integer.parseInt(x.nextLine());
					Game.xp2=pXp;
					//x.nextLine();
				break;
			case "Underground:":
				Level.underground = Boolean.parseBoolean(x.nextLine());
				break;
			case "Entities:":
				int mobX;
				int mobY;
				int mobDam;
				int mobHp;
				int mobViewDist;
				int mobScale;
				int mobColor;
				int mobSpeed;
				int mobCount;
				int mobRadius;
				int mobNum1;
				String mobType;
				String mobString1;
				boolean mobShouldDrop;
				boolean mobBool1;
				boolean end=false;
				while(!end){
				String type;
				type=x.nextLine();
				switch(type){
				case "Skeleton":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobDam = Integer.parseInt(x.nextLine());
					mobHp = Integer.parseInt(x.nextLine());
					mobViewDist = Integer.parseInt(x.nextLine());
					mobShouldDrop = Boolean.parseBoolean(x.nextLine());
					level.spawn(new BasicEnemy(level, mobX, mobY, mobDam, mobHp, mobViewDist, mobShouldDrop,0));
					break;
				case "Zombie":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobDam = Integer.parseInt(x.nextLine());
					mobHp = Integer.parseInt(x.nextLine());
					mobViewDist = Integer.parseInt(x.nextLine());
					mobShouldDrop = Boolean.parseBoolean(x.nextLine());
					level.spawn(new BasicEnemy(level, mobX, mobY, mobDam, mobHp, mobViewDist, mobShouldDrop,1));
					break;
				case "XpOrb":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					int mobValue = Integer.parseInt(x.nextLine());
					level.spawn(new XpOrb(level, mobX, mobY, mobValue));
					break;
				case "Particle":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobType = x.nextLine();
					mobScale = Integer.parseInt(x.nextLine());
					mobColor = Integer.parseInt(x.nextLine());
					mobString1 = x.nextLine();
					level.spawn(new Particle(level, mobX, mobY, mobType, mobScale, mobColor, mobString1));
					break;
				case "Spawner":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobType = x.nextLine();
					mobSpeed = Integer.parseInt(x.nextLine());
					mobCount = Integer.parseInt(x.nextLine());
					mobRadius = Integer.parseInt(x.nextLine());
					mobHp = Integer.parseInt(x.nextLine());
					mobNum1 = Integer.parseInt(x.nextLine());
					mobBool1 = Boolean.parseBoolean(x.nextLine());
					level.spawn(new Spawner(level, mobX, mobY, mobType, mobSpeed, mobCount, mobRadius, mobHp, mobNum1, mobBool1));
					break;
				case "WinPad":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobBool1 = Boolean.parseBoolean(x.nextLine());
					mobString1 = x.nextLine();
					level.spawn(new WinPad(level, mobX, mobY, mobBool1, mobString1));
					break;
				case "Text":
					mobX = Integer.parseInt(x.nextLine());
					mobY = Integer.parseInt(x.nextLine());
					mobString1 = x.nextLine();
					mobNum1=Integer.parseInt(x.nextLine());
					level.spawn(new Text(level, mobX, mobY, mobString1, mobNum1));
					break;
				case "End":
					end=true;
					break;
				}
				}
				break;
			default:
				break;
			}
			//System.out.println(""+x.nextLine());
		}
		//System.out.println(""+name);
		//System.out.println(""+spawnX);
		//System.out.println(""+spawnY);
	}
	
	public static void closeFile(){
		x.close();
	}
	
}
