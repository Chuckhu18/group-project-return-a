import acm.graphics.*;

public class Circle {
	private char letter;
	private static int inSize;
	private int outSize;
	private int x, y;
	private int speed;
	private boolean good;
	private GOval innerCircle;
	private GOval outerCircle;
	private GLabel text;
	
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
		// TODO: adds circle to the screen
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