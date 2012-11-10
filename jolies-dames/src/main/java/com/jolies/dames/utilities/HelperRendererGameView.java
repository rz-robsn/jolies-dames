package com.jolies.dames.utilities;

import android.opengl.GLES20;
import android.util.Log;

public class HelperRendererGameView {

	private static final String TAG = HelperRendererGameView.class.getSimpleName();
	
	/** 
	 * Helper function to compile a shader.
	 * 
	 * @param shaderType The shader type.
	 * @param shaderSource The shader source code.
	 * @return An OpenGL handle to the shader.
	 */
	public static int compileShader(final int shaderType, final String shaderSource) 
	{
		int shaderHandle = GLES20.glCreateShader(shaderType);

		if (shaderHandle != 0) 
		{
			// Pass in the shader source.
			GLES20.glShaderSource(shaderHandle, shaderSource);

			// Compile the shader.
			GLES20.glCompileShader(shaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0) 
			{
				Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}

		if (shaderHandle == 0)
		{			
			throw new RuntimeException("Error creating shader.");
		}
		
		return shaderHandle;
	}	
	
	/**
	 * Helper function to compile and link a program.
	 * 
	 * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
	 * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
	 * @param attributes Attributes that need to be bound to the program.
	 * @return An OpenGL handle to the program.
	 */
	public static int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes) 
	{
		int programHandle = GLES20.glCreateProgram();
		
		if (programHandle != 0) 
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShaderHandle);			

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);
			
			// Bind attributes
			if (attributes != null)
			{
				final int size = attributes.length;
				for (int i = 0; i < size; i++)
				{
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
				}						
			}
			
			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0) 
			{				
				Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}
		
		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}
		
		return programHandle;
	}
	
    public static final String getFragmentShader()
    {
        final String fragmentShader =
            "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a 
                                                    // precision in the fragment shader.                
          + "varying vec4 v_Color;          \n"     // This is the color from the vertex shader interpolated across the 
                                                    // triangle per fragment.             
          + "void main()                    \n"     // The entry point for our fragment shader.
          + "{                              \n"
          + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.          
          + "}                              \n";
        return fragmentShader;
    }

    public static final String getVertexShader()
    {
        final String vertexShader =
            "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
            
          + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
          + "attribute vec4 a_Color;        \n"     // Per-vertex color information we will pass in.              
          
          + "varying vec4 v_Color;          \n"     // This will be passed into the fragment shader.
          
          + "void main()                    \n"     // The entry point for our vertex shader.
          + "{                              \n"
          + "   v_Color = a_Color;          \n"     // Pass the color through to the fragment shader. 
                                                    // It will be interpolated across the triangle.
          + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
          + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in                                                                   
          + "}                              \n";    // normalized screen coordinates.
        return vertexShader;
    }
}
