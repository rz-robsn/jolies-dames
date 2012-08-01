#pragma once

#include "GameUtilities.h"
#include "GameListener.h"

/// <summary> 
///		Game class. Implements all the rules of the checker game and fires
///		game events.
///	</summary>
class Game
{
private:
	GameListener* listener;

	GamePiece grid[7][7];
	Player currentPlayer;

public:

	Game(void);

	/// <summary> Starts the Game </summary>
	void start();

	/// <summary> Sets this game's listener. </summary>
	/// <param name="listener"> [in,out] The listener. Does not assume ownership. </param>
	void setListener(GameListener& listener);

	/// <summary> Move the piece from (xStart, yStart) to (xEnd, yEnd).
	/// 		  If the move is illegal, the piece stays at (xStart, yStart). </summary>
	/// <param name="player"> The player who is moving the piece. </param>
	void movePiece(int xStart, int yStart, int xEnd, int yEnd, Player player);

	virtual ~Game(void);
};

