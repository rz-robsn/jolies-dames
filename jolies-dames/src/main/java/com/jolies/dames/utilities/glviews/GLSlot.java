package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.jolies.dames.R;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureInfo;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.primitives.Plane;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class GLSlot {

	/** The Different colors a slot can have */
	public static enum SlotColor {BLUE, GREEN, BROWN, BEIGE}

	private static final float MODEL_DIMENSION_XY = 1.125f;
	private static final float MODEL_DIMENSION_Z = 0.052f;
	
	private static final float DIMENSION_XY = 0.125f;
	private static final float DIMENSION_Z = 0.025f;
	
	private static final float SCALE_XY = DIMENSION_XY/MODEL_DIMENSION_XY;
	private static final float SCALE_Z = DIMENSION_Z/MODEL_DIMENSION_Z;
	
	public BaseObject3D object;
	
	public GLSlot(Context context, TextureManager textureManager, Number3D position, SlotColor color)
	{	
		ObjParser objParser = new ObjParser(context.getResources(), textureManager, R.raw.slot_obj);
		objParser.parse();
		object = objParser.getParsedObject();
		
		object.setScaleX(SCALE_XY);
		object.setScaleY(SCALE_XY);
		object.setScale(SCALE_Z);
		
		DiffuseMaterial material = new DiffuseMaterial();
		material.setAmbientIntensity(0.75f);				
		object.setMaterial(material);
		this.changeSlotColorTo(color);		
	}
	
	public void changeSlotColorTo(SlotColor color)
	{
		Number3D newColor = null;
		
		switch(color)
		{
			case BEIGE:
				newColor = new Number3D(1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f);
				break;
			case BLUE:
				newColor = new Number3D(0.1686f, 0.1098f, 0.8392f);
				break;
			case BROWN:
				newColor = new Number3D(0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f);
				break;
			case GREEN:
				newColor = new Number3D(0.133333f, 0.72157f, 0.027450f);
				break;
			default:
				newColor = new Number3D(1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f);
				break;
		}
		
		DiffuseMaterial material = (DiffuseMaterial) object.getMaterial();
		material.setAmbientColor(newColor);
	}
}
