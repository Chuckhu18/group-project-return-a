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
	public static String currentPlayerFile = "";
	
	private MainApplication program;
	GButton playAgain;
	GButton mainMenu;
	GLabel titleLabel, yourScoresLabel, allScoresLabel;
	
	GLabel nameLabel;
	private ArrayList<GLabel> scoresLabels;
	GRect scoreRect1,scoreRect2;
	
	GImage background;
	
	Player playerInfo;
	
	GLabel get1;
	GLabel get2;
	GLabel get3;
	
	GLabel get4;
	GLabel get5;
	GLabel get6;
	GLabel get7;
	
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
		
		/*
		nameLabel = new GLabel("",200,180);
		nameLabel.setFont(new Font("Serif", Font.BOLD, 24));
		
		scoresLabels = new GLabel("", 300,180);
		scoresLabels.setFont(new Font("Serif", Font.BOLD, 24));
		*/
		playerInfo = new Player();
		
		//Player.testFunc();
		//TODO: change this to get the name from main menu
		//nameLabel.setLabel(playerInfo.getName());
		
//		int arrSize = playerInfo.getScore().size();
//		for(int i = 0; i < arrSize; i++) {
//			scoresToDisplay += playerInfo.getScore().get(i) + " ";
//		}
		//scoresToDisplay = playerInfo.ReadScoreFromFile();
//		scoresLabels.setLabel(scoresToDisplay);
		
		ArrayList<String> currentScores = new ArrayList<String>();
		ArrayList<String> allScores = new ArrayList<String>();
		
		scoresLabels = new ArrayList<GLabel>();
		
		//currentScores = Player.ReadScoresFromFile(FILEDIRECTORY + playerInfo.getName() + ".txt");
		currentScores = playerInfo.ReadScoresFromFile(FILEDIRECTORY + "Chuck.txt");
		allScores = playerInfo.ReadScoresFromFile(FILEDIRECTORY + "allScores.txt");
		
		get1 = new GLabel(currentScores.get(0),120,150);
		get2 = new GLabel(currentScores.get(1),120,180);
		get3 = new GLabel(currentScores.get(2),120,210);
		
		get4 = new GLabel(allScores.get(0),445,150);
		get5 = new GLabel(allScores.get(1),445,180);
		get6 = new GLabel(allScores.get(2),445,210);
		get7 = new GLabel(allScores.get(3),445,240);
		
		
		
		/*Dont know how to do the arraylist for glabel
		for(int i = 0; i < currentScores.size(); i++) {
			scoresLabels.get(i).setLabel(currentScores.get(i));
			scoresLabels.get(i).setLocation(120, 100*(i+1));
			//scoresLabels.set(i, new GLabel(currentScores.get(i), 120, 100*(i+1)));
		}
		*/
		
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
		
		program.add(get1);
		program.add(get2);
		program.add(get3);
		program.add(get4);
		program.add(get5);
		program.add(get6);
		program.add(get7);
		
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
		
		program.remove(get1);
		program.remove(get2);
		program.remove(get3);
		program.remove(get4);
		program.remove(get5);
		program.remove(get6);
		program.remove(get7);
		
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
