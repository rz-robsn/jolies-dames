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
	this->jumpingPiece = NULL;
}

void Game::setListener(GameListener* listener)
{
	this->listener = listener;
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

	if(this->jumpingPiece != NULL)
	{
		delete this->jumpingPiece ;
	}
	this->jumpingPiece = NULL;

	this->listener->onNewGame();
}

void Game::movePiece(int xStart, int yStart, int xEnd, int yEnd)
{
	GamePiece movingPiece = this->getGamePieceAt(xStart, yStart);
	if (!this->moveIsLegal(xStart, yStart, xEnd, yEnd))
	{
		this->listener->onIllegalMove(xStart, yStart, xEnd, yEnd, movingPiece);
	}
	else
	{
		// move the piece
		this->setGamePieceAt(xStart, yStart, EMPTY_SLOT);
		this->setGamePieceAt(xEnd, yEnd, movingPiece);

		// if the moving piece is jumping over an enemy piece.
		if (std::abs(yEnd - yStart) == 2)
		{
			// eat The piece in between (xStart, yStart) and (xEnd, yEnd)
			int xEnemy = xStart + (xEnd - xStart)/2; 
			int yEnemy = yStart + (yEnd - yStart)/2;

			GamePiece eatenPiece = this->getGamePieceAt(xEnemy, yEnemy);
			this->setGamePieceAt(xEnemy, yEnemy, EMPTY_SLOT);
			this->listener->onPieceEaten(xEnemy, yEnemy, eatenPiece);

			if(this->jumpingPiece != NULL)
			{
				delete this->jumpingPiece ;
			}
			this->jumpingPiece = new Slot(xEnd, yEnd);	
		}
		else if(this->jumpingPiece != NULL)
		{
			delete this->jumpingPiece ;
		} 

		this->listener->onPieceMoved(xStart, yStart, xEnd, yEnd, movingPiece);

		// Promote to king if piece reached the end.
		if (this->pieceHasReachedOppositeEnd(xEnd, yEnd))
		{
			this->promotePiece(xEnd, yEnd);
		}

		// If both players can't move any pieces
		if( !this->playerCanStillMoveAPiece(this->currentPlayer)
			&& !this->playerCanStillMoveAPiece(Game::getOpponent(currentPlayer)))
		{
			this->listener->onDraw();
		}
		else if (!this->playerCanStillMoveAPiece(Game::getOpponent(currentPlayer))) // the other player cannot move
		{
			this->listener->onPlayerWin(this->currentPlayer);
		}
		else if (this->jumpingPiece != NULL
				&& this->pieceCanEatEnemyPiece(xEnd, yEnd, this->currentPlayer)) // the moving piece can chain jumps
		{
			this->listener->onPieceCanStillJump(xEnd, yEnd, movingPiece);
		}
		else 
		{
			this->switchTurn();
		}
	}
}

list<Slot> Game::getAvailableMovesForPiece(int x, int y)
{
	return this->getAvailableMovesForPiece(x, y, this->currentPlayer);
}

list<Slot> Game::getAvailableMovesForPiece(int x, int y, Player player)
{
	if(this->getGamePieceAt(x,y) == EMPTY_SLOT)
	{
		return list<Slot>();
	}	
	else if (this->jumpingPiece != NULL
			&& Game::pieceBelongsToPlayer(getGamePieceAt(jumpingPiece->x, jumpingPiece->y), player)
			&& (this->jumpingPiece->x != x || this->jumpingPiece->y != y))// there is another piece that is chain Jumping
	{
		return list<Slot>();		
	}
	else if(!Game::pieceBelongsToPlayer(getGamePieceAt(x, y), player))
	{
		return list<Slot>(); 
	}	
	else if (!this->pieceCanEatEnemyPiece(x, y))
	{
		// check if there exists another piece that can eat an enemy piece
		// and return empty list if it does (because the player must move that other piece)  
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				if( i != x && j != y
					&& this->pieceCanEatEnemyPiece(i,j)
					&& Game::getPlayerOwningPiece(this->getGamePieceAt(x,y)) == Game::getPlayerOwningPiece(this->getGamePieceAt(i,j)))
				{
					return list<Slot>();
				}
			}
		}
		
		// return all empty slots the piece can move to.
		list<Slot> moves = list<Slot>(4);
		this->addAvailableMovesToEmptySlots(x, y, moves);
		return moves;
	}
	else 
	{
		return this->getAllMovesThatEatEnemy(x, y);
	}
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

void Game::switchTurn()
{
	this->currentPlayer = (currentPlayer == PLAYER_RED) ? PLAYER_WHITE : PLAYER_RED;

	if(this->jumpingPiece != NULL)
	{
		delete this->jumpingPiece ;
	}
	this->jumpingPiece = NULL;
}

bool Game::moveIsLegal(int xStart, int yStart, int xEnd, int yEnd)
{ 
	list<Slot> moves = this->getAvailableMovesForPiece(xStart, yStart);
	bool moveIsLegal = false;
	for (list<Slot>::iterator it = moves.begin(); it != moves.end(); it++)
	{
		if (it->x == xEnd && it->y == yEnd)
		{
			return true;
		}
	}
	return false;
}

bool Game::pieceCanEatEnemyPiece(int x, int y)
{
	return this->pieceCanEatEnemyPiece(x, y, this->currentPlayer);
}

bool Game::pieceCanEatEnemyPiece(int x, int y, Player player)
{
	return (this->getAllMovesThatEatEnemy(x,y).size() > 0);
}

