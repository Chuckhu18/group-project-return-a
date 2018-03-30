import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Song {
// TODO: see how you can read from file the songs and hardcode it into this class
	//look at the system design report on how to implement something like that
	private String songName;
	private int circleSize;
	private int shrinkSpeed;
	private int tempo;
	private String circleList = "";
	public Song(String s, int cS, int sS, int temp) {
		songName = s;
		circleSize = cS;
		shrinkSpeed = sS;
		tempo = temp;
	}
	
	public String getSongName() {
		return songName;
	}
	public int getCirlceSize() {
		return circleSize;
	}
	public String getCircleList() {
		return circleList;
	}
	public int getShrinkSpeed() {
		return shrinkSpeed;
	}
	public int getTempo() {
		return tempo;
	}
	public void run() {
		
	}
	public void readSong(String fileName) throws IOException {
		
	    FileReader file = new FileReader(fileName);
	    BufferedReader reader = new BufferedReader(file);

	    String line = reader.readLine();

	    while (line != null) {
	        songName += line;
	        line = reader.readLine();
	    }
	    System.out.println(songName);
	
	
	//implement read from file method 
	//to test this write a function to have all the variables printed out to the console
}
//	public static void main(String [ ] args)
//	{
//		Song sing = new Song(circleList, circleSize, circleSize, circleSize);
//		try {
//			readSong("Song.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(0);
//		}  
//	}
}