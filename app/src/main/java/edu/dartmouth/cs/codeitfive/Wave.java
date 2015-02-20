package edu.dartmouth.cs.codeitfive;

import android.content.Context;
import edu.dartmouth.cs.codeitfive.opengl.Texture;

/**
 * Created by Archy on 2/19/15.
 */
public class Wave extends Texture {

    private static float texture[] = {
//            0.0f, 0.0f,
//            1.0f, 0.0f,
//            1.0f, 1.5f,
//            0.0f, 1.5f
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
    };


    public Wave() {
        super(texture);
    }
}
