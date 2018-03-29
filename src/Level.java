import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.*;
import acm.program.*;

public class Level extends GraphicsProgram {
	// ***Instance variables***
	int score, health;
	Song song;
	Circle circle;
	ArrayList<Circle> circles;
	ArrayList<Character> character;
	AudioPlayer player;
	boolean isPaused;
	String folder = "sounds/";
	String filename = "hotelCali.mp3";
	Timer timer = new Timer(500, this);

	public Circle createCircle() {
		Circle temp = new Circle('a', 100, 20, 20, 10, true, null, null, null);
		return temp;
	}

	public void run() {
		player = AudioPlayer.getInstance();
		circle = createCircle();
		startAudioFile();
		timer.start();
	}

	// ***member methods***
	public void actionPerformed(ActionEvent e) {
		System.out.println(circle);
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