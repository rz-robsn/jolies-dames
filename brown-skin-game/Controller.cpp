#pragma once

#include <conio.h>

#include "Controller.h"

Controller::Controller(void)
{
	this->game = new Game();
	this->board = new CheckerBoard();
	
	this->previousSlotClicked = Slot(NO_SLOT, NO_SLOT);
	this->previousSlotsHighLighted = list<Slot>();
}

void Controller::start()
{
	this->game->setListener(this);
	this->board->setOnSlotClikedListener(this);
	
	this->game->newGame();
	this->board->initWindow();

	//char input;
	//do
	//{
	//	input = _getch();
	//	switch (input)
	//	{
	//	case 'n':
	//		this->game->newGame();
	//		break;

	//	case 'v':
	//		this->board->changeViewPosition();
	//		break;
	//	}	
	//}
	//while(input != 'q');
}

void Controller::onNewGame()
{
	this->board->setGrid(this->game->getGrid());
}

void Controller::onPlayerWin(Player player)
{
	switch (player)
	{
		case PLAYER_RED:
			this->board->playPlayerWinAnimation("RED PLAYER");
			break;
		case PLAYER_WHITE:
			this->board->playPlayerWinAnimation("WHITE PLAYER");
			break;
	}
}

void Controller::onDraw()
{
	this->board->playDrawAnimation();
	// play draw sound.
}

void Controller::onPieceMoved(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece)
{
	this->board->movePiece(xStart, yStart, xEnd, yEnd);
	// play move piece sound
	
	this->unHighLightSlots(this->previousSlotsHighLighted);
	this->previousSlotsHighLighted = list<Slot>();
}

void Controller::onPieceEaten(int x, int y, GamePiece gamePiece)
{
	this->board->destroyPiece(x, y);

	// play destroy piece sound
}

void Controller::onPieceBecameKing(int x, int y, GamePiece gamePiece)
{
	this->board->transformPieceToKing(x, y);

	// Play king piece sound
}

void Controller::onIllegalMove(int xStart, int yStart, int xEnd, int yEnd, GamePiece gamePiece)
{
	this->board->playIllegalMoveAnimation(xStart, yStart, xEnd, yEnd);
	// Play illegal Sound.

	this->unHighLightSlots(this->previousSlotsHighLighted);
	this->previousSlotsHighLighted = list<Slot>();
}

void Controller::onPieceCanStillJump(int x, int y, GamePiece gamePiece)
{
	this->board->highLightSlot(x, y);

	this->previousSlotsHighLighted.push_back(Slot(x,y));
}

void Controller::onSlotClicked(int x, int y)
{
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
			this->highLightSlots(moves);
			this->previousSlotsHighLighted.insert(previousSlotsHighLighted.end(), moves.begin(), moves.end());
			this->previousSlotClicked = Slot(x, y);		
		}
		else 
		{
			// Play illegal Move sound
		}
	}
}

void Controller::highLightSlots(list<Slot> slots)
{
	for(list<Slot>::iterator it = slots.begin(); it != slots.end(); it++)
	{
		this->board->highLightSlot(it->x, it->y);
	}
}

void Controller::unHighLightSlots(list<Slot> slots)
{
	for(list<Slot>::iterator it = slots.begin(); it != slots.end(); it++)
	{
		this->board->unHighLightSlot(it->x, it->y);
	}
}

Controller::~Controller(void)
{
	delete this->game;
	delete this->board;
}