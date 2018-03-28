import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import acm.graphics.*;
import acm.program.*;


public class Level extends GraphicsProgram {
//Instance variables
	//Song song;
	int score, health;
	ArrayList<Circle> circles;
	ArrayList<Character> character;
	AudioPlayer player;
	boolean isPaused;


//member methods
public int getScore() {
	return score;
}//getScore

public void startAudioFile() {
	isPaused = false;
	//player.playSound(folder, filename);
}

public void pauseAudio() {
	//player.pauseSound(folder, filename);
	isPaused = true;
}

public void resumeAudio() {
	if(isPaused) {
		//player.playSound(folder, filename);
		isPaused = false;
	}
}

public void restartAudio() {
	//player.stopSound(folder, filename);
	//player.playSound(folder, filename);
}

@Override
public void keyPressed(KeyEvent e) {
	;
}

@Override
public void mouseClicked(MouseEvent e) {
	;
}



}// Level