package com.jolies.dames.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Interface meant to be used in RajawaliRenderer class.
 */
public interface ListenerOnSurfaceCreated 
{
	/**
	 * Called when the onSurfaceCreated method of the RajawaliRender has Been called.
	 * 
	 * @param glUnused
	 * @param config
	 */
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config);
}
