import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.*;

public class MainMenuPane extends GraphicsPane {
	private MainApplication program; // a way to put things on the screen
	Player player = new Player();
	GButton play;
	GImage mainMenu;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	GButton howTo;
	GParagraph howtoPlay = new GParagraph("", 20, 20);
	GButton howToBack;

	// TODO: implement the settings menu object
	// private SettingsMenu settings = new SettingsMenu(program);
	public MainMenuPane(MainApplication app) { // always call this app
		super();
		program = app;
		play = new GButton("GO", 600, 300, 50, 30, Color.CYAN); // this is going to create another button and then
															// filling it in with information
		howTo = new GButton("HOW TO PLAY", 120, 300, 100, 30,Color.GRAY); // instiating, an object is an instance of a clas, initialize the
														// object in the
		howToBack = new GButton("BACK",20,170,50,30);
		
		mainMenu = new GImage("theAGame.png", 150,100);
	}

	@Override
	public void showContents() { // this is like your main method in this class. THis is going to add all the
									// contents to the menu page
		// TODO Auto-generated method stub
		// 800 x 480
		program.add(mainMenu);
		program.add(play);
		program.add(howTo);
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // the size of the applet is:
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(play);
		program.remove(howTo);
		program.remove(howtoPlay);
		program.remove(howToBack);
		program.remove(mainMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == play) {
			hideContents();
			program.switchToLevel();
			// TODO: uncomment this line below
			// program.switchToMenu(SettingsMenu);
		}
		if (obj == howTo) {
			hideContents();
			howtoPlay.setText("Press GO to play. \n\n"
					+ "*** You must click on the program at the beginning to play (we are working on it) ***\n\n"
					+ "Press the corresponding key on the keyboard to the ones on the screen. \n"
					+ "For maximum points "
					+ "press the letter when the outside circle's diameter is the same as \n"
					+ " the inside's.");
			// howtoPlay.add();
			howtoPlay.setFont("Arial-18");
			program.add(howtoPlay);
			program.add(howToBack);
		}
		if (obj == howToBack) {
			hideContents();
			program.switchToMenu();
		}
	}

}
