#include "Game.h"
using std::vector;

Game::Game(void)
{
	// create new grid
	grid = new vector<vector<GamePiece>>(GRID_SIZE);
	for (int i = 0 ; i < GRID_SIZE ; i++)
	{
		grid->at(i) = vector<GamePiece>(GRID_SIZE);
	}

	// creating the 3 WHITE rows
	//this->initPiecesWithFirstSlotEmpty(*grid[0], )
}


Game::~Game(void)
{
	delete[] grid;
}

void Game::initPiecesWithFirstSlotEmpty(vector<GamePiece>& gridRow, GamePiece piece)
{
	for (int i = 0 ; i < GRID_SIZE ; i++)
	{
		gridRow.at(i) = (i % 2 == 0) ? EMPTY_SLOT : piece;
	}
}

void Game::initPiecesWithFirstSlotContainingPiece(vector<GamePiece>& gridRow, GamePiece piece)
{
	for (int i = 0 ; i < GRID_SIZE ; i++)
	{
		gridRow.at(i) = (i % 2 == 1) ? EMPTY_SLOT : piece;
	}
}