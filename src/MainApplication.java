import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private MainMenuPane menu;
	private EndOfGamePane end;
	private Level level;
	private SettingsPane settings;
	private int count;
	private GLabel youWin = new GLabel("YOU WIN", 100, 100);
	private GLabel youLose = new GLabel("YOU LOSE", 100, 100);
	public Timer time;
	
	


	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		youWin.setColor(Color.PINK);
		youWin.setFont("Arial-32");
		youLose.setColor(Color.CYAN);
		youLose.setFont("Arial-32");
		settings = new SettingsPane(this);
		time = new Timer(10, this);
		level = new Level(this);
		menu = new MainMenuPane(this);
		end = new EndOfGamePane(this);
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
			switchToEndofGame();
		}
		
		// Player has cleared all of the defined circles
		if(level.getHasWon()) {

			time.stop();
			System.out.println("\nYou won! Final score : " + level.getScore());
			System.out.println("\n~~Score References~~\nPerfect score : 2700\nAmazing score : 1350");
			System.out.println("Great score   : 675\nGood score    : 270\nBad score     : 27");
			add(youWin);
			youWin.sendToFront();
			switchToEndofGame();
		}	
		
	}
	public void switchToMenu() {
		//playRandomSound();
		count++;
		switchToScreen(menu); //should be main changing for tests
		//switchToScreen(end);//test for EndOfGamePane class
	}
	
	public void switchToSettings() {
		switchToScreen(settings);
	}
	public void switchToEndofGame() {
		level.pauseAudio();
		switchToScreen(end);
	}
	public void switchToLevel() {
		switchToScreen(level);
		level.run();
		time.restart();
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
