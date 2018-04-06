import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.*;
public class MainMenu extends GraphicsPane{
	private MainApplication program; // a way to put things on the screen
	Player player = new Player();
	GButton play;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	GButton howTo;
	GParagraph howtoPlay = new GParagraph("", 400, 240);
	//TODO: implement the settingsmenu object
	//private SettingsMenu settings = new SettingsMenu(program);
	public MainMenu(MainApplication app){ //always call this app
		play = new GButton("GO", 500, 300, 50, 30, Color.CYAN); //this is going to create another button and then filling it in with information
		howTo = new GButton("Play", 300, 200, 50, 30); //instiating, an object is an instance of a clas, initialize the object in the 
	}

	public void run() {
		
	}
	@Override
	public void showContents() { // this is like your main method in this class. THis is going to add all the contents to the menu page 
		// TODO Auto-generated method stub
		//800 x 480
		program.add(play);
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); //the size of the applet is: 
	}
	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(play);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == play) {
			hideContents();
			//TODO: uncomment this line below
			//program.switchToMenu(SettingsMenu);
		}
		if (obj == howTo) {
			hideContents();
			howtoPlay.setText("This is how to play: fill this in in the main menu class");
			//howtoPlay.add();
			program.add(howtoPlay);
		}
	}

}
