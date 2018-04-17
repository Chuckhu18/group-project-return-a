
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
import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Player extends GraphicsProgram{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 480;
	public static final int NUM_PIXELS = WINDOW_WIDTH * WINDOW_HEIGHT;
	
	Song song;
	Level s;
	private static String name;
	private static ArrayList<String> scores;
	private static ArrayList<GLabel> labels;
	private static GLabel label;
	public static final String FILENAME = "player.txt";
	
	public Player() {
		//creating a score list
		scores = new ArrayList<String>();
	}
	
	public static void testFunc() {
		name = "Chuck";
		scores.add("1000");
		scores.add("666");
		scores.add("12347");
	}

	// private static BufferedWriter bw = null;
	// private static FileWriter fw = null;
	
/*  //Created own screen for testing
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		GRect rect = new GRect(10, 10, 800-20, 480-20);
		scores = new ArrayList<String>();
		name = "Chuck";
		//scores.add(Integer.toString(s.getScore()));
		scores.add("9999");
		//scores.add("99");
		
		saveScoresToFile();
		ReadScoreFromFile();

		GLabel nameLabel = new GLabel(name, 100, 100);
		nameLabel.setFont(new Font("Arial",0,30));
		add(nameLabel);
		
		for (int i = 0; i < scores.size(); i++) {
			GLabel scoreLabel = new GLabel(scores.get(i), 100, i * 80 + 200);
			scoreLabel.setFont(new Font("Arial",0,30));
			add(scoreLabel);
		}
		
		add(rect);
		
	}
	*/
	
	public static void main(String[] args) {
		name = "Chuck";
		//creating a score list
		scores = new ArrayList<String>();
		
		testFunc();
		
		//Save created array list to file "player.txt" with ; for each element.
		saveScoresToFile();
		//Read all elements from the player.txt file.
		String readfromfile = ReadScoreFromFile(FILENAME);
		System.out.println("Printing again: " + readfromfile );
	}

	public String getName() {
		return Player.name;
	}

	public ArrayList<String> getScore() {
		return this.scores;
	}
	
	/*
	public void addScore(Song song, int scoreAdd) {
		System.out.println("The song is " + song.getSongName());
	}
*/

	public void addScore(String scoreAdd) {
		scores.add(scoreAdd);
	}


	public static void saveScoresToFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;

		System.out.println("Writing file ...");
		try {
			fw = new FileWriter(FILENAME);
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
			System.out.println("Printing what's in the player.txt:");
			
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