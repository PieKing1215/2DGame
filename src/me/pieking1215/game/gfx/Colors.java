package me.pieking1215.game.gfx;

public class Colors {

	public static int get(int color1,int color2,int color3,int color4) {
		//System.out.println((get(color4)<<24)+(get(color3)<<16)+(get(color2)<<8)+(get(color1)));
		return (get(color4)<<24)+(get(color3)<<16)+(get(color2)<<8)+(get(color1));
	}
	
	private static int get(int color) {
		if(color<0) return 255;
		int r = color/100%10;
		int g = color/10%10;
		int b = color%10;
		return r*36 + g*6 + b;
	}
	
	public static int tint(int color, int col2) {
		//Color c = new Color(color);
		
		int rOut = Math.round((float) (((0xFF & ( color >> 16))+(0xFF & ( col2 >> 16))/2)));
		int gOut = Math.round((float) (((0xFF & ( color >> 8))+(0xFF & ( col2 >> 8))/2)));
		int bOut = Math.round((float) (((0xFF & ( color >> 0))+(0xFF & ( col2 >> 0))/2)));
		
		//System.out.println(rOut+" "+gOut+" "+bOut);
		
		return (bOut | gOut << 8 | rOut << 16);
	}
	
}
