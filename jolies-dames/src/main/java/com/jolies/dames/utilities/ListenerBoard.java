package com.jolies.dames.utilities;

public interface ListenerBoard
{
    /**
     * Executes when the user(s) has selected 
     * the slot at position (x, y) of the board.
     * 
     * @param x
     * @param y
     */
    public void onSlotSelected(int x, int y);
}
