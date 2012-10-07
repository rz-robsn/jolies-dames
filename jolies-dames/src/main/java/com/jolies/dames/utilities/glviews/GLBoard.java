package com.jolies.dames.utilities.glviews;

import android.graphics.RectF;

import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.Slot;

public class GLBoard extends GLView{
			
	private GLSlot[][] glSlots;
	private GLPiece glPiece;
	
	private Slot slotSelected = null;
	private Slot[] slotsToHighLight;
	
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
				glSlots[i][j] = new GLSlot(slotTopLeftData, slotLength, defaultColor(i, j));
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
     * @param slotSelected the slotSelected to set
     */
    public void setSlotSelected(Slot slotSelected)
    {
        if(this.slotSelected != null)
        {
            this.setGLSlotColor(this.slotSelected, defaultColor(this.slotSelected));
        }
        
        this.setGLSlotColor(slotSelected, SlotColor.BLUE);
        this.slotSelected = slotSelected;
    }

    /**
	 * Sets the color of the passed slot on the grid to the passed color.
	 * 
	 * @param slot the slot.
	 * @param color the color to set the slot to.
	 */
	public void setGLSlotColor(Slot slot, SlotColor color)
	{
	    this.setGLSlotColor(slot.x, slot.y, color);
	}
	
	/**
	 * Sets the slot at position (x, y) to the passed color
	 * 
	 * @param x
	 * @param y
	 * @param color the color to set the slot to.
	 */
	public void setGLSlotColor(int x, int y, SlotColor color)
	{
        float[] slotTopLeftData = {
                topLeft[0]+ x * this.getSlotLength(),
                topLeft[1],
                topLeft[2]+ (CheckerGame.GRID_SIZE - y-1) * this.getSlotLength()
        };
	    this.glSlots[x][y] = new GLSlot(slotTopLeftData, this.getSlotLength(), color);
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

	/**
	 * @param slot
	 * @return the default color of the passed slot
	 */
    private static SlotColor defaultColor(Slot slot)
    {
        return defaultColor(slot.x, slot.y);
    }
		
    /**
     * @param x
     * @param y
     * @return the default color of the slot at position (x, y)
     */
    private static SlotColor defaultColor(int x, int y)
    {
        return (x + y) % 2 == 0 ? SlotColor.BROWN : SlotColor.BEIGE;
    }

}
