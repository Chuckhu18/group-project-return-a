
/**
 * File: Player
 * -------------------------
 * This class simply stores the user’s data! This data includes the user’s name, 
 * and a list of scores from high to low. 
 * 
 * @author Chuck
 */

import java.awt.Font;
import java.io.*;
import java.util.ArrayList;

public class Player{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	Song song;
	//Level l = new Level();
	private static String name;
	private static int currentScore;
	private static ArrayList<String> scores;
	private static ArrayList<String> allScores;
	private static ArrayList<String> allScoresName;
	public static final String FILENAME = "allScores.txt";
	public static String currentPlayerFile = "";
	
	public Player() {
		//creating a score list
		//scores = new ArrayList<String>();
	}
	
	public static void main(String[] args) {
		//creating a score array list
		scores = new ArrayList<String>();
		allScores = new ArrayList<String>();
		testFunc();
		Player player = new Player();
		//initialize files with created scores
		player.saveScoresToFile(scores,currentPlayerFile);
		
		//TODO: the name should get from user input in main menu 
		/*
		if((name + ".txt") != currentPlayerFile) {
			System.out.println("Creating new player info...");
			scores.add(Integer.toString(currentScore));
			saveScoresToFile(scores,currentPlayerFile);
		}
		*/
		
		//read all elements from name.txt file and pull scores out
		scores = player.ReadScoresFromFile(currentPlayerFile);
		System.out.println("Printing current player scores: " + scores );
		
		//add current score to sorted array list from playername.txt file
		boolean flag = false;
		for(int i = 0; i < scores.size(); i++)
			if(!flag && currentScore > Integer.parseInt(scores.get(i))) {
				scores.add(i, Integer.toString(currentScore));
				flag = true;
			}
		
		if (scores.size() > 3) {
			scores.remove(3);
		}
		System.out.println("After replace with current: " + scores );
		
		//save the new score array list in the file for current player file
		player.saveScoresToFile(scores,currentPlayerFile);
		
		
		allScores =  player.ReadScoresFromFile(FILENAME);
		System.out.println("Printing all scores: " + allScores );
		
		//Save created array list to file "player.txt" with ; for each element.
		//saveScoresToFile(currentPlayerFile);
	}
	
	public static void testFunc() {
		name = "Chuck";
		currentPlayerFile = name + ".txt";
		currentScore = 1000;
		scores.add("999");
		scores.add("400");
		scores.add("200");
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}

	public ArrayList<String> getScore() {
		return scores;
	}

	public void addScore(String scoreAdd) {
		scores.add(scoreAdd);
	}

	public void saveScoresToFile(ArrayList<String> inStr, String f) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("Writing file ...");
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			for (String str : inStr) {
				bw.write(str+"\n");
				System.out.println("writing: " + str);
			}
			System.out.println("Done writing");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				System.out.println("bw fw closed");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public ArrayList<String> ReadScoresFromFile(String filename) {
		ArrayList<String> read = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String sCurrentLine;
			System.out.println("Printing what's in the "+filename+": ");
			
			while ((sCurrentLine = br.readLine()) != null) {
				read.add(sCurrentLine);
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}
	
	public static String ReadScoreFromFile(String filenam) {
		String sCurrentLine = "";
		String returnLine = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filenam))) {
			System.out.println("Printing what's in the "+ filenam);
			
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				returnLine += sCurrentLine;
				sCurrentLine += sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnLine;
	}
}