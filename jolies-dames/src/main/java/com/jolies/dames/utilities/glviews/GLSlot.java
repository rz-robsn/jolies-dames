package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class GLSlot {
	
	/** How many bytes per float. */
	private static final int mBytesPerFloat = 4;
	
	/** Size of the position data in elements. */
	private static final int mPositionDataSize = 3;
	
	/** Size of the color data in elements. */
	private static final int mColorDataSize = 4;
	
	/** How many elements per position vertex. */
	private static final int mStridePositionBytes = mPositionDataSize * mBytesPerFloat;	

	/** How many elements per color vertex. */
	private static final int mStrideColorBytes = mColorDataSize * mBytesPerFloat;	

	/** The Different colors a slot can have */
	public static enum Color {BLUE, GREEN, BROWN, BEIGE}
	private final static float[] blueColorData = {
		
		// R, G, B, A
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
	};
	
	private final static float[] greenColorData = { 
		
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
	};
	
	private final static float[] brownColorData = { 

		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f 				
	};
	
	private final static float[] beigeColorData = { 
		
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,	
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
	};
	
	/** Top left corner of the slot. */	
	private FloatBuffer position;
	
	/** Color of the Slot. */	
	private FloatBuffer color;

	/**
	 * Contructor for GLSLot. The Slot drawn will always be parrallel to the Y-plan.
	 * 
	 * @param topLeftPosition top-left coordinate of the slot to draw
	 * @param length dimension of the square slot to draw
	 * @param color color of the slot.
	 */
	public GLSlot(float[] topLeftPosition, float length, Color color) {
		super();

		float[] positionData = {
				
				// X, Y, Z
				topLeftPosition[0], topLeftPosition[1], topLeftPosition[2],
				topLeftPosition[0] + length, topLeftPosition[1], topLeftPosition[2],
				topLeftPosition[0], topLeftPosition[1], topLeftPosition[2] + length,
				topLeftPosition[0], topLeftPosition[1], topLeftPosition[2] + length,
				topLeftPosition[0] + length, topLeftPosition[1], topLeftPosition[2],
				topLeftPosition[0] + length, topLeftPosition[1], topLeftPosition[2] + length
		};

		float[] colorData = null;
		switch(color)
		{
			case BLUE:
				colorData = blueColorData;
				break;
				
			case BEIGE:
				colorData = beigeColorData;
				break;
				
			case BROWN:
				colorData = brownColorData;
				break;
				
			case GREEN:
				colorData = greenColorData;
				break;
		}		
				
		// Initialize the buffers.
		this.position = ByteBuffer.allocateDirect(positionData.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		this.position.put(positionData).position(0);
		
		this.color = ByteBuffer.allocateDirect(colorData.length * mBytesPerFloat)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		this.color.put(colorData).position(0);
	}
	
	public void draw(float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix, float[] mProjectionMatrix, int mPositionHandle, int mColorHandle, int mMVPMatrixHandle)
	{		
		// Pass in the position information
		position.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
        		mStridePositionBytes, position);                        
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        // Pass in the color information
        color.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
        		mStrideColorBytes, color);                
        GLES20.glEnableVertexAttribArray(mColorHandle);
        
		// This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
     }
	
	
}
