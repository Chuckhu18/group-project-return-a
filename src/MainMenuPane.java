import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.*;

public class MainMenuPane extends GraphicsPane {
	private MainApplication program; // a way to put things on the screen
	private Player player;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;

	private ArrayList<GObject> screenObjects = new ArrayList<GObject>();
	private GImage howTo;
	private GParagraph howtoPlay;
	private GImage howToBack;
	private GLabel input;
	private GLabel enterName;
	private GImage toSettings;
	private GImage mainMenu;

	public MainMenuPane(MainApplication app) { // always call this app
		super();
		program = app;
		player = new Player();
		player.setName("");
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);
		
		mainMenu = new GImage("LOGO.png", 100,100);
		mainMenu.setSize(mainMenu.getWidth()/5, mainMenu.getHeight()/5);
		screenObjects.add(mainMenu);
		
		howTo = new GImage("howTobutton.png", mainMenu.getX()+50, 300); // instantiating, an object is an instance of a class
		howTo.setSize(howTo.getWidth()/2, howTo.getHeight()/2);
		screenObjects.add(howTo);
		
		toSettings = new GImage("rightArrowbutton.png", 350, 0);
		toSettings.setSize(toSettings.getWidth()/2, toSettings.getHeight()/2);
		toSettings.setLocation(howTo.getX()+howTo.getWidth()+toSettings.getWidth(),howTo.getY()+howTo.getHeight()/2-toSettings.getHeight()/2);
		screenObjects.add(toSettings);

		input = new GLabel("", 0, 0);
		makeSeeable(input);
		input.setLocation(toSettings.getX()+toSettings.getWidth()*1.25, 327);
		screenObjects.add(input);
		
		enterName = new GLabel ("Click to enter name!", input.getX(), input.getY());
		
		String helpText = "Enter your name on the main menu then press the arrow to start playing!\n\n";
		helpText += "You can change your song and difficulty selection on the settings screen.\n";
		helpText += "Higher difficulty songs will feature more complicated input sequences and/or tempo changes.\n\n";
		helpText += "In game, press the letter indicated by the blue circles to gain points!\n";
		helpText += "You gain more points by clicking when the outer circle is as small as possible.\n";
		helpText += "Circles will flash when they are at an ideal time to be pressed, use this to your advantage!\n\n";
		helpText += "Be careful of red circles, as you will lose health if you click them.\n";
		helpText += "You will also lose health if a blue circle disappears before you can click it.\n";
		helpText += "The game will end when you have clicked all of the circles or run out of health.";
		
		howtoPlay = new GParagraph("", 20, 50);
		howtoPlay.setText(helpText);
		howtoPlay.setFont("Arial-18");
		howtoPlay.setColor(Color.white);
		
		howToBack = new GImage("leftArrowbutton copy.png",0,0);
		howToBack.setSize(howToBack.getWidth()/2, howToBack.getHeight()/2);
		howToBack.setLocation(howtoPlay.getX() + howToBack.getWidth()/2, howtoPlay.getY() + howtoPlay.getHeight() + howToBack.getHeight()*2);
		
	}



	@Override
	public void showContents() {
		// Displays all of the objects on the screen
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		program.add(backRect);
		
		for(GObject obj : screenObjects) {
			program.add(obj);
		}
		
		makeSeeable(input);
		
		if(player.getName().length() == 0) {
			program.add(enterName);
			makeSeeable(enterName);
		}
	}

	@Override
	public void hideContents() {
		program.remove(backRect);
		
		for(GObject obj : screenObjects) {
			program.remove(obj);
		}

		program.remove(enterName);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		enterName.setLabel("Enter your name! (8 char max)");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == toSettings) {
			if(player.getName().length()!=0 && player.getName().length() <= 8) {
				program.switchToSettings();
			}
		} else if (obj == howTo) {
			hideContents();
			program.add(backRect);
			program.add(howtoPlay);
			program.add(howToBack);
		} else if (obj == howToBack) {
			program.remove(howtoPlay);
			program.remove(howToBack);
			program.switchToMenu();
		}
	}
	public void keyPressed(KeyEvent e) {
		//No spaces, one word no illegal characters, can't put in symbols 
		String userInput = input.getLabel();

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		
		input.setLabel(userInput);
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
