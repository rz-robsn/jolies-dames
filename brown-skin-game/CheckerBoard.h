#pragma once

#include "Game.h"
#include "GameListener.h"

class CheckerBoard : GameListener
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

