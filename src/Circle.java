import java.text.DecimalFormat;
import acm.graphics.*;

public class Circle {
	DecimalFormat df = new DecimalFormat("#.###"); // Used so the toString function prints nicer numbers for location
	private char letter;
	private static double inSize = 30.0; // Picking random number for testing, TODO: replace with real values
	private double outSize;
	private double x, y;
	private double speed;
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
	public Circle(char letter, double outSize, double x, double y, double speed, boolean good) {
		this.letter = letter;
		this.outSize = outSize;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.good = good;
		
		innerCircle = new GOval(x-(inSize/2),y-(inSize/2),inSize,inSize);
		outerCircle = new GOval(innerCircle.getX()-(outSize/2),innerCircle.getY()-(outSize/2),inSize+outSize,inSize+outSize);
		
		// TODO: calculate center of circle to correctly center the text
		text = new GLabel(Character.toString(letter),x-inSize/2,y-inSize/2);
	}
	
	
	/**
	 * Full constructor - will never be used in practice
	 * TODO: remove before final game is done, not sure if it might be needed to test later
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
	}
	
	
	/**
	 * Shrinks the circle by the amount specified by the speed variable in this class.
	 * Also updates the outer GOval so it reflects this change
	 */
	public void shrink(){
		// TODO: makes circle shrink
		outSize -= speed;
		
		// Does math to keep the outer circle centered
		double newX = innerCircle.getX() - outSize / 2;
		double newY = innerCircle.getY() - outSize / 2;
				
		outerCircle.setLocation(newX, newY);
		outerCircle.setSize(inSize+outSize, inSize+outSize);
	}
	
	/**
	 * For testing purposes display the contents of this circle as a string
	 * @return
	 */
	public String toString() {
		String toReturn = "";
		
		toReturn += "Letter: "+letter+", Size (in,out): ("+inSize+","+new Double(inSize+outSize)+"), ";
		toReturn += "X/Y Location: ("+df.format(x)+","+df.format(y)+"), Speed: "+speed+", Good: "+good;
		
		return toReturn;
	}
	
	public char getLetter() {
		return letter;
	}
	public static double getInSize() {
		return inSize;
	}
	public double getOutSize() {
		return outSize;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public GOval getInnerCircle() {
		return innerCircle;
	}
	public GOval getOuterCircle() {
		return outerCircle;
	}	
	public GLabel getLabel() {
		return text;
	}
}