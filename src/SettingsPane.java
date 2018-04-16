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
private GButton nextDiff;
private GButton prevDiff;
private GButton play;
private GLabel song;
private GLabel difficulty;
private GButton easy;
private GButton medium;
private GButton hard;
private GButton hotelCalifornia;
private GButton gucciGang;
private GButton THAB;
private GButton nextSong;
private GButton prevSong;
private ArrayList<GButton> difficultyChoices = new ArrayList<GButton>();
private ArrayList<GButton> songChoices = new ArrayList<GButton>();
private GLabel settingsHeading;
private GButton back;
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
		difficulty = new GLabel("Difficulty: ", 67,225);
		difficulty.setFont("Arial-24");
		song = new GLabel("Song: ",67,195);
		song.setFont("Arial-24");
		
		
		easy = new GButton("EASY", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT,Color.GREEN);
		medium = new GButton("MEDIUM", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT, Color.YELLOW);
		hard = new GButton("HARD", BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT, Color.RED);
		
		nextDiff = new GButton(">", BOX_X + BOX_WIDTH + 5, DIFF_Y,BOX_HEIGHT,BOX_HEIGHT,Color.CYAN);
		prevDiff = new GButton("<", BOX_X - BOX_HEIGHT - 5, DIFF_Y, BOX_HEIGHT, BOX_HEIGHT, Color.BLACK);
		prevDiff.setColor(Color.CYAN);
		
		play = new GButton("PLAY", 100, 100, BOX_WIDTH/2, BOX_HEIGHT, Color.CYAN);
		back = new GButton("BACK", 100, 100, BOX_WIDTH/2, BOX_HEIGHT, Color.BLACK);
		play.setLocation((program.getWindowWidth()/2) + BOX_WIDTH/2, 400);
		back.setLocation((program.getWindowWidth()/2) - BOX_WIDTH/2, 400);
		back.setColor(Color.CYAN);
		
		hotelCalifornia = new GButton ("Hotel California", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		gucciGang = new GButton ("Gucci Gang", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		THAB = new GButton("To Hell and Back", BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		
		nextSong = new GButton(">", nextDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.CYAN);
		prevSong = new GButton("<", prevDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.BLACK);
		prevSong.setColor(Color.CYAN);
		
		difficultyChoices.add(easy);
		difficultyChoices.add(medium);
		difficultyChoices.add(hard);
		songChoices.add(hotelCalifornia);
		songChoices.add(THAB);
		songChoices.add(gucciGang);
	}
	
	
	
	@Override
	public void showContents() {
		program.add(backRect);
		program.add(settingsHeading);
		program.add(song);
		program.add(difficulty);
		
		program.add(nextDiff);
		program.add(prevDiff);
		program.add(hard);
		program.add(medium);
		program.add(easy);
		
		program.add(nextSong);
		program.add(prevSong);
		program.add(THAB);
		program.add(gucciGang);
		program.add(hotelCalifornia);
		
		program.add(play);
		program.add(back);
	}

	@Override
	public void hideContents() {
		program.remove(backRect);
		program.remove(settingsHeading);
		program.remove(song);
		program.remove(difficulty);
		
		program.remove(nextDiff);
		program.remove(prevDiff);
		program.remove(hard);
		program.remove(medium);
		program.remove(easy);
		
		program.remove(nextSong);
		program.remove(prevSong);
		program.remove(THAB);
		program.remove(gucciGang);
		program.remove(hotelCalifornia);
		
		program.remove(play);
		program.remove(back);
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
