#pragma once

#include <string>

class CheckerBoard
{
private:

public:

	CheckerBoard();
	virtual ~CheckerBoard(void);

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

	/// <summary> Play the animation to play when the passed player has won. </summary>
	/// <param name="player"> The player type. Either equals "RED PLAYER" or "WHITE PLAYER".</param>
	void playPlayerWinAnimation(std::string player);

	/// <summary> Play the animation to play when the piece at position
	///	 (xStart, yStart)
	///	 has illegaly tried to move to position (xEnd, yEnd). </summary>
	void playIllegalMoveAnimation(int xStart, int yStart, int xEnd, int yEnd);

	/// <summary> Sets the function that executes when the user has clicked 
	/// 		  on position (x, y) of the board. </summary>
	void setOnPieceClickedListener(void (*onPieceCliked)(int x, int y));
};


