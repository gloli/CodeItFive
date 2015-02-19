/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.dartmouth.cs.codeitfive.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

import edu.dartmouth.cs.codeitfive.Globals;
import edu.dartmouth.cs.codeitfive.Wave;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements Renderer {

    private static final String TAG = "MyGLRenderer";

    private float mHeight;
    private float mWidth;
    private Wave background;
    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRunTime = 0;

    public MyGLRenderer() {
        this.background = new Wave();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreate()");
        unused.glEnable(GL10.GL_TEXTURE_2D);

        // Set the background frame color
        unused.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        unused.glClearDepthf(1.0f);

        unused.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Enable texture coordinate arrays.
        unused.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Enable blend to create transperency
        unused.glEnable(GL10.GL_BLEND);
        unused.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        unused.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        // set image
        background.loadTexture(unused, Globals.context);
    }

    public void DrawBackground(GL10 gl) {
        Log.d(TAG, "DrawBackground()");
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //gl.glScalef(.15f, Globals.getProportionateHeight(0.15f), .15f);
        //gl.glTranslatef(2.8f, 1f, 0f);
        //gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 1f, -1.0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.7f, -1.0f);

        background.draw(gl);
        gl.glPopMatrix();

        gl.glLoadIdentity();

    }


    @Override
    public void onDrawFrame(GL10 unused) {
        Log.d(TAG, "onDrawFrame()");
        // Draw background color
        unused.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        unused.glLoadIdentity();
        unused.glTranslatef(0.0f, 0.0f, -5.0f);

        loopStart = System.currentTimeMillis();

        // Draw triangle
        //DrawBackground(unused);
        background.draw(unused);

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Log.d(TAG, "onSurfaceChange()");

        if (height == 0)
            height = 1;
        Globals.GAME_SCREEN_WIDTH = width;
        mWidth = width;
        Globals.GAME_SCREEN_HEIGHT = height;
        mHeight = height;

        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        unused.glViewport(0, 0, width, height);
        unused.glMatrixMode(GL10.GL_PROJECTION);
        unused.glLoadIdentity();

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(unused, 45.0f, (float) width / (float) height, 0.1f, 100.0f);


        // Put OpenGL to projectiong matrix to access glOrthof()
        unused.glMatrixMode(GL10.GL_MODELVIEW);

        // Load current identity of OpenGL state
        unused.glLoadIdentity();

        // set orthogonal two dimensional rendering of scene
        //unused.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

    }



    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getHeight() {
        return mHeight;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setHeight(float height) {
        mHeight = height;
    }


}