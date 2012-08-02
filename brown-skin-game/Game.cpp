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

	// Filling the grid with pieces and empty slots
	this->initPiecesWithFirstSlotContainingPiece(grid->at(0), WHITE_PIECE);
	this->initPiecesWithFirstSlotEmpty(grid->at(1), WHITE_PIECE);
	this->initPiecesWithFirstSlotContainingPiece(grid->at(2), WHITE_PIECE);
	this->initRowWithEmptySlot(grid->at(3));
	this->initRowWithEmptySlot(grid->at(4));
	this->initPiecesWithFirstSlotEmpty(grid->at(5), RED_PIECE);
	this->initPiecesWithFirstSlotContainingPiece(grid->at(6), RED_PIECE);
	this->initPiecesWithFirstSlotEmpty(grid->at(7), RED_PIECE);
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

void Game::initRowWithEmptySlot(vector<GamePiece>& gridRow)
{
	for (int i = 0 ; i < GRID_SIZE ; i++)
	{
		gridRow.at(i) = EMPTY_SLOT;
	}
}

void Game::setListener(GameListener& listener)
{
	*(this->listener) = listener;
}