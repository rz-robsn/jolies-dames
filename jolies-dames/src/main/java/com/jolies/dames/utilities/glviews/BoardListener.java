package com.jolies.dames.utilities.glviews;

public interface BoardListener
{
    /**
     * Executes when the user(s) has selected 
     * the slot at position (x, y) of the board.
     * 
     * @param x
     * @param y
     */
    public void onSlotSelected(int x, int y);

    /**
     * Executes when the user(s) requests a new game.
     */
    public void onNewGameRequested();
}
