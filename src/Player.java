
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
import acm.graphics.*;

public class Player {
	Song song;
	private String name;
	private static ArrayList<String> scores;
	public static final String FILENAME = "player.txt";

	// private static BufferedWriter bw = null;
	// private static FileWriter fw = null;

	public static void main(String[] args) {

		//creating a score list
		scores = new ArrayList<String>();
		scores.add("chuck");
		scores.add("1000");
		scores.add("123");
		
		//Save created array list to file "player.txt" with ; for each element.
		saveScoreToFile();
		//Read all elements from the player.txt file.
		ReadScoreFromFile();
	}

	public String getName() {
		return name;
	}

	public ArrayList getScore() {
		return scores;
	}

	public static void saveScoreToFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("Writing file ...");
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);

			for (String str : scores) {
				bw.write(str+";");
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

	public static void ReadScoreFromFile() {
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

	public void addScore(Song song, int scoreAdd) {

	}

}