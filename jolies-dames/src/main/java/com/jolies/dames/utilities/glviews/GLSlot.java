package com.jolies.dames.utilities.glviews;

import rajawali.BaseObject3D;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;
import android.content.Context;

public class GLSlot {

	/** The Different colors a slot can have */
	public static enum SlotColor {BLUE, GREEN, BROWN, BEIGE}

	private static final float MODEL_DIMENSION_XZ = 1.125f;
	private static final float MODEL_DIMENSION_Y = 0.052f;
	
	public static final float DIMENSION_XZ = 0.125f;
	public static final float DIMENSION_Y = 0.025f;
	
	private static final float SCALE_XZ = DIMENSION_XZ/MODEL_DIMENSION_XZ;
	private static final float SCALE_Y = DIMENSION_Y/MODEL_DIMENSION_Y;
	
	public BaseObject3D object;
	
	public GLSlot(Context context, TextureManager textureManager, Number3D position, SlotColor color)
	{			
		object = GLObjParser.getInstance(context, textureManager).getSlotObj3D();
		object.setPosition(position);
		
		object.setScaleX(SCALE_XZ);
		object.setScaleZ(SCALE_XZ);
		object.setScaleY(SCALE_Y);
		
		this.changeSlotColorTo(color);		
	}
	
	public void changeSlotColorTo(SlotColor color)
	{
		DiffuseMaterial material = new DiffuseMaterial();
		material.setAmbientIntensity(0.75f);						
		Number3D newColor = null;
		
		switch(color)
		{
			case BROWN:				
				object.setMaterial(new SimpleMaterial());
				break;
				
			case BEIGE:				
				newColor = new Number3D(1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f);
				material.setAmbientColor(newColor);
				object.setMaterial(material);
				break;
				
			case BLUE:
				newColor = new Number3D(0.1686f, 0.1098f, 0.8392f);
				material.setAmbientColor(newColor);
				object.setMaterial(material);
				break;
				
			case GREEN:
				newColor = new Number3D(0.133333f, 0.72157f, 0.027450f);
				material.setAmbientColor(newColor);
				object.setMaterial(material);
				break;
				
			default:
				break;
		}
		
		
	}
}
