package com.jolies.dames.utilities.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jolies.dames.utilities.ListenerGame;

public class CheckerGame
{
    public static final int GRID_SIZE = 8;
    
    /**
     * Exception for internal use that is thrown on unexpected/illogical method calls
     */
    private static class CheckerGameException extends Exception
    {
        public CheckerGameException(String message)
        {
            super(message);
        }
    }
    
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
        GamePiece movingPiece = this.getGamePieceAt(xStart, yStart);
        if (!this.moveIsLegal(xStart, yStart, xEnd, yEnd))
        {
            this.listener.onIllegalMove(xStart, yStart, xEnd, yEnd, movingPiece);
        }
        else
        {
            // move the piece
            this.setGamePieceAt(xStart, yStart, GamePiece.EMPTY_SLOT);
            this.setGamePieceAt(xEnd, yEnd, movingPiece);

            // if the moving piece is jumping over an enemy piece.
            if (Math.abs(yEnd - yStart) == 2)
            {
                // eat The piece in between (xStart, yStart) and (xEnd, yEnd)
                int xEnemy = xStart + (xEnd - xStart)/2; 
                int yEnemy = yStart + (yEnd - yStart)/2;

                GamePiece eatenPiece = this.getGamePieceAt(xEnemy, yEnemy);
                this.setGamePieceAt(xEnemy, yEnemy, GamePiece.EMPTY_SLOT);
                this.listener.onPieceEaten(xEnemy, yEnemy, eatenPiece);

                this.jumpingPiece = new Slot(xEnd, yEnd);  
            }
            else
            {
                this.jumpingPiece = null;
            } 

            this.listener.onPieceMoved(xStart, yStart, xEnd, yEnd, movingPiece);

            // Promote to king if piece reached the end.
            try
            {
                if (this.pieceHasReachedOppositeEnd(xEnd, yEnd))
                {
                    this.promotePiece(xEnd, yEnd);
                }
            }
            catch (CheckerGameException ignored)
            {
                ignored.printStackTrace();
            }

            // If both players can't move any pieces
            if( !this.playerCanStillMoveAPiece(this.currentPlayer)
                && !this.playerCanStillMoveAPiece(getOpponent(currentPlayer)))
            {
                this.gameIsOver = true;
                this.listener.onDraw();
            }
            else if (!this.playerCanStillMoveAPiece(getOpponent(currentPlayer))) // the other player cannot move
            {
                this.gameIsOver = true ;
                this.listener.onPlayerWin(this.currentPlayer);
            }
            else if (this.jumpingPiece != null
                    && this.pieceCanEatEnemyPiece(xEnd, yEnd, this.currentPlayer)) // the moving piece can chain jumps
            {
                this.listener.onPieceCanStillJump(xEnd, yEnd, movingPiece);
            }
            else 
            {
                this.switchTurn();
            }
        }
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
        return this.getAvailableMovesForPiece(x, y, this.currentPlayer);
    }
    
    public ArrayList<ArrayList<GamePiece>> getGrid()
    {
        return this.grid;
    }
    
    public GamePiece getGamePieceAt(int x, int y)
    {
        return this.grid.get(y).get(x);
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
        if(this.gameIsOver)
        {
            return new LinkedList<Slot>();
        }
        if(this.getGamePieceAt(x,y) == GamePiece.EMPTY_SLOT)
        {
            return new LinkedList<Slot>();
        }   
        else if (this.jumpingPiece != null
                && pieceBelongsToPlayer(getGamePieceAt(jumpingPiece.x, jumpingPiece.y), player)
                && (this.jumpingPiece.x != x || this.jumpingPiece.y != y))// there is another piece that is chain Jumping
        {
            return new LinkedList<Slot>();        
        }
        else if(!pieceBelongsToPlayer(getGamePieceAt(x, y), player))
        {
            return new LinkedList<Slot>(); 
        }   
        else if (!this.pieceCanEatEnemyPiece(x, y))
        {
            // check if there exists another piece that can eat an enemy piece
            // and return empty list if it does (because the player must move that other piece)  
            for (int i = 0; i < GRID_SIZE; i++)
            {
                for (int j = 0; j < GRID_SIZE; j++)
                {
                    try
                    {
                        if( !(i == x && j == y)
                            && this.pieceCanEatEnemyPiece(i,j)
                            && getPlayerOwningPiece(this.getGamePieceAt(x,y)) == getPlayerOwningPiece(this.getGamePieceAt(i,j)))
                        {
                            return new LinkedList<Slot>();
                        }
                    }
                    catch (CheckerGameException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            // return all empty slots the piece can move to.
            List<Slot> moves = new LinkedList<Slot>();
            try
            {
                this.addAvailableMovesToEmptySlots(x, y, moves);
            }
            catch (CheckerGameException e)
            {
                e.printStackTrace();
            }
            return moves;
        }
        else 
        {
            return this.getAllMovesThatEatEnemy(x, y);
        }
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
        this.currentPlayer = (currentPlayer == Player.PLAYER_RED) ? Player.PLAYER_WHITE : Player.PLAYER_RED;
        this.jumpingPiece = null;
    }
    
    private boolean moveIsLegal(int xStart, int yStart, int xEnd, int yEnd)
    {
        List<Slot> moves = this.getAvailableMovesForPiece(xStart, yStart);        
        for (Slot move : moves)
        {
            if (move.x == xEnd && move.y == yEnd)
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y, Player player)
    {
        return this.getAllMovesThatEatEnemy(x,y).size() > 0;
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y)
    {
        return this.pieceCanEatEnemyPiece(x, y, this.currentPlayer);
    }
    
    private boolean pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy,
            int xEmptySlot, int yEmptySlot)
    {
        try
        {
            return getPlayerOwningPiece(this.getGamePieceAt(x,y)) 
                        != getPlayerOwningPiece(this.getGamePieceAt(xEnemy, yEnemy))
                   && this.getGamePieceAt(xEmptySlot, yEmptySlot) == GamePiece.EMPTY_SLOT;
        }
        catch (IndexOutOfBoundsException e)
        {
            return false;
        }
        catch (CheckerGameException e)
        {
            return false;
        }
    }
    
    private boolean playerCanStillMoveAPiece(Player player)
    {
        for (int i = 0; i < GRID_SIZE; i++)
        {
            for (int j = 0; j < GRID_SIZE; j++)
            {
                if (this.pieceBelongsToPlayer(i, j, player)
                    && this.getAvailableMovesForPiece(i, j, player).size() > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private List<Slot> getAllMovesThatEatEnemy(int x, int y)
    {
        List<Slot> returnList = new LinkedList<Slot>();
        switch(this.getGamePieceAt(x,y))
        {
            case RED_KING_PIECE:
            case WHITE_KING_PIECE:
                if(this.pieceCanEatEnemyPiece(x, y, x-1, y-1, x-2, y-2))
                {
                    returnList.add(new Slot(x-2, y-2));
                }
                if(this.pieceCanEatEnemyPiece(x, y, x+1, y-1, x+2, y-2))
                {
                    returnList.add(new Slot(x+2, y-2));
                }
                if(this.pieceCanEatEnemyPiece(x, y, x-1, y+1, x-2, y+2))
                {
                    returnList.add(new Slot(x-2, y+2));
                }
                if(this.pieceCanEatEnemyPiece(x, y, x+1, y+1, x+2, y+2))
                {
                    returnList.add(new Slot(x+2, y+2));
                }
                break;
            case RED_PIECE:
                if(this.pieceCanEatEnemyPiece(x, y, x-1, y-1, x-2, y-2))
                {
                    returnList.add(new Slot(x-2, y-2));
                }
                if(this.pieceCanEatEnemyPiece(x, y, x+1, y-1, x+2, y-2))
                {
                    returnList.add(new Slot(x+2, y-2));
                }
                break;
            case WHITE_PIECE:
                if(this.pieceCanEatEnemyPiece(x, y, x-1, y+1, x-2, y+2))
                {
                    returnList.add(new Slot(x-2, y+2));
                }
                if(this.pieceCanEatEnemyPiece(x, y, x+1, y+1, x+2, y+2))
                {
                    returnList.add(new Slot(x+2, y+2));
                }
                break;
            case EMPTY_SLOT:
                break;
        }
        return returnList;
    }
    
    private void addAvailableMovesToEmptySlots(int x, int y, List<Slot> moves) throws CheckerGameException
    {
        switch(this.getGamePieceAt(x,y))
        {
            case RED_KING_PIECE:
            case WHITE_KING_PIECE:
                this.pushSlotIfEmpty(x-1, y-1, moves);
                this.pushSlotIfEmpty(x+1, y-1, moves);
                this.pushSlotIfEmpty(x-1, y+1, moves);
                this.pushSlotIfEmpty(x+1, y+1, moves);
                break;
            case WHITE_PIECE:
                this.pushSlotIfEmpty(x-1, y+1, moves);
                this.pushSlotIfEmpty(x+1, y+1, moves);
                break;
            case RED_PIECE:
                this.pushSlotIfEmpty(x-1, y-1, moves);
                this.pushSlotIfEmpty(x+1, y-1, moves);
                break;
            case EMPTY_SLOT:
                throw new CheckerGameException("Don't call Game::addAvailableMovesToEmptySlots() on EMPTY_SLOT.");
        }
    }
    
    private void pushSlotIfEmpty(int x, int y, List<Slot> moves)
    {
        try
        {
            if(this.getGamePieceAt(x,y) == GamePiece.EMPTY_SLOT)
            {
                moves.add(new Slot(x,y));
            }
        }
        catch (IndexOutOfBoundsException doNothing) {}
    }
    
    private boolean pieceHasReachedOppositeEnd(int x, int y) throws CheckerGameException
    {
        switch(this.getGamePieceAt(x,y))
        {
            case RED_PIECE:
            case RED_KING_PIECE:
                return y == 0;

            case WHITE_PIECE:   
            case WHITE_KING_PIECE:
                return y == GRID_SIZE-1;

            case EMPTY_SLOT :
            default:
                throw new CheckerGameException("Don't call Game::pieceHasReachedOppositeEnd on EMPTY_SLOT");
        }
    }
    
    private void promotePiece(int x, int y) throws CheckerGameException 
    {   
        switch(this.getGamePieceAt(x,y))
        {
            case RED_PIECE:     
                this.setGamePieceAt(x, y, GamePiece.RED_KING_PIECE);
                this.listener.onPieceBecameKing(x, y, GamePiece.RED_KING_PIECE);                    
                break;

            case WHITE_PIECE:
                this.setGamePieceAt(x, y, GamePiece.WHITE_KING_PIECE);
                this.listener.onPieceBecameKing(x, y, GamePiece.WHITE_KING_PIECE);

            case RED_KING_PIECE:
            case WHITE_KING_PIECE:
                break;

            case EMPTY_SLOT :
                throw new CheckerGameException("Don't call Game::promotePiece on EMPTY_SLOT");
        }
    }
    
    private boolean pieceBelongsToPlayer(int x, int y, Player player)
    {
        return pieceBelongsToPlayer(this.getGamePieceAt(x, y), player);
    }
    
    private static boolean pieceBelongsToPlayer(GamePiece piece, Player player)
    {
        try
        {
            return piece != GamePiece.EMPTY_SLOT && getPlayerOwningPiece(piece) == player;
        }
        catch (CheckerGameException e)
        {
            return false;
        }
    }
    
    private void setGamePieceAt(int x, int y, GamePiece piece)
    {
        this.grid.get(y).set(x, piece);
    }
    
    private static Player getPlayerOwningPiece(GamePiece piece) throws CheckerGameException
    {
        switch(piece)
        {
            case RED_PIECE:
            case RED_KING_PIECE:
                return Player.PLAYER_RED;
            case WHITE_PIECE:   
            case WHITE_KING_PIECE:
                return Player.PLAYER_WHITE;
            case EMPTY_SLOT :
            default:
                throw new CheckerGame.CheckerGameException("Don't call Game::getPlayerOwningPiece(EMPTY_SLOT)");
        }
    }
    
    private static Player getOpponent(Player player)
    {
        return (player == Player.PLAYER_RED) ? Player.PLAYER_WHITE : Player.PLAYER_RED;
    }
}
