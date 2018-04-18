
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
	private static ArrayList<String> scores;
	public static final String FILENAME = "player.txt";
	public static String currentPlayerFile = "";
	
	public Player() {
		//creating a score list
		scores = new ArrayList<String>();
	}
	
	public static void testFunc() {
		name = "Chuck";
		currentPlayerFile = name + ".txt";
		//scores.add(Integer.toString(l.getScore()));
		scores.add("100");
		scores.add("123");
		scores.add("9999");
		
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	}

	// private static BufferedWriter bw = null;
	// private static FileWriter fw = null;
	
	public static void main(String[] args) {
		//creating a score list
		scores = new ArrayList<String>();
		
		testFunc();
		
		//Save created array list to file "player.txt" with ; for each element.
		saveScoresToFile(currentPlayerFile);
		//Read all elements from the player.txt file.
		String readfromfile = ReadScoreFromFile(currentPlayerFile);
		System.out.println("Printing again: " + readfromfile );
	}


	public ArrayList<String> getScore() {
		return scores;
	}

	public void addScore(String scoreAdd) {
		scores.add(scoreAdd);
	}


	public static void saveScoresToFile(String f) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("Writing file ...");
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			//bw.write( name +";");
			for (String str : scores) {
				bw.write(str+" ");
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

/*	public static void ReadScoreFromFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;
			
			System.out.println("Printing what's in the player.txt:");
			
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	*/
	public static String ReadScoreFromFile(String filename) {
		String sCurrentLine = "";
		String returnLine = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			System.out.println("Printing what's in the "+filename);
			
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