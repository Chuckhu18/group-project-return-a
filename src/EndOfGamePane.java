import java.awt.Color;

import acm.graphics.*;

public class EndOfGamePane extends GraphicsPane{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	//public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	
	private MainApplication program;
	GButton playAgain;
	GButton mainMenu;
	GLabel highScores;
	GRect socreRect;
	
	
	
	
	
	public EndOfGamePane(MainApplication app) {
		program = app;
		playAgain = new GButton("PLAY AGAIN", 200, 400, 80, 30, Color.CYAN);
		mainMenu = new GButton("MAIN MENU", 500, 400, 80, 30, Color.CYAN);
		socreRect = new GRect (200,100,400,300);
		
		
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		program.add(playAgain);
		program.add(mainMenu);
		program.add(socreRect);
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(playAgain);
		program.remove(mainMenu);
		program.remove(socreRect);
		
	}

}
