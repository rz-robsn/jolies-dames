package com.jolies.dames.utilities.glviews;

import rajawali.BaseObject3D;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;
import android.content.Context;

public class GLPiece {

	/** The Different colors a Piece can have */
	public static enum PieceColor { WHITE, RED }

	public final static float DIMENSION_XZ = 0.125f;
	public final static float DIMENSION_Y = 0.025f;
	
	/**
	 * The gap distance between the top piece and the bottom piece
	 * when the piece is a kingPiece.
	 */
	private final static float KING_PIECE_GAP = 0.01f;	
	
	public BaseObject3D object;
	
	public GLPiece(Context context, TextureManager textureManager,
			Number3D position, PieceColor color, boolean kingPiece)
	{
				
		if(kingPiece)
		{
			BaseObject3D bottomPieceObject = GLObjParser.getInstance(context, textureManager).getPieceObj3D();
			BaseObject3D topPieceObject = GLObjParser.getInstance(context, textureManager).getPieceObj3D();			

			object = new BaseObject3D();
			topPieceObject.setPosition(Number3D.add(object.getPosition(), new Number3D(0, DIMENSION_Y/2 + KING_PIECE_GAP, 0)));

			if(color == PieceColor.RED)
			{
				DiffuseMaterial material = new DiffuseMaterial();
				material.setAmbientIntensity(1f);
				Number3D redColor = new Number3D(255, 0, 0);
				material.setAmbientColor(redColor);

				bottomPieceObject.setMaterial(material);
				topPieceObject.setMaterial(material);
			}
			
			object.addChild(bottomPieceObject);
			object.addChild(topPieceObject);
		}
		else
		{
			object = GLObjParser.getInstance(context, textureManager).getPieceObj3D();
			
			if(color == PieceColor.RED)
			{
				DiffuseMaterial material = new DiffuseMaterial();
				material.setAmbientIntensity(0.75f);
				Number3D redColor = new Number3D(255, 0, 0);
				material.setAmbientColor(redColor);

				object.setMaterial(material);
			}
		}
		
		object.setPosition(position);
	}
}
