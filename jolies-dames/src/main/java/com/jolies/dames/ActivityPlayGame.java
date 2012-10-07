package com.jolies.dames;

import com.jolies.dames.utilities.GLSurfaceViewGame;
import com.jolies.dames.utilities.ListenerBoard;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ActivityPlayGame extends Activity implements ListenerBoard
{
    private GLSurfaceViewGame surface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        surface = new GLSurfaceViewGame(this);
        this.setContentView(surface);        
        surface.setListenerBoard(this);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        surface.onPause();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        surface.onResume();
    }

    @Override
    public void onSlotSelected(int x, int y)
    {
        // TODO Auto-generated method stub
        
    }
    
}
