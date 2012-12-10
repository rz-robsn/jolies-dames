package com.jolies.dames.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.parser.ObjParser;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;

public class RendererHelloRajawali extends RajawaliRenderer{

	static Sphere mSphere;
	DirectionalLight mLight;
	
	public RendererHelloRajawali(Context context) 
	{
		super(context);
		setFrameRate(30);
	}

	@Override
	protected void initScene() 
	{
		// TODO Auto-generated method stub
		super.initScene();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		super.onSurfaceCreated(gl, config);
		mLight = new DirectionalLight(1, -1, 0.5f); // set the direction
		mLight.setPower(1.5f);
		
		ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, com.jolies.dames.R.raw.piece_obj);
		objParser.parse();
		BaseObject3D mObject = objParser.getParsedObject();
		mObject.addLight(mLight);
		addChild(mObject);
		
		Plane plane = new Plane(1,1,1,1);
		plane.addLight(mLight);
		DiffuseMaterial material = new DiffuseMaterial();
		plane.setMaterial(material);
		this.addChild(plane);
		
		this.mCamera.setZ(-4.2f);
		this.mCamera.setY(2f);
		this.mCamera.setLookAt(0, 0, 0);
		
		mCamera.setZ(-4.2f);
	}

}
