import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import acm.graphics.*;

public class EndOfGamePane extends GraphicsPane{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	//public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	public static final String FILENAME = "../"+"player.txt";
	
	private MainApplication program;
	GButton playAgain;
	GButton mainMenu;
	GLabel titleLabel;
	//private static ArrayList<GLabel> scoresLabels;
	private String scoresToDisplay = "";
	GLabel scoresLabels;
	GRect scoreRect;
	GImage background;
	
	Player playerInfo;
	
	public EndOfGamePane(MainApplication app) {
		program = app;
		background = new GImage("back.jpg",0,0);
		scoreRect = new GRect (150,100,500,300);
		scoreRect.setFillColor(Color.WHITE);
		scoreRect.setFilled(true);
		
		titleLabel = new GLabel("HIGH SCORES", 320, 80);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		
		playAgain = new GButton("PLAY AGAIN", 220, 410, 80, 30, Color.GRAY);
		mainMenu = new GButton("MAIN MENU", 480, 410, 80, 30, Color.CYAN);
		
		playerInfo = new Player();
		scoresLabels = new GLabel("Scores will be showing here", 200,200);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		
		playerInfo.testFunc();
		
		int arrSize = playerInfo.getScore().size();
		for(int i = 0; i < arrSize; i++) {
			scoresToDisplay += playerInfo.getScore().get(i) + " ";
		}
		//scoresToDisplay = playerInfo.ReadScoreFromFile();
		scoresLabels.setLabel(scoresToDisplay);
		String readfromfile = Player.ReadScoreFromFile();
		scoresLabels.setLabel(readfromfile);
	}
	
//	public static String ReadScoreFromFile() {
//		String sCurrentLine ="";
//		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
//
//			System.out.println("Printing what's in the player.txt:");
//			
//			while ((sCurrentLine = br.readLine()) != null) {
//				System.out.println(sCurrentLine);
//				sCurrentLine += sCurrentLine;
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sCurrentLine;
//	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		program.add(background);
		program.add(playAgain);
		program.add(mainMenu);
		program.add(titleLabel);
		program.add(scoreRect);
		
		program.add(scoresLabels);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(playAgain);
		program.remove(mainMenu);
		program.remove(titleLabel);
		program.remove(scoreRect);
		program.remove(background);
		
		program.remove(scoresLabels);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
	if (obj == mainMenu) {
		program.switchToMenu();
	}
	if (obj == playAgain) {
		//program.level = new Level(program);
		program.switchToLevel();
	}
	}
}
