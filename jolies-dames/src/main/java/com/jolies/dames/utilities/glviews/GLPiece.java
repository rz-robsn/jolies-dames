package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.FloatMath;

public class GLPiece extends GLView {

	/** The Different colors a Piece can have */
	public static enum PieceColor { WHITE, RED }
	
	private final static float[] WHITE_COLOR_DATA = { 1.0f, 1.0f, 1.0f, 1.0f };
	private final static float[] RED_COLOR_DATA = { 0.8f, 0.0f, 0.0f, 1.0f };
	
	/** The Number of vertices to be used to draw a disk */
	private static final int NUM_OF_DISK_VERTICES = 720;

	private FloatBuffer topDiskPosition;
	private FloatBuffer bottomDiskPosition;
	private FloatBuffer color;

	/**
	 * Constructor, defines a cylinder parallel to the XZ-plane to be drawn
	 * 
	 * @param topCenterPosition
	 * @param bottomCenterPosition
	 * @param radius
	 */
	public GLPiece(float[] topCenterPosition, float[] bottomCenterPosition,
			float radius, PieceColor color) 
	{
		// The vertex data array of the top disk.
		float[] topDiskVerticesData = new float[(NUM_OF_DISK_VERTICES + 1) * POSITION_DATA_SIZE];

		// The vertex data array of the bottom disk.
		float[] bottomDiskVerticesData = new float[(NUM_OF_DISK_VERTICES + 1) * POSITION_DATA_SIZE];

		// The vertex data array of the cylinder sides.
		float[] cylinderSideVerticesData = new float[6 * NUM_OF_DISK_VERTICES];

		// The vertexColorDatas
		float[] diskVerticesDataColor = new float[(NUM_OF_DISK_VERTICES + 1) * COLOR_DATA_SIZE];

		
		/* Vertex Array initialization */
		topDiskVerticesData[0] = topCenterPosition[0];
		topDiskVerticesData[1] = topCenterPosition[1];
		topDiskVerticesData[2] = topCenterPosition[2];
		for (int i = 1; i <= NUM_OF_DISK_VERTICES; i++) {
			float stepSize = (float) ((2 * Math.PI) / NUM_OF_DISK_VERTICES);
			topDiskVerticesData[3 * i] = topCenterPosition[0] + radius * FloatMath.cos(i * stepSize);
			topDiskVerticesData[3 * i + 1] = topCenterPosition[1];
			topDiskVerticesData[3 * i + 2] = topCenterPosition[2] + radius * FloatMath.sin(i * stepSize);
		}

		bottomDiskVerticesData[0] = bottomCenterPosition[0];
		bottomDiskVerticesData[1] = bottomCenterPosition[1];
		bottomDiskVerticesData[2] = bottomCenterPosition[2];
		for (int i = 1; i <= NUM_OF_DISK_VERTICES; i++) {
			float stepSize = (float) ((2 * Math.PI) / NUM_OF_DISK_VERTICES);
			bottomDiskVerticesData[3 * i] = bottomCenterPosition[0] + radius * FloatMath.cos(i * stepSize);
			bottomDiskVerticesData[3 * i + 1] = bottomCenterPosition[1];
			bottomDiskVerticesData[3 * i + 2] = bottomCenterPosition[2] + radius * FloatMath.sin(i * stepSize);
		}

		
		/* Color Array initialization */
		float[] selectedColorData = null;
		switch(color)
		{
			case RED:
				selectedColorData = GLPiece.RED_COLOR_DATA;
				break;
			case WHITE:
				selectedColorData = GLPiece.WHITE_COLOR_DATA;
				break;
		}
		
		for (int i = 0; i < (NUM_OF_DISK_VERTICES + 1) * COLOR_DATA_SIZE; i += COLOR_DATA_SIZE) {
			diskVerticesDataColor[i] = selectedColorData[0];
			diskVerticesDataColor[i + 1] = selectedColorData[1];
			diskVerticesDataColor[i + 2] = selectedColorData[2];
			diskVerticesDataColor[i + 3] = selectedColorData[3];			
		}

		
		/* Buffer initialization */
		this.topDiskPosition = ByteBuffer
				.allocateDirect(topDiskVerticesData.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.topDiskPosition.put(topDiskVerticesData).position(0);
		
		this.bottomDiskPosition = ByteBuffer
				.allocateDirect(bottomDiskVerticesData.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.bottomDiskPosition.put(bottomDiskVerticesData).position(0);

		this.color = ByteBuffer
				.allocateDirect(
						diskVerticesDataColor.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.color.put(diskVerticesDataColor).position(0);
	}

	@Override
	public void draw(float[] mMVPMatrix, float[] mModelMatrix,
			float[] mViewMatrix, float[] mProjectionMatrix,
			int mPositionHandle, int mColorHandle, int mMVPMatrixHandle) 
	{
		this.drawDisk(topDiskPosition, mMVPMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
		this.drawDisk(bottomDiskPosition, mMVPMatrix, mModelMatrix, mViewMatrix, mProjectionMatrix, mPositionHandle, mColorHandle, mMVPMatrixHandle);
	}

	private void drawDisk(FloatBuffer diskBuffer, 
			float[] mMVPMatrix, float[] mModelMatrix, float[] mViewMatrix,
			float[] mProjectionMatrix, int mPositionHandle, int mColorHandle,
			int mMVPMatrixHandle) 
	{
		// Pass in the position information
		diskBuffer.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, POSITION_DATA_SIZE,
				GLES20.GL_FLOAT, false, STRIDE_POSITION_BYTES, diskBuffer);
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Pass in the color information
		color.position(0);
		GLES20.glVertexAttribPointer(mColorHandle, COLOR_DATA_SIZE,
				GLES20.GL_FLOAT, false, STRIDE_COLOR_BYTES, color);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		// This multiplies the view matrix by the model matrix, and stores the
		// result in the MVP matrix
		// (which currently contains model * view).
		Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

		// This multiplies the modelview matrix by the projection matrix, and
		// stores the result in the MVP matrix
		// (which now contains model * view * projection).
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, NUM_OF_DISK_VERTICES + 1);
	}

}
