package com.jolies.dames;

import android.os.Bundle;

import com.jolies.dames.utilities.RendererHelloRajawali;

import rajawali.RajawaliActivity;

public class ActivityHelloRajawali extends RajawaliActivity {

	private RendererHelloRajawali mRenderer; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mRenderer = new RendererHelloRajawali(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
	}

}