bool Game::pieceCanEatEnemyPiece(int x, int y, int xEnemy, int yEnemy, int xEmptySlot, int yEmptySlot)
{
	try
	{
		bool result = Game::getPlayerOwningPiece(this->getGamePieceAt(x,y)) 
						!= Game::getPlayerOwningPiece(this->getGamePieceAt(xEnemy, yEnemy))
					  && this->getGamePieceAt(xEmptySlot, yEmptySlot) == EMPTY_SLOT;
		return result;
	}
	catch (std::out_of_range e)
	{
		return false;
	}
	catch (const char* msg)
	{
		return false;
	}
}

bool Game::playerCanStillMoveAPiece(Player player)
{
	for (int i = 0; i < GRID_SIZE; i++)
	{
		for (int j = 0; j < GRID_SIZE; j++)
		{
			if (this->pieceBelongsToPlayer(i, j, player)
				&& this->getAvailableMovesForPiece(i, j, player).size() > 0)
			{
				return true;
			}
		}
	}
	return false;
}

list<Slot> Game::getAllMovesThatEatEnemy(int x, int y)
{
	list<Slot> returnList = list<Slot>();
	switch(this->getGamePieceAt(x,y))
	{
		case RED_KING_PIECE:
		case WHITE_KING_PIECE:
			if(this->pieceCanEatEnemyPiece(x, y, x-1, y-1, x-2, y-2))
			{
				returnList.push_back(Slot(x-2, y-2));
			}
			if(this->pieceCanEatEnemyPiece(x, y, x+1, y-1, x+2, y-2))
			{
				returnList.push_back(Slot(x+2, y-2));
			}
			if(this->pieceCanEatEnemyPiece(x, y, x-1, y+1, x-2, y+2))
			{
				returnList.push_back(Slot(x-2, y+2));
			}
			if(this->pieceCanEatEnemyPiece(x, y, x+1, y+1, x+2, y+2))
			{
				returnList.push_back(Slot(x+2, y+2));
			}
			break;
		case RED_PIECE:
			if(this->pieceCanEatEnemyPiece(x, y, x-1, y-1, x-2, y-2))
			{
				returnList.push_back(Slot(x-2, y-2));
			}
			if(this->pieceCanEatEnemyPiece(x, y, x+1, y-1, x+2, y-2))
			{
				returnList.push_back(Slot(x+2, y-2));
			}
			break;
		case WHITE_PIECE:
			if(this->pieceCanEatEnemyPiece(x, y, x-1, y+1, x-2, y+2))
			{
				returnList.push_back(Slot(x-2, y+2));
			}
			if(this->pieceCanEatEnemyPiece(x, y, x+1, y+1, x+2, y+2))
			{
				returnList.push_back(Slot(x+2, y+2));
			}
			break;
		case EMPTY_SLOT:
			break;
	}
	return returnList;
}

void Game::addAvailableMovesToEmptySlots(int x, int y, list<Slot>& moves)
{
	switch(this->getGamePieceAt(x,y))
	{
		case RED_KING_PIECE:
		case WHITE_KING_PIECE:
			this->pushSlotIfEmpty(x-1, y-1, moves);
			this->pushSlotIfEmpty(x+1, y-1, moves);
			this->pushSlotIfEmpty(x-1, y+1, moves);
			this->pushSlotIfEmpty(x+1, y+1, moves);
			break;
		case WHITE_PIECE:
			this->pushSlotIfEmpty(x-1, y+1, moves);
			this->pushSlotIfEmpty(x+1, y+1, moves);
			break;
		case RED_PIECE:
			this->pushSlotIfEmpty(x-1, y-1, moves);
			this->pushSlotIfEmpty(x+1, y-1, moves);
			break;
		case EMPTY_SLOT:
			throw "Don't call Game::addAvailableMovesToEmptySlots() on EMPTY_SLOT.";
	}
}

bool Game::pieceBelongsToPlayer(int x, int y, Player player)
{
	return Game::pieceBelongsToPlayer(this->getGamePieceAt(x, y), player);
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

Player Game::getOpponent(Player player)
{
	return (player == PLAYER_RED) ? PLAYER_WHITE : PLAYER_RED;
}

void Game::pushSlotIfEmpty(int x, int y, list<Slot>& moves)
{
	try
	{
		if(this->getGamePieceAt(x,y) == EMPTY_SLOT)
		{
			moves.push_back(Slot(x,y));
		}
	}
	catch (std::out_of_range doNothing) {}
}

bool Game::pieceHasReachedOppositeEnd(int x, int y)
{
	switch(this->getGamePieceAt(x,y))
	{
		case RED_PIECE:
		case RED_KING_PIECE:
			return y == 0;

		case WHITE_PIECE:	
		case WHITE_KING_PIECE:
			return y == GRID_SIZE-1;

		case EMPTY_SLOT :
			throw "Don't call Game::pieceHasReachedOppositeEnd on EMPTY_SLOT";
	}
}

void Game::promotePiece(int x, int y)
{
	switch(this->getGamePieceAt(x,y))
	{
		case RED_PIECE:		
			this->setGamePieceAt(x, y, RED_KING_PIECE);
			this->listener->onPieceBecameKing(x, y, RED_KING_PIECE);					
			break;

		case WHITE_PIECE:
			this->setGamePieceAt(x, y, WHITE_KING_PIECE);
			this->listener->onPieceBecameKing(x, y, WHITE_KING_PIECE);

		case RED_KING_PIECE:
		case WHITE_KING_PIECE:
			break;

		case EMPTY_SLOT :
			throw "Don't call Game::promotePiece on EMPTY_SLOT";
	}
}

GamePiece& Game::getGamePieceAt(int x, int y)
{
	return grid->at(y).at(x);
}

void Game::setGamePieceAt(int x, int y, GamePiece piece)
{
	grid->at(y).at(x) = piece;
}

Game::~Game(void)
{
	delete[] grid;
}