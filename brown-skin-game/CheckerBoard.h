#pragma once

#include <vector>

#include "OnSlotClickedListener.h"
#include "GameUtilities.h"

class CheckerBoard
{
private:

public:

	CheckerBoard();
	virtual ~CheckerBoard(void);

	/// <summary> Sets this board's onSlotClikedListener. </summary>
	/// <param name="listener"> If non-null, the listener. </param>
	void setOnSlotClikedListener(OnSlotClickedListener* listener);

	/// <summary> Sets the grid. </summary>
	void setGrid(std::vector<std::vector<GamePiece>>* grid);

	/// <summary> Initialises the window and draw board + pieces for first play. </summary>
	void initWindow();

	/// <summary> Highlights the slot at position (x, y) of the board. </summary>
	void highLightSlot(int x, int y);

	/// <summary> un-highlights the slot at position (x, y) of the board. </summary>
	void unHighLightSlot(int x, int y);
};


