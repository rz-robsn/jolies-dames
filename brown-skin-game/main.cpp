#include <iostream>
#include <windows.h>

#include "Controller.h"

int main (int argc, char **argv)
{
	Controller* gameController = new Controller();
	gameController->start();

	return 0;
}
