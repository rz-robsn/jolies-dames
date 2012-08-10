#pragma once

#include <string>

#include "OnSlotClickedListener.h"

class CheckerBoard
{
private:

public:

	CheckerBoard();
	virtual ~CheckerBoard(void);

	/// <summary> Sets this board's onSlotClikedListener. </summary>
	/// <param name="listener"> If non-null, the listener. </param>
	void setOnSlotClikedListener(OnSlotClickedListener* listener);

	/// <summary> Translates the piece from (xStart, yStart) to (xEnd, yEnd). </summary>
	void movePiece(int xStart, int  yStart, int xEnd, int yEnd);

	/// <summary> Destroys (= eat) the piece at position (x,y) of the board. </summary>
	void destroyPiece(int x, int y);

	/// <summary> Initialises the window and draw board + pieces for first play. </summary>
	void initWindow();

	/// <summary> Removes all pieces from the board and redraws them at their initial position. </summary>
	void redrawNewBoard();

	/// <summary> Change view position. </summary>
	void changeViewPosition();

	/// <summary> Moves light source. </summary>
	void moveLightSource();

	/// <summary> Transforms piece at position (x, y) of the board into a king Piece. </summary>
	void transformPieceToKing(int x, int y);

	/// <summary> Highlights the slot at position (x, y) of the board. </summary>
	void highLightSlot(int x, int y);

	/// <summary> un-highlights the slot at position (x, y) of the board. </summary>
	void unHighLightSlot(int x, int y);

	/// <summary> Play the animation to play when the passed player has won. </summary>
	/// <param name="player"> The player type. Either equals "RED PLAYER" or "WHITE PLAYER".</param>
	void playPlayerWinAnimation(std::string player);

	/// <summary> Play the animation to play when the piece at position
	///	 (xStart, yStart)
	///	 has illegaly tried to move to position (xEnd, yEnd). </summary>
	void playIllegalMoveAnimation(int xStart, int yStart, int xEnd, int yEnd);
};


