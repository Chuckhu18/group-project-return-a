import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
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
		//Execute the timer functions from level
		level.action();
		
		/*
		 * These check to see if the player has won or lost
		 * TODO: 
		 * gracefully close Level and send the important
		 * data to the score display screen instead of
		 * instantly closing the game
		 */
		
		// Player has run out of health before end of song
		if(level.getHealth() <= 0) {
			System.out.println("\nYou lost!\nFinal score   : " + level.getScore());
			System.out.println("\n~~Score References~~\nPerfect score : 2700\nAmazing score : 1350");
			System.out.println("Great score   : 675\nGood score    : 270\nBad score     : 27");
			System.exit(0);
		}
		
		// Player has cleared all of the defined circles
		if(level.getHasWon()) {
			System.out.println("\nYou won! Final score : " + level.getScore());
			System.out.println("\n~~Score References~~\nPerfect score : 2700\nAmazing score : 1350");
			System.out.println("Great score   : 675\nGood score    : 270\nBad score     : 27");
			System.exit(0);
		}	
		
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
