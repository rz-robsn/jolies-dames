#include "CheckerBoard.h"


CheckerBoard::CheckerBoard(Game& game)
{
	this->game = &game;
	game.setListener(*this);
}


CheckerBoard::~CheckerBoard(void)
{
	delete this->game;
}
