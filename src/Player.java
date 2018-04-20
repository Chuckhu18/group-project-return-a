
/**
 * File: Player
 * -------------------------
 * This class simply stores the user’s data! This data includes the user’s name, 
 * and a list of scores from high to low. 
 * 
 * @author Chuck
 */

import java.io.*;
import java.util.ArrayList;

public class Player {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	private String name;
	private int currentScore;
	private ArrayList<String> scores;
	public static final String FILENAME = "allScores.txt";
	public static String currentPlayerFile = "";
	private MainApplication main;

	public Player() {
		main = new MainApplication();
		setName(main.getName());
		setCurrentScore(main.getScore());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public void setCurrentScore(int s) {
		this.currentScore = s;
	}

	public int getCurrentScore() {
		return this.currentScore;
	}

	public ArrayList<String> getScoreList() {
		return this.scores;
	}

	public void addScoreToList(String scoreAdd) {
		this.scores.add(scoreAdd);
	}

	public void saveScoresToFile(ArrayList<String> inStr, String f) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);

			for (String str : inStr) {
				bw.write(str + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public ArrayList<String> ReadScoresFromFile(String filename) {
		ArrayList<String> read = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				read.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}
}