#pragma once

#include "CheckerBoard.h"
#include "GL_CheckerBoard.h"

CheckerBoard::CheckerBoard(){}

void CheckerBoard::setOnSlotClikedListener(OnSlotClickedListener* listener)
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

void CheckerBoard::highLightSlot(int x, int y){}

void CheckerBoard::unHighLightSlot(int x, int y){}

CheckerBoard::~CheckerBoard(void){}