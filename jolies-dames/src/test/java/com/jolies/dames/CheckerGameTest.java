package com.jolies.dames;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GameListener;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CheckerGameTest
{
    GameListener mockListener;
    CheckerGame game;
    
    @Before
    public void setUp() throws Exception
    {
        mockListener = mock(GameListener.class);
        
        game = new CheckerGame();
        game.setListener(mockListener);
        game.newGame();
    }
    
    @Test
    public void shouldPlayRoundNormally() throws Exception
    {
        /* Making some Illegal Moves */
        // Moving White Piece
        // Moving Piece to slot not in range
        // Moving empty slot
        // Moving Piece surrounded by other pieces
        
        
        // Moving legally Red Piece
        
        /* Making Illegal Moves */
        // Moving Red Piece
        
        // Moving legally White Piece
        
        /* Making Illegal Moves */
        // Moving Red Piece BackWards
        
        // Assert size of list of moves available for some other red piece is
        // equal to 2
        
        // move Red Piece
        
        // Assert that only move available is eating red piece.
        // Eat Red Piece
    }
    
    @After
    public void tearDown() throws Exception
    {

    }
}
