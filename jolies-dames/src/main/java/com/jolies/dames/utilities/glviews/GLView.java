package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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
	
	/**
	 * Copy the elements contained in the data array into a newly created buffer FloatBuffer.
	 * and put the buffer position to 0. 
	 * 
	 * @param buffer an un-initialized FloatBuffer.
	 * @param data
	 */
	protected void initializeFloatBuffer(FloatBuffer buffer, float[] data)
	{
		buffer = ByteBuffer.allocateDirect(data.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		buffer.put(data).position(0);
	}
	
	public abstract void draw(float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle);
}
