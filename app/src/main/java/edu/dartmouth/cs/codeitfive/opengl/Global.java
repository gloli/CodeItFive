package edu.dartmouth.cs.codeitfive.opengl;

import android.content.Context;

import edu.dartmouth.cs.codeitfive.R;

public class Global {
	public static Context context;
    public static int BACKGROUND = R.drawable.coke_background;
	public static final int GAME_THREAD_FPS_SLEEP = (1000/60);
	public static int GAME_SCREEN_WIDTH = 0;
	public static int GAME_SCREEN_HEIGHT = 0;
	public static final int CONTROL_RELEASED = 0;
	public static final int ACCELERATOR_PRESSED = 1; 
	public static final int BREAKs_PRESSED = 2;
	public static int PLAYER_ACTION = 0;
	
	
	
	public static float getProportionateHeight(float width){
		
		float ratio = (float)GAME_SCREEN_WIDTH/GAME_SCREEN_HEIGHT;
		float height = ratio * width;		
		return height;
	}
	
}
