import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsPane {
	
	// Screen variables
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	
	// System variables
	private int numTicks = 0; // How many timer ticks have gone by
	MainApplication program;
	private Random rand;
	private AudioPlayer audioPlayer;
	private boolean isPaused;
	private boolean hasWon = false; // is set to true when the player has won the game
	private int vicCount = 0; // Used to stop the game from immediately ending after last circle
	
	// Player-related variables
	public static final int MAX_HEALTH = 10000; // Never set to less than 2000 or casual HP loss breaks
	private int health = MAX_HEALTH;
	private int score = 0;
	
	// Song and circle variables
	private Song song;
	private ArrayList<Circle> circles; // Stores the circles being displayed on the screen
	private ArrayList<Character> characters; // Stores the characters to feed into the circles
	private int circleCount; // counts how many circles have spawned since the last important event
	private double tempo; // How often to spawn circles
	private ArrayList<Integer> tempoChangeTimes; // times to change tempo
	private ArrayList<Integer> tempoChangeValues; // how much to change tempo by
	private double nextCircleSpawn; // saves when to spawn the next circle

	
	// Used to load song from file to play
	
	/*
	 * Valid song names:
	 * ToHellAndBack
	 * hotelCali
	 */
	private String folder = "sounds/";
	private String filename = "hotelCali";
	private String diffNum = "1";
	
	// UI elements
	private GLabel scoreLabel; // holds the score for now
	private GRect healthBar; // displays HP percentage
	private GRect emptyHPBar; // Empty HP Bar to show full
	//private GRect backRect; // Grey box the circles spawn inside of
	private GLabel paused;
	private GButton pause;
	private GButton cont;
	private GButton restart;
	private GButton change;
	
	// Used for circle generation to prevent overlapping
	private double lastXloc[] = {9999, 9999, 9999, 9999, 9999, 9999};
	private double lastYloc[] = {9999, 9999, 9999, 9999, 9999, 9999};
	private int count = 0;
	private int temp = 0;
	
	// Used to generate circles inside of bounds
	private double rangeMin = 0.9;
	private double rangeMax = 0.2;
	
	public Level(MainApplication app) {
		super();
		program = app;
		filename = app.getSongChoice();
		diffNum = Integer.toString(app.getDiffChoice()+1);
		
		//run();
	}

	public void createCircle() {
		// Generate random coordinate to put the circle at within the bounds of the screen
		double xloc = getNewLoc(WINDOW_WIDTH);
		double yloc = getNewLoc(WINDOW_HEIGHT);
		//System.out.println(count);
		
		/*
		 * keep circle created in side the screen by 100*60 pixel
		 * make sure circles are not created outside the screen
		 * make sure circles are not overlapped
		 */
		int breakk = 0;
		while(ifLocAvailable(WINDOW_WIDTH, xloc, lastXloc) == '0') {
			xloc = getNewLoc(WINDOW_WIDTH);
			System.out.println("X>>>>>>>>"+ Double.toString(xloc));
			breakk++;
			if(breakk > 10) {break;}
		}
		while(ifLocAvailable(WINDOW_HEIGHT,yloc, lastYloc) == '0') {
			yloc = getNewLoc(WINDOW_HEIGHT);
			System.out.println("Y>>>>>>>>"+ Double.toString(yloc));
			breakk++;
			if(breakk > 10) {break;}
		}

		lastXloc[count] = xloc;
		lastYloc[count] = yloc;
		count++;
		if (count == lastXloc.length) {
			count = 0;
		}
		
		
		/*
		 * Makes some circles randomly "bad"
		 * TODO:
		 * Make a better implementation
		 */
		
		boolean cirGood = true;
		
		if(rand.nextInt(10) == 0) {
			cirGood = false;
			System.out.println("Bad circle generated");
		}
		
		
		if (characters.size() > 0) {
			Circle toAdd = new Circle(characters.remove(0), song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), cirGood);
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
	 * help method for generating random location
	 */
	private double getNewLoc(int base) {
		double newloc = base * (rangeMin + (rangeMax - rangeMin) * rand.nextDouble());
		return newloc;
	}
	
	private char ifLocAvailable(int base, double in, double[] last) {
		for (int i = 0; i < last.length; i++) {
			if (Math.abs(in - last[i]) < 140) {
				System.out.println("========" + Double.toString(Math.abs(in-last[i])) );
				return '0';
			}
		}
		return '1';
	}

	/**
	 * Creates an ArrayListof characters from given string
	 * @param str string to turn into ArrayList
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
		
		restart = new GButton("Restart", 235, 300, 100, 40);
		restart.setFillColor(Color.YELLOW);
		change = new GButton("Change Settings", 335, 300, 100, 40);
		change.setFillColor(Color.BLACK);
		change.setColor(Color.CYAN);
		pause = new GButton("Pause <space>", 675, 15,100,30);
		cont = new GButton("Resume [Esc]", 435, 300, 100, 40);
		cont.setFillColor(Color.GREEN);
		paused = new GLabel("PAUSED!",260,200);
		paused.setFont("Arial-60");

		
		// Initializes background rectangle
		//backRect = new GRect(10, 10, WINDOW_WIDTH-20, WINDOW_HEIGHT-20);
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
		program.add(pause);
		
		rand = new Random();

		// loads file for song we want to play
		song = new Song(filename+diffNum); 
		
		
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters
		tempoChangeTimes = song.getTempoChangeTimes();
		tempoChangeValues = song.getTempoChangeValues();
		tempo = song.getTempo();
		nextCircleSpawn = song.getStartDelay();
		// Turns the string from Song into an ArrayList of characters to feed into the circles
		createCharArrList(song.getCircleList());

		// start audio and timer
		audioPlayer = AudioPlayer.getInstance();
		program.time.start();
	}

	// ***member methods***

	/**
	 * Timer function, executed every time the timer ticks
	 */
	public void action() {
		numTicks++;

		// Create a new circle every interval of time specified by the song's Tempo
		if (numTicks == Math.floor(nextCircleSpawn)) {
			System.out.println(nextCircleSpawn);
			createCircle(); // Make a new circle
			circleCount++;
			nextCircleSpawn+=tempo;
		}
		
		// If there are tempo changes ahead in the song
		if(tempoChangeTimes.size()>0 && tempoChangeValues.size()>0) {
			if(circleCount == tempoChangeTimes.get(0)) {
				if(tempoChangeValues.get(0)>0) // If the tempo change is positive
					tempo = tempo / tempoChangeValues.remove(0);
				else
					tempo = tempo * Math.abs(tempoChangeValues.remove(0));
				tempoChangeTimes.remove(0);
				System.out.println(tempo);
				circleCount = 0;
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
				if(circle.getRemoveCounter() == 0) { // If the circle has not been removed from the screen
					circle.updateColor(song.getCircleSize());
				}
				
				// If circles have shrunk to be the same size in and out
				if (circle.getOutSize() < 0) {
					if(circle.getRemoveCounter() == 0) {
						if(circle.isGood()) { // If it is a good circle
							// Add the text displaying that you missed
							circle.updateLabel("MISS",Color.BLACK);
							health-=(MAX_HEALTH/10);
						}
						else { // Bad circles
							circle.updateLabel("NICE",Color.BLUE);
							health+=MAX_HEALTH/15;
						}
					}
					else {
						circle.setRemoveCounter(circle.getRemoveCounter() + 1);
					}
				}

				if (circle.getRemoveCounter() >= 20) {
					circle.removeLabel();
					circles.remove(circle);
				}
			}
		}
		
		if(numTicks % 3 == 0 && vicCount == 0) // Every 2 ticks of the timer take some health off
			health-=MAX_HEALTH/200;
		
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
		audioPlayer.playSound(folder, filename+".mp3");
	}// startAudioFile

	public void pauseAudio() {
		audioPlayer.pauseSound(folder, filename + ".mp3");
		isPaused = true;
	}// pause
	
	public void stopAudio() {
		audioPlayer.stopSound(folder, filename + ".mp3");
	}

	public void resumeAudio() {
		if (isPaused) {
			audioPlayer.playSound(folder, filename + ".mp3");
			isPaused = false;
		}
	}// resume

	public void restartAudio() {
		if (!isPaused) {
			audioPlayer.stopSound(folder, filename + ".mp3");
			audioPlayer.playSound(folder, filename + ".mp3");
			isPaused = false;
		}
	}// restart

	@Override
	public void keyTyped(KeyEvent e) { // using keyTyped to help ensure valid input
		boolean found = false; // tracks if we have found a matching circle
		
		String text = "";
		Color cirColor = Color.BLACK;
		if ((e.getKeyChar() == KeyEvent.VK_SPACE) && !isPaused)
		{
			pauseGame();
		}
		if((e.getKeyChar() == KeyEvent.VK_ESCAPE) && isPaused) {
			unPauseGame();
		}
		
		// Iterate through all circles on the screen
		for(Circle circle : circles) {
			// if you pressed a key matching a circle who is still shrinking
			if(e.getKeyChar() == circle.getLetter() && circle.getRemoveCounter() == 0) { 
				// Add the text displaying that you pressed it right
				// Pick which text based on how small the outer circle was

				double size = circle.getOutSize(); // store outer size to a variable for efficiency
				double init = song.getCircleSize(); // store initial size for math
				
				// Note: all numbers are subject to change
				if(circle.isGood()) { // Do this if the match is good
					if (size <= (init / 100)) { // If you press in the last hundredth of the timer
						text = "PERFECT!";
						cirColor = Color.WHITE;
						score+=100;
						health+=MAX_HEALTH/5;
					}
					else if (size <= (init / 10)) { // If you press between 9/10 and 99/100
						text = "AMAZING!";
						cirColor = Color.CYAN;
						score+=50;
						health+=MAX_HEALTH/10;
					}
					else if (size <= (init / 5)) {  // If you press between 4/5 and 9/10
						text = "GREAT";
						cirColor = Color.GREEN;
						score+=25;
						health+=MAX_HEALTH/15;
					}
					else if (size <= (init / 2)) { // If you press between 1/2 and 4/5
						text = "GOOD";
						cirColor = Color.YELLOW;
						score+=5;
						health+=MAX_HEALTH/75;
						
					}
					else { // If you press in the first half of the timer
						text = "OK";
						cirColor = Color.ORANGE;
						score+=1;
						health+=MAX_HEALTH/250;
					}
				} // End of good circle code
				else { // You clicked on a "bad" circle
					text = "BAD";
					cirColor = Color.RED;
					health-=MAX_HEALTH/15;
				}
				
				// hide the GOvals after updating the label
				circle.updateLabel(text, cirColor);
				
				found = true; // Remember that we found the circle
			}
		}
		
		if(!found) { // if match was found
			//System.out.println("No match for "+e.getKeyChar()+"!"); // Print to console no match was found
			if(vicCount==0) health-=MAX_HEALTH/20; // Take health off if the game is still running
		}
	}// keyPressed



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
			stopAudio();
			program.switchToLevel();
		}
		if (obj == change) {
			stopAudio();
			program.switchToSettings();
		}
	}// mouseClicked

	public void pauseGame() {
		program.time.stop();
		pauseAudio();
		program.add(cont);
		program.add(paused);
		program.add(restart);
		program.add(change);
		program.remove(pause);
	}
	
	public void unPauseGame() {
		program.time.restart();
		resumeAudio();
		program.remove(cont);
		program.remove(paused);
		program.remove(restart);
		program.remove(change);
		program.add(pause);
	}

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
		program.remove(backRect);
		program.remove(emptyHPBar);
		program.remove(healthBar);
		program.remove(scoreLabel);
		program.remove(pause);
		program.remove(paused);
		program.remove(change);
		program.remove(restart);
		program.remove(cont);
		
		for(Circle circle : circles) {
			circle.removeCircles();
			circle.removeLabel();
		}
		program.time.stop();
	}

}// Level