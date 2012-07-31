#pragma once

#include "Game.h"

class CheckerBoard
{
private:
	Game* game;


public:

	/// <summary> Constructor. </summary>
	/// <param name="game"> 
	///		The game object to listen events from. Assumes ownership of this parameter.
	///	</param>
	CheckerBoard(Game& game);
	virtual ~CheckerBoard(void);
};

