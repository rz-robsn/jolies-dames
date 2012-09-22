package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.FloatMath;

public class GLPiece extends GLView{

	/**
	 *  The Number of vertices to be used to draw a disk.
	 */
	private static final int NUM_OF_DISK_VERTICES = 720;
	
	private FloatBuffer position;
	private FloatBuffer color;
	
	/**
	 * Constructor, defines a cylinder parallel to the XZ-plane to be drawn
	 * 
	 * @param topCenterPosition
	 * @param bottomCenterPosition
	 * @param radius
	 */
	public GLPiece(float[] topCenterPosition, float[] bottomCenterPosition, float radius)
	{
		// The vertex data array of the top disk.
		float[] topDiskVerticesData = new float[(NUM_OF_DISK_VERTICES + 1)*POSITION_DATA_SIZE];
		float[] topDiskVerticesDataColor = new float[(NUM_OF_DISK_VERTICES + 1)*COLOR_DATA_SIZE];
		
		// The vertex data array of the bottom disk.
		float[] bottomDiskVerticesData = new float[(NUM_OF_DISK_VERTICES + 1)*POSITION_DATA_SIZE];

		// The vertex data array of the cylinder sides.
		float[] cylinderSideVerticesData = new float[6*NUM_OF_DISK_VERTICES];
		
		topDiskVerticesData[0] = topCenterPosition[0];
		topDiskVerticesData[1] = topCenterPosition[1];
		topDiskVerticesData[2] = topCenterPosition[2];
		for (int i = 1; i <= NUM_OF_DISK_VERTICES; i++)
		{
			float stepSize =  (float) ((2*Math.PI)/NUM_OF_DISK_VERTICES);
			topDiskVerticesData[3*i] = topCenterPosition[0] + radius * FloatMath.cos(i * stepSize);
			topDiskVerticesData[3*i +1] = topCenterPosition[1];
			topDiskVerticesData[3*i +2] = topCenterPosition[2] + radius * FloatMath.sin(i * stepSize);
		}
		
		// Creating color arrays :
		for (int i = 0; i < (NUM_OF_DISK_VERTICES + 1)*COLOR_DATA_SIZE ; i += COLOR_DATA_SIZE)
		{
			topDiskVerticesDataColor[i] = 0.133333f;
			topDiskVerticesDataColor[i+1] = 0.72157f;
			topDiskVerticesDataColor[i+2] = 0.027450f;
			topDiskVerticesDataColor[i+3] = 1.0f;	
		}
		
		// Initialize the buffers.
		this.position = ByteBuffer.allocateDirect(topDiskVerticesData.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		this.position.put(topDiskVerticesData).position(0);
		
		this.color = ByteBuffer.allocateDirect(topDiskVerticesDataColor.length * BYTES_PER_FLOAT)
		        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		this.color.put(topDiskVerticesDataColor).position(0);
	}
	
	@Override
	public void draw(float[] mMVPMatrix, float[] mModelMatrix,
			float[] mViewMatrix, float[] mProjectionMatrix,
			int mPositionHandle, int mColorHandle, int mMVPMatrixHandle) 
	{
		// Pass in the position information
		position.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false,
        		STRIDE_POSITION_BYTES, position);                        
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        // Pass in the color information
        color.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false,
        		STRIDE_COLOR_BYTES, color);                
        GLES20.glEnableVertexAttribArray(mColorHandle);
        
		// This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, NUM_OF_DISK_VERTICES + 1);
	}

}
