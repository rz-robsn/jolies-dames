#pragma once

#include <conio.h>
#include "SoundPlayer.h"
#include "Controller.h"

Controller::Controller(void)
{
	this->game = new Game();
	this->board = new CheckerBoard();
	
	this->previousSlotClicked = Slot(NO_SLOT, NO_SLOT);
	this->slotsToHighLight = NULL;
}

void Controller::start()
{
	this->game->setListener(this);
	this->board->setOnSlotClikedListener(this);
	
	this->game->newGame();
	this->board->initWindow();
}

void Controller::onNewGame()
{
	this->board->setGrid(this->game->getGrid());

	if(this->slotsToHighLight != NULL)
	{
		delete this->slotsToHighLight;
	}
	this->slotsToHighLight = new std::list<Slot>();
	this->board->setSlotsToHighLight(this->slotsToHighLight);
}

void Controller::onPlayerWin(Player player)
{
	switch (player)
	{
		case PLAYER_RED:
			break;
		case PLAYER_WHITE:
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
	
	// highlite possible moves
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
}

void Controller::onPieceCanStillJump(int x, int y, GamePiece gamePiece)
{
	// HighLight possible slots
}

void Controller::onSlotClicked(int x, int y)
{
	// Reset slots to none.
	this->slotsToHighLight->clear();
	
	if(this->previousSlotClicked.x != NO_SLOT && this->previousSlotClicked.y != NO_SLOT)
	{
		game->movePiece(previousSlotClicked.x, previousSlotClicked.y, x, y);
		this->previousSlotClicked = Slot(NO_SLOT, NO_SLOT);
	}
	else 
	{
		list<Slot> moves = game->getAvailableMovesForPiece(x, y);

		if (moves.size() > 0)
		{
			this->slotsToHighLight->insert(this->slotsToHighLight->end(), moves.begin(), moves.end());
		}
		else 
		{
			//SoundPlayer::playIllegalSound();
		}
	}
}

Controller::~Controller(void)
{
	delete this->game;
	delete this->board;
}