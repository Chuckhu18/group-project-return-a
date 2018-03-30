import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsProgram {
	// ***Instance variables***
	int score, health, counter1=0;
	Song song;
	Circle circle;
	ArrayList<Circle> circles;
	ArrayList<Character> characters;
	AudioPlayer player;
	boolean isPaused;
	String folder = "sounds/";
	String filename = "hotelCali.mp3";
	Timer timer = new Timer(500, this);

	public void createCircle() {
		// 
		if(characters.size()>0)
			circles.add(new Circle(characters.remove(0), 10.0, 20.0, 20.0, 1.0, true));
		else
			circles.add(new Circle('7', 10.0, 20.0, 20.0, 1.0, false));
		
	}

	public void run() {
		circles = new ArrayList<Circle>(); // Initializes ArrayList of Circles
		characters = new ArrayList<Character>(); // Initializes ArrayList of characters
		
		// Adds characters to the characters ArrayList
		// TODO: generate characters ArrayList from incoming Song data
		characters.add('a');
		characters.add('b');
		characters.add('c');
		
		// Adds circle to the board using hardcoded values
		// TODO: randomize start position, get size and speed data from incoming Song data
		createCircle();
		
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
		
		if(counter1 % 6 == 0) {
			createCircle(); // Make a new circle every few ticks to test
		}
		
		/*
		 *  TODO: Development note (Race, 03/29/18):
		 *  I am thinking we might have been wrong to decide to have each circle with its own timer,
		 *  I think in hindsight it's better to just call the shrink() function from this timer every
		 *  time. Will talk about in class tomorrow and probably implement over the weekend.
		 */
		
		if(circles.size()>=1) {
			int count=0;
			for(Circle circle:circles) {
				circle.shrink();
				if(circle.getOutSize() <= 0) {
					circles.remove(circle);
				}else {			
				System.out.println(count+": "+circle);
				count++;
				}
			}
			System.out.println();
		}
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