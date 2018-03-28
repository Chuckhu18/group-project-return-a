

public class Song {
// TODO: see how you can read from file the songs and hardcode it into this class
	//look at the system design report on how to implement something like that
	public Song(String fileName) {
		//implement read from file method 
		//to test this write a function to have all the variables printed out to the console
	}
	private String songName;
	private int circleSize;
	private int shrinkSpeed;
	private int tempo;
	private String circleList;
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
}