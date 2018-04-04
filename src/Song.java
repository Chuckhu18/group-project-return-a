import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Song {
	// TODO: see how you can read from file the songs and hardcode it into this
	// class
	// look at the system design report on how to implement something like that
	// make a constructor for song
	private String songName;
	private double circleSize;
	private double shrinkSpeed;
	private int tempo;
	private String circleList;
	private String[] stringArray;

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

	public void run() {

	}

	// make this function outside of the class
	public void readSong(String fileName) throws IOException {

		// File file = new File("src/Song.txt");
		// for (String fileNames : file.list()) System.out.println(fileNames);
		FileReader file = new FileReader("src/" + fileName);
		BufferedReader reader = new BufferedReader(file);
		String line = reader.readLine();
		// System.out.println(line);
		SavingValues(line);
		// System.out.println(line);

		// System.out.println("hi");
		reader.close();

		// implement read from file method
		// to test this write a function to have all the variables printed out to the
		// console
	}

	// create a constuctor for this partciular manner of input
	// read from file outsdie of the class and create the song object within this
	// class using the function below
	// TODO make saving values a constructor
	private void SavingValues(String lines) {
		stringArray = lines.split(";");
		songName = stringArray[0];
		circleSize = Double.parseDouble(stringArray[1]);
		shrinkSpeed = Double.parseDouble(stringArray[1]);
		tempo = Integer.parseInt(stringArray[3]);
		circleList = stringArray[4];

		for (String string : stringArray) {

			System.out.println(string);
		}
	}

	public static void main(String[] args) {
		Song sing = new Song(); // using default constructor
		try {
			sing.readSong("Song.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
}