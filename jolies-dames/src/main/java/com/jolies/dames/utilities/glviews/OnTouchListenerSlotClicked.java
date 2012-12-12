package com.jolies.dames.utilities.glviews;

import com.jolies.dames.utilities.ListenerBoard;
import com.jolies.dames.utilities.RendererGameView;
import com.jolies.dames.utilities.model.Slot;

import android.graphics.RectF;
import android.opengl.GLES10;
import android.opengl.GLU;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 *  This class interprets the touch controls on the screen and dispatches the appropriate
 *  events. 
 */
public class OnTouchListenerSlotClicked implements OnTouchListener
{
    private ListenerBoard listener;
    private RendererGameView renderer;
    
    public OnTouchListenerSlotClicked(RendererGameView renderer)
    {
        super();
        this.renderer = renderer;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {                
        return true;
    }
    
    public void setListener(ListenerBoard listener)
    {
        this.listener = listener;
    }
}
