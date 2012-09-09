package com.jolies.dames;

import com.jolies.dames.utilities.RendererGameView;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ActivityPlayGame extends Activity {

	private GLSurfaceView surface ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		surface = new GLSurfaceView(this);
		
        // Create an OpenGL ES 2.0 context.
		surface.setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
		surface.setRenderer(new RendererGameView());

        // Render the view only when there is a change in the drawing data
		surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
		this.setContentView(surface);
	}

	@Override
	protected void onPause() {
		super.onPause();
		surface.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		surface.onResume();
	}

}
