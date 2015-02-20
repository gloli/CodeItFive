package edu.dartmouth.cs.codeitfive.opengl;

import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.dartmouth.cs.codeitfive.Wave;

public class GameRenderer implements Renderer {

//	private TexRoad road = new TexRoad();
//	private TexCar car = new TexCar();
//	private TexController accelerator = new TexController();
//	private TexController breaks = new TexController();
    private Wave background = new Wave();
	
	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		loopStart = System.currentTimeMillis();
		try {
			if (loopRunTime < Global.GAME_THREAD_FPS_SLEEP) {
				Thread.sleep(Global.GAME_THREAD_FPS_SLEEP - loopRunTime);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		DrawBackground(gl);
//		DrawCar(gl);
//		DrawAccel(gl);
//		DrawBreaks(gl);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	
	public void DrawBackground(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		//gl.glScalef(1f, 1f, 1f);
		//gl.glTranslatef(0f, 0f, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		//gl.glTranslatef(0.0f, 0.7f, 0.0f);

		background.draw(gl);
		gl.glPopMatrix();
		
		gl.glLoadIdentity();
	}

	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Enable 2D maping capability
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		
		// Text depthe of all objects on surface
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		// Enable blend to create transperency
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        background.loadTexture(gl, Global.BACKGROUND, Global.context);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Enable game screen width and height to access other functions and classes
		Global.GAME_SCREEN_WIDTH = width;
		Global.GAME_SCREEN_HEIGHT = height;
		
		// set position and size of viewport 
		gl.glViewport(0, 0, width, height);
		
		// Put OpenGL to projectiong matrix to access glOrthof()
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		// Load current identity of OpenGL state
		gl.glLoadIdentity();
		
		// set orthogonal two dimensional rendering of scene
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
	}

}
