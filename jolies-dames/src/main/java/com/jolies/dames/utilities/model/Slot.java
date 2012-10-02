package com.jolies.dames.utilities.model;

public class Slot {

    public final int x;
    public final int y;
    
    public Slot(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
    }
    
    public boolean equals(Slot slot)
    {
        return this.x == slot.x && this.y == slot.y;
    }
    
}
