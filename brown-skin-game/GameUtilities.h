#pragma once

typedef enum {
	PLAYER_RED ,
	PLAYER_WHITE
} Player;

typedef enum {
	EMPTY_SLOT,
	RED_PIECE ,
	WHITE_PIECE,
	RED_KING_PIECE,
	WHITE_KING_PIECE
} GamePiece;

/// <summary> Slot, represents a position on the grid. </summary>
struct Slot
{
	int x;
	int y;

	Slot(int x, int y) : x(x), y(y) {};
	bool operator=(Slot* slot)
	{
		return this->x == slot->x && this->y == slot->y;
	}
};