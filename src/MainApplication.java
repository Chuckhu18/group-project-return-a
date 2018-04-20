import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final String MUSIC_FOLDER = "sounds";

	private MainMenuPane menu;
	private EndOfGamePane end;
	public Level level;
	private SettingsPane settings;
	private int count;
	private GLabel youWin = new GLabel("YOU WIN", 100, 100);
	private GLabel youLose = new GLabel("YOU LOSE", 100, 100);
	public Timer time;
	private String songChoice;
	private int diffChoice;
	private int score = 0;

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
		
		// Player has either won or lost
		if(level.getHealth() <= 0 || level.getHasWon()) {
			time.stop();
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
		switchToScreen(end);
	}
	public void switchToLevel() {
		switchToScreen(level);
	}
	public void playGame() {
		time.start();
	}
 
	// ***member methods***

	
	public String getSongChoice() {
		return songChoice;
	}
	public void setSongChoice(String songChoice) {
		this.songChoice = songChoice;
	}
	public int getDiffChoice() {
		return diffChoice;
	}
	public void setDiffChoice(int diffChoice) {
		this.diffChoice = diffChoice;
	}
	
	public int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	
	public int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s){
		score = s;
	}
}
