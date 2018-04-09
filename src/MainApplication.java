import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private SomePane somePane;
	private MainMenuPane menu;
	private Level level;
	private int count;
	
	
	public Timer time;


	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		time = new Timer(10, this);
		level = new Level(this);
		menu = new MainMenuPane(this);
		switchToMenu();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		level.action();
	}
	public void switchToMenu() {
		//playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		//playRandomSound();
		switchToScreen(somePane);
	}
	public void switchToLevel() {
		switchToScreen(level);
		level.run();
		time.start();
	}
	public void playGame() {
		time.start();
	}
	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	// ***member methods***

}
