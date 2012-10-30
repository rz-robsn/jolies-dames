package com.jolies.dames;

import static com.jolies.dames.matchers.IsAnyGamePiece.isAnyGamePiece;

import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

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
        mockListener = mock(ListenerGame.class);
        game = new CheckerGame();        
        game.setListener(mockListener);
        grid = game.getGrid();
        
        game.newGame();
        reset(mockListener);
    }

    @Test
    public void shouldCallListenerOnNewGame() throws Exception
    {
        game.newGame();
        verify(mockListener).onNewGame();
    }
    
    @Test
    public void shouldCallListenerOnIllegalMove() throws Exception
    {                
        // Making some Illegal Moves                
        game.movePiece(6, 2, 7, 3); // Moving White Piece
        game.movePiece(7, 5, 6, 8); // Moving Piece to slot not in range
        game.movePiece(2, 2, 3, 3); // Moving empty slot
        game.movePiece(4, 1, 3, 2); // Moving Piece surrounded by other pieces
 
        verify(mockListener, times(4)).onIllegalMove(anyInt(), anyInt(), anyInt(), anyInt(), isAnyGamePiece());
    }
    
    @Test
    public void shouldCallListenerOnRedPieceMoved() throws Exception
    {        
        // Moving legally Red Piece
        game.movePiece(3, 3, 4, 4);
        
        verify(mockListener).onPieceMoved(3, 3, 4, 4, GamePiece.RED_PIECE);
    }

    @Test
    public void shouldCallListenerOnMovingRedPieceAtWhiteTurn() throws Exception
    {        
        // Moving legally Red Piece
        game.movePiece(3, 5, 4, 4);
        
        /* Making Illegal Moves */
        game.movePiece(4, 4, 3, 3); // Moving Red Piece
        
        verify(mockListener).onPieceMoved(3, 3, 4, 4, GamePiece.RED_PIECE);
        verify(mockListener).onIllegalMove(4, 4, 5, 6, GamePiece.RED_PIECE);
    }
    
    @Test
    public void shouldCallListenerIllegalMoveOnMovingPieceBackWards() throws Exception
    { 
        this.fillRow(6, GamePiece.EMPTY_SLOT);
        
        game.movePiece(3, 5, 2, 6); // Moving Red Piece Backwards
        
        verify(mockListener).onIllegalMove(3, 5, 2, 1, GamePiece.RED_PIECE);
    }

    @Test
    public void shouldHaveOnlyJumpingWhitePieceAsLegalMove() throws Exception
    {   
        // Setting up a piece that can only be jumped by red piece at position (7, 5)
        this.grid.get(5).set(5, GamePiece.EMPTY_SLOT); // set grid[5][5] = EMPTY_SLOT. 
        this.grid.get(4).set(6, GamePiece.WHITE_PIECE); // set grid[6][4] = WHITE_PIECE.
        
        // Trying to move red pieces other than (7,5)
        game.movePiece(1, 5, 0, 4);
        game.movePiece(6, 6, 5, 5);
        game.movePiece(3, 5, 4, 4);
        
        // Trying to move some white pieces
        game.movePiece(0, 2, 1, 3);
        game.movePiece(6, 2, 5, 3);
        
        // Jumping the Piece
        game.movePiece(7, 5, 5, 3);
        
        verify(mockListener).onIllegalMove(1, 5, 0, 4, GamePiece.RED_PIECE);
        verify(mockListener).onIllegalMove(6, 6, 5, 5, GamePiece.RED_PIECE);
        verify(mockListener).onIllegalMove(3, 5, 4, 4, GamePiece.RED_PIECE);
        verify(mockListener).onIllegalMove(0, 2, 1, 3, GamePiece.WHITE_PIECE);
        verify(mockListener).onIllegalMove(6, 2, 5, 3, GamePiece.WHITE_PIECE);
        
        verify(mockListener).onPieceEaten(6, 4, GamePiece.WHITE_PIECE);
        verify(mockListener).onPieceMoved(7, 5, 5, 3, GamePiece.RED_PIECE);        
    }
    
    @Test
    public void shouldCallListenerOnPieceBecameKingWhenPieceReachesEndOfBoard() throws Exception
    {
        this.fillRow(1, GamePiece.EMPTY_SLOT);
        
        this.grid.get(1).set(2, GamePiece.RED_PIECE); // set grid[2][1] = RED_PIECE. 

        // Moving Piece to the Opposite End
        game.movePiece(2, 1, 1, 0);
        
        verify(mockListener).onPieceMoved(2, 1, 1, 0, GamePiece.RED_PIECE);
        verify(mockListener).onPieceBecameKing(1, 0, GamePiece.RED_KING_PIECE);
    }

    @Test
    public void shouldAllowKingPiecesToMoveBackWards() throws Exception
    {
        this.grid.get(2).set(3, GamePiece.RED_KING_PIECE); // set grid[3][2] = KING_RED_PIECE. 

        assertThat(game.getAvailableMovesForPiece(3, 2).size(), equalTo(4));
        
        // Moving King Piece
        game.movePiece(3, 2, 2, 3);
        
        verify(mockListener).onPieceMoved(3, 2, 2, 3, GamePiece.RED_KING_PIECE);
    }
    
    @After
    public void tearDown() throws Exception
    {
        reset(mockListener);
    }
    
    private void printGame() throws Exception
    {
        System.out.println("Current Game :");
        for (int i = 7; i >=  0; i--)
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
    
    private void fillRow(int rowIndex, GamePiece gamePiece)
    {
        // Setting all the gamePieces at y =  to EMPTY_SLOTS. 
        for(int i = 0; i < 8 ; i++)
        {
            this.grid.get(rowIndex).set(i, gamePiece);
        }
    }
    
    private void emptyGrid()
    {
        for (int i = 0; i < 8; i++)
        {
            this.fillRow(i, GamePiece.EMPTY_SLOT);
        }
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
