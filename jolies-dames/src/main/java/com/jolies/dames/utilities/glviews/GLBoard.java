package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.jolies.dames.utilities.glviews.GLSlot.Color;
import com.jolies.dames.utilities.model.CheckerGame;

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

	/** Top left position of the board */
	private final float[] topLeft;

	/** The Dimension of the board */
	private final float length;
	
	private GLSlot[][] glSlots;
	
	/**
	 * Constructor
	 * 
	 * @param topLeft Top Left position of the board. The board will be parallel to the Y-plane.
	 * @param length the Dimension of the square board.
	 */
	public GLBoard(float[] topLeft, float length) {
		
		this.topLeft = topLeft;
		this.length = length;
		
		/* Creating the GLSlots */
		glSlots = new GLSlot[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		
		// the dimension of an individual slot;
		float slotLength = length/CheckerGame.GRID_SIZE;
		
		for (int i = 0; i < CheckerGame.GRID_SIZE; i++)
		{
			for (int j = 0; j < CheckerGame.GRID_SIZE; j++)
			{
				float[] slotTopLeftData = {
						topLeft[0]+ i * slotLength,
						topLeft[1],
						topLeft[2]+ (CheckerGame.GRID_SIZE - j-1) * slotLength
				};
				glSlots[i][j] = new GLSlot(slotTopLeftData, slotLength, (i + j) % 2 == 0 ? Color.BROWN : Color.BEIGE);
			}	
		}
	}

	public void draw(float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle)
	{		
		for(GLSlot[] slots : this.glSlots)
		{
			for(GLSlot glSlot : slots)
			{
				glSlot.draw(mMVPMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
			}
		}
    }
}
