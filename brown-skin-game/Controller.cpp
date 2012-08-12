#pragma once

#include <conio.h>
#include "SoundPlayer.h"
#include "Controller.h"

Controller::Controller(void)
{
	this->game = new Game();
	this->board = new CheckerBoard();
	
	this->previousSlotSelected = Slot(NO_SLOT, NO_SLOT);
	this->slotsToHighLight = new std::list<Slot>();
}

void Controller::start()
{
	this->game->setListener(this);
	this->board->setOnSlotSelectedListener(this);
	
	this->game->newGame();
	this->board->initWindow();
}

void Controller::onNewGame()
{
	this->board->setGrid(this->game->getGrid());
	this->slotsToHighLight->clear();
	this->board->setSlotsToHighLight(this->slotsToHighLight);
}

void Controller::onPlayerWin(Player player)
{
	switch (player)
	{
		case PLAYER_RED:
			//SoundPlayer::playPlayerWinSound("RED PLAYER");
			break;
		case PLAYER_WHITE:
			//SoundPlayer::playPlayerWinSound("WHITE PLAYER");
			break;
	}
}

void Controller::onDraw()
{
	//SoundPlayer::playDrawSound();
}

void Controller::onPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece)
{
	//SoundPlayer::playMovePieceSound();
}

void Controller::onPieceEaten(int x, int y, GamePiece gamePiece)
{
	//SoundPlayer::playPieceDestroyedSound();
}

void Controller::onPieceBecameKing(int x, int y, GamePiece gamePiece)
{
	// Play king piece sound
}

void Controller::onIllegalMove(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece)
{
	//SoundPlayer::playIllegalSound();
	this->slotsToHighLight->clear();
}

void Controller::onPieceCanStillJump(int x, int y, GamePiece gamePiece)
{
	// HighLight possible slots
	list<Slot> moves = game->getAvailableMovesForPiece(x, y);
	this->slotsToHighLight->insert(this->slotsToHighLight->end(), moves.begin(), moves.end());
}

void Controller::onSlotSelected(int x, int y)
{
	this->slotsToHighLight->clear();
	
	if(this->previousSlotSelected.x != NO_SLOT && this->previousSlotSelected.y != NO_SLOT)
	{
		game->movePiece(previousSlotSelected.x, previousSlotSelected.y, x, y);
		this->previousSlotSelected = Slot(NO_SLOT, NO_SLOT);
	}
	else 
	{
		list<Slot> moves = game->getAvailableMovesForPiece(x, y);

		if (moves.size() > 0)
		{
			this->slotsToHighLight->insert(this->slotsToHighLight->end(), moves.begin(), moves.end());
			this->previousSlotSelected = Slot(x, y);
		}
	}
}

void Controller::onNewGameRequested()
{
	this->game->newGame();
}

Controller::~Controller(void)
{
	delete this->game;
	delete this->board;
	delete this->slotsToHighLight;
}