import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	private static final int BOX_HEIGHT = 25;
	private static final int BOX_WIDTH = BOX_HEIGHT * 4;
	private static final int BOX_X = 350;
	private static final int DIFF_Y = 208;
	private static final int SONG_Y = 180;
		
	private MainApplication program;
	
	private ArrayList<GObject> screenObjects = new ArrayList<GObject>();
	private GLabel difficulty;
	private GImage nextDiff;
	private GImage prevDiff;
	private GButton diffButton;
	
	private GLabel songLabel;
	private GImage nextSong;
	private GImage prevSong;
	private GButton songButton;
	
	private GImage play;
	private GImage back;
	
	private String[] songList = {"Hotel California","To Hell and Back","Video Killed ... Star"};
	private String[] diffList = {"Easy","Medium","Hard"};
	private GLabel settingsHeading;
	private int difficultyChoice = 99999;
	private int songChoice = 99999;
	
	private ArrayList<GImage> selButtons;

	public SettingsPane(MainApplication app) {
		super();
		program = app;
		
		selButtons = new ArrayList<GImage>();
		
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);
		
		settingsHeading = new GLabel("SETTINGS", 100, 100);
		settingsHeading.setFont("Arial-36");
		settingsHeading.setLocation((program.getWindowWidth() / 2) - (settingsHeading.getWidth()/2), 100);
		settingsHeading.setColor(Color.white);
		screenObjects.add(settingsHeading);
		
		difficulty = new GLabel("Difficulty: ", 175,225);
		difficulty.setFont("Arial-24");
		difficulty.setColor(Color.WHITE);
		screenObjects.add(difficulty);
		
		songLabel = new GLabel("Song: ",175,195);
		songLabel.setFont("Arial-24");
		songLabel.setColor(Color.white);
		screenObjects.add(songLabel);
		
		diffButton = new GButton(diffList[Math.abs(difficultyChoice%3)], BOX_X, DIFF_Y, BOX_WIDTH, BOX_HEIGHT,Color.GREEN);
		screenObjects.add(diffButton);
		
		nextDiff = new GImage("rightArrowbutton.png", BOX_X + BOX_WIDTH + 5, DIFF_Y);
		prevDiff = new GImage("leftArrowbutton copy.png", BOX_X - BOX_HEIGHT - 5, DIFF_Y);
		selButtons.add(nextDiff);
		selButtons.add(prevDiff);
		
		screenObjects.add(nextDiff);
		screenObjects.add(prevDiff);
		
		play = new GImage("playbutton.png", 100, 100);
		back = new GImage("leftArrowbutton copy.png", 100, 100);
		
		back.setLocation((program.getWindowWidth()/2) -100, 350);
		play.setLocation(back.getX()+back.getWidth()/2+25, back.getY() - play.getHeight()/2+25);
		play.setSize(play.getWidth()/2, play.getHeight());
		back.setSize(play.getHeight(), play.getHeight()/1.25);
		
		screenObjects.add(play);
		screenObjects.add(back);
		
		songButton = new GButton(songList[Math.abs(songChoice%3)], BOX_X, SONG_Y, BOX_WIDTH, BOX_HEIGHT);
		screenObjects.add(songButton);
		
		nextSong = new GImage("rightArrowbutton.png", nextDiff.getX(), SONG_Y);
		prevSong = new GImage("leftArrowbutton copy.png", prevDiff.getX(), SONG_Y);
		selButtons.add(nextSong);
		selButtons.add(prevSong);
		screenObjects.add(nextSong);
		screenObjects.add(prevSong);
		
		for(GImage image : selButtons) {
			image.setSize(image.getWidth()/2, image.getHeight()/2);
			image.move(-5, 0);
		}
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
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			program.setDiffChoice(Math.abs(difficultyChoice%3));
			
			switch(Math.abs(songChoice%3)) {
				case 0: program.setSongChoice("hotelCali"); break;
				case 1: program.setSongChoice("ToHellAndBack"); break;
				case 2: program.setSongChoice("VTKTRS"); break;
			}
			
			program.switchToLevel();
		}
		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.switchToMenu();
		}
	}
}
