package com.jolies.dames.utilities.glviews;

import rajawali.BaseObject3D;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;

import com.jolies.dames.utilities.ListenerBoard;
import com.jolies.dames.utilities.RendererGameView;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.Slot;

import android.graphics.RectF;
import android.opengl.GLES10;
import android.opengl.GLU;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 *  This class interprets the touch controls on the screen and dispatches the appropriate
 *  events. 
 */
public class OnTouchListenerSlotClicked implements OnTouchListener, OnObjectPickedListener
{
    private ListenerBoard listener = null;
    private RendererGameView renderer;
    private ObjectColorPicker mPicker;
    
    public OnTouchListenerSlotClicked(RendererGameView renderer)
    {
        super();
        this.renderer = renderer;
        
    	mPicker = new ObjectColorPicker(renderer);
    	mPicker.setOnObjectPickedListener(this);
    	
    	for (int i = 0 ; i < this.renderer.getBoard().object.getNumChildren(); i++)
    	{
    		mPicker.registerObject(this.renderer.getBoard().object.getChildAt(i));	
    	}   	
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			this.mPicker.getObjectAt(event.getX(), event.getY());
		}
    	
        return true;
    }
    
	@Override
	public void onObjectPicked(BaseObject3D object)
	{
    	for (int i = 0 ; i < CheckerGame.GRID_SIZE; i++)
    	{
        	for (int j = 0 ; j < CheckerGame.GRID_SIZE; j++)
        	{
        		if(this.renderer.getBoard().getSlotObj3DAt(i, j) == object
        			&& this.listener != null)
        		{
        			this.listener.onSlotSelected(i, j);
        			break;
        		}
        	}	    		
    	}	
	}
    
    public void setListener(ListenerBoard listener)
    {
        this.listener = listener;
    }
}
