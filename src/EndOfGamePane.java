import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import acm.graphics.*;

public class EndOfGamePane extends GraphicsPane{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	public static final String FILENAME = "../"+"player.txt";
	public static final String FILEDIRECTORY = "../";
	public static String currentPlayerFile = FILEDIRECTORY + "Chuck.txt";
	
	private MainApplication program;
	GButton playAgain;
	GButton mainMenu;
	GLabel titleLabel, yourScoresLabel, allScoresLabel;
	
	GLabel nameLabel;
	private ArrayList<GLabel> yourScoresLabels;
	private ArrayList<GLabel> allScoresLabels;
	GRect scoreRect1,scoreRect2;
	
	GImage background;
	
	Player playerInfo;
	String currentName;
	
	public EndOfGamePane(MainApplication app) {
		program = app;
		//adding background picture
		background = new GImage("back.jpg",0,0);
		background.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//adding rectangles for displaying scores
		scoreRect1 = new GRect (100,120,275,275);
		scoreRect1.setFillColor(Color.WHITE);
		scoreRect1.setFilled(true);
		
		scoreRect2 = new GRect (425,120,275,275);
		scoreRect2.setFillColor(Color.WHITE);
		scoreRect2.setFilled(true);
		
		//adding labels
		titleLabel = new GLabel("HIGH SCORES", 280, 60);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
		
		yourScoresLabel = new GLabel("YOUR SCORES", 130, 120);
		yourScoresLabel.setFont(new Font("Serif", Font.BOLD, 30));
		
		allScoresLabel = new GLabel("ALL SCORES", 450, 120);
		allScoresLabel.setFont(new Font("Serif", Font.BOLD, 30));
		
		//buttons for "play again" and "main menu".
		playAgain = new GButton("PLAY AGAIN", 220, 410, 80, 30, Color.GRAY);
		mainMenu = new GButton("MAIN MENU", 480, 410, 80, 30, Color.CYAN);
		
		playerInfo = new Player();
		//playerInfo.testFunc();
		
		currentName = playerInfo.getName();
		int currentScore = 500;
		
		ArrayList<String> yourScores = new ArrayList<String>();
		ArrayList<String> allScores = new ArrayList<String>();
		yourScores.add("999");
		yourScores.add("400");
		yourScores.add("100");
		
		playerInfo.saveScoresToFile(yourScores,currentPlayerFile);
		
		//currentScores = Player.ReadScoresFromFile(FILEDIRECTORY + playerInfo.getName() + ".txt");
		yourScores = playerInfo.ReadScoresFromFile(currentPlayerFile);
		System.out.println("Printing current player scores: " + yourScores );
		
		// add current score to sorted array list from playername.txt file
		boolean flag = false;
		for (int i = 0; i < yourScores.size(); i++)
			if (!flag && currentScore > Integer.parseInt(yourScores.get(i))) {
				yourScores.add(i, Integer.toString(currentScore));
				flag = true;
			}
		
		if (yourScores.size() > 3) {
			yourScores.remove(3);
		}
		System.out.println("After replace with current: " + yourScores);
		
		//save the new score array list in the file for current player file
		playerInfo.saveScoresToFile(yourScores,currentPlayerFile);
		
		allScores = playerInfo.ReadScoresFromFile(FILEDIRECTORY + "allScores.txt");
		
		yourScoresLabels = new ArrayList<GLabel>();
		allScoresLabels = new ArrayList<GLabel>();
		for(int i = 0; i < yourScores.size(); i++) {
			yourScoresLabels.add(new GLabel(yourScores.get(i),120,150+(30*i)));
		}
		
		for(int i = 0; i < allScores.size(); i++) {
			allScoresLabels.add(new GLabel(allScores.get(i),445,150+(30*i)));
		}
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		program.add(background);
		program.add(playAgain);
		program.add(mainMenu);
		program.add(titleLabel);
		program.add(yourScoresLabel);
		program.add(allScoresLabel);
		program.add(scoreRect1);
		program.add(scoreRect2);

		for(int i = 0; i < yourScoresLabels.size(); i++) {
			program.add(yourScoresLabels.get(i));
		}
		
		for(int i = 0; i < allScoresLabels.size(); i++) {
			program.add(allScoresLabels.get(i));
		}
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(playAgain);
		program.remove(mainMenu);
		program.remove(titleLabel);
		program.remove(yourScoresLabel);
		program.remove(allScoresLabel);
		program.remove(scoreRect1);
		program.remove(scoreRect2);
		program.remove(background);
		
		for(int i = 0; i < yourScoresLabels.size(); i++) {
			program.remove(yourScoresLabels.get(i));
		}
		
		for(int i = 0; i < allScoresLabels.size(); i++) {
			program.remove(allScoresLabels.get(i));
		}
		
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
