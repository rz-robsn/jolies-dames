package com.jolies.dames.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jolies.dames.R;
import com.jolies.dames.utilities.glviews.GLBoard;
import com.jolies.dames.utilities.glviews.GLPiece;
import com.jolies.dames.utilities.glviews.GLPiece.PieceColor;
import com.jolies.dames.utilities.glviews.GLSlot;
import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;
import com.jolies.dames.utilities.model.Slot;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureInfo;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

/**
 * This class implements our custom renderer. Note that the GL10 parameter passed in is unused for OpenGL ES 2.0
 * renderers -- the static class GLES20 is used instead.
 */
public class RendererGameView extends RajawaliRenderer 
{
	private GLBoard board;
	private DirectionalLight mLight;
    
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
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
	{
		super.onSurfaceCreated(glUnused, config);
		
		mLight = new DirectionalLight(1, -1, 0.5f); // set the direction
		mLight.setPower(1.5f);		
		
//		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.piece_obj);
//		objParser.parse();
//		BaseObject3D mObject = objParser.getParsedObject();
//		mObject.addLight(mLight);
//		addChild(mObject);
				
		float[] topLeftPosition = {-0.45f, 0.0f, -0.5f};
		GLBoard board = new GLBoard(mContext, mTextureManager, topLeftPosition);
		board.object.addLight(mLight);
		this.addChild(board.object);
		
		GLPiece piece = new GLPiece(mContext, mTextureManager, new Number3D(0,0.3f,0), PieceColor.WHITE, true);
		piece.object.addLight(mLight);
		this.addChild(piece.object);
		
		this.mCamera.setPosition(0.0f, 1.5f, 1.5f);
		this.mCamera.setLookAt(0, 0, 0);
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
}
