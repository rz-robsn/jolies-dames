package com.jolies.dames.utilities.glviews;

import java.util.ArrayList;

import android.graphics.RectF;

import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.jolies.dames.utilities.model.Slot;

public class GLBoard {
	
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
	}
	
	/**
	 * Destroys the piece at position (x,y) on the grid.
	 * 
	 * @param x
	 * @param y
	 */
	public void destroyPieceAt(int x, int y)
	{
	}
	
	/**
     * @return the Top Left position of the board. The board will be parallel to the XZ-plane.
     */
    public float[] getTopLeft()
    {
    	return null;
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
