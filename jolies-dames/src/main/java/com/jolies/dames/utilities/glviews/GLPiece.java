package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.jolies.dames.R;
import com.jolies.dames.utilities.glviews.GLSlot.SlotColor;

import rajawali.BaseObject3D;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.FloatMath;

public class GLPiece {

	/** The Different colors a Piece can have */
	public static enum PieceColor { WHITE, RED }

	private final static float DIMENSION_XZ = 0.125f;
	private final static float DIMENSION_Y = 0.025f;
	
	/**
	 * The gap distance between the top piece and the bottom piece
	 * when the piece is a kingPiece.
	 */
	private final static float KING_PIECE_GAP = 0.01f;	
	
	public BaseObject3D object;
	private PieceColor pieceColor;
	
	public GLPiece(Context context, TextureManager textureManager,
			Number3D position, PieceColor color, boolean kingPiece)
	{
		this.pieceColor = color;
		
		ObjParser objParser = new ObjParser(context.getResources(), textureManager, R.raw.piece_obj);
		objParser.parse();
		
		if(kingPiece)
		{
			BaseObject3D bottomPieceObject = objParser.getParsedObject();
			BaseObject3D topPieceObject = bottomPieceObject.clone();			

			object = new BaseObject3D();
			topPieceObject.setPosition(object.getPosition().add(0, DIMENSION_Y/2 + KING_PIECE_GAP, 0));

			if(color == PieceColor.RED)
			{
				bottomPieceObject.removeTexture(bottomPieceObject.getTextureInfoList().get(0));
				bottomPieceObject.addTexture(textureManager.addTexture(BitmapFactory.decodeResource(context.getResources(), R.drawable.wooden_planks_red)));														
			}
			
			object = new BaseObject3D();
			object.addChild(bottomPieceObject);
			object.addChild(topPieceObject);
		}
		else
		{
			object = objParser.getParsedObject();
			
			if(color == PieceColor.RED)
			{
				object.removeTexture(object.getTextureInfoList().get(0));					
				object.addTexture(textureManager.addTexture(BitmapFactory.decodeResource(context.getResources(), R.drawable.wooden_planks_red)));									
			}
		}
		
		object.setPosition(position);
	}
}
