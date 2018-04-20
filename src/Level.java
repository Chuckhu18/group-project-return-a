import java.awt.event.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.*;

public class Level extends GraphicsPane {

	// Screen variables
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;

	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 100;
	private static final int PAUSE_B_X = 235;
	private static final int PAUSE_B_Y = 300;

	// System variables
	private int numTicks; // How many timer ticks have gone by
	private MainApplication program;
	private Random rand;
	private boolean isPaused;
	private boolean hasWon = false; // is set to true when the player has won the game
	private int vicCount; // Used to stop the game from immediately ending after last circle

	// Player-related variables
	public static final int MAX_HEALTH = 10000; // Never set to less than 2000 or casual HP loss breaks
	private int health;
	private int score;

	// Song and circle variables
	private Song song;
	private ArrayList<Circle> circles; // Stores the circles being displayed on the screen
	private ArrayList<Character> characters; // Stores the characters to feed into the circles
	private int circleCount; // counts how many circles have spawned since the last important event
	private double tempo; // How often to spawn circles
	private ArrayList<Integer> tempoChangeTimes; // times to change tempo
	private ArrayList<Integer> tempoChangeValues; // how much to change tempo by
	private double nextCircleSpawn; // saves when to spawn the next circle

	// Used to load song
	private String filename;
	private String diffNum;

	// UI elements
	private ArrayList<GObject> uiObjects;
	private GLabel scoreLabel; // holds the score for now
	private GRect healthBar; // displays HP percentage
	private GRect emptyHPBar; // Empty HP Bar to show full
	private GButton pause; // Pause button in-game

	// Pause menu objects
	private ArrayList<GObject> pauseObjects = new ArrayList<GObject>();
	private GLabel paused;
	private GButton cont;
	private GButton restart;
	private GButton change;

	// Used for circle generation to prevent overlapping
	private ArrayList<Double> lastX = new ArrayList<Double>();
	private ArrayList<Double> lastY = new ArrayList<Double>();

	// Used to generate circles inside of bounds
	private double rangeMin = 0.9;
	private double rangeMax = 0.2;

	public Level(MainApplication app) {
		super();
		program = app;
		// audioPlayer = program.getAudioPlayer();
	}

	@Override
	public void showContents() {
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		rand = new Random();

		// Initialize counters
		score = 0;
		vicCount = 0;
		numTicks = 0;
		health = MAX_HEALTH;
		hasWon = false;

		// Load filename and difficulty
		filename = program.getSongChoice();
		diffNum = Integer.toString(program.getDiffChoice() + 1);

		// Create pause menu objects
		initializePauseMenu();
		isPaused = false;

		// Create UI element objects
		initializeUI();

		program.add(backRect); // Adds the background rectangle

		// Adds UI elements to the screen
		for (GObject obj : uiObjects)
			program.add(obj);

		// loads file for song we want to play
		song = new Song(filename + diffNum);

		// Use the information from the song we chose to create gameplay elements
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters
		tempoChangeTimes = song.getTempoChangeTimes();
		tempoChangeValues = song.getTempoChangeValues();
		tempo = song.getTempo();
		nextCircleSpawn = song.getStartDelay();
		createCharArrList(song.getCircleList());

		// start audio and timer
		// audioPlayer = program.getAudioPlayer();
		program.time.start();

		program.startAudioFile();
	}

	@Override
	public void hideContents() {
		program.remove(backRect);

		// Clear the pause menu if it was open
		for (GObject obj : pauseObjects)
			program.remove(obj);

		// Clear the UI elements
		for (GObject obj : uiObjects)
			program.remove(obj);

		// Remove any circles left on the screen
		for (Circle circle : circles) {
			circle.removeCircles();
			circle.removeLabel();
		}

		program.time.stop(); // Stop the timer
		program.stopAudio(); // Stop the audio
		program.setScore(score);

	}

