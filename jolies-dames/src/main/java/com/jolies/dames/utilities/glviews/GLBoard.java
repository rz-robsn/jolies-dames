package com.jolies.dames.utilities.glviews;

import java.util.ArrayList;

import rajawali.BaseObject3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;

import android.content.Context;
import android.graphics.RectF;

import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.jolies.dames.utilities.model.Slot;

public class GLBoard {
	
    /** The Width and Height values of the board*/
    public static final float DIMENSION = GLSlot.DIMENSION_XZ * CheckerGame.GRID_SIZE;
    
	private GLSlot[][] glSlots;
	private GLPiece[][] glPieces;
	
	private Slot slotSelected = null;
	private Slot[] slotsToHighLight = {};
	
	private ALight mLight;
	
	public Context context;
	public TextureManager textureManager;
	
	public BaseObject3D object;
	
	/**
	 * Top Left position of the board. The board will be parallel to the XZ-plane.
	 */
	private Number3D topLeft;
	
	/**
	 * The Board's piece factory
	 */
	private GLPieceFactory piecesFactory;
	
	/**
	 * Constructor
	 * 
	 * @param context
	 * @param textureManager
	 * @param topLeft Position of the top-leftmost slot of the board. The board will be parallel to the XZ-plane.
	 * @param mLight The light
	 */
	public GLBoard(Context context, TextureManager textureManager,
			Number3D topLeft, ALight mLight) {
		super();
		this.context = context;
		this.textureManager = textureManager;
		this.topLeft = topLeft;
		this.mLight = mLight;
		
		this.object = new BaseObject3D();
		
		/* Creating the GLSlots */
		glSlots = new GLSlot[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		for(int i = 0; i < CheckerGame.GRID_SIZE; i++)
		{
			for(int j = 0; j < CheckerGame.GRID_SIZE; j++)
			{				
				setGLSlotAtPosition(i, j, new GLSlot(
						context,
						textureManager, 
						Number3D.add(topLeft, new Number3D(i*GLSlot.DIMENSION_XZ, 0, (CheckerGame.GRID_SIZE - j-1) * GLSlot.DIMENSION_XZ)),
						defaultColor(i,j)));
				this.object.addChild(this.getSlotObj3DAt(i, j));
			}			
		}
	
		/* Creating the GLPieces */
		glPieces = new GLPiece[CheckerGame.GRID_SIZE][CheckerGame.GRID_SIZE];
		piecesFactory = new GLPieceFactory(this);
		
		this.object.addLight(mLight);
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
		getGLSlotAtPosition(x, y).changeSlotColorTo(color);
	}

	private GLSlot getGLSlotAtPosition(int x, int y) 
	{
		return this.glSlots[x][y];
	}
	
	private void setGLSlotAtPosition(int x, int y, GLSlot slot) 
	{
		this.glSlots[x][y] = slot;
	}
	
	public BaseObject3D getSlotObj3DAt(int x, int y)
	{
		return getGLSlotAtPosition(x, y).object;
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
		// Deleting previous piece
		GLPiece oldPiece = this.glPieces[x][y]; 		
		if (oldPiece != null)
		{
			this.object.removeChild(oldPiece.object);
		}
		
		GLPiece newPiece = this.piecesFactory.getGLPiece(x, y, gamePiece);
		if(newPiece != null)
		{
			newPiece.object.addLight(mLight);
			this.object.addChild(newPiece.object);	
		}
		this.glPieces[x][y] = newPiece;
		
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
		// Keeping the array of pieces consistent
		GLPiece piece = this.glPieces[xStart][yStart];
		this.glPieces[xStart][yStart] = null;
		this.glPieces[xEnd][yEnd] = piece;
		
		// performing the animation
		piece.object.setPosition(this.getPositionForSlot(xEnd, yEnd));
	}
	
	public Number3D getPositionForSlot(int x, int y)
	{
    	Number3D position = this.getTopLeft();
    	position = Number3D.add(position, new Number3D(x * GLSlot.DIMENSION_XZ, 0, (CheckerGame.GRID_SIZE - 1 - y) * GLSlot.DIMENSION_XZ));
    	position = Number3D.add(position, new Number3D(
    			0, 
    			GLSlot.DIMENSION_Y/2 + GLPiece.DIMENSION_Y/2, 
    			0));
    	
    	return position;
	}
	
	/**
	 * Destroys the piece at position (x,y) on the grid.
	 * 
	 * @param x
	 * @param y
	 */
	public void destroyPieceAt(int x, int y)
	{
		this.object.removeChild(this.glPieces[x][y].object);
		this.glPieces[x][y] = null;
	}
	
	/**
     * @return the Top Left position of the board. The board will be parallel to the XZ-plane.
     */
    public Number3D getTopLeft()
    {
    	return this.topLeft;
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
