#pragma once

#include <iostream>
#include "GameListener.h"
#include "Game.h"

class Controller : GameListener
{
public:
	Controller(void);
	virtual ~Controller(void);

	void start();

private:
	Game* game;
};

