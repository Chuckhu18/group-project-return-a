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