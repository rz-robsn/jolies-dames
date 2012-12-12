package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.FloatMath;

public class GLPiece {

	/** The Different colors a Piece can have */
	public static enum PieceColor { WHITE, RED }
	
	private final static float[] WHITE_COLOR_DATA = { 1.0f, 1.0f, 1.0f, 1.0f };
	private final static float[] RED_COLOR_DATA = { 0.8f, 0.0f, 0.0f, 1.0f };


}
