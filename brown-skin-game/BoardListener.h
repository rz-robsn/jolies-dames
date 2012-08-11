#pragma once

class BoardListener
{
public:
	/// <summary> Executes when the user has clicked 
	/// 		  the slot at position (x, y) of the board. </summary>
	virtual void onSlotClicked(int x, int y) = 0;

	virtual ~BoardListener(void){};
};

