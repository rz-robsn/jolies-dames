#include "SoundPlayer.h"
#include "windows.h"
#include "mmsystem.h"



SoundPlayer::SoundPlayer(void)
{
}

void SoundPlayer::playMovePieceSound()
{
	PlaySound(TEXT("131662__bertrof__game-sound-correct-v2.wav"),NULL, SND_LOOP | SND_ASYNC);
}

void SoundPlayer::playIllegalSound()
{
	PlaySound(TEXT("108737__branrainey__boing.wav"),NULL, SND_LOOP | SND_ASYNC);
}
	
void SoundPlayer::playDrawSound()
{
	PlaySound(TEXT("131659__bertrof__game-sound-intro-to-game"),NULL, SND_LOOP | SND_ASYNC);
}

void SoundPlayer::playPieceDestroyedSound()
{
	PlaySound(TEXT("34232__hardpcm__chip116.wav"),NULL, SND_LOOP | SND_ASYNC);
}

void SoundPlayer::playPlayerWinSound(std::string player)
{
	PlaySound(TEXT("86879__milton__winn.wav"),NULL, SND_LOOP | SND_ASYNC);
}

SoundPlayer::~SoundPlayer(void)
{
}
