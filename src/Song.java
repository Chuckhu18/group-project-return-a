import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Song {
// TODO: see how you can read from file the songs and hardcode it into this class
	//look at the system design report on how to implement something like that
	private String songName = "";
	private int circleSize = 0;
	private int shrinkSpeed =0;
	private int tempo = 0;
	private String circleList = "";
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
}