package com.jolies.dames.utilities.glviews;

import rajawali.math.Number3D;

import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.model.GamePiece;

/**
 *  Piece Factory class, used to create GLPieces from a GamePiece.
 *
 */
public class GLPieceFactory
{        
    private GLBoard board;
    
    /**
     * Constructor, the height of a king Piece will be double the height of a normal piece.
     * 
     * @param normalPieceHeight the height of a Normal Piece.
     */
    public GLPieceFactory(GLBoard board)
    {
        super();
        this.board = board;
    }

    /**
     * Returns a GLPiece corresponding to the passed position on the grid 
     * and the passed gamePiece.
     * 
     * @param x
     * @param y
     * @param gamePiece
     * @return a GLPiece corresponding to the passed position on the grid 
     * 		   and the passed gamePiece.
     */
    public GLPiece getGLPiece(int x, int y, GamePiece gamePiece)
    {
    	Number3D position = this.board.getPositionForSlot(x, y);
    	
        switch(gamePiece)
        {
            case EMPTY_SLOT:
                return null;

            case RED_KING_PIECE:
            	return new GLPiece(
            			this.board.context, 
            			this.board.textureManager,
            			position, 
            			PieceColor.RED, 
            			true);

            case RED_PIECE:
            	return new GLPiece(
            			this.board.context, 
            			this.board.textureManager,
            			position, 
            			PieceColor.RED, 
            			false);
            	
            case WHITE_KING_PIECE:
            	return new GLPiece(
            			this.board.context, 
            			this.board.textureManager,
            			position, 
            			PieceColor.WHITE, 
            			true);
                
            case WHITE_PIECE:
            	return new GLPiece(
            			this.board.context, 
            			this.board.textureManager,
            			position, 
            			PieceColor.WHITE, 
            			false);                
            default:
                return null;           
        }
    }
}
