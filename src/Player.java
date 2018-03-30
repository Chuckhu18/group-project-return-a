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
	
//	private static BufferedWriter bw = null;
//	private static FileWriter fw = null;
	
	
	public static void main(String[] args) {
		
		scores = new ArrayList<String>();
		scores.add("chuck");
		scores.add("1");
		scores.add("123");

		saveScoreToFile();
		
		System.out.println(scores);
		
	}
	
//	public ArrayList<String> readFile(String file){
//		try {
//			Reader reader = new BufferedReader(reader);
//			BufferedReader buffer = new BufferedReader(reader);
//			
//		}
//	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList getScore(){
		return scores;
	}
	
	public static void saveScoreToFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		System.out.println("Writing file...");
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			
			for(String str: scores) {
				  bw.write(str);
				  System.out.println("writing: " + str);
				}
			System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
				}finally {
					try {
						if (bw != null)
							bw.close();
						System.out.println("bw closed");
						if (fw != null)
							fw.close();
						} catch (IOException ex) {
							ex.printStackTrace();
							}
					}
		}

	public void addScore(Song song, int scoreAdd) {
		
	}
	
}