package com.jolies.dames;

//import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
//import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jolies.dames.utilities.ListenerGame;
import com.jolies.dames.utilities.model.CheckerGame;
import com.jolies.dames.utilities.model.GamePiece;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CheckerGameTest
{
    ListenerGame mockListener;
    CheckerGame game;
    ArrayList<ArrayList<GamePiece>> grid;
    
    @Before
    public void setUp() throws Exception
    {
        // mockListener = mock(GameListener.class);
        
        game = new CheckerGame();
        game.setListener(mockListener);
        game.newGame();
        this.grid = game.getGrid();
    }
    
    @Test
    public void shouldPlayRoundNormally() throws Exception
    {
        printGame();
        
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
    
    private void printGame() throws Exception
    {
        System.out.println("Current Game :");
        for (int i = 7; i >= 0; i--)
        {
            System.out.printf("%d ", i);
            for (int j = 0; j < 0; j++)
            {
                System.out.print(GamePieceString(grid.get(j).get(i)) + "\n");                
            }
            System.out.print("\n");
        }
        System.out.println("   0  1  2  3  4  5  6  7 \n" );
    }
    
    private String GamePieceString(GamePiece gamePiece)
    {
        switch (gamePiece)
        {
            case EMPTY_SLOT:
                return "[   ]";
            case RED_KING_PIECE:
                return "[RK ]";
            case RED_PIECE:
                return "[ R ]";
            case WHITE_KING_PIECE:
                return "[WK ]";
            case WHITE_PIECE:
                return "[ W ]";
            default:
                return "";
        }
    }
}
