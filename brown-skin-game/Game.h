#pragma once

typedef enum {
	PLAYER_RED ,
	PLAYER_BLACK
} Player;

/// <summary> 
///		Game class. Implements all the rules of the checker game and fires
///		game events.
///	</summary>
class Game
{
private:
	(void) (*onStart)(void);
	(void) (*onPlayerWin)(Player player);
	(void) (*onPieceMoved)(int xStart, int yStart, int xEnd, int yEnd);
	(void) (*onPieceEaten)(int x, int y);

public:
	Game(void);

	/// <summary> Starts the Game </summary>
	void start();

	/// <summary> Sets the function to execute when the game starts. </summary>
	/// <param name="onStart"> The function to execute when the game is started. </param>
	void setOnStart(void(*onStart)(void));

	/// <summary> Sets the function to execute when a player wins. </summary>
	/// <param name="onPlayerWin">
	/// 	The function to execute when the player passed as parameter wins. 
	/// </param>
	void setOnPlayerWin(void (*onPlayerWin)(Player player));

	/// <summary> Sets the function to execute when a piece has moved. </summary>
	/// <param name="onPieceMoved">
	/// 	The function to execute when the piece at position (xStart, yStart) 
	/// 	has moved to position (xEnd, yEnd).
	/// </param>
	void setOnPieceMoved(void (*onPieceMoved)(int xStart, int yStart, int xEnd, int yEnd));

	/// <summary> Sets the function to execute when a piece has been eaten </summary>
	/// <param name="onPieceEaten"> 
	/// 	The function to execute when the piece at position (x,y) has been eaten.
	/// </param>
	void setOnPieceEaten(void (*onPieceEaten)(int x, int y));

	virtual ~Game(void);
};

