#pragma once

#include <vector>
#include <list>

#include "BoardListener.h"
#include "GameUtilities.h"

class CheckerBoard
{
private:

public:

	CheckerBoard();
	virtual ~CheckerBoard(void);

	/// <summary> Sets this board's onSlotClikedListener. </summary>
	/// <param name="listener"> If non-null, the listener. </param>
	void setOnSlotClikedListener(BoardListener* listener);

	/// <summary> Sets the grid. </summary>
	void setGrid(std::vector<std::vector<GamePiece>>* grid);

	/// <summary> Initialises the window and draw board + pieces for first play. </summary>
	void initWindow();

	/// <summary> Highlights the slot at position (x, y) of the board. </summary>
	void setSlotsToHighLight(std::list<Slot>* slots);
};


