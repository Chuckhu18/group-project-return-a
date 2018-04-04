import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsProgram {
	// ***Instance variables***
	int score, health, counter1 = 0;
	Random rand;
	Song song;
	Circle circle;
	ArrayList<Circle> circles; // Stores the circles being displayed on the screen
	ArrayList<Character> characters; // Stores the characters to feed into the circles
	AudioPlayer player;
	boolean isPaused;
	String folder = "sounds/";
	String filename = "RainsItPours.mp3";
	Timer timer = new Timer(50, this); // Timer ticks 20 times per second

	public void createCircle() {
		// Generate random coordinate to put the circle at, don't care where yet
		// TODO: be smarter about where it spawns
		double xloc = 500 * rand.nextDouble();
		double yloc = 500 * rand.nextDouble();

		Circle toAdd;
		if (characters.size() > 0)
			toAdd = new Circle(characters.remove(0), song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), true);
		else // Make dummy circle object for testing
			toAdd = new Circle('7', song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), false);

		// Add shapes to screen from the Circle, then add the Circle to the ArrayList of
		// Circles
		add(toAdd.getInnerCircle());
		add(toAdd.getOuterCircle());
		add(toAdd.getLabel());
		circles.add(toAdd);

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
		setSize(500, 500); // Arbitrary numbers so far for screen size
		rand = new Random();
		// I picked random numbers that look nice for the timer values, will have to
		// test more
		song = new Song(filename, 15.0, 0.15, 30, "abcdefghijklmnopqrstuvwxyz"); // using all characters in alphabetical
																					// order for easy testing
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters

		// Turns the string from Song into an ArrayList of characters to feed into the
		// circles
		createCharArrList(song.getCircleList());

		// start audio and timer
		player = AudioPlayer.getInstance();
		startAudioFile();
		timer.start();
	}

	// ***member methods***

	/**
	 * Timer function, executed every time the timer ticks
	 */
	public void actionPerformed(ActionEvent e) {
		counter1++;

		// Create a new circle every interval of time specified by the song's Tempo
		if (counter1 % song.getTempo() == 0) {
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
					// Add the text displaying that you missed
					circle.getLabel().setLabel("MISS");
					circle.getLabel().setColor(Color.BLACK);
					circle.setRemoveCounter(circle.getRemoveCounter() + 1);
					circle.removeCircles();
				}

				if (circle.getRemoveCounter() == 20) {
					circles.remove(circle);
					circle.removeLabel();
				}
				else { // If circles are still bigger out than in

					if (counter1 % 20 == 0) { // Only display circles once per second
						System.out.println(count + ": " + circle);
						count++;
					}
				}
			}
		}


		if (counter1 % 20 == 0)
			System.out.println(); // Print a blank line after displaying current status of circles ArrayList
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
	public void keyPressed(KeyEvent e) {
		;
	}// keyPressed

	@Override
	public void mouseClicked(MouseEvent e) {
		;
	}// mouseClicked

	// ***getters***
	public int getScore() {
		return score;
	}// getScore

}// Level