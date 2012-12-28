package com.jolies.dames;


import rajawali.RajawaliFragmentActivity;
import com.jolies.dames.utilities.ListenerBoard;
import com.jolies.dames.utilities.ListenerGame;
import com.jolies.dames.utilities.ListenerOnSurfaceCreated;
import com.jolies.dames.utilities.RendererGameView;
import com.jolies.dames.utilities.glviews.OnTouchListenerSlotClicked;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.jolies.dames.utilities.model.Player;
import com.jolies.dames.utilities.model.Slot;

import android.os.Bundle;

public class ActivityPlayGame extends RajawaliFragmentActivity implements ListenerBoard, ListenerGame, ListenerOnSurfaceCreated
{
	private RendererGameView mRenderer;
    private CheckerGame game;
    
    private Slot previouslySelectedSlot = null;
    
    @Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		
		mRenderer = new RendererGameView(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		mRenderer.setListenerOnSurfaceCreated(this);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        this.mSurfaceView.onPause();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        this.mSurfaceView.onResume();
    }

    @Override
    public void onSlotSelected(int x, int y)
    {        
        if(this.previouslySelectedSlot != null)
        {
            if(!(this.previouslySelectedSlot.x == x && this.previouslySelectedSlot.y == y))
            {
                this.mRenderer.getBoard().unhighlightAllSlots();

                this.game.movePiece(this.previouslySelectedSlot.x, this.previouslySelectedSlot.y, x, y);            
                this.previouslySelectedSlot = null;        
            }
        }
        else
        {
            this.mRenderer.getBoard().unhighlightAllSlots();

            
            // highlight moves available for piece at position (x,y)
            Slot[] moves = this.game.getAvailableMovesForPiece(x, y).toArray(new Slot[]{});

            if(moves.length > 0)
            {
                this.mRenderer.getBoard().setSlotsToHighLight(moves);
                this.previouslySelectedSlot = new Slot(x,y);   
            }
        }
    }

	@Override
	public void onPinchOrExpand(float deltaValue) 
	{
		this.mRenderer.increaseZoomFactorBy(deltaValue/150);
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
                this.mRenderer.getBoard().createPieceAtPosition(i, j, gamePiece);
            }    
        }
        this.previouslySelectedSlot = null;
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
        this.mRenderer.getBoard().movePiece(xStart, yStart, xEnd, yEnd);
    }

    @Override
    public void onPieceEaten(int x, int y, GamePiece gamePiece)
    {
        this.mRenderer.getBoard().destroyPieceAt(x, y);
    }

    @Override
    public void onPieceBecameKing(int x, int y, GamePiece gamePiece)
    {
        this.mRenderer.getBoard().createPieceAtPosition(x, y, gamePiece);
    }

    @Override
    public void onIllegalMove(int xStart, int yStart, int xEnd, int yEnd,
            GamePiece gamePiece)
    {
        this.mRenderer.getBoard().unhighlightAllSlots();        
    }

    @Override
    public void onPieceCanStillJump(int x, int y, GamePiece gamePiece)
    {
        // TODO Auto-generated method stub        
    }

	@Override
	public void onSceneInitialized() 
	{
		OnTouchListenerSlotClicked touchListener = new OnTouchListenerSlotClicked(mRenderer);
		touchListener.setListener(this);
		this.mSurfaceView.setOnTouchListener(touchListener);
		
        game = new CheckerGame();
        game.setListener(this);
        game.newGame();
	}
}
