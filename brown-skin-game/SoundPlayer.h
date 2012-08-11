#pragma once

#include <string>

class SoundPlayer
{
public:
	SoundPlayer(void);

	/// <summary> Play move piece sound. </summary>
	static void playMovePieceSound();

	/// <summary> Play illegal sound. </summary>
	static void playIllegalSound();

	/// <summary> Play draw sound. </summary>
	static void playDrawSound();

	/// <summary> Play piece destroyed sound. </summary>
	static void playPieceDestroyedSound();

	/// <summary> Play player window sound. </summary>
	/// <param name="player"> The player type. Either equals "RED PLAYER" or "WHITE PLAYER".</param>
	static void playPlayerWinSound(std::string player);

	virtual ~SoundPlayer(void);
};

