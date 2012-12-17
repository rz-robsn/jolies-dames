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

	private static final float MODEL_DIMENSION_XZ = 1.125f;
	private static final float MODEL_DIMENSION_Y = 0.052f;
	
	public static final float DIMENSION_XZ = 0.125f;
	public static final float DIMENSION_Y = 0.025f;
	
	private static final float SCALE_XZ = DIMENSION_XZ/MODEL_DIMENSION_XZ;
	private static final float SCALE_Y = DIMENSION_Y/MODEL_DIMENSION_Y;
	
	public BaseObject3D object;
	
	public GLSlot(Context context, TextureManager textureManager, Number3D position, SlotColor color)
	{	
		ObjParser objParser = new ObjParser(context.getResources(), textureManager, R.raw.slot_obj);
		objParser.parse();
		object = objParser.getParsedObject();
		object.setPosition(position);
		
		object.setScaleX(SCALE_XZ);
		object.setScaleZ(SCALE_XZ);
		object.setScaleY(SCALE_Y);
		
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
