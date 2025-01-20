import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Audio {
	private Sound sound;

	
	public Audio(String file) {
		try {
			sound = new Sound("res/audio/"+file+"");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Audio(String file, boolean isLooped, float volume) {
		try {
			sound = new Sound("res/audio/"+file+"");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		sound.loop(1.0f, volume);
		
	}
	
	public void play() {
		sound.play();
	}
	
	public void play(float vol) {
		sound.play(1.0f, vol);
	}
	
	public void play(float pitch, float vol) {
		sound.play(pitch, vol);
	}
	
	public void stop() {
		sound.stop();
	}
	
}
