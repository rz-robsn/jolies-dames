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
    
    /**
     * Executes when the user(s) has made a pinch or an Expand gesture
     * 
     * @param deltaValue the amount of pixels by which the distance between the two pointers 
     * 	has been reduced or increased. 
     */
    public void onPinchOrExpand(float deltaValue);
    
}
