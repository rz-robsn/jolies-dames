#include "CheckerBoard.h"


CheckerBoard::CheckerBoard(Game& game)
{
	this->game = &game;
}


CheckerBoard::~CheckerBoard(void)
{
	delete this->game;
}
