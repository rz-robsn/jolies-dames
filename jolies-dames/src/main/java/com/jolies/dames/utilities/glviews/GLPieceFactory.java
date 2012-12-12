package com.jolies.dames.utilities.glviews;

import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.model.GamePiece;

/**
 *  Piece Factory class, used to create GLPieces from a GamePiece.
 *
 */
public class GLPieceFactory
{        
    private float normalPieceHeight;
    
    /**
     * Constructor, the height of a king Piece will be double the height of a normal piece.
     * 
     * @param normalPieceHeight the height of a Normal Piece.
     */
    public GLPieceFactory(float normalPieceHeight)
    {
        super();
        this.normalPieceHeight = normalPieceHeight;
    }

    /**
     * Returns a GLPiece whose height and color will be derived from the gamePiece parameter.
     * 
     * @param bottomCenterPosition
     * @param radius
     * @param gamePiece
     * @return
     */
    public GLPiece getGLPiece(float[] bottomCenterPosition, float radius, GamePiece gamePiece)
    {
        switch(gamePiece)
        {
            case EMPTY_SLOT:
                return null;

            case RED_KING_PIECE:
                float[] topCenterPositionRK = {
                        bottomCenterPosition[0],
                        bottomCenterPosition[1] + getKingPieceHeight(),
                        bottomCenterPosition[2]
                };
                //return new GLPiece(topCenterPositionRK, bottomCenterPosition, radius, PieceColor.RED);
                return null;

            case RED_PIECE:
                float[] topCenterPositionR = {
                        bottomCenterPosition[0],
                        bottomCenterPosition[1] + normalPieceHeight,
                        bottomCenterPosition[2]
                };
                //return new GLPiece(topCenterPositionR, bottomCenterPosition, radius, PieceColor.RED);
                return null;

            case WHITE_KING_PIECE:
                float[] topCenterPositionWK = {
                        bottomCenterPosition[0],
                        bottomCenterPosition[1] + getKingPieceHeight(),
                        bottomCenterPosition[2]
                };               
                //return new GLPiece(topCenterPositionWK, bottomCenterPosition, radius, PieceColor.WHITE);
                return null;

                
            case WHITE_PIECE:
                float[] topCenterPositionW = {
                        bottomCenterPosition[0],
                        bottomCenterPosition[1] + normalPieceHeight,
                        bottomCenterPosition[2]
                };
                //return new GLPiece(topCenterPositionW, bottomCenterPosition, radius, PieceColor.WHITE);
                return null;
                
            default:
                return null;           
        }
    }
    
    public float getKingPieceHeight()
    {
        return this.normalPieceHeight * 2;
    }
}
