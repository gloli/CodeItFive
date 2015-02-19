package edu.dartmouth.cs.codeitfive;

import android.content.Context;
import edu.dartmouth.cs.codeitfive.opengl.Texture;

/**
 * Created by Archy on 2/19/15.
 */
public class Wave extends Texture {

    private static float texture[] = {
            0.0f,  0.622008459f, 0.0f,   // top
            -0.5f, -0.311004243f, 0.0f,   // bottom left
            0.5f, -0.311004243f, 0.0f    // bottom right
    };


    public Wave() {
        super(texture);
    }
}