	@Override
	public void keyTyped(KeyEvent e) { // using keyTyped to help ensure valid input
		boolean found = false; // tracks if we have found a matching circle

		String text = "";
		Color cirColor = Color.BLACK;

		if ((e.getKeyChar() == KeyEvent.VK_SPACE) && !isPaused) {
			pauseGame();
			found = true;
		}

		if ((e.getKeyChar() == KeyEvent.VK_ESCAPE) && isPaused) {
			unPauseGame();
			found = true;
		}

		// Iterate through all circles on the screen
		for (Circle circle : circles) {
			// if you pressed a key matching a circle who is still shrinking
			if (e.getKeyChar() == circle.getLetter() && circle.getRemoveCounter() == 0) {
				// Add the text displaying that you pressed it right
				// Pick which text based on how small the outer circle was

				double size = circle.getOutSize(); // store outer size to a variable for efficiency
				double init = song.getCircleSize(); // store initial size for math

				// Note: all numbers are subject to change
				if (circle.isGood()) { // Do this if the match is good
					if (size <= (init / 100)) { // If you press in the last hundredth of the timer
						text = "PERFECT!";
						cirColor = Color.WHITE;
						score += 100;
						health += MAX_HEALTH / 5;
					} else if (size <= (init / 10)) { // If you press between 9/10 and 99/100
						text = "AMAZING!";
						cirColor = Color.CYAN;
						score += 50;
						health += MAX_HEALTH / 10;
					} else if (size <= (init / 5)) { // If you press between 4/5 and 9/10
						text = "GREAT";
						cirColor = Color.GREEN;
						score += 25;
						health += MAX_HEALTH / 15;
					} else if (size <= (init / 2)) { // If you press between 1/2 and 4/5
						text = "GOOD";
						cirColor = Color.YELLOW;
						score += 5;
						health += MAX_HEALTH / 75;

					} else { // If you press in the first half of the timer
						text = "OK";
						cirColor = Color.ORANGE;
						score += 1;
						health += MAX_HEALTH / 250;
					}
				} // End of good circle code
				else { // You clicked on a "bad" circle
					text = "BAD";
					cirColor = Color.RED;
					health -= MAX_HEALTH / 15;
				}

				// hide the GOvals after updating the label
				circle.updateLabel(text, cirColor);

				found = true; // Remember that we found the circle
			} else if (!Character.isLetter(e.getKeyChar())) {
				found = true; // Don't punish the player for pressing a non-letter key
			}
		}

		if (!found) { // if match was found
			if (vicCount == 0)
				health -= MAX_HEALTH / 20; // Take health off if the game is still running
		}
	}// keyTyped

