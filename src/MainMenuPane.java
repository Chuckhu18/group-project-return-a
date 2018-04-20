import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import acm.graphics.*;
import acm.program.*;
import javafx.scene.text.Font;

public class MainMenuPane extends GraphicsPane {
	private MainApplication program; // a way to put things on the screen
	private Player player = new Player();
	private GImage toSettings;
	private GImage mainMenu;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	private int counter = 0;

	GImage howTo;
	GParagraph howtoPlay = new GParagraph("", 20, 50);

	GImage howToBack;
	GButton scoreBoard;
	GLabel input;
	GLabel enterName;

	public MainMenuPane(MainApplication app) { // always call this app
		super();
		program = app;
		player.setName("");
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);
		
		toSettings = new GImage("rightArrowbutton.png", 350, 310); // this is going to create another button and then

		toSettings.setSize(toSettings.getWidth()/2, toSettings.getHeight()/2);								// filling it in with information

		
		howTo = new GImage("howTobutton.png", 80, 300); // instiating, an object is an instance of a clas, initialize the
		// object in the

		howToBack = new GImage("leftArrowbutton copy.png",20,190);
		howToBack.setSize(howToBack.getWidth()/2, howToBack.getHeight()/2);
		howTo.setSize(howTo.getWidth()/2, howTo.getHeight()/2);
		mainMenu = new GImage("LOGO.png", 100,100);
		mainMenu.setSize(mainMenu.getWidth()/5, mainMenu.getHeight()/5);
		input = new GLabel("", 400, 330);
		enterName = new GLabel ("Enter your name (8 char max)", input.getX(), input.getY());
		//adding score board on the menu
		scoreBoard = new GButton("Score Board", 320, 300, 140, 35);
	}



	@Override
	public void showContents() { // this is like your main method in this class. THis is going to add all the
		// contents to the menu page
		// TODO Auto-generated method stub
		// 800 x 480
		//		userInput.setLocation(500, 300);
		//		userInput.addActionListener(program);
		//		program.add(userInput);
		program.add(backRect);
		program.add(mainMenu);
		program.add(toSettings);
		program.add(howTo);
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // the size of the applet is:

		program.add(input);
		makeSeeable(input);
		program.add(enterName);
		makeSeeable(enterName);
		counter++;

		if(counter > 1) { // If we have been to the screen before
			program.remove(enterName);

		}
		//adds in sequential order

		//program.add(scoreBoard);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(backRect);
		program.remove(toSettings);
		program.remove(howTo);
		program.remove(howtoPlay);
		program.remove(howToBack);
		program.remove(mainMenu);
		program.remove(enterName);
		program.remove(input);

		//program.remove(scoreBoard);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == toSettings) {
			if(player.getName().length()!=0 && player.getName().length() <= 8) {
				program.switchToSettings();
			}
		}
		if (obj == howTo) {
			hideContents();
			program.add(backRect);
			howtoPlay.setText("Enter you name then press the arrow. \n"
					+ "On the settings screen, choose your song and difficulty then press 'PLAY'.\n"
					+ "Once the game starts, press the key on the keyboard that corresponds"
					+ "to the ones on the screen.\n"
					+ "For maximum points press the correct key when the letter turns white.\n\n"
					+ "If a circle is red, it is BAD! If you press on a red circle there is a health penalty.");
			howtoPlay.setFont("Arial-18");
			howtoPlay.setColor(Color.white);
			program.add(howtoPlay);
			program.add(howToBack);
		}
		if (obj == howToBack) {
			hideContents();
			program.switchToMenu();
		}
	}
	public void keyPressed(KeyEvent e) {
		//No spaces, one word no illegal characters, can't put in symbols 
		String userInput = input.getLabel();

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			hideContents();
			program.switchToSettings();

		}
		else if (userInput.length() > 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			userInput = userInput.substring(0, userInput.length() - 1);
			if(userInput.length()==0) program.add(enterName);
		}
		else if(Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) {
				userInput = userInput+e.getKeyChar();
				program.remove(enterName);
			}
		
		input.setLabel(userInput); //look up windows file name restrictions, can you put a slash in the file etc
		player.setName(userInput);
		program.setPlayer(player);
	}
	public String getUserInput() {
		return input.getLabel();
	}

	public Player getPlayer() {
		return player;
	}

	private void makeSeeable(GLabel label) {
		label.setColor(Color.BLACK);
		label.setFont("Courier-24");
	}
}
