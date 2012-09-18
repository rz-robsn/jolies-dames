package com.jolies.dames.utilities;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

/**
 * This class implements our custom renderer. Note that the GL10 parameter passed in is unused for OpenGL ES 2.0
 * renderers -- the static class GLES20 is used instead.
 */
public class RendererGameView implements GLSurfaceView.Renderer 
{
	/**
	 * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
	 * of being located at the center of the universe) to world space.
	 */
	private float[] mModelMatrix = new float[16];

	/**
	 * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
	 * it positions things relative to our eye.
	 */
	private float[] mViewMatrix = new float[16];

	/** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
	private float[] mProjectionMatrix = new float[16];
	
	/** Allocate storage for the final combined matrix. This will be passed into the shader program. */
	private float[] mMVPMatrix = new float[16];
	
	/** Store our model position in a float buffer. */
	private final FloatBuffer boardVerticesPosition;

	/** Store our model color in a float buffer. */
	private final FloatBuffer boardVerticesColor;
	
	/** This will be used to pass in the transformation matrix. */
	private int mMVPMatrixHandle;
	
	/** This will be used to pass in model position information. */
	private int mPositionHandle;
	
	/** This will be used to pass in model color information. */
	private int mColorHandle;

	/** How many bytes per float. */
	private final int mBytesPerFloat = 4;
	
	/** Size of the position data in elements. */
	private final int mPositionDataSize = 3;
	
	/** Size of the color data in elements. */
	private final int mColorDataSize = 4;
	
	/** How many elements per position vertex. */
	private final int mStridePositionBytes = mPositionDataSize * mBytesPerFloat;	

	/** How many elements per color vertex. */
	private final int mStrideColorBytes = mColorDataSize * mBytesPerFloat;	
			
				
	/**
	 * Initialize the model data.
	 */
	public RendererGameView()
	{	
		// Define points for square.
		// This square is red.
		final float[] boardVerticesPositionData = {
				
				// X, Y, Z, 
	            0.5f, -0.5f, 0.0f, 
	            0.5f, 0.5f, 0.0f,
	            -0.5f, -0.5f, 0.0f, 
	            -0.5f, -0.5f, 0.0f, 
	            0.5f, 0.5f, 0.0f,
	            -0.5f, 0.5f, 0.0f
		};
		
		final float[] boardVerticesColorData = {

				// R, G, B, A
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f,
	            1.0f, 0.0f, 0.0f, 1.0f
		};
		
		// Initialize the buffers.
		boardVerticesPosition = ByteBuffer.allocateDirect(boardVerticesPositionData.length * mBytesPerFloat)
        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		boardVerticesPosition.put(boardVerticesPositionData).position(0);
		
		// Initialize the buffers.
		boardVerticesColor = ByteBuffer.allocateDirect(boardVerticesColorData.length * mBytesPerFloat)
        .order(ByteOrder.nativeOrder()).asFloatBuffer();		
		boardVerticesColor.put(boardVerticesColorData).position(0);
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
	{
		// Set the background clear color to gray.
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
	
		// Position the eye behind the origin.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = 1.5f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -5.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

		final String vertexShader =
			"uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
			
		  + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
		  + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.			  
		  
		  + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
		  
		  + "void main()                    \n"		// The entry point for our vertex shader.
		  + "{                              \n"
		  + "   v_Color = a_Color;          \n"		// Pass the color through to the fragment shader. 
		  											// It will be interpolated across the triangle.
		  + "   gl_Position = u_MVPMatrix   \n" 	// gl_Position is a special variable used to store the final position.
		  + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in 			                                            			 
		  + "}                              \n";    // normalized screen coordinates.
		
		final String fragmentShader =
			"precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a 
													// precision in the fragment shader.				
		  + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the 
		  											// triangle per fragment.			  
		  + "void main()                    \n"		// The entry point for our fragment shader.
		  + "{                              \n"
		  + "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.		  
		  + "}                              \n";												
		
		// Load in the vertex shader.
		int vertexShaderHandle = RendererGameViewHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
		
		// Load in the fragment shader shader.
		int fragmentShaderHandle = RendererGameViewHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
				
		// Create a program object and store the handle to it.
		String[] attributes = {"a_Position", "a_Color"};
		int programHandle = RendererGameViewHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, attributes);
		        
        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");        
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");        
        
        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(programHandle);        
	}	
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) 
	{
		// Set the OpenGL viewport to the same size as the surface.
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 100.0f;
		
		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
	}	

	@Override
	public void onDrawFrame(GL10 glUnused) 
	{
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);			        
                        
        // Draw the triangle facing straight on.
        Matrix.setIdentityM(mModelMatrix, 0);        
        drawBoard();
	}	
	
	/**
	 * Draws a board from the given vertex data.
	 * 
	 * @param aBoardBuffer The buffer containing the vertex data.
	 */
	private void drawBoard()
	{		
		// Pass in the position information
		boardVerticesPosition.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
        		mStridePositionBytes, boardVerticesPosition);                        
        GLES20.glEnableVertexAttribArray(mPositionHandle);        
        
        // Pass in the color information
        boardVerticesColor.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
        		mStrideColorBytes, boardVerticesColor);                
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
