package com.jolies.dames.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class GLBoard {
	
	/** How many bytes per float. */
	private static final int mBytesPerFloat = 4;
	
	/** Size of the position data in elements. */
	private static final int mPositionDataSize = 3;
	
	/** Size of the color data in elements. */
	private static final int mColorDataSize = 4;
	
	/** How many elements per position vertex. */
	private static final int mStridePositionBytes = mPositionDataSize * mBytesPerFloat;	

	/** How many elements per color vertex. */
	private static final int mStrideColorBytes = mColorDataSize * mBytesPerFloat;	

	/** Store our model position in a float buffer. */
	private final FloatBuffer boardVerticesPosition;

	/** Store our model color in a float buffer. */
	private final FloatBuffer boardVerticesColor;

	/** Top left position of the board */
	private final float[] topLeft;

	/** The Dimension of the board */
	private final float length;
	
	/**
	 * Constructor
	 * 
	 * @param topLeft Top Left position of the board. The board will be parallel to the Y-plane.
	 * @param length the Dimension of the square board.
	 */
	public GLBoard(float[] topLeft, float length) {
		
		this.topLeft = topLeft;
		this.length = length;
		
		// Define points for square.
		// This square is red.
		final float[] boardVerticesPositionData = {
				
				// X, Y, Z, 
	            topLeft[0], topLeft[1], topLeft[2] + length,
	            topLeft[0] + length, topLeft[1], topLeft[2] + length,
	            topLeft[0], topLeft[1], topLeft[2],
	            topLeft[0], topLeft[1], topLeft[2],
	            topLeft[0] + length, topLeft[1], topLeft[2] + length,
	            topLeft[0] + length, topLeft[1], topLeft[2],
		};
		
		final float[] boardVerticesColorData = {

				// R, G, B, A
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f
		};
		
		// Initialize the buffers.
		boardVerticesPosition = ByteBuffer.allocateDirect(boardVerticesPositionData.length * mBytesPerFloat)
        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		boardVerticesPosition.put(boardVerticesPositionData).position(0);
		
		boardVerticesColor = ByteBuffer.allocateDirect(boardVerticesColorData.length * mBytesPerFloat)
        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		boardVerticesColor.put(boardVerticesColorData).position(0);
	}

	public void draw(float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle)
	{		
		// Pass in the position information
		boardVerticesPosition.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
        		mStridePositionBytes, boardVerticesPosition);                        
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        // Pass in the color information
        boardVerticesColor.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
        		mStrideColorBytes, boardVerticesColor);                
        GLES20.glEnableVertexAttribArray(mColorHandle);
        
		// This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        }
}
