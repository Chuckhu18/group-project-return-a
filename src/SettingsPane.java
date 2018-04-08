import java.util.ArrayList;

import acm.graphics.GLabel;

public class SettingsPane extends GraphicsPane {
private MainApplication program;
private GLabel settings;
private GLabel easy;
private GLabel medium;
private GLabel hard;
private GLabel hotelCalifornia;
private GLabel back;
private GLabel next;
private ArrayList<GLabel> difficultyChoices;
private ArrayList<GLabel> songChoices;

	public SettingsPane(MainApplication app) {
		super();
		program = app;
		settings = new GLabel("Setting",(program.WINDOW_WIDTH/2)-settings.getWidth(),(program.WINDOW_HEIGHT/2)-settings.getHeight());
		
		program.add(settings);
		
		difficultyChoices.add(easy);
		difficultyChoices.add(medium);
		difficultyChoices.add(hard);
		songChoices.add(hotelCalifornia);
		
	}
	
	
	
	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}

}
