package com.jolies.dames.utilities.glviews;

import com.jolies.dames.R;

import rajawali.BaseObject3D;
import rajawali.materials.TextureManager;
import rajawali.parser.ObjParser;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class responsible for parsing model files and creating
 * corresponding BaseObject3D objects. 
 */
public class GLObjParser 
{
	private static GLObjParser instance = null;
	
	private BaseObject3D slotObj3D;
	private BaseObject3D pieceObj3D;
	private Bitmap redPieceBitMap;
	
	private GLObjParser(Context context, TextureManager textureManager)
	{
		ObjParser slotParser = new ObjParser(context.getResources(), textureManager, R.raw.slot_obj);
		ObjParser pieceParser = new ObjParser(context.getResources(), textureManager, R.raw.piece_obj);
		
		slotParser.parse();
		pieceParser.parse();
		
		this.slotObj3D = slotParser.getParsedObject();
		this.pieceObj3D = pieceParser.getParsedObject();
		
		this.redPieceBitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wooden_planks_red);
	}
	
	public static GLObjParser getInstance(Context context, TextureManager textureManager)
	{
		if(instance == null)
		{
			instance = new GLObjParser(context, textureManager);			
		}		
		
		return instance;
	}
	
	public BaseObject3D getPieceObj3D()
	{
		return this.pieceObj3D.clone(true);
	}
	
	public BaseObject3D getSlotObj3D()
	{
		return this.slotObj3D.clone(true);		
	}
	
	public Bitmap getRedPieceBitMap(Context context)
	{
		if(this.redPieceBitMap.isRecycled())
		{
			this.redPieceBitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wooden_planks_red);
		}
		
		return this.redPieceBitMap; 
	}
}
