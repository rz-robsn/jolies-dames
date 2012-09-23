package com.jolies.dames.utilities.glviews;

import android.opengl.Matrix;

import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;

public class GLBoard extends GLView{
			
	private GLSlot[][] glSlots;
	
	private GLPiece glPiece;
	
	/**
	 * Constructor
	 * 
	 * @param topLeft Top Left position of the board. The board will be parallel to the XZ-plane.
	 * @param length the Dimension of the square board.
	 */
	public GLBoard(float[] topLeft, float length) {
		
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
				glSlots[i][j] = new GLSlot(slotTopLeftData, slotLength, (i + j) % 2 == 0 ? SlotColor.BROWN : SlotColor.BEIGE);
			}	
		}
		
		float[] pieceTopCenterData = {
				topLeft[0]+ slotLength/2f,
				topLeft[1]+ 0.025f,
				topLeft[2]+ slotLength/ 2f
		};
		float[] pieceBottomCenterData = {
				topLeft[0]+ slotLength/2f,
				topLeft[1],
				topLeft[2]+ slotLength/2f
		};		
		
		glPiece = new GLPiece(pieceTopCenterData, pieceBottomCenterData, slotLength/2f, GLPiece.PieceColor.RED);
	}

	@Override
	public void draw(float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle)
	{			 
		for(GLSlot[] slots : this.glSlots)
		{
			for(GLSlot glSlot : slots)
			{
				glSlot.draw(mMVPMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
			}
		}
		glPiece.draw(mMVPMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
   }
}
