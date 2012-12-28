package com.jolies.dames.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jolies.dames.utilities.glviews.GLBoard;
import com.jolies.dames.utilities.model.Slot;

import android.content.Context;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;

/**
 * This class implements our custom renderer. Note that the GL10 parameter passed in is unused for OpenGL ES 2.0
 * renderers -- the static class GLES20 is used instead.
 */
public class RendererGameView extends RajawaliRenderer 
{
	private GLBoard board;
	private ALight mLight;
    private ListenerOnSurfaceCreated listener = null;
    
    private float zoomFactor = 1;
	
	/**
	 * Initialize the model data.
	 */
	public RendererGameView(Context context) 
	{
		super(context);
		setFrameRate(30);
	}

	@Override
	protected void initScene() 
	{
		super.initScene();
		
		mLight = new DirectionalLight(1, -1, 0.5f); // set the direction
		mLight.setPower(1.5f);		
		
		Number3D topLeftPosition = new Number3D(-0.45f, 0.0f, -0.5f);
		this.board = new GLBoard(mContext, mTextureManager, topLeftPosition, mLight);
		this.addChild(board.object);
		
		this.mCamera.setPosition(0.0f, this.zoomFactor * 1.6f, this.zoomFactor * 1.6f);
		this.mCamera.setLookAt(0, 0, 0);
		
		if(listener != null)
		{
			listener.onSceneInitialized();
		}
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
	{
		super.onSurfaceCreated(glUnused, config);
	}	
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) 
	{
		super.onSurfaceChanged(glUnused, width, height);
	}	

	@Override public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
	
    /**
     * @return the GLBoard object that is rendered.
     */
    public final GLBoard getBoard()
    {
        return board;
    }	
    
    /**
     * Select the slot in the grid and color it with the
     * slot selection color.
     * 
     * @param slotToSelect the slot to select
     */
    public void selectSlot(Slot slot)
    {
        this.board.setSlotSelected(slot);
    }
    
    public void setListenerOnSurfaceCreated(ListenerOnSurfaceCreated listener)
    {
    	this.listener = listener;
    }
    
    public void increaseZoomFactorBy(float deltaValue)
    {
    	this.zoomFactor = Clamp(zoomFactor + deltaValue, -3, 3);
		this.mCamera.setPosition(0.0f, this.zoomFactor * 1.6f, this.zoomFactor * 1.6f);
    }
    
    private static float Clamp(float value, float minBound, float maxBound)
    {
    	float returnValue = value;
    	returnValue = Math.min(returnValue, maxBound);
    	returnValue = Math.max(returnValue, minBound);
    	return returnValue;    	
    }
}
