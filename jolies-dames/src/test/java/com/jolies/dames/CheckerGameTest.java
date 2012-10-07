package com.jolies.dames;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static com.jolies.dames.matchers.IsAnyGamePiece.isAnyGamePiece;

import java.util.ArrayList;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

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
        mockListener = mock(ListenerGame.class);
        game = new CheckerGame();
        
        game.setListener(mockListener);
    }
    
    @Test
    public void shouldPlayRoundNormally() throws Exception
    {
        // Creating a new game.
        game.newGame();
        verify(mockListener).onNewGame();
        
        this.grid = game.getGrid();
        printGame();
        
        // Making some Illegal Moves                
        game.movePiece(6, 2, 7, 3); // Moving White Piece
        game.movePiece(7, 5, 6, 8); // Moving Piece to slot not in range
        game.movePiece(2, 2, 3, 3); // Moving empty slot
        game.movePiece(4, 1, 3, 2); // Moving Piece surrounded by other pieces
        
        verify(mockListener, times(4)).onIllegalMove(anyInt(), anyInt(), anyInt(), anyInt(), isAnyGamePiece());
        
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
