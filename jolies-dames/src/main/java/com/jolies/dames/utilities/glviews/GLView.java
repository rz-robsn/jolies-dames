package com.jolies.dames.utilities.glviews;

public abstract class GLView {

	/** How many bytes per float. */
	protected static final int BYTES_PER_FLOAT = 4;
	
	/** Size of the position data in elements. */
	protected static final int POSITION_DATA_SIZE = 3;
	
	/** Size of the color data in elements. */
	protected static final int COLOR_DATA_SIZE = 4;
	
	/** How many elements per position vertex. */
	protected static final int STRIDE_POSITION_BYTES = POSITION_DATA_SIZE * BYTES_PER_FLOAT;	

	/** How many elements per color vertex. */
	protected static final int STRIDE_COLOR_BYTES = COLOR_DATA_SIZE * BYTES_PER_FLOAT;	
		
	public abstract void draw(float[] mMVPMatrix, float[] mModelViewMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle);
}
