import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.*;

public class EndOfGamePane extends GraphicsPane {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final String ALLSCORESFILE = "../allScores.txt";
	public static final String FILEDIRECTORY = "../";
	public static String currentPlayerFile = "";

	private MainApplication program;
	GButton playAgain;
	GButton mainMenu;
	GLabel titleLabel, yourScoresLabel, allScoresLabel, currentPlayerNameLabel;

	GLabel nameLabel;
	private ArrayList<GLabel> yourScoresLabels;
	private ArrayList<GLabel> allScoresLabels;
	GRect scoreRect1, scoreRect2;

	GImage background;

	Player playerInfo;
	String currentName;
	int currentScore;
	ArrayList<String> yourScores = new ArrayList<String>();
	ArrayList<GObject> screenObjects = new ArrayList<GObject>();

	public EndOfGamePane(MainApplication app) {
		program = app;
		// adding background picture
		background = new GImage("back.jpg", 0, 0);
		background.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		screenObjects.add(background);

		// adding rectangles for displaying scores
		scoreRect1 = new GRect(100, 120, 275, 275);
		scoreRect1.setFillColor(Color.WHITE);
		scoreRect1.setFilled(true);
		screenObjects.add(scoreRect1);

		scoreRect2 = new GRect(425, 120, 275, 275);
		scoreRect2.setFillColor(Color.WHITE);
		scoreRect2.setFilled(true);
		screenObjects.add(scoreRect2);

		// adding labels
		titleLabel = new GLabel("HIGH SCORES", 280, 60);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
		screenObjects.add(titleLabel);

		yourScoresLabel = new GLabel("YOUR SCORES", 130, 120);
		yourScoresLabel.setFont(new Font("Serif", Font.BOLD, 30));
		screenObjects.add(yourScoresLabel);

		allScoresLabel = new GLabel("ALL SCORES", 450, 120);
		allScoresLabel.setFont(new Font("Serif", Font.BOLD, 30));
		screenObjects.add(allScoresLabel);

		// buttons for "play again" and "main menu".
		playAgain = new GButton("PLAY AGAIN", 220, 410, 80, 30, Color.GRAY);
		mainMenu = new GButton("MAIN MENU", 480, 410, 80, 30, Color.CYAN);
		screenObjects.add(playAgain);
		screenObjects.add(mainMenu);

	}

	// ***display current player's scores from current player's name .txt file
	public void displayYourScores() {

		currentName = playerInfo.getName();
		currentScore = playerInfo.getCurrentScore();

		currentPlayerNameLabel = new GLabel(currentName + ":", 120, 150);

		// initialize score list
		yourScores.add("0");

		// check if the current score is higher than previous scores,
		// if so, then replace it with current score
		boolean flag1 = false;
		for (int i = 0; i < yourScores.size(); i++)
			if (!flag1 && currentScore > Integer.parseInt(yourScores.get(i))) {
				yourScores.add(i, Integer.toString(currentScore));
				flag1 = true;
			}
		// will remove the lowest current score if arraylist is full
		while (yourScores.size() > 3) {
			yourScores.remove(3);
		}

		// after all scores sorted, then add GLabel for them
		yourScoresLabels = new ArrayList<GLabel>();
		for (int i = 0; i < yourScores.size(); i++) {
			yourScoresLabels.add(new GLabel(yourScores.get(i), 120, 180 + (30 * i)));
		}

	}

	// ***display from allScores.txt file
	public void displayAllScores() {
		ArrayList<String> allScores = new ArrayList<String>();
		allScoresLabels = new ArrayList<GLabel>();

		allScores = playerInfo.ReadScoresFromFile(ALLSCORESFILE);

		String splitArr[];
		ArrayList<String> splitName = new ArrayList<String>();
		ArrayList<String> splitScore = new ArrayList<String>();
		ArrayList<String> newAllScore = new ArrayList<String>();

		// splitting name and score by ","
		int nameIndex = -1;
		int scoreIndex = -1;
		boolean flag2 = false;
		for (int i = 0; i < allScores.size(); i++) {
			splitArr = allScores.get(i).split(",");
			if (splitArr[0].equals(currentName)) {
				nameIndex = i;
			}
			if (!flag2 && currentScore > Integer.parseInt(splitArr[1])) {
				scoreIndex = i;
				flag2 = true;
			}
			splitName.add(splitArr[0]);
			splitScore.add(splitArr[1]);
		}

		// replace high score if current score is higher than any of in the allScore.txt
		if (nameIndex != -1) {
			if (currentScore > Integer.parseInt(splitScore.get(nameIndex))) {
				splitScore.set(nameIndex, Integer.toString(currentScore));
				splitName.add(scoreIndex, currentName);
				splitScore.add(scoreIndex, Integer.toString(currentScore));
				splitName.remove(nameIndex + 1);
				splitScore.remove(nameIndex + 1);
			}
		}

		// combining replaced name and score as a string
		for (int i = 0; i < allScores.size(); i++) {
			newAllScore.add(splitName.get(i) + "," + splitScore.get(i));
		}

		// if player name not in record, add it to the high score list
		if (nameIndex == -1 && scoreIndex != -1) {
			newAllScore.add(scoreIndex, currentName + "," + currentScore);
		}

		// the maximum amount of high scores is 7, which are how many can be displayed
		// on the screen.
		while (newAllScore.size() > 7) {
			newAllScore.remove(7);
		}

		// save the new high scores to file
		playerInfo.saveScoresToFile(newAllScore, ALLSCORESFILE);

		// setup GLabel for new high Scores
		for (int i = 0; i < newAllScore.size(); i++) {
			allScoresLabels.add(new GLabel(newAllScore.get(i), 445, 150 + (30 * i)));
		}

	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		for (GObject object : screenObjects) {
			program.add(object);
		}

		playerInfo = program.getPlayer();
		playerInfo.setCurrentScore(program.getScore());

		displayYourScores();
		displayAllScores();

		program.add(currentPlayerNameLabel);

		
		for(int i = 0; i < yourScoresLabels.size(); i++) {
			if(!yourScoresLabels.get(i).getLabel().equals("0")) {
				program.add(yourScoresLabels.get(i));
			}
		}

		for (int i = 0; i < allScoresLabels.size(); i++) {
			program.add(allScoresLabels.get(i));
		}

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		for (GObject object : screenObjects) {
			program.remove(object);
		}
		for (int i = 0; i < yourScoresLabels.size(); i++) {
			program.remove(yourScoresLabels.get(i));
		}

		for (int i = 0; i < allScoresLabels.size(); i++) {
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
			program.switchToLevel();
		}
	}
}
