#pragma once

class BoardListener
{
public:
	/// <summary> Executes when the user(s) has selected
	/// 		  the slot at position (x, y) of the board. </summary>
	virtual void onSlotSelected(int x, int y) = 0;

	/// <summary> Executes when the user(s) requests a new game. </summary>
	virtual void onNewGameRequested() = 0;

	virtual ~BoardListener(void){};
};

