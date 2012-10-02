package com.jolies.dames.utilities.model;

public interface GameListener
{
    
    /**
     * Executes when the game starts.
     */
    public void onNewGame();
    
    /**
     * Executes when a player wins.
     * 
     * @param player
     *            The winning player.
     */
    public void onPlayerWin(Player player);
    
    /**
     * Executes the game ends in a draw.
     */
    public void onDraw();
    
    /**
     * Executes when the piece at position (xStart, yStart) has moved to
     * position (xEnd, yEnd).
     * 
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @param gamePiece
     *            The type of piece that moved. Never equals to EMPTY_SLOT.
     */
    public void onPieceMoved(int xStart, int yStart, int xEnd, int yEnd,
            GamePiece gamePiece);
    
    /**
     * Executes when the piece at position (x,y) has been eaten
     * 
     * @param x
     * @param y
     * @param gamePiece
     *            The type of piece that moved. Never equals to EMPTY_SLOT.
     */
    public void onPieceEaten(int x, int y, GamePiece gamePiece);
    
    /**
     * Executes the piece at position (x,y) becomes king. (x,y) are the position
     * of piece after it moved.
     * 
     * @param x
     * @param y
     * @param gamePiece
     *            The type of piece that became king. Is either equal to
     *            RED_KING_PIECE or WHITE_KING_PIECE.
     */
    public void onPieceBecameKing(int x, int y, GamePiece gamePiece);
    
    /**
     * Executes when the piece at position (xStart, yStart) has illegaly tried
     * to move to position (xEnd, yEnd).
     * 
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @param gamePiece
     *            The type of piece that illegally tried to move. Never equals
     *            to EMPTY_SLOT.
     */
    public void onIllegalMove(int xStart, int yStart, int xEnd, int yEnd,
            GamePiece gamePiece);
    
    /**
     * Executes when a piece that just moved to position (x, y) can still jump
     * to another slot.
     * 
     * @param x
     * @param y
     * @param gamePiece
     *            The type of piece that just moved. Never equals to EMPTY_SLOT.
     */
    public void onPieceCanStillJump(int x, int y, GamePiece gamePiece);
    
}
