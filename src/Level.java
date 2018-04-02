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
	ArrayList<GLabel> resultText; // Stores the labels that appear when you click on a circle
	AudioPlayer player;
	boolean isPaused;
	String folder = "sounds/";
	String filename = "hotelCali.mp3";
	Timer timer = new Timer(50, this); // Timer ticks 20 times per second

	public void createCircle() {
		//Generate random coordinate to put the circle at, don't care where yet
		// TODO: be smarter about where it spawns
		double xloc = 500 * rand.nextDouble();
		double yloc = 500 * rand.nextDouble();
		
		Circle toAdd;
		if (characters.size() > 0)
			toAdd = new Circle(characters.remove(0), song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), true);
		else // Make dummy circle object for testing
			toAdd = new Circle('7', song.getCircleSize(), xloc, yloc, song.getShrinkSpeed(), false);

		// Add shapes to screen from the Circle, then add the Circle to the ArrayList of Circles
		add(toAdd.getInnerCircle());
		add(toAdd.getOuterCircle());
		add(toAdd.getLabel());
		circles.add(toAdd);
		
	}

	public void run() {
		setSize(500,500); // Arbitrary numbers so far for screen size
		rand = new Random();
		// I picked random numbers that look nice for the timer values, will have to test more
		song = new Song(filename, 15.0, 0.15, 30, "abcdefghijklmnopqrstuvwxyz"); // using all characters in alphabetical order for easy testing
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters
		resultText = new ArrayList<GLabel>(); // Initializes resultText ArrayList
		
		// Adds characters to the characters ArrayList
		// TODO: generate characters ArrayList from incoming Song data
		characters.add('a');
		characters.add('b');
		characters.add('c');

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

		if (counter1 % song.getTempo() == 0) {
			createCircle(); // Make a new circle
		}

		if (circles.size() >= 1) {
			int count = 0;
			
			// Have to use manual definition of for loop to avoid ConcurrentModificationException
			for (int i = 0; i < circles.size(); i++) {
				Circle circle = circles.get(i);
				circle.shrink();
				// TODO: make circles get smaller on display
				if (circle.getOutSize() < 0) {
					// Add the text displaying that you missed
					GLabel newlabel = new GLabel("Miss!",circle.getX(),circle.getY());
					resultText.add(newlabel); // Adds miss label to the screen
					add(newlabel);
					
					circles.remove(circle);
					// TODO: remove circles from display
				} else {
					if(counter1%20==0) { // Only display circles once per second
						System.out.println(count + ": " + circle);
						count++;
					}
				}
			}
		}
		
		
		// TODO: make the labels get removed after the same amount of time
		// currently the first one is removed once per second, which could result in a long wait
		if(resultText.size() >= 1) { // if there are result labels on the screen
			if(counter1%20 == 0) { // every second remove one label
				resultText.get(0).setVisible(false);
				resultText.remove(0);
			}
		}
		
		if(counter1%20==0) System.out.println(); // Print a blank line after displaying current status of circles ArrayList
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