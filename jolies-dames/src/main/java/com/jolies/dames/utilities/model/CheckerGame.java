package com.jolies.dames.utilities.model;

import java.util.ArrayList;
import java.util.List;

public class CheckerGame
{
    public static final int GRID_SIZE = 8;
    
    private GameListener listener;
    
    // / <summary> If the current player's last move was a jump,
    // / this attribute will contain current slot of the jumping piece,
    // / otherwise equals NULL. </summary>
    private Slot jumpingPiece;
    
    private ArrayList<ArrayList<GamePiece>> grid;
    private Player currentPlayer;
    
    private boolean gameIsOver;
    
    public CheckerGame()
    {
    }
    
    // / <summary> Sets this game's listener. </summary>
    // / <param name="listener"> [in,out] The listener. Does not assume
    // ownership. </param>
    public void setListener(GameListener listener)
    {
    }
    
    // / <summary> Starts a new Game </summary>
    public void newGame()
    {
    }
    
    // / <summary> Move the piece from (xStart, yStart) to (xEnd, yEnd).
    // / If the move is illegal, the piece stays at (xStart, yStart). </summary>
    public void movePiece(int xStart, int yStart, int xEnd, int yEnd)
    {
    }
    
    // / <summary> Returns the list of slots where the piece at (x,y) can move
    // to.
    // / Will return empty list if the piece does not belong to the current
    // / player </summary>
    // / <returns> The available moves for piece. </returns>
    public List<Slot> getAvailableMovesForPiece(int x, int y)
    {
        return null;
    }
    
    public ArrayList<ArrayList<GamePiece>> getGrid()
    {
        return null;
    }
    
    // / <summary> Returns the list of slots where the piece at (x,y) can move
    // to.
    // / </summary>
    // / <param name="player"> The player who is attempting to move the piece.
    // </param>
    // / <returns> The available moves for piece. </returns>
    private List<Slot> getAvailableMovesForPiece(int x, int y, Player player)
    {
        return null;
    }
    
    void initPiecesWithFirstSlotEmpty(ArrayList<GamePiece> gridRow,
            GamePiece piece)
    {
    }
    
    void initPiecesWithFirstSlotContainingPiece(ArrayList<GamePiece> gridRow,
            GamePiece piece)
    {
    }
    
    void initRowWithEmptySlot(ArrayList<GamePiece> gridRow)
    {
    }
    
    void switchTurn()
    {
    }
    
    boolean moveIsLegal(int xStart, int yStart, int xEnd, int yEnd)
    {
        return false;
    }
    
    boolean pieceCanEatEnemyPiece(int x, int y, Player player)
    {
        return false;
    }
    
    boolean pieceCanEatEnemyPiece(int x, int y)
    {
        return false;
    }
    
    boolean pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy,
            int xEmptySlot, int yEmptySlot)
    {
        return false;
    }
    
    boolean playerCanStillMoveAPiece(Player player)
    {
        return false;
    }
    
    List<Slot> getAllMovesThatEatEnemy(int x, int y)
    {
        return null;
    }
    
    void addAvailableMovesToEmptySlots(int x, int y, List<Slot> moves)
    {
    }
    
    void pushSlotIfEmpty(int x, int y, List<Slot> moves)
    {
    }
    
    boolean pieceHasReachedOppositeEnd(int x, int y)
    {
        return false;
    }
    
    void promotePiece(int x, int y)
    {
    }
    
    boolean pieceBelongsToPlayer(int x, int y, Player player)
    {
        return false;
    }
    
    static boolean pieceBelongsToPlayer(GamePiece piece, Player player)
    {
        return false;
    }
    
    GamePiece getGamePieceAt(int x, int y)
    {
        return null;
    }
    
    void setGamePieceAt(int x, int y, GamePiece piece)
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