	@Override
	public void mousePressed(MouseEvent e) {

		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == pause) {
			pauseGame();
		}
		if (obj == cont) {
			unPauseGame();
		}
		if (obj == restart) {
			program.restartLevel();
		}
		if (obj == change) {
			program.switchToSettings();
		}
	}// mousePressed

	/**
	 * Timer function, executed every time the timer ticks
	 */
	public void action() {
		numTicks++;

		// Constantly reset window size
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		// Create a new circle every interval of time specified by the song's Tempo
		if (numTicks == Math.floor(nextCircleSpawn)) {
			createCircle(); // Make a new circle
			circleCount++;
			nextCircleSpawn += tempo;
		}

		// If there are tempo changes ahead in the song
		if (tempoChangeTimes.size() > 0 && tempoChangeValues.size() > 0) {
			if (circleCount == tempoChangeTimes.get(0)) {
				if (tempoChangeValues.get(0) > 0) // If the tempo change is positive
					tempo = tempo / tempoChangeValues.remove(0);
				else
					tempo = tempo * Math.abs(tempoChangeValues.remove(0));

				tempoChangeTimes.remove(0);
				circleCount = 0; // reset the count for the next change
			}
		}

		// If there are circles on the screen
		if (circles.size() >= 1) {
			// Have to use manual definition of for loop to avoid
			// ConcurrentModificationException
			for (int i = 0; i < circles.size(); i++) {

				// Shrink circle
				Circle circle = circles.get(i);
				circle.shrink();

				// Change the color of the circle when you are in "AMAZING" and "PERFECT" range
				if (circle.getRemoveCounter() == 0) { // If the circle has not been removed from the screen
					circle.updateColor(song.getCircleSize());
				}

				// If circles have shrunk to be the same size in and out
				if (circle.getOutSize() < 0) {
					if (circle.getRemoveCounter() == 0) { // If we have not started removing it yet
						if (circle.isGood()) { // If it is a good circle
							circle.updateLabel("MISS", Color.BLACK);
							health -= (MAX_HEALTH / 10);
						} else { // Bad circles
							circle.updateLabel("NICE", Color.BLUE);
							health += MAX_HEALTH / 15;
						}
					} else { // If we have started removing it
						circle.setRemoveCounter(circle.getRemoveCounter() + 1);
					}
				}

				// Remove the label after enough time
				if (circle.getRemoveCounter() >= 20) {
					circle.removeLabel();
					circles.remove(circle);
				}
			}
		}

		if (numTicks % 3 == 0 && vicCount == 0) // Every 2 ticks of the timer take some health off
			health -= MAX_HEALTH / 2000;

		scoreLabel.setLabel("Your Score:" + Integer.toString(score)); // updates score label every tick
		scoreLabel.sendToFront(); // makes sure this is always on top of circles

		if (health > MAX_HEALTH)
			health = MAX_HEALTH; // Stop HP from growing above 100

		// Updates health bar display every tick
		healthBar.setSize(emptyHPBar.getWidth() * (health / (double) MAX_HEALTH), emptyHPBar.getHeight());
		healthBar.setLocation(emptyHPBar.getX() + (emptyHPBar.getWidth() - healthBar.getWidth()), emptyHPBar.getY());

		// Changes HP bar color based on health
		if (health > MAX_HEALTH / 75) // 75% and up
			healthBar.setFillColor(Color.GREEN);
		else if (health > MAX_HEALTH / 50) // 50% - 75%
			healthBar.setFillColor(Color.ORANGE);
		else if (health > MAX_HEALTH / 25) // 25% - 50%
			healthBar.setFillColor(Color.YELLOW);
		else // under 25%
			healthBar.setFillColor(Color.RED);

	}// action()

	public void pauseGame() {
		program.time.stop();
		program.pauseAudio();
		program.remove(pause); // pause button on in-game UI

		for (GObject obj : pauseObjects)
			program.add(obj); // buttons on the pause menu
	}

	public void unPauseGame() {
		program.time.restart();
		program.resumeAudio();
		program.add(pause);

		for (GObject obj : pauseObjects)
			program.remove(obj);
	}

	/**
	 * This function creates the objects for the pause menu and adds them to an
	 * ArrayList for easy access
	 */
	private void initializePauseMenu() {
		restart = new GButton("Restart", PAUSE_B_X, PAUSE_B_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		restart.setFillColor(Color.YELLOW);
		pauseObjects.add(restart);

		change = new GButton("Change Settings", PAUSE_B_X + BUTTON_WIDTH, PAUSE_B_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		change.setFillColor(Color.BLACK);
		change.setColor(Color.CYAN);
		pauseObjects.add(change);

		cont = new GButton("Resume [Esc]", PAUSE_B_X + BUTTON_WIDTH * 2, PAUSE_B_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		cont.setFillColor(Color.GREEN);
		pauseObjects.add(cont);

		paused = new GLabel("PAUSED!", 260, 200);
		paused.setFont("Arial-60");
		pauseObjects.add(paused);
	}

	/**
	 * This function creates the objects for the user interface and adds them to an
	 * ArrayList for easy access
	 */
	private void initializeUI() {
		uiObjects = new ArrayList<GObject>();

		// Initializes background rectangle
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);

		// Initializes pause button
		pause = new GButton("Pause <space>", 675, 15, BUTTON_WIDTH, BUTTON_HEIGHT);
		uiObjects.add(pause);

		// Initializes score label
		scoreLabel = new GLabel("Your Score:" + Integer.toString(score), 15, 30);
		scoreLabel.setFont(new Font("Arial", 0, 20));
		uiObjects.add(scoreLabel);

		// Initializes bottom-of-screen health bar
		emptyHPBar = new GRect(10, backRect.getY() + backRect.getHeight(), backRect.getWidth(), 10);
		healthBar = new GRect(emptyHPBar.getX(), emptyHPBar.getY(), emptyHPBar.getWidth(), emptyHPBar.getHeight());
		emptyHPBar.setFilled(true);
		healthBar.setFilled(true);
		healthBar.setFillColor(Color.GREEN);
		uiObjects.add(emptyHPBar);
		uiObjects.add(healthBar);
	}

	/**
	 * Creates an ArrayListof characters from given string
	 * 
	 * @param str
	 *            string to turn into ArrayList
	 */
	private void createCharArrList(String str) {
		characters = new ArrayList<Character>(); // reset ArrayList so we start from scratch

		for (int i = 0; i < str.length(); i++) {
			// if the current character in the string is a letter or number (ignores weird
			// stuff)
			if (Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i))) {
				characters.add(str.charAt(i));
			}
		}
	}

	public void createCircle() {
		// Generate random coordinate to put the circle at within the bounds of the
		// screen
		double xloc = getNewLoc(WINDOW_WIDTH);
		double yloc = getNewLoc(WINDOW_HEIGHT);

		/*
		 * keep circle created in side the screen by 100*60 pixel make sure circles are
		 * not created outside the screen make sure circles are not overlapped
		 */

		while (ifLocNotAvailable(xloc, lastX)) {
			xloc = getNewLoc(WINDOW_WIDTH);
		}

		while (ifLocNotAvailable(yloc, lastY)) {
			yloc = getNewLoc(WINDOW_HEIGHT);
		}

		lastX.add(xloc);
		lastY.add(yloc);

		if (lastX.size() > 4) {
			lastX.remove(0);
			lastY.remove(0);
		}

		// Randomly determine if a circle is "bad" or not
		boolean cirGood = true;
		if (rand.nextInt(10) == 0)
			cirGood = false;

		// Create and add the circle to the screen if there are characters left to add
		if (characters.size() > 0) {
			Circle toAdd = new Circle(characters.remove(0), song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(),
					cirGood);
			// Add shapes to screen from the Circle, then add the Circle to the ArrayList
			program.add(toAdd.getInnerCircle());
			program.add(toAdd.getOuterCircle());
			program.add(toAdd.getLabel());
			circles.add(toAdd);
		} else { // No more characters left to add
			if (circles.size() <= 0) { // If there are no more circles on screen
				if (vicCount > 1) { // If the timer is counting down
					vicCount--;
				} else if (vicCount == 1) { // If the countdown has counted down all the way
					hasWon = true; // Declare that the user has won
				} else { // If countdown has not been started
					vicCount = 2; // game will wait 2 spawn ticks before ending
				}
			}
		}
	}

	/**
	 * help method for generating random location
	 */
	private double getNewLoc(int base) {
		return (base * (rangeMin + (rangeMax - rangeMin) * rand.nextDouble()));
	}

	private boolean ifLocNotAvailable(double in, ArrayList<Double> last) {
		for (int i = 0; i < last.size(); i++) {
			if (Math.abs(in - last.get(i)) <= 45) {
				// System.out.println("========" + Double.toString(Math.abs(in - last.get(i)))
				// );
				return true;
			}
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	public int getHealth() {
		return health;
	}

	public boolean getHasWon() {
		return hasWon;
	}

	public void setPaused(boolean p) {
		isPaused = p;
	}

}// Level