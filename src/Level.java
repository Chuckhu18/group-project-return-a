import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsProgram {
	// Instance variables
	int score, health;
	Song song;
	ArrayList<Circle> circles;
	ArrayList<Character> character;
	AudioPlayer player;
	boolean isPaused;
	String folder = "sounds/";
	String filename = "r2d2.mp3";
	Timer timer = new Timer(2000, this);

	public void run() {
		player = AudioPlayer.getInstance();
		timer.start();
	}

	// member methods
	public void createCirle() {
		;
	}
	
	public void actionPerformed(ActionEvent e) {
		startAudioFile();
	}//actionPerformed

	public int getScore() {
		return score;
	}// getScore

	public void startAudioFile() {
		isPaused = false;
		player.playSound(folder, filename);
		System.out.println("SOUND PLAYED");
	}//startAudioFile

	public void pauseAudio() {
		// player.pauseSound(folder, filename);
		isPaused = true;
	}//pause

	public void resumeAudio() {
		if (isPaused) {
			// player.playSound(folder, filename);
			isPaused = false;
		}
	}//resume

	public void restartAudio() {
		 player.stopSound(folder, filename);
		 player.playSound(folder, filename);
	}//restart

	@Override
	public void keyPressed(KeyEvent e) {
		;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		;
	}

}// Level