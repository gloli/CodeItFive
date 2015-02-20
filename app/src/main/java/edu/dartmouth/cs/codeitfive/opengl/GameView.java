package edu.dartmouth.cs.codeitfive.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GameView extends GLSurfaceView {

	private GameRenderer renderer;
	
	public GameView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
		renderer = new GameRenderer();
		
		this.setRenderer(renderer);
	}

}
