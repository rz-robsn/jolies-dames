package com.jolies.dames.utilities.glviews;

import rajawali.BaseObject3D;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;

import com.jolies.dames.utilities.ListenerBoard;
import com.jolies.dames.utilities.RendererGameView;
import com.jolies.dames.utilities.model.CheckerGame;
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
    
    private float previousDistance = -1;
    
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
    	if(event.getPointerCount() >= 2
    		&& event.getAction() == MotionEvent.ACTION_MOVE) // the gesture is a pinch/expand gesture
    	{    		
			float distance = (float)getDistance(
					event.getX(0), 
					event.getY(0),
					event.getX(1), 
					event.getY(1)
			);
        	
    		if(this.previousDistance != -1 && this.previousDistance != distance)
    		{
    			this.listener.onPinchOrExpand(distance - previousDistance); 
    		}
    		
			this.previousDistance = distance;
    	}
    	else if(event.getAction() == MotionEvent.ACTION_POINTER_UP 
    			|| event.getAction() == MotionEvent.ACTION_UP)
    	{
    		this.previousDistance = -1;
    	}
    	else if(event.getAction() == MotionEvent.ACTION_DOWN)
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
    
    /**
     * Returns the distance between the points (x1, y1) and (x2, y2)
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static double getDistance(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
