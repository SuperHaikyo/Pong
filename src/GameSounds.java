import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

@SuppressWarnings("unused")

public class GameSounds {

	public static void main(String[] args)
	{

	music();
}

	public static void music() 
	{

	AudioPlayer MGP = AudioPlayer.player;
	AudioStream BGM;
	AudioData MD;

	ContinuousAudioDataStream loop = null;

	try {
	/* Here you can pass one flag like repeat == True or False. */
	while(true) {
	/* Read the file every time you want to play. */
	InputStream test = new FileInputStream("/Pong The Return of the Ball/src/music.ogg");
	BGM = new AudioStream(test);
	AudioPlayer.player.start(BGM);
 
	/* Sleep till the length of the file (File Length == 73 seconds.) */
		Thread.sleep(67800);
	}

	} catch (FileNotFoundException e)
	{
		System.out.print(e.toString());
	} catch (Exception error) {
		System.out.print(error.toString());
	}

	MGP.start(loop);
	
	}
}