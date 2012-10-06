package com.jolies.dames.utilities;

import com.jolies.dames.utilities.glviews.BoardListener;
import com.jolies.dames.utilities.glviews.TouchListenerSurfaceViewGame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GLSurfaceViewGame extends GLSurfaceView {

    private RendererGameView renderer;
    private TouchListenerSurfaceViewGame touchListener;
    
	public GLSurfaceViewGame(Context context) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		this.setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		renderer = new RendererGameView();
		this.setRenderer(renderer);
		
		touchListener = new TouchListenerSurfaceViewGame(renderer);
		this.setOnTouchListener(touchListener);
	}

    public void setListener(BoardListener listener)
    {
        this.touchListener.setListener(listener);
    }

}
