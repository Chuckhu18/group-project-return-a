import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final String MUSIC_FOLDER = "sounds";

	private MainMenuPane menu;
	private Player player;
	private AudioPlayer audioPlayer;

	private EndOfGamePane end;
	public Level level;
	private SettingsPane settings;
	public Timer time;
	private String songChoice;
	private String audioPath = "sounds/";
	private String audioFilename;
	private int diffChoice;
	private int score = 0;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		audioPlayer = AudioPlayer.getInstance();
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
		switchToScreen(menu); //should be main changing for tests
	}
	
	public void switchToSettings() {
		switchToScreen(settings);
	}
	public void switchToEndofGame() {
		switchToScreen(end);
	}
	public void switchToLevel() {
		audioFilename = songChoice+".mp3";
		switchToScreen(level);
	}
	public void playGame() {
		time.start();
	}

	public String getSongChoice() {
		return songChoice;
	}
	public void setSongChoice(String songChoice) {
		this.songChoice = songChoice;
	}
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
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
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	public void startAudioFile() {
		audioPlayer.playSound(audioPath, audioFilename);
	}

	public void pauseAudio() {
		level.setPaused(true);
		audioPlayer.pauseSound(audioPath, audioFilename);
	}
	
	public void stopAudio() {
		audioPlayer.stopSound(audioPath, audioFilename);
	}

	public void resumeAudio() {
		level.setPaused(false);
		audioPlayer.playSound(audioPath, audioFilename);
	}
}
