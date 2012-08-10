#pragma once

#include <Windows.h>

#include "Controller.h"

Controller::Controller(void)
{
	this->game = new Game();
	//this->board = new CheckerBoard();
}

void Controller::start()
{
	//this->game->setListener(this);
	//this->board->setOnSlotClikedListener(this);

	this->game->newGame();
}

void Controller::onNewGame(){}

void Controller::onPlayerWin(Player player){}

void Controller::onDraw(){}

void Controller::onPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece){}

void Controller::onPieceEaten(int x, int y, GamePiece gamePiece){}

void Controller::onPieceBecameKing(int x, int y, GamePiece gamePiece){}

void Controller::onIllegalMove(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece){}

void Controller::onPieceCanStillJump(int x, int y, GamePiece gamePiece){}

void Controller::onSlotClicked(int x, int y){}

Controller::~Controller(void)
{
	delete this->game;
}