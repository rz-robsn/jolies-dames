package com.jolies.dames.utilities;

import com.jolies.dames.utilities.glviews.BoardListener;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GLSurfaceViewGame extends GLSurfaceView {

    private BoardListener listener;
    
	public GLSurfaceViewGame(Context context) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		this.setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		this.setRenderer(new RendererGameView());
	}

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }

    public void setListener(BoardListener listener)
    {
        this.listener = listener;
    }

}
