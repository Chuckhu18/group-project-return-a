import java.awt.Color;
import java.awt.Component;

import acm.graphics.*;
import acm.program.*;
public class MainMenu extends GraphicsProgram{
	Player player = new Player();
	GButton play = new GButton("Play", 300, 200, 50, 30);
	
	public MainMenu(Player currplayer){
		GButton play = new GButton("Play", 300, 200, 50, 30, Color.CYAN);
	}
	public void display() {
		setSize(500, 500);
		play.Add(play);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//play = GButton("Play", 300, 200, 50, 30);
		Player player = new Player();
		MainMenu main = new MainMenu(player);
		main.display();
		
	}
	

}
