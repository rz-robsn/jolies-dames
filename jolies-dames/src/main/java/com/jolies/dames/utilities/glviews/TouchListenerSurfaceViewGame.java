package com.jolies.dames.utilities.glviews;

import com.jolies.dames.utilities.RendererGameView;

import android.graphics.PointF;
import android.opengl.GLES10;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 *  This class interprets the touch controls on the screen and dispatches the appropriate
 *  events. 
 */
public class TouchListenerSurfaceViewGame implements OnTouchListener
{
    private BoardListener listener;
    private RendererGameView renderer;
    
    public TouchListenerSurfaceViewGame(RendererGameView renderer)
    {
        super();
        this.renderer = renderer;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        float x = event.getX();
        float y = renderer.getmViewPort()[3] - event.getY();
        
        float[] nearPos = new float[4];
        float[] farPos = new float[4];

        boolean unprojectedNear = (GLU.gluUnProject(x, y, 0.0f,
                renderer.getmModelViewMatrix(), 0, renderer.getmProjectionMatrix(), 0,
            renderer.getmViewPort(), 0, nearPos, 0) == GLES10.GL_TRUE);
        boolean unprojectedFar = (GLU.gluUnProject(x, y, 1.0f,
            renderer.getmModelViewMatrix(), 0, renderer.getmProjectionMatrix(), 0,
            renderer.getmViewPort(), 0, farPos, 0) == GLES10.GL_TRUE);

        if (unprojectedNear && unprojectedFar)
        {
            // To convert the transformed 4D vector to 3D, you must divide
            // it by the W component
            nearPos = convertTo3d(nearPos);
            farPos = convertTo3d(farPos);

            float floorViewX = (((farPos[0] - nearPos[0]) / 9) * nearPos[2]) + nearPos[0];
            float floorViewY = ((((farPos[1] - nearPos[1]) / 9) * nearPos[2])  + nearPos[1]) + (2f * nearPos[1]);
        
            Log.d("opengl", "floorViewX=" + floorViewX + ", floorViewY=" + floorViewY);
        }
                
        return true;
    }
    
    public void setListener(BoardListener listener)
    {
        this.listener = listener;
    }    
    
    private float[] convertTo3d(float[] vector) {
        float[] result = new float[4];

        for (int index = 0; index < vector.length; index++) {
            result[index] = vector[index] / vector[3];
        }

        return result;
    }
}
