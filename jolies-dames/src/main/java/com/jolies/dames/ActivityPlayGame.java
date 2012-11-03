package com.jolies.dames;

import com.jolies.dames.utilities.GLSurfaceViewGame;
import com.jolies.dames.utilities.ListenerBoard;
import com.jolies.dames.utilities.ListenerGame;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.jolies.dames.utilities.model.Player;

import android.app.Activity;
import android.os.Bundle;

public class ActivityPlayGame extends Activity implements ListenerBoard, ListenerGame
{
    private GLSurfaceViewGame surface;
    private CheckerGame game;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        surface = new GLSurfaceViewGame(this);
        this.setContentView(surface);        
        surface.setListenerBoard(this);
        
        game = new CheckerGame();
        game.setListener(this);
        game.newGame();
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

    @Override
    public void onNewGame()
    {
        // Create initial Pieces Positions
        for (int i = 0; i < CheckerGame.GRID_SIZE; i++)
        {
            for (int j = 0; j < CheckerGame.GRID_SIZE; j++)
            {
                GamePiece gamePiece = this.game.getGamePieceAt(i, j);
                this.surface.getRenderer().getBoard().createPieceAtPosition(i, j, gamePiece);
            }    
        }
    }

    @Override
    public void onPlayerWin(Player player)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDraw()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPieceMoved(int xStart, int yStart, int xEnd, int yEnd,
            GamePiece gamePiece)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPieceEaten(int x, int y, GamePiece gamePiece)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPieceBecameKing(int x, int y, GamePiece gamePiece)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onIllegalMove(int xStart, int yStart, int xEnd, int yEnd,
            GamePiece gamePiece)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPieceCanStillJump(int x, int y, GamePiece gamePiece)
    {
        // TODO Auto-generated method stub
        
    }
    
}
