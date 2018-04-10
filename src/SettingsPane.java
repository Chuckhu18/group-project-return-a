import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
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
private GLabel back;
private GLabel next;
private ArrayList<GButton> difficultyChoices = new ArrayList<GButton>();
private ArrayList<GButton> songChoices = new ArrayList<GButton>();
private String songChoice;
private GLabel settingsHeading;
private int difficultyChoice = 0;

	public SettingsPane(MainApplication app) {
		super();
		program = app;
		
		settingsHeading = new GLabel("SETTINGS",250, 100);
		settingsHeading.setFont("Arial-36");
		
		difficulty = new GLabel("Difficulty: ", 67,225);
		difficulty.setFont("Arial-24");
		easy = new GButton("EASY", 200,208, 100, 25,Color.GREEN);
		medium = new GButton("MEDIUM", 200,208, 100, 25, Color.YELLOW);
		hard = new GButton("HARD", 200,208, 100, 25, Color.RED);
		
		nextDiff = new GButton(">", 305,208,25,25,Color.CYAN);
		prevDiff = new GButton("<", 170, 208, 25, 25, Color.GRAY);
		
		play = new GButton("PLAY", 350, 350, 50, 25, Color.CYAN);
		
		song = new GLabel("Song: ",67,195);
		song.setFont("Arial-24");
		hotelCalifornia = new GButton ("Hotel California", 200, 180, 100,25);
		difficultyChoices.add(easy);
		difficultyChoices.add(medium);
		difficultyChoices.add(hard);
		songChoices.add(hotelCalifornia);
		songChoices.add(gucciGang);
		
		
	}
	
	
	
	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(song);
		program.add(nextDiff);
		program.add(prevDiff);
		program.add(difficulty);
		program.add(settingsHeading);
		program.add(easy);
		program.add(hard);
		program.add(medium);
		program.add(hotelCalifornia);
		program.add(play);
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(song);
		program.remove(difficulty);
		program.remove(settingsHeading);
		program.remove(easy);
		program.remove(medium);
		program.remove(hard);
		program.remove(nextDiff);
		program.remove(prevDiff);
		program.remove(play);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj ==play) {
			hideContents();
			program.switchToLevel();
		}
		if (obj == nextDiff) {
			if (difficultyChoice == 2) {
				difficultyChoice = 0;
			}
			else {
				difficultyChoice++;
			}
		}
		if (obj == prevDiff) {
			if (difficultyChoice == 0)
			{
				difficultyChoice = 2;
			}
			else {
				difficultyChoice--;
			}
		}
		difficultyChoices.get(difficultyChoice).sendToFront();
	}
}
