package com.jolies.dames.utilities;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jolies.dames.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
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
		mLight = new DirectionalLight(0.1f, 0.2f, 1.0f); // set the direction
		mLight.setPower(1.5f);
		
		Bitmap bg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.selera_sari);
		mSphere = new Sphere(1, 12, 12);
		DiffuseMaterial material = new DiffuseMaterial();
		mSphere.setMaterial(material);
		mSphere.setLight(mLight);
		mSphere.addTexture(mTextureManager.addTexture(bg));
		addChild(mSphere);
		
		mCamera.setZ(-4.2f);
	}

}
