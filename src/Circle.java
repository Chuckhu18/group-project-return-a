import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import acm.graphics.*;

public class Circle {
	private DecimalFormat df = new DecimalFormat("#.###"); // Used so the toString function prints nicer numbers for location
	private int removeCounter = 0; // Initialize removeCounter to 0
	private char letter;
	private static double inSize = 50.0; // Size of inner circle
	private double outSize;
	private double x, y;
	private double speed;
	private boolean good;
	private GOval innerCircle;
	private GOval outerCircle;
	private GLabel text;

	/**
	 * Circle constructor
	 * 
	 * @param letter
	 *            Letter that will be in the circle
	 * @param outSize
	 *            How much bigger the outer circle is than the inner circle
	 * @param x
	 *            - X coordinate of circle's location
	 * @param y
	 *            - Y coordinate of circle's location
	 * @param speed
	 *            - How fast the circle shrinks
	 * @param good
	 *            - If the circle gives points or not
	 */
	public Circle(char letter, double outSize, double x, double y, double speed, boolean good) {
		this.letter = letter;
		this.outSize = outSize;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.good = good;

		// Create GOval objects
		innerCircle = new GOval(x - (inSize / 2), y - (inSize / 2), inSize, inSize);
		outerCircle = new GOval(innerCircle.getX() - (outSize / 2), innerCircle.getY() - (outSize / 2),
				inSize + outSize, inSize + outSize);

		// Center the text in the circle
		text = new GLabel(Character.toString(letter), x, y);
		text.setLocation(x-text.getWidth(), y + text.getHeight()/1.5);
		text.setFont(new Font("Arial",0,40)); // Makes text easier to read

		// Makes good circles blue and bad circles red
		if (good) setColor(Color.BLUE);
		else setColor(Color.RED);

	}

	/**
	 * Shrinks the circle by the amount specified by the speed variable in this
	 * class. Also updates the outer GOval so it reflects this change
	 */
	public void shrink() {
		outSize -= speed;

		// Does math to keep the outer circle centered
		double newX = innerCircle.getX() - outSize / 2;
		double newY = innerCircle.getY() - outSize / 2;

		outerCircle.setLocation(newX, newY);
		outerCircle.setSize(inSize + outSize, inSize + outSize);

	}

	/**
	 * Hides outer circles so that we can display the Hit/Miss message
	 */
	public void removeCircles() {
		innerCircle.setVisible(false);
		outerCircle.setVisible(false);
		removeCounter++;
		text.setFont(new Font("Arial",0,16)); // Makes font smaller when circles are removed
	}

	/**
	 * Remove the GObjects entirely
	 */
	public void removeLabel() {
		text.setVisible(false);
		text = null;
		innerCircle = null;
		outerCircle = null;
	}	
	
	/**
	 * Updates color of circle as it shrinks
	 * @param origSize Original size of circles
	 */
	public void updateColor(double origSize) {
		Color cirColor = text.getColor(); // Default to no change
		if(good) {
			if (outSize <= origSize/100) { // PERFECT
				cirColor = Color.WHITE;
			} else if(outSize <= origSize/10) { // AMAZING
				cirColor = Color.CYAN;
			}
		}
		setColor(cirColor);
	}
	
	/**
	 * Sets the different parts of the circle to be a new color
	 * @param color Color to set everything to
	 */
	public void setColor(Color color) {
		innerCircle.setColor(color);
		outerCircle.setColor(color);
		text.setColor(color);
	}
	
	/**
	 * Updates the text of the label when removing the circles
	 * @param text Text to use as the label
	 * @param color Color to make the text
	 */
	public void updateLabel(String str, Color color) {
		text.setLabel(str);
		text.setColor(color);
		removeCircles();
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

	public int getRemoveCounter() {
		return removeCounter;
	}

	public void setRemoveCounter(int removeCounter) {
		this.removeCounter = removeCounter;
	}
	
	public boolean isGood() {
		return good;
	}
}