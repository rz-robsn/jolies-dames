package com.jolies.dames.utilities.glviews;

import java.util.ArrayList;

import android.graphics.RectF;

import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.jolies.dames.utilities.model.Slot;

public class GLBoard extends GLView{
	
    /** Height of a normal piece */
    private static final float NORMAL_PIECE_HEIGHT = 0.025f;
    
	private GLSlot[][] glSlots;
	private GLPiece[][] glPieces;
	
	private Slot slotSelected = null;
	private Slot[] slotsToHighLight = {};
	
	/**
	 * Top Left position of the board. The board will be parallel to the XZ-plane.
	 */
	private float[] topLeft;
	
	/**
	 * The width and height values of the square board.
	 */
	private float length;
	
	/**
	 * The Board's piece factory
	 */
	private GLPieceFactory piecesFactory;
	
	/**
	 * Constructor
	 * 
	 * @param topLeft Top Left position of the board. The board will be parallel to the XZ-plane.
	 * @param length the width and weight values of the square board.
	 */
	public GLBoard(float[] topLeft, float length) {
		
		/* Creating the GLSlots */
		glSlots = new GLSlot[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		glPieces = new GLPiece[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		piecesFactory = new GLPieceFactory(NORMAL_PIECE_HEIGHT);
		
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
        for(GLPiece[] pieces : this.glPieces)
        {
            for(GLPiece piece : pieces)
            {
                if (piece != null)
                {
                    piece.draw(mMVPMatrix, mModelViewMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
                }
            }
        }
	}
	
	/**
     * @param slotSelected the slotSelected to set
     */
    public void setSlotSelected(Slot slotSelected)
    {
        // Un-selecting previous slot
        if(this.slotSelected != null)
        {
            boolean slotSelectedShouldBeHighlighted = false;
            for(Slot slot : this.slotsToHighLight)
            {
                if (slot.equals(this.slotSelected))
                {
                    slotSelectedShouldBeHighlighted = true;
                    break;
                }
            }
            this.setGLSlotColor(this.slotSelected, slotSelectedShouldBeHighlighted ? SlotColor.GREEN 
                                                                                   : defaultColor(this.slotSelected));
        }
        
        this.setGLSlotColor(slotSelected, SlotColor.BLUE);
        this.slotSelected = slotSelected;
    }

    /**
     * Sets the slots to Highlight to the passed slotsToHighLight on the grid and
     * sets the previous highlighted slots' colors to their respective default ones.
     * 
     * @param slotsToHighLight the slotsToHighLight to set
     */
    public void setSlotsToHighLight(Slot[] slotsToHighLight)
    {
        // Un-highlighting previous highlighted slots
        if (this.slotsToHighLight.length > 0)
        {
            for(Slot slot : this.slotsToHighLight)
            {
                this.setGLSlotColor(slot, slot.equals(this.slotSelected) ? SlotColor.BLUE 
                                                                         : defaultColor(slot));
            }
        }
        
        this.slotsToHighLight = slotsToHighLight;
        for(Slot slot : this.slotsToHighLight)
        {
            this.setGLSlotColor(slot, SlotColor.GREEN);
        }
    }
    
    /**
     * Reset all highlighted slots to their default colors.
     */
    public void unhighlightAllSlots()
    {
        Slot[] emptySlots = new Slot[]{};
        this.setSlotsToHighLight(emptySlots);
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
                topLeft[2]+ (CheckerGame.GRID_SIZE - (y+1)) * this.getSlotLength()
        };
	    this.glSlots[x][y] = new GLSlot(slotTopLeftData, this.getSlotLength(), color);
	}
	
	/**
	 * Creates a new Piece at Position (x,y) on the board.
	 * Deletes any other piece that was previously at that position.
	 * 
	 * @param x
	 * @param y
	 * @param GamePiece The piece's associated gamePiece
	 */
	public void createPieceAtPosition(int x, int y, GamePiece gamePiece)
	{
        float slotLength = getSlotLength();

        float[] pieceBottomCenterData = {
                topLeft[0]+ x*slotLength + slotLength/2f,
                topLeft[1],
                topLeft[2]+ (CheckerGame.GRID_SIZE-(y+1)) * slotLength + slotLength/2f
        };      
                
	    this.glPieces[x][y] = this.piecesFactory.getGLPiece(pieceBottomCenterData, slotLength/2f, gamePiece);
	}
	
	/**
	 * Moves the piece from (xStart, yStart) to (xEnd, yEnd).
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	public void movePiece(int xStart, int yStart, int xEnd, int yEnd)
	{
	    this.glPieces[xEnd][yEnd] = this.glPieces[xStart][yStart];
	    this.glPieces[xStart][yStart] = null;
	    this.glPieces[xEnd][yEnd].setPosition(
	            topLeft[0]+ xEnd*this.getSlotLength() + this.getSlotLength()/2f,
	            topLeft[2]+ (CheckerGame.GRID_SIZE - (yEnd+1)) * this.getSlotLength() + this.getSlotLength()/2f);
	}
	
	/**
	 * Destroys the piece at position (x,y) on the grid.
	 * 
	 * @param x
	 * @param y
	 */
	public void destroyPieceAt(int x, int y)
	{
	    this.glPieces[x][y] = null;
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
