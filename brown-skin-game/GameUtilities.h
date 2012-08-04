#pragma once

typedef enum {
	PLAYER_RED = 0,
	PLAYER_WHITE = 1
} Player;

typedef enum {
	EMPTY_SLOT = 0,
	RED_PIECE = 1,
	WHITE_PIECE = 2,
	RED_KING_PIECE = 3,
	WHITE_KING_PIECE = 4
} GamePiece;

/// <summary> Slot, represents a position on the grid. </summary>
struct Slot
{
	int x;
	int y;

	Slot(){};
	Slot(int x, int y) : x(x), y(y) {};
	bool operator=(Slot* slot)
	{
		return this->x == slot->x && this->y == slot->y;
	};
};