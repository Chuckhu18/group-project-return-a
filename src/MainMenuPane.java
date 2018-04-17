import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

import acm.graphics.*;
import acm.program.*;

public class MainMenuPane extends GraphicsPane {
	private MainApplication program; // a way to put things on the screen
	Player player = new Player();
	GImage toSettings;
	GImage mainMenu;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	GImage howTo;
	GParagraph howtoPlay = new GParagraph("", 20, 20);
	GImage howToBack;
	GLabel userInput;

	// TODO: implement the settings menu object
	// private SettingsMenu settings = new SettingsMenu(program);
	public MainMenuPane(MainApplication app) { // always call this app
		super();
		program = app;
		toSettings = new GImage("playbutton.png", 600, 300); // this is going to create another button and then
		toSettings.setSize(toSettings.getWidth()/2, toSettings.getHeight()/2);													// filling it in with information
		howTo = new GImage("howTobutton.png", 120, 300); // instiating, an object is an instance of a clas, initialize the
														// object in the
		howToBack = new GImage("leftArrowbutton copy.png",20,170);
		howToBack.setSize(howToBack.getWidth()/2, howToBack.getHeight()/2);
		howTo.setSize(howTo.getWidth()/2, howTo.getHeight()/2);
		mainMenu = new GImage("LOGO.png", 150,100);
		mainMenu.setSize(mainMenu.getWidth()/5, mainMenu.getHeight()/5);
		userInput = new GLabel("", 50, 30);
		
	}

	@Override
	public void showContents() { // this is like your main method in this class. THis is going to add all the
									// contents to the menu page
		// TODO Auto-generated method stub
		// 800 x 480
		userInput.setLocation(500, 300);
		userInput.addActionListener(program);
		program.add(userInput);
		program.add(mainMenu);
		program.add(toSettings);
		program.add(howTo);
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // the size of the applet is:
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(toSettings);
		program.remove(howTo);
		program.remove(howtoPlay);
		program.remove(howToBack);
		program.remove(mainMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == toSettings) {
			hideContents();
			program.switchToSettings();
			// TODO: uncomment this line below
			// program.switchToMenu(SettingsMenu);
		}
		if (obj == howTo) {
			hideContents();
			howtoPlay.setText("Press GO to play. \n\n"
					+ "*** You must click on the program at the beginning to play (we are working on it) ***\n\n"
					+ "Press the corresponding key on the keyboard to the ones on the screen. \n"
					+ "For maximum points press the letter when the outside circle's diameter is the same as \n"
					+ " the inside's.");
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
