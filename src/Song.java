import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Song {
	private String songName; // Name of song
	private double circleSize; // Starting size of the outside circles
	private double shrinkSpeed; // How fast the circles shrink
	private int tempo; // How often circles are created
	private String circleList; // list of characters that will appear on the screen
	private int startDelay; // How many ticks to delay the music starting
	private ArrayList<Integer> tempoChangeTimes =  new ArrayList<Integer>(); // timestamps when we should change tempo
	private ArrayList<Integer> tempoChangeValues =  new ArrayList<Integer>(); // amount to change tempo by
	
	/***
	 * 
	 * @param s
	 *            Song Name
	 * @param cS
	 *            Circle Size
	 * @param sS
	 *            Shrink Speed
	 * @param temp
	 *            Tempo
	 * @param cL
	 *            String of characters
	 */
	public Song(String s, double cS, double sS, int temp, String cL) {
		songName = s;
		circleSize = cS;
		shrinkSpeed = sS;
		tempo = temp;
		circleList = cL;
	}

	public Song() {
		songName = "";
		circleSize = 0.0;
		shrinkSpeed = 0.0;
		tempo = 0;
		circleList = "";
	}

	public String getSongName() {
		return songName;
	}

	public double getCircleSize() {
		return circleSize;
	}

	public String getCircleList() {
		return circleList;
	}

	public double getShrinkSpeed() {
		return shrinkSpeed;
	}

	public int getTempo() {
		return tempo;
	}
	
	public ArrayList<Integer> getTempoChangeTimes() {
		return tempoChangeTimes;
	}
	
	public ArrayList<Integer> getTempoChangeValues() {
		return tempoChangeValues;
	}
	
	public int getStartDelay() {
		return startDelay;
	}
	
	public Song(String filename) {
		try {
			readSong("songs/"+filename+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	// make this function outside of the class
	private void readSong(String fileName) throws IOException {		
	    FileReader file = new FileReader(fileName);
	    BufferedReader reader = new BufferedReader(file);
	    
	    // Read in guarenteed aspects of a song
	    songName = reader.readLine();
	    circleSize = Double.parseDouble(reader.readLine());
	    shrinkSpeed = Double.parseDouble(reader.readLine());
	    tempo = Integer.parseInt(reader.readLine());
	    startDelay = Integer.parseInt(reader.readLine());
	    circleList = reader.readLine();
	    
	    
	    // Read in parts that might not be in every song
	    String line = reader.readLine();
	    if(line!=null) {
	    	String[] list = line.split(";");
	    	for(int i = 0; i < list.length; i++)
	    		tempoChangeTimes.add(Integer.parseInt(list[i]));
	    	line = reader.readLine();
	    }
	    if(line!=null) {
	    	String[] list = line.split(";");
	    	for(int i = 0; i < list.length; i++)
	    		tempoChangeValues.add(Integer.parseInt(list[i]));
	    }

	   reader.close();
}
	
	//create a constuctor for this partciular manner of input
	// read from file outsdie of the class and create the song object within this class using the function below
	
		
	public static void main(String[] args) {
		Song sing = new Song(); // using default constructor
		try {
			sing.readSong("src/songs/Song.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
}