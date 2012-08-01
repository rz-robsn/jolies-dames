#include "Game.h"


Game::Game(void)
{
}


Game::~Game(void)
{
	for (int i = 0 ; i < 7 ; i++)
	{
		delete[] grid[i];
	}
	delete[] grid;
}
