package com.jolies.dames.utilities.glviews;

import java.nio.FloatBuffer;

public class GLPiece extends GLView{

	/**
	 *  The Number of vertices to be used to draw a disk.
	 */
	private static final int NUM_OF_DISK_VERTICES = 180;
	
	private FloatBuffer position;
	private FloatBuffer color;
	
	/**
	 * Constructor, defines a cylinder to be drawn
	 * 
	 * @param TopCenterPosition
	 * @param BottomCenterPosition
	 * @param radius
	 */
	public GLPiece(float[] TopCenterPosition, float[] BottomCenterPosition, float radius)
	{
		// The vertex data array of the top disk.
		float[] topDiskVerticesData = new float[NUM_OF_DISK_VERTICES + 1];
		
		// The vertex data array of the bottom disk.
		float[] bottomDiskVerticesData = new float[NUM_OF_DISK_VERTICES + 1];

		// The vertex data array of the cylinder sides.
		float[] cylinderSideVerticesData = new float[6*NUM_OF_DISK_VERTICES];
	}
	
	@Override
	public void draw(float[] mMVPMatrix, float[] mModelMatrix,
			float[] mViewMatrix, float[] mProjectionMatrix,
			int mPositionHandle, int mColorHandle, int mMVPMatrixHandle) {
		// TODO Auto-generated method stub
		
	}

}
