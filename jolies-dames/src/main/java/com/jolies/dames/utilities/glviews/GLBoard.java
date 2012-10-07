package com.jolies.dames.utilities.glviews;

import android.graphics.RectF;

import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;

public class GLBoard extends GLView{
			
	private GLSlot[][] glSlots;
	
	private GLPiece glPiece;
	
	/**
	 * Top Left position of the board. The board will be parallel to the XZ-plane.
	 */
	private float[] topLeft;
	
	/**
	 * The width and weight values of the square board.
	 */
	private float length;
	
	/**
	 * Constructor
	 * 
	 * @param topLeft Top Left position of the board. The board will be parallel to the XZ-plane.
	 * @param length the width and weight values of the square board.
	 */
	public GLBoard(float[] topLeft, float length) {
		
		/* Creating the GLSlots */
		glSlots = new GLSlot[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		
		this.topLeft = topLeft;
		this.length = length; 
		float slotLength = this.getSlotLength();
		
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
	public void draw(float[] mMVPMatrix, float[] mModelViewMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle)
	{			 
		for(GLSlot[] slots : this.glSlots)
		{
			for(GLSlot glSlot : slots)
			{
				glSlot.draw(mMVPMatrix, mModelViewMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
			}
		}
		glPiece.draw(mMVPMatrix, mModelViewMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
   }
	
	/**
     * @return the Top Left position of the board. The board will be parallel to the XZ-plane.
     */
    public float[] getTopLeft()
    {
        return topLeft;
    }

	/**
	 * @return the width and height of an individual square slot.
	 */
	public float getSlotLength()
	{
	    return this.length/CheckerGame.GRID_SIZE;
	}
	
	/**
	 * @return the RectF object containing the board's coordinate
	 */
	public RectF getRectF()
	{
	    return new RectF(topLeft[0], topLeft[2], topLeft[0] + length, topLeft[2] + length);
	}
	
    /**
     * @return returns the RectF object of the GSlot at the grid position (x, y)
     */
	public RectF getRectFForPiece(int x, int y)
	{
	    return this.glSlots[x][y].getRectF();
	}
	
}
