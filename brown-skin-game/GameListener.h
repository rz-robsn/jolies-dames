#pragma once

#include "GameUtilities.h"

class GameListener
{
public:

	/// <summary> Executes when the game starts. </summary>
	virtual void OnStart() = 0;

	/// <summary> Executes when a player wins. </summary>
	/// <param name="player">
	/// 	The winning player.
	/// </param>
	virtual void OnPlayerWin(Player player) = 0;

	/// <summary> Executes the game ends in a draw. </summary>
	virtual void OnDraw() = 0;

	/// <summary> 
	///  Executes when the piece at position 
	///	 (xStart, yStart)
	///	 has moved to position (xEnd, yEnd). 
	///	</summary>
	/// <param name="gamePiece">
	/// 	The type of piece that moved. Never equals to EMPTY_SLOT.
	/// </param>	
	virtual void OnPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece) = 0;

	/// <summary> Executes when the piece at position (x,y) has been eaten </summary>
	/// <param name="gamePiece">
	/// 	The type of piece that has been eaten. Never equals to EMPTY_SLOT.
	/// </param>
	virtual void OnPieceEaten(int x, int y, GamePiece gamePiece) = 0;

	/// <summary> Executes the piece at position (x,y) becomes king. 
	/// 		  (x,y) are the position of piece after it moved. </summary>
	/// <param name="gamePiece"> 
	/// 	The type of piece that became king. Is either equal to
	/// 	RED_KING_PIECE or WHITE_KING_PIECE.
	/// </param>
	virtual void OnPieceBecameKing(int x, int y, GamePiece gamePiece) = 0;

	virtual ~GameListener() {};
};

