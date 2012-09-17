package com.jolies.dames.utilities;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLSurfaceViewGame extends GLSurfaceView {

	public GLSurfaceViewGame(Context context) {
		super(context);

		// Create an OpenGL ES 2.0 context.
		this.setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		this.setRenderer(new RendererGameView());

	}

}
