package com.jolies.dames.utilities.glviews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.jolies.dames.R;

import rajawali.BaseObject3D;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.TextureManager;
import rajawali.math.Number3D;
import rajawali.parser.ObjParser;
import rajawali.primitives.Plane;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GLSlot {

	/** The Different colors a slot can have */
	public static enum SlotColor {BLUE, GREEN, BROWN, BEIGE}

	private final static int WIDTH = 1;
	private final static int HEIGHT = 1;
	private final static int SEGMENTW = 1;
	private final static int SEGMENTH = 1;
	
	private final static DiffuseMaterial MATERIAL = new DiffuseMaterial();
	
	private final static float[] blueColorData = {
		
		// R, G, B, A
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
		0.1686f, 0.1098f, 0.8392f, 1.0f, 
	};
	
	private final static float[] greenColorData = { 
		
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
		0.133333f, 0.72157f, 0.027450f, 1.0f,
	};
	
	private final static float[] brownColorData = { 

		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f, 
		0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f, 1.0f 				
	};
	
	private final static float[] beigeColorData = { 
		
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,	
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
		1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f, 1.0f,
	};
	
	public BaseObject3D object;
	
	public GLSlot(Context context, TextureManager textureManager, Number3D position, SlotColor color)
	{
		ObjParser objParser = new ObjParser(context.getResources(), textureManager, R.raw.slot_obj);
		objParser.parse();
		object = objParser.getParsedObject();
		
		object.getMaterial().setUseColor(true);
		object.setPosition(position);
		//object.setColor(GLSlot.getColorForSlotColor(color));
	}
	
	private static Number3D getColorForSlotColor(SlotColor color)
	{
		switch(color)
		{
			case BEIGE:
				return new Number3D(1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f);				
			case BLUE:
				return new Number3D(0.1686f, 0.1098f, 0.8392f);
			case BROWN:
				return new Number3D(0.81960784313725490196078431372549f, 0.54509803921568627450980392156863f, 0.27843137254901960784313725490196f);
			case GREEN:
				return new Number3D(0.133333f, 0.72157f, 0.027450f);
			default:
				return new Number3D(1.0f, 0.8078431372549019607843137254902f, 0.61960784313725490196078431372549f);
		}
	}
}
