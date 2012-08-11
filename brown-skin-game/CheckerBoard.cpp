#pragma once

#include "CheckerBoard.h"
#include "GL_CheckerBoard.h"



CheckerBoard::CheckerBoard(){}

void CheckerBoard::setOnSlotClikedListener(OnSlotClickedListener* listener){}

void CheckerBoard::movePiece(int xStart, int  yStart, int xEnd, int yEnd){}

void CheckerBoard::destroyPiece(int x, int y){}

void CheckerBoard::initWindow()
{
	char fakeParam[] = "fake";
	char *fakeargv[] = { fakeParam, NULL };
	
	init_view (1, fakeargv);
}

void CheckerBoard::redrawNewBoard(){}

void CheckerBoard::changeViewPosition(){}

void CheckerBoard::moveLightSource(){}

void CheckerBoard::transformPieceToKing(int x, int y){}

void CheckerBoard::highLightSlot(int x, int y){}

void CheckerBoard::unHighLightSlot(int x, int y){}

void CheckerBoard::playPlayerWinAnimation(std::string player){}

void CheckerBoard::playDrawAnimation(){}

void CheckerBoard::playIllegalMoveAnimation(int xStart, int yStart, int xEnd, int yEnd){}

CheckerBoard::~CheckerBoard(void){}