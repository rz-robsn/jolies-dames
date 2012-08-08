#include "Controller.h"

Controller::Controller(void)
{
}

Controller::~Controller(void)
{
}

 void GameListener::onNewGame() {};

 void GameListener::onPlayerWin(Player player) {};

 void GameListener::onDraw() {};

 void GameListener::onPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece) {};

 void GameListener::onPieceEaten(int x, int y, GamePiece gamePiece) {};

 void GameListener::onPieceBecameKing(int x, int y, GamePiece gamePiece) {};

 void GameListener::onIllegalMove(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece) {};

 void GameListener::onPieceCanStillJump(int x, int y, GamePiece gamePiece) {};
