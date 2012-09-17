package com.jolies.dames;

import com.jolies.dames.utilities.GLSurfaceViewGame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ActivityPlayGame extends Activity {

	private GLSurfaceView surface ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		surface = new GLSurfaceViewGame(this);
		this.setContentView(surface);
	}

	@Override
	protected void onPause() {
		super.onPause();
		surface.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		surface.onResume();
	}

}
