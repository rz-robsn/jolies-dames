#pragma once

#include <vector>
#include <list>

#include "GameUtilities.h"
#include "GameListener.h"

using std::vector;
using std::list;

/// <summary> 
///		Game class. Implements all the rules of the checker game and fires
///		game events.
///	</summary>
class Game
{
public:

	Game(void);

	/// <summary> Sets this game's listener. </summary>
	/// <param name="listener"> [in,out] The listener. Does not assume ownership. </param>
	void setListener(GameListener& listener);

	/// <summary> Starts a new Game </summary>
	void newGame();

	/// <summary> Move the piece from (xStart, yStart) to (xEnd, yEnd).
	/// 		  If the move is illegal, the piece stays at (xStart, yStart). </summary>
	/// <param name="player"> The player who is moving the piece. </param>
	void movePiece(int xStart, int yStart, int xEnd, int yEnd, Player player);

	/// <summary> Returns the list of slots where the piece at (x,y) can move to. </summary>
	/// <param name="player"> The player who is moving the piece. </param>
	/// <returns> The available moves for piece. </returns>
	list<Slot> getAvailableMovesForPiece(int x, int y);

	virtual ~Game(void);

private:
	GameListener* listener;

	static const int GRID_SIZE = 8;
	vector<vector<GamePiece>>* grid;
	Player currentPlayer;

	void initPiecesWithFirstSlotEmpty(vector<GamePiece>& gridRow, GamePiece piece);
	void initPiecesWithFirstSlotContainingPiece(vector<GamePiece>& gridRow, GamePiece piece);
	void initRowWithEmptySlot(vector<GamePiece>& gridRow);
	void switchTurn();

	bool moveIsLegal(int xStart, int yStart, int xEnd, int yEnd, Player player);

	bool pieceCanEatEnemyPiece(int x, int y);
	bool pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy, int xEmptySlot, int yEmptySlot);

	bool playerCanStillMoveAPiece(Player player);

	list<Slot> getAllMovesThatEatEnemy(int x, int y);
	void addAvailableMovesToEmptySlots(int x, int y, list<Slot>& moves);	
	void pushSlotIfEmpty(int x, int y, list<Slot>& moves);

	bool pieceBelongsToPlayer(int x, int y, Player player);	
	static bool pieceBelongsToPlayer(GamePiece piece, Player player);

	static Player getPlayerOwningPiece(GamePiece piece);
	static Player getOpponent(Player player);

	GamePiece& getGamePieceAt(int x, int y);
	void setGamePieceAt(int x, int y, GamePiece piece);
};

