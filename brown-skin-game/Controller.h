#pragma once

#include <iostream>
#include "GameListener.h"
#include "Game.h"

class Controller : public GameListener
{
public:
	Controller(void);
	virtual ~Controller(void);

	void start();

	// Inherited from GameListener
	void onNewGame();
	void onPlayerWin(Player player);
	void onDraw();
	void onPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece);
	void onPieceEaten(int x, int y, GamePiece gamePiece);
	void onPieceBecameKing(int x, int y, GamePiece gamePiece);
	void onIllegalMove(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece);
	void onPieceCanStillJump(int x, int y, GamePiece gamePiece);


private:
	Game* game;
};

