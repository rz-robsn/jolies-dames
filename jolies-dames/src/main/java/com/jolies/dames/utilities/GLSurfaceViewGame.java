package com.jolies.dames.utilities;

import com.jolies.dames.utilities.glviews.TouchListenerSurfaceViewGame;
import com.jolies.dames.utilities.model.Slot;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GLSurfaceViewGame extends GLSurfaceView
{
    
    private RendererGameView renderer;
    private TouchListenerSurfaceViewGame touchListener;
    
    public GLSurfaceViewGame(Context context)
    {
        super(context);
        
        // Create an OpenGL ES 2.0 context.
        this.setEGLContextClientVersion(2);
        
        // Set the Renderer for drawing on the GLSurfaceView
        renderer = new RendererGameView();
        this.setRenderer(renderer);
        
        touchListener = new TouchListenerSurfaceViewGame(renderer);
        this.setOnTouchListener(touchListener);
        
        Slot[] slots = { new Slot(0, 0), new Slot(5, 5), new Slot(7, 7) };
        renderer.highLightSlots(slots);
    }
    
    public void setListenerBoard(ListenerBoard listener)
    {
        this.touchListener.setListener(listener);
    }
    
}
