package com.jolies.dames.utilities.model;

import java.util.ArrayList;
import java.util.List;

import com.jolies.dames.utilities.ListenerGame;

public class CheckerGame
{
    public static final int GRID_SIZE = 8;
    
    private ListenerGame listener;
    
    /**
     * If the current player's last move was a jump, this attribute will contain
     * current slot of the jumping piece, otherwise equals null.
     */
    private Slot jumpingPiece;
    
    private ArrayList<ArrayList<GamePiece>> grid;
    private Player currentPlayer;
    
    private boolean gameIsOver;
    
    public CheckerGame()
    {
        // create new grid
        grid = new ArrayList<ArrayList<GamePiece>>(GRID_SIZE);
        for (int i = 0 ; i < GRID_SIZE ; i++)
        {
            grid.add(new ArrayList<GamePiece>(GRID_SIZE));
        }
        this.jumpingPiece = null;
    }
    
    /**
     * Sets this game's listener.
     * 
     * @param listener
     *            The listener
     */
    public void setListener(ListenerGame listener)
    {
        this.listener = listener;
    }
    
    /**
     * Starts a new Game
     */
    public void newGame()
    {
        // Filling the grid with pieces and empty slots
        this.initPiecesWithFirstSlotContainingPiece(grid.get(0), GamePiece.WHITE_PIECE);
        this.initPiecesWithFirstSlotEmpty(grid.get(1), GamePiece.WHITE_PIECE);
        this.initPiecesWithFirstSlotContainingPiece(grid.get(2), GamePiece.WHITE_PIECE);
        this.initRowWithEmptySlot(grid.get(3));
        this.initRowWithEmptySlot(grid.get(4));
        this.initPiecesWithFirstSlotEmpty(grid.get(5), GamePiece.RED_PIECE);
        this.initPiecesWithFirstSlotContainingPiece(grid.get(6), GamePiece.RED_PIECE);
        this.initPiecesWithFirstSlotEmpty(grid.get(7), GamePiece.RED_PIECE);

        this.currentPlayer = Player.PLAYER_RED;
        this.gameIsOver = false;

        this.jumpingPiece = null;

        this.listener.onNewGame();
    }
    
    /**
     * Move the piece from (xStart, yStart) to (xEnd, yEnd). If the move is
     * illegal, the piece stays at (xStart, yStart).
     * 
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     */
    public void movePiece(int xStart, int yStart, int xEnd, int yEnd)
    {
    }
    
    /**
     * Returns the list of slots where the piece at (x,y) can move to. Will
     * return empty list if the piece does not belong to the current player.
     * 
     * @param x
     * @param y
     * @return The list of slots where the piece at (x,y) can move to. Will
     *         return empty list if the piece does not belong to the current
     *         player.
     */
    public List<Slot> getAvailableMovesForPiece(int x, int y)
    {
        return null;
    }
    
    public ArrayList<ArrayList<GamePiece>> getGrid()
    {
        return this.grid;
    }
    
    /**
     * Returns the list of slots where the piece at (x,y) can move to.
     * 
     * @param x
     * @param y
     * @param player
     *            The player who is attempting to move the piece.
     * @return The available moves for piece at position (x,y).
     */
    private List<Slot> getAvailableMovesForPiece(int x, int y, Player player)
    {
        return null;
    }
    
    private void initPiecesWithFirstSlotEmpty(ArrayList<GamePiece> gridRow,
            GamePiece piece)
    {
        for (int i = 0 ; i < GRID_SIZE ; i++)
        {
            gridRow.add(i, (i % 2 == 0) ? GamePiece.EMPTY_SLOT : piece) ;
        }
    }
    
    private void initPiecesWithFirstSlotContainingPiece(
            ArrayList<GamePiece> gridRow, GamePiece piece)
    {
        for (int i = 0 ; i < GRID_SIZE ; i++)
        {
            gridRow.add(i, (i % 2 == 1) ? GamePiece.EMPTY_SLOT : piece) ;
        }
    }
    
    private void initRowWithEmptySlot(ArrayList<GamePiece> gridRow)
    {
        for (int i = 0 ; i < GRID_SIZE ; i++)
        {
            gridRow.add(i, GamePiece.EMPTY_SLOT) ;
        }
    }
    
    private void switchTurn()
    {
    }
    
    private boolean moveIsLegal(int xStart, int yStart, int xEnd, int yEnd)
    {
        return false;
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y, Player player)
    {
        return false;
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y)
    {
        return false;
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy,
            int xEmptySlot, int yEmptySlot)
    {
        return false;
    }
    
    private boolean playerCanStillMoveAPiece(Player player)
    {
        return false;
    }
    
    private List<Slot> getAllMovesThatEatEnemy(int x, int y)
    {
        return null;
    }
    
    private void addAvailableMovesToEmptySlots(int x, int y, List<Slot> moves)
    {
    }
    
    private void pushSlotIfEmpty(int x, int y, List<Slot> moves)
    {
    }
    
    private boolean pieceHasReachedOppositeEnd(int x, int y)
    {
        return false;
    }
    
    private void promotePiece(int x, int y)
    {
    }
    
    private boolean pieceBelongsToPlayer(int x, int y, Player player)
    {
        return false;
    }
    
    private static boolean pieceBelongsToPlayer(GamePiece piece, Player player)
    {
        return false;
    }
    
    private GamePiece getGamePieceAt(int x, int y)
    {
        return null;
    }
    
    private void setGamePieceAt(int x, int y, GamePiece piece)
    {
    }
    
    private static Player getPlayerOwningPiece(GamePiece piece)
    {
        return null;
    }
    
    private static Player getOpponent(Player player)
    {
        return null;
    }
}
