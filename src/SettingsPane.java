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
	private GButton diffButton;
	
	private GLabel songLabel;
	private GButton nextSong;
	private GButton prevSong;
	private GButton songButton;
	
	private GButton play;
	private GButton back;
	
	private String[] songList = {"Hotel California","To Hell and Back","Video Killed ... Star"};
	private String[] diffList = {"Easy","Medium","Hard"};
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
		
		songLabel = new GLabel("Song: ",67,195);
		songLabel.setFont("Arial-24");
		screenObjects.add(songLabel);
		
		diffButton = new GButton(diffList[Math.abs(difficultyChoice%3)], BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT,Color.GREEN);
		screenObjects.add(diffButton);
		
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
		
		songButton = new GButton(songList[Math.abs(songChoice%3)], BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		screenObjects.add(songButton);
		
		nextSong = new GButton(">", nextDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.CYAN);
		prevSong = new GButton("<", prevDiff.getX(), SONG_Y, BOX_HEIGHT, BOX_HEIGHT, Color.BLACK);
		prevSong.setColor(Color.CYAN);
		screenObjects.add(nextSong);
		screenObjects.add(prevSong);
	}
	
	@Override
	public void showContents() {
		program.add(backRect);
		
		for(GObject obj : screenObjects)
			program.add(obj);
	}

	@Override
	public void hideContents() {
		program.remove(backRect);
		
		for(GObject obj : screenObjects)
			program.remove(obj);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj ==play) {
			program.setDiffChoice(Math.abs(difficultyChoice%3));
			
			switch(Math.abs(songChoice%3)) {
				case 0: program.setSongChoice("hotelCali"); break;
				case 1: program.setSongChoice("ToHellAndBack"); break;
				case 2: program.setSongChoice("VTKTRS"); break;
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
			program.switchToMenu();
		}
		
		switch(Math.abs(difficultyChoice%3)) {
			case 0: diffButton.setFillColor(Color.GREEN); break;
			case 1: diffButton.setFillColor(Color.YELLOW); break;
			case 2: diffButton.setFillColor(Color.RED); break;
		}
		
		songButton.setText(songList[Math.abs(songChoice%3)]);
		diffButton.setText(diffList[Math.abs(difficultyChoice%3)]);
	}
}
