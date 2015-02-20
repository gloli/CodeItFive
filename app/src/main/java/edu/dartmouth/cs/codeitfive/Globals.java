/**
 * Globals.java
 * 
 * Created by Xiaochao Yang on Dec 9, 2011 1:43:35 PM
 * 
 */

package edu.dartmouth.cs.codeitfive;


// More on class on constants:
// http://www.javapractices.com/topic/TopicAction.do?Id=2

import android.content.Context;
import android.util.AttributeSet;

public abstract class Globals {

	// Debugging tag
	public static final String TAG = "CodeItFive";


    public static Context context;
    public static AttributeSet attributes;

    public static int GAME_SCREEN_WIDTH = 0;
    public static int GAME_SCREEN_HEIGHT = 0;

	public static final int ACCELEROMETER_BUFFER_CAPACITY = 2048;
	public static final int ACCELEROMETER_BLOCK_CAPACITY = 64;

    public static int SHAKE_ACTION = 0;
    public static final int SHAKE_ID_SOFT = 0;
    public static final int SHAKE_ID_MODERATE = 1;
    public static final int SHAKE_ID_HARD = 2;
    public static final int SHAKE_ID_NONE = 3;

    public static final float SHAKE_MAX = 100.0f;
    public static int BACKGROUND = R.drawable.coke_background;


    public static float getProportionateHeight(float width){

        float ratio = (float)GAME_SCREEN_WIDTH/GAME_SCREEN_HEIGHT;
        float height = ratio * width;
        return height;
    }

}
