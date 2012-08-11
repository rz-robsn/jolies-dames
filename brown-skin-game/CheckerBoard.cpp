#pragma once

#include "CheckerBoard.h"
#include "GL_CheckerBoard.h"

CheckerBoard::CheckerBoard(){}

void CheckerBoard::setOnSlotClikedListener(BoardListener* listener)
{
	::listener = listener;
}

void CheckerBoard::setGrid(std::vector<std::vector<GamePiece>>* grid)
{
	::grid = grid;
}

void CheckerBoard::initWindow()
{
	char fakeParam[] = "fake";
	char *fakeargv[] = { fakeParam, NULL };
	
	init_view (1, fakeargv);
}

void CheckerBoard::setSlotsToHighLight(std::list<Slot>* slots)
{
	::slotsToHighLight = slots;
}

CheckerBoard::~CheckerBoard(void){}