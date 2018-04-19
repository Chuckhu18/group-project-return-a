import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private static final int BOX_HEIGHT = 25;
	private static final int BOX_WIDTH = BOX_HEIGHT * 4;
	private static final int BOX_X = 200;
	private static final int DIFF_Y = 208;
	private static final int SONG_Y = 180;
		
	private MainApplication program;
	
	private ArrayList<GObject> screenObjects = new ArrayList<GObject>();
	private GLabel difficulty;
	private GButton nextDiff;
	private GButton prevDiff;
	private GButton easy;
	private GButton medium;
	private GButton hard;
	
	private GLabel song;
	private GButton hotelCalifornia;
	private GButton gucciGang;
	private GButton THAB;
	private GButton nextSong;
	private GButton prevSong;
	
	private GButton play;
	private GButton back;
	
	private ArrayList<GButton> difficultyChoices = new ArrayList<GButton>();
	private ArrayList<GButton> songChoices = new ArrayList<GButton>();
	private GLabel settingsHeading;
	private int difficultyChoice = 99999;
	private int songChoice = 99999;

	public SettingsPane(MainApplication app) {
		super();
		program = app;
		
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);
		
		settingsHeading = new GLabel("SETTINGS", 100, 100);
		settingsHeading.setLocation((program.getWindowWidth() / 2) - (settingsHeading.getWidth()), 100);
		settingsHeading.setFont("Arial-36");
		screenObjects.add(settingsHeading);
		
		difficulty = new GLabel("Difficulty: ", 67,225);
		difficulty.setFont("Arial-24");
		screenObjects.add(difficulty);
		
		song = new GLabel("Song: ",67,195);
		song.setFont("Arial-24");
		screenObjects.add(song);
		
		easy = new GButton("EASY", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT,Color.GREEN);
		medium = new GButton("MEDIUM", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT, Color.YELLOW);
		hard = new GButton("HARD", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT, Color.RED);
		
		nextDiff = new GButton(">", BOX_X + BOX_WIDTH + 5, DIFF_Y,BOX_HEIGHT,BOX_HEIGHT,Color.CYAN);
		prevDiff = new GButton("<", BOX_X - BOX_HEIGHT - 5, DIFF_Y, BOX_HEIGHT, BOX_HEIGHT, Color.BLACK);
		prevDiff.setColor(Color.CYAN);
		screenObjects.add(nextDiff);
		screenObjects.add(prevDiff);
		
		play = new GButton("PLAY", 100, 100, BOX_WIDTH/2, BOX_HEIGHT, Color.CYAN);
		back = new GButton("BACK", 100, 100, BOX_WIDTH/2, BOX_HEIGHT, Color.BLACK);
		play.setLocation((program.getWindowWidth()/2) + BOX_WIDTH/2, 400);
		back.setLocation((program.getWindowWidth()/2) - BOX_WIDTH/2, 400);
		back.setColor(Color.CYAN);
		screenObjects.add(play);
		screenObjects.add(back);
		
		hotelCalifornia = new GButton ("Hotel California", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		gucciGang = new GButton ("Gucci Gang", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		THAB = new GButton("To Hell and Back", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		
		nextSong = new GButton(">", nextDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.CYAN);
		prevSong = new GButton("<", prevDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.BLACK);
		prevSong.setColor(Color.CYAN);
		screenObjects.add(nextSong);
		screenObjects.add(prevSong);
		
		difficultyChoices.add(easy);
		difficultyChoices.add(medium);
		difficultyChoices.add(hard);
		
		songChoices.add(hotelCalifornia);
		songChoices.add(THAB);
		songChoices.add(gucciGang);
	}
	
	@Override
	public void showContents() {
		for(GObject obj : screenObjects)
			program.add(obj);
		
		for(GObject obj : difficultyChoices)
			program.add(obj);
		
		for(GObject obj : songChoices)
			program.add(obj);
	
		difficultyChoices.get(Math.abs(difficultyChoice%3)).sendToFront();
		songChoices.get(Math.abs(songChoice%3)).sendToFront();
	}

	@Override
	public void hideContents() {
		for(GObject obj : screenObjects)
			program.remove(obj);

		for(GObject obj : difficultyChoices)
			program.remove(obj);
		
		for(GObject obj : songChoices)
			program.remove(obj);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj ==play) {
			hideContents();
			program.setDiffChoice(Math.abs(difficultyChoice%3));
			
			switch(Math.abs(songChoice%3)) {
				case 0: program.setSongChoice("hotelCali"); break;
				case 1: program.setSongChoice("ToHellAndBack"); break;
				case 2: program.setSongChoice("gucciGang"); break;
			}
			
			program.switchToLevel();
		}
		if (obj == nextDiff) {
			difficultyChoice++;
		}
		if (obj == prevDiff) {
			difficultyChoice--;
		}
		if (obj == nextSong) {
			songChoice++;
		}
		if (obj == prevSong) {
			songChoice--;
		}
		if (obj == back) {
			hideContents();
			program.switchToMenu();
		}
		difficultyChoices.get(Math.abs(difficultyChoice%3)).sendToFront();
		songChoices.get(Math.abs(songChoice%3)).sendToFront();
	}
}
