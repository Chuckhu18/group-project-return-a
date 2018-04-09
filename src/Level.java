import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsPane implements KeyListener {
	
	// ***Instance variables***
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final int MAX_HEALTH = 10000; // Never set to less than 2000 or casual HP loss breaks
	int score, health = MAX_HEALTH, numTicks = 0;
	MainApplication program;
	Random rand;
	Song song;
	Circle circle;
	ArrayList<Circle> circles; // Stores the circles being displayed on the screen
	ArrayList<Character> characters; // Stores the characters to feed into the circles
	AudioPlayer player;
	boolean isPaused;
	private boolean hasWon = false; // is set to true when the player has won the game
	private int vicCount = 0; // Used to stop the game from immediately ending after last circle
	
	// Used to load song from file to play
	String folder = "sounds/";
	String filename = "HotelCali.mp3";
	//Timer timer = new Timer(10, this); // Timer now executed from MainApplication
	
	// UI elements
	private GLabel scoreLabel; // holds the score for now
	private GRect healthBar; // displays HP percentage
	private GRect emptyHPBar; // Empty HP Bar to show full
	private GRect backRect; // Grey box the circles spawn inside of
	
	// Used for circle generation to prevent overlapping
	private double lastXloc = 0;
	private double lastYloc = 0;
	private double lastXloc2 = 0;
	private double lastYloc2 = 0;
	//private double lastXloc3 = 0;
	//private double lastYloc3 = 0;
	
	// Used to generate circles inside of bounds
	private int temp = 1;
	private double rangeMin = 0.9;
	private double rangeMax = 0.1;
	
	public Level(MainApplication app) {
		super();
		program = app;
		//run();
	}

	public void createCircle() {
		// Generate random coordinate to put the circle at within the bounds of the screen
		double xloc = WINDOW_WIDTH * (rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
		double yloc = WINDOW_HEIGHT *(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
		
		// keep circle created in side the screen by 100*60 pixel
		// make sure circles are not created outside the screen
		// make sure circles are not overlapped

		
		int tries = 0;
		while(Math.abs(xloc - lastXloc) < 100 || Math.abs(xloc - lastXloc2) < 100) {
			xloc = WINDOW_WIDTH * (rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			
			tries+=1;
			//System.out.println(tries);
			//if(tries>10) break;
		}
		tries = 0;
		while(Math.abs(yloc - lastYloc) < 100 || Math.abs(yloc - lastYloc2) < 100 ) {
			yloc = WINDOW_HEIGHT *(rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
			
			tries+=1;
			//System.out.println(tries);
			//if(tries>10) break;
		}
		
		if(temp == 1) {
			lastXloc = xloc;
			lastYloc = yloc;
		}
		else{
			lastXloc2 = xloc;
			lastYloc2 = yloc;
		}
		
		temp = temp*-1;
		
		
		if (characters.size() > 0) {
			Circle toAdd = new Circle(characters.remove(0), song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), true);
			// Add shapes to screen from the Circle, then add the Circle to the ArrayList
			program.add(toAdd.getInnerCircle());
			program.add(toAdd.getOuterCircle());
			program.add(toAdd.getLabel());
			circles.add(toAdd);
		}
		else { // No more characters left to add
			if (circles.size() <= 0) { // If there are no more circles on screen
				if(vicCount > 1) { // If the timer is counting down
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

	public void run() {
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Arbitrary numbers so far for screen size
		//program.addKeyListeners(); // Taken out to prevent double execution of events
		program.setFocusable(true);
		program.requestFocus();
		//program.addMouseListeners(program); // Taken out to prevent double execution of events
		
		// Initializes background rectangle
		backRect = new GRect(10, 10, WINDOW_WIDTH-20, WINDOW_HEIGHT-20);
		backRect.setFillColor(Color.GRAY);
		backRect.setFilled(true);
		
		// Initializes score label
		scoreLabel = new GLabel("Your Score:" + Integer.toString(score),15,30);
		scoreLabel.setFont(new Font("Arial",0,20));
		
		// Initializes bottom-of-screen health bar
		emptyHPBar = new GRect(10,backRect.getY()+backRect.getHeight(),backRect.getWidth(),10);
		healthBar = new GRect(emptyHPBar.getX(),emptyHPBar.getY(),emptyHPBar.getWidth(),emptyHPBar.getHeight());
		emptyHPBar.setFilled(true);
		healthBar.setFilled(true);
		healthBar.setFillColor(Color.GREEN);
		
		// Adds everything to the screen
		program.add(backRect);
		program.add(scoreLabel);
		program.add(emptyHPBar);
		program.add(healthBar);
		
		rand = new Random();
		// I picked random numbers that look nice for the timer values, will have to test more
		// using all characters in alphabetical order for easy testing
		song = new Song(filename, 15.0, 0.075, 100, "abcdefghijklmnopqrstuvwxyza"); 
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters

		// Turns the string from Song into an ArrayList of characters to feed into the circles
		createCharArrList(song.getCircleList());

		// start audio and timer
		player = AudioPlayer.getInstance();
		startAudioFile();
		program.time.start();
	}

	// ***member methods***

	/**
	 * Timer function, executed every time the timer ticks
	 */
	public void action() {
		numTicks++;

		// Create a new circle every interval of time specified by the song's Tempo
		if (numTicks % song.getTempo() == 0) {
			createCircle(); // Make a new circle
		}

		// If there are circles on the screen
		if (circles.size() >= 1) {
			int count = 0;

			// Have to use manual definition of for loop to avoid
			// ConcurrentModificationException
			for (int i = 0; i < circles.size(); i++) {

				// Shrink circle
				Circle circle = circles.get(i);
				circle.shrink();

				// If circles have shrunk to be the same size in and out
				if (circle.getOutSize() < 0) {
					if(circle.getRemoveCounter() == 0) {
						// Add the text displaying that you missed
						circle.getLabel().setLabel("MISS");
						circle.getLabel().setColor(Color.BLACK);
						circle.removeCircles();
						health-=(MAX_HEALTH/10);
					}
					else {
						circle.setRemoveCounter(circle.getRemoveCounter() + 1);
					}
				}

				if (circle.getRemoveCounter() >= 20) {
					circles.remove(circle);
					circle.removeLabel();
				}
			}
		}
		
		if(numTicks % 3 == 0 && vicCount == 0) // Every 2 ticks of the timer take some health off
			health-=MAX_HEALTH/2000;
		
		scoreLabel.setLabel("Your Score:" + Integer.toString(score)); // updates score label every tick
		scoreLabel.sendToFront(); // makes sure this is always on top of circles
		
		if (health > MAX_HEALTH) health = MAX_HEALTH; // Stop HP from growing above 100
		
		// Updates health bar display every tick
		healthBar.setSize(emptyHPBar.getWidth() * (health/(double) MAX_HEALTH),emptyHPBar.getHeight());
		healthBar.setLocation(emptyHPBar.getX() + (emptyHPBar.getWidth() - healthBar.getWidth()),emptyHPBar.getY());
		
		// Changes HP bar color based on health
		if (health > MAX_HEALTH / 75)
			healthBar.setFillColor(Color.GREEN);
		else if (health > MAX_HEALTH / 50)
			healthBar.setFillColor(Color.ORANGE);
		else if (health > MAX_HEALTH / 25)
			healthBar.setFillColor(Color.YELLOW);
		else
			healthBar.setFillColor(Color.RED);
		
	}// actionPerformed

	public void startAudioFile() {
		isPaused = false;
		player.playSound(folder, filename);
		System.out.println("SOUND PLAYED");
	}// startAudioFile

	public void pauseAudio() {
		player.pauseSound(folder, filename);
		isPaused = true;
	}// pause

	public void resumeAudio() {
		if (isPaused) {
			player.playSound(folder, filename);
			isPaused = false;
		}
	}// resume

	public void restartAudio() {
		player.stopSound(folder, filename);
		player.playSound(folder, filename);
	}// restart

	@Override
	public void keyTyped(KeyEvent e) { // using keyTyped to help ensure valid input
		boolean found = false; // tracks if we have found a matching circle
		
		// Iterate through all circles on the screen
		for(Circle circle : circles) {
			// if you pressed a key matching a circle who is still shrinking
			if(e.getKeyChar() == circle.getLetter() && circle.getRemoveCounter() == 0) { 
				// Add the text displaying that you pressed it right
				// Pick which text based on how small the outer circle was

				double size = circle.getOutSize(); // store outer size to a variable for efficiency
				double init = song.getCircleSize(); // store initial size for math
				
				// Note: all numbers are subject to change
				if (size <= (init / 100)) { // If you press in the last hundredth of the timer
					circle.getLabel().setLabel("PERFECT!");
					circle.getLabel().setColor(Color.WHITE);
					score+=100;
					health+=MAX_HEALTH/5;
				}
				else if (size <= (init / 10)) { // If you press between 9/10 and 99/100
					circle.getLabel().setLabel("AMAZING!");
					circle.getLabel().setColor(Color.CYAN);
					score+=50;
					health+=MAX_HEALTH/10;
				}
				else if (size <= (init / 5)) {  // If you press between 4/5 and 9/10
					circle.getLabel().setLabel("GREAT!");
					circle.getLabel().setColor(Color.GREEN);
					score+=25;
					health+=MAX_HEALTH/15;
				}
				else if (size <= (init / 2)) { // If you press between 1/2 and 4/5
					circle.getLabel().setLabel("GOOD!");
					circle.getLabel().setColor(Color.YELLOW);
					score+=5;
					health+=MAX_HEALTH/75;
					
				}
				else { // If you press in the first half of the timer
					circle.getLabel().setLabel("OK!");
					circle.getLabel().setColor(Color.ORANGE);
					score+=1;
					health+=MAX_HEALTH/250;
				}
				
				// hide the GOvals after updating the label
				circle.removeCircles();
				
				found = true; // Remember that we found the circle
			}
		}
		
		if(found) { // if match was found
			System.out.println("Circle "+e.getKeyChar()+" removed!"); // Print to console no match was found
		}else { // if not found
			System.out.println("No match for "+e.getKeyChar()+"!"); // Print to console no match was found
			if(vicCount==0) health-=MAX_HEALTH/20; // Take health off if the game is still running
		}
	}// keyPressed

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse clicked at ("+e.getX()+","+e.getY()+")!");
	}// mouseClicked

	// ***getters***
	public int getScore() {
		return score;
	}// getScore
	
	public int getHealth() {
		return health;
	}// getHealth
	
	public boolean getHasWon() {
		return hasWon;
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}

}// Level