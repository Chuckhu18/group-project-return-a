
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
import acm.program.GraphicsProgram;

public class Player extends GraphicsProgram{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	
	Song song;
	private static String name;
	private static ArrayList<String> scores;
	private static ArrayList<GLabel> labels;
	private static GLabel label;
	public static final String FILENAME = "player.txt";

	// private static BufferedWriter bw = null;
	// private static FileWriter fw = null;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		GRect rect = new GRect(0, 0, 200, 200);
		scores = new ArrayList<String>();
		name = "Chuck";
		scores.add("1000");
		scores.add("9999");
		// scores.add("2000");
		
		saveScoresToFile();
		ReadScoreFromFile();

		GLabel nameLabel = new GLabel(name, 100, 100);
		add(nameLabel);
		for (int i = 0; i < scores.size(); i++) {
			GLabel scoreLabel = new GLabel(scores.get(i), 100, i * 50 + 150);
			//label.setFont("Times");
			add(scoreLabel);
		}
		
		
		add(rect);
		
	}
	
	
//	public static void main(String[] args) {
//
//		//creating a score list
//		scores = new ArrayList<String>();
//		scores.add("chuck");
//		scores.add("1000");
//		scores.add("123");
//		
//		//Save created array list to file "player.txt" with ; for each element.
//		saveScoreToFile();
//		//Read all elements from the player.txt file.
//		ReadScoreFromFile();
//	}

	public String getName() {
		return name;
	}

	public ArrayList getScore() {
		return scores;
	}

	public static void saveScoresToFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("Writing file ...");
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			
			bw.write( name +";");
			for (String str : scores) {
				bw.write(str+",");
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
		System.out.println("The song is " + song.getSongName());
	}

}