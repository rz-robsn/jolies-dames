package com.jolies.dames.utilities;

import com.jolies.dames.utilities.glviews.BoardListener;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GLSurfaceViewGame extends GLSurfaceView {

    private BoardListener listener;
    private RendererGameView renderer;
    
	public GLSurfaceViewGame(Context context) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		this.setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		renderer = new RendererGameView();
		this.setRenderer(renderer);
	}

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void setListener(BoardListener listener)
    {
        this.listener = listener;
    }

}
