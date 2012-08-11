#pragma once

#include "OnSlotClickedListener.h"
#include "GameUtilities.h"
#include <vector>

extern OnSlotClickedListener* listener;
extern std::vector<std::vector<GamePiece>>* grid;

// ----------------------------------------------------------
// GL Functions
// ----------------------------------------------------------

void resize_window (int w, int h);

void draw_light ();

void draw_board ();

// Function for drawing a red piece
void draw_red_piece (int x, int y);

// Function for drawing a white piece
void draw_white_piece (int x, int y);

// Function for drawing a red king
void draw_red_king (int x, int y);

// Function for drawing a white king
void draw_white_king (int x, int y);

// Sets the pieces at their starting positions on the board
void set_pieces ();

// ----------------------------------------------------------
// Input Functions
// ----------------------------------------------------------

// Enables mouse buttons for input
void mouse_click (int button, int state, int x, int y);

// Enables Function keys for input
void special_keys (int key, int x, int y);

// ----------------------------------------------------------
// Display Function
// ----------------------------------------------------------
void display ();

// ----------------------------------------------------------
// Animation Function
// ----------------------------------------------------------
void animate();

// ----------------------------------------------------------
// Main Function
// ----------------------------------------------------------
int init_view (int argc, char *argv[]);