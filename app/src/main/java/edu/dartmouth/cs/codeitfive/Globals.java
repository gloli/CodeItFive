/**
 * Globals.java
 * 
 * Created by Xiaochao Yang on Dec 9, 2011 1:43:35 PM
 * 
 */

package edu.dartmouth.cs.codeitfive;


// More on class on constants:
// http://www.javapractices.com/topic/TopicAction.do?Id=2

public abstract class Globals {

	// Debugging tag
	public static final String TAG = "CodeItFive";
	
	
	public static final int ACCELEROMETER_BUFFER_CAPACITY = 2048;
	public static final int ACCELEROMETER_BLOCK_CAPACITY = 64;
	
	public static final int SHAKE_ID_HARD = 2;
	public static final int SHAKE_ID_MODERATE = 1;
	public static final int SHAKE_ID_SOFT = 0;
  public static final int SHAKE_ID_NONE = 3;


  public static final int SERVICE_TASK_TYPE_COLLECT = 0;
	public static final int SERVICE_TASK_TYPE_CLASSIFY = 1;

	public static final String CLASS_LABEL_KEY = "label";	
	public static final String CLASS_LABEL_HARD = "hard";
	public static final String CLASS_LABEL_MODERATE = "moderate";
	public static final String CLASS_LABEL_SOFT = "soft";

	public static final String FEAT_FFT_COEF_LABEL = "fft_coef_";
	public static final String FEAT_MAX_LABEL = "max";
	public static final String FEAT_SET_NAME = "accelerometer_features";

	public static final String FEATURE_FILE_NAME = "features.arff";
	public static final String RAW_DATA_NAME = "raw_data.txt";
	public static final int FEATURE_SET_CAPACITY = 10000;
	
	public static final int NOTIFICATION_ID = 1;
	
	
  
}
