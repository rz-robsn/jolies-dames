package com.jolies.dames.utilities.glviews;

import com.jolies.dames.utilities.RendererGameView;

import android.graphics.PointF;
import android.opengl.GLES10;
import android.opengl.GLU;
import android.util.FloatMath;
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
            
            
          // Logic taken from 
          // http://softwareprodigy.blogspot.fr/2009/08/gluunproject-for-iphone-opengl-es.html

          float cX, cY, cZ, fX, fY, fZ;
          cX = nearPos[0]/nearPos[3];
          cY = nearPos[1]/nearPos[3];
          cZ = nearPos[2]/nearPos[3];
          fX = farPos[0]/farPos[3];
          fY = farPos[1]/farPos[3];
          fZ = farPos[2]/farPos[3];            
            
          //ray
          fX -= cX;
          fY -= cY;
          fZ -= cZ;
          float rayLength = FloatMath.sqrt(cX*cX + cY*cY + cZ*cZ);
          
          //normalize
          fX /= rayLength;
          fY /= rayLength;
          fZ /= rayLength;

          //T = [planeNormal.(pointOnPlane - rayOrigin)]/planeNormal.rayDirection;
          //pointInPlane = rayOrigin + (rayDirection * T);

          float pointInPlaneX = 0;
          float pointInPlaneY = 0;
          float pointInPlaneZ = 0;
          float planeNormalX = 0;
          float planeNormalY = 1;
          float planeNormalZ = 0;

          pointInPlaneX -= cX;
          pointInPlaneY -= cY;
          pointInPlaneZ -= cZ;

          float dot1, dot2;
          dot1 = (planeNormalX * pointInPlaneX) + (planeNormalY * pointInPlaneY) + (planeNormalZ * pointInPlaneZ);
          dot2 = (planeNormalX * fX) + (planeNormalY * fY) + (planeNormalZ * fZ);
          float t = dot1/dot2;

          fX *= t;
          fY *= t;
          fZ *= t;
          
          float pointTouchedX = fX + cX;
          float pointTouchedY = fY + cY;
          float pointTouchedZ = fZ + cZ;          
          
          Log.d("opengl", "x=" + pointTouchedX + " y=" + pointTouchedY + ", z=" + pointTouchedZ);
        }
                
        return true;
    }
    
    public void setListener(BoardListener listener)
    {
        this.listener = listener;
    }    
}
