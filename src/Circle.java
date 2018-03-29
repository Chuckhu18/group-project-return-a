import acm.graphics.*;

public class Circle {
	private char letter;
	private static int inSize = 20; // Picking random number for testing, TODO: replace with real values
	private int outSize;
	private int x, y;
	private int speed;
	private boolean good;
	private GOval innerCircle;
	private GOval outerCircle;
	private GLabel text;
	
	/**
	 * Circle constructor
	 * @param letter Letter that will be in the circle
	 * @param outSize How much bigger the outer circle is than the inner circle
	 * @param x - X coordinate of circle's location
	 * @param y - Y coordinate of circle's location
	 * @param speed - How fast the circle shrinks
	 * @param good - If the circle gives points or not
	 */
	public Circle(char letter, int outSize, int x, int y, int speed, boolean good) {
		this.letter = letter;
		this.outSize = inSize+outSize;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.good = good;
	}
	
	
	/**
	 * Full constructor - will never be used in practice
	 */
	public Circle(char letter, int outSize, int x, int y, int speed, boolean good, GOval innerCircle, GOval outerCircle,
			GLabel text) {
		this.letter = letter;
		this.outSize = outSize;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.good = good;
		this.innerCircle = innerCircle;
		this.outerCircle = outerCircle;
		this.text = text;
		draw();		
	}
	
	
	/**
	 * Shrinks the circle by the amount specified by the speed variable in this class
	 */
	private void shrink(){
		// TODO: makes circle shrink
	}
	
	/**
	 * Adds the circle to the screen at this object's X/Y coordinates
	 */
	private void draw() {
		// TODO: creates GOvals and draws them to the screen
	}
	
	/**
	 * Removes the circle from the screen
	 */
	private void remove() {
		// TODO: removes circle from the screen
	}
	
	/**
	 * For testing purposes display the contents of this circle as a string
	 * @return
	 */
	public String toString() {
		String toReturn = "";
		
		toReturn += "Letter: "+letter+", Size (in,out): ("+inSize+","+outSize+"), ";
		toReturn += "X/Y Location: ("+x+","+y+"), Speed: "+speed+", Good: "+good;
		
		return toReturn;
	}
	
	public char getLetter() {
		return letter;
	}
	public static int getInSize() {
		return inSize;
	}
	public int getOutSize() {
		return outSize;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}	
}