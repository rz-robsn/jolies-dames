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
}

void Game::setListener(GameListener& listener)
{
	*(this->listener) = listener;
}

void Game::newGame()
{
	// Filling the grid with pieces and empty slots
	this->initPiecesWithFirstSlotContainingPiece(grid->at(0), WHITE_PIECE);
	this->initPiecesWithFirstSlotEmpty(grid->at(1), WHITE_PIECE);
	this->initPiecesWithFirstSlotContainingPiece(grid->at(2), WHITE_PIECE);
	this->initRowWithEmptySlot(grid->at(3));
	this->initRowWithEmptySlot(grid->at(4));
	this->initPiecesWithFirstSlotEmpty(grid->at(5), RED_PIECE);
	this->initPiecesWithFirstSlotContainingPiece(grid->at(6), RED_PIECE);
	this->initPiecesWithFirstSlotEmpty(grid->at(7), RED_PIECE);

	this->currentPlayer = PLAYER_RED;
}

void Game::movePiece(int xStart, int yStart, int xEnd, int yEnd, Player player)
{
	GamePiece movingPiece = this->getGamePieceAt(xStart, yStart);
	if (this->moveIsLegal(xStart, yStart, xEnd, yEnd, player))
	{
		// move the piece
		this->setGamePieceAt(xStart, yStart, EMPTY_SLOT);
		this->setGamePieceAt(xEnd, yEnd, movingPiece);

		// if you jump over a piece, eat it.

		// if the other player cannot move, the current player wins
		// else if the piece can still jump
		//		alert Listener player can still jump.
		//		do nothing (wait for next movePiece call)
		// else
		//      switch player.		
	}
	else
	{
		this->listener->onIllegalMove(xStart, yStart, xEnd, yEnd, movingPiece);
	}
}

list<Slot> Game::getAvailableMovesForPiece(int x, int y, Player player)
{
	return list<Slot>();
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

void Game::switchPlayer()
{
	this->currentPlayer = (currentPlayer == PLAYER_RED) ? PLAYER_WHITE : PLAYER_RED;
}

bool Game::moveIsLegal(int xStart, int yStart, int xEnd, int yEnd, Player player)
{
	if(!Game::pieceBelongsToPlayer(getGamePieceAt(xStart, yStart), player))
	{
		return false;
	} 
	else
	{
		list<Slot> moves = this->getAvailableMovesForPiece(xStart, yStart, player);
		bool moveIsLegal = false;
		for (list<Slot>::iterator it = moves.begin(); it != moves.end(); it++)
		{
			if (it->x == xEnd && it->y == yEnd)
			{
				moveIsLegal = true;
			}
		}
	}	
	// set move is Legal to false if there is no piece that this piece can eat
	// and there is another piece that can eat a piece.
	
}

bool Game::pieceCanEatEnemyPiece(int x, int y)
{
	bool result = false;
		
	switch(this->getGamePieceAt(x,y))
	{
		case RED_KING_PIECE:
		case WHITE_KING_PIECE:			
			result = this->pieceCanEatEnemyPiece(x, y, x-1, y-1)
					|| this->pieceCanEatEnemyPiece(x, y, x+1, y-1);

		case WHITE_PIECE:
		case RED_PIECE:
			result = result
					|| this->pieceCanEatEnemyPiece(x, y, x-1, y+1)
					|| this->pieceCanEatEnemyPiece(x, y, x+1, y+1);
	}

	return result;
}

bool Game::pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy)
{
	GamePiece enemyPiece;
	try
	{
		enemyPiece = Game::getGamePieceAt(xEnemy, yEnemy);
	}
	catch (std::out_of_range e)
	{
		return false;
	}

	return Game::getPlayerOwningPiece(this->getGamePieceAt(x,y)) != Game::getPlayerOwningPiece(enemyPiece);
}

bool Game::pieceBelongsToPlayer(GamePiece piece, Player player)
{
	return piece != EMPTY_SLOT && Game::getPlayerOwningPiece(piece) == player;
}

Player Game::getPlayerOwningPiece(GamePiece piece)
{
	switch(piece)
	{
		case RED_PIECE:
		case RED_KING_PIECE:
			return PLAYER_RED;
		case WHITE_PIECE:	
		case WHITE_KING_PIECE:
			return PLAYER_WHITE;
		case EMPTY_SLOT :
			throw "Don't call Game::getPlayerOwningPiece(EMPTY_SLOT)";
	}
}

GamePiece& Game::getGamePieceAt(int x, int y)
{
	return grid->at(x).at(y);
}

void Game::setGamePieceAt(int x, int y, GamePiece piece)
{
	grid->at(x).at(y) = piece;
}

Game::~Game(void)
{
	delete[] grid;
}