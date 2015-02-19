package edu.dartmouth.cs.codeitfive.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import edu.dartmouth.cs.codeitfive.Globals;
import edu.dartmouth.cs.codeitfive.R;

public class Texture {

    private FloatBuffer vertBuf;
    private FloatBuffer texBuf;
    private ByteBuffer indexBuf;

    // texture pointer
    private int[] textures = new int[1];

    private float vertices[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,


    };

    // values will pass from subclass
    private float texture[];


    private byte indices[] = {
            0,1,2,
            0,2,3
    };



    public Texture(float _texture[]) {

        texture = _texture;

        // 4 bytes per coordinate
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());

        // allocate byte buffer memory
        vertBuf = byteBuf.asFloatBuffer();

        // fill with vertices
        vertBuf.put(vertices);

        // point cursor to beginning of buffer
        vertBuf.position(0);

        // put textures into byte buffer
        byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        texBuf = byteBuf.asFloatBuffer();
        texBuf.put(texture);
        texBuf.position(0);

        indexBuf = ByteBuffer.allocateDirect(indices.length);
        indexBuf.put(indices);
        indexBuf.position(0);
    }




    public void draw(GL10 gl){
        gl.glPushMatrix();
        // bind to generated texture
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // point to buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glColor4f (0.0f, 1.0f, 0.0f, 0.5f);

        // set face rotation
        gl.glFrontFace(GL10.GL_CCW);
//        gl.glEnable(GL10.GL_CULL_FACE);
//        gl.glCullFace(GL10.GL_BACK);

        // point to vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuf);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuf);

        // draw vertices
//        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
//                GL10.GL_UNSIGNED_BYTE, indexBuf);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        // disable client state
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
       // gl.glDisable(GL10.GL_CULL_FACE);

        gl.glPopMatrix();
    }

    // load image and bind to surface
    // called in MyGLRenderer > onSurfaceCreated
    public void loadTexture(GL10 gl, Context context){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), Globals.BACKGROUND, opts);
        // create texture pointer
        gl.glGenTextures(1, textures, 0);

        // bind to textures array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // create filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        // create 2D texture image from bitap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

}
