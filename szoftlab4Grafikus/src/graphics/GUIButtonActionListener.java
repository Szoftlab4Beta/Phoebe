package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import szoftlab4Proto.Game;
import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

public class GUIButtonActionListener implements ActionListener {

	Direction dir = Direction.None;
	PatchType patch = PatchType.None;
	
	Game game;
	GraphicsController window;
	
	public GUIButtonActionListener(Game game) {
		this.game = game;
	}
	
	public void setMainWindow(GraphicsController window){
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonMsg = e.getActionCommand();
		if(buttonMsg.equals("Left"))
			dir = Direction.West;
		else if(buttonMsg.equals("Up"))
			dir = Direction.North;
		else if(buttonMsg.equals("Right"))
			dir = Direction.East;
		else if(buttonMsg.equals("Down"))
			dir = Direction.South;
		else if(buttonMsg.equals("None"))
			dir = Direction.None;
		else if(buttonMsg.equals("Oil"))
			patch = PatchType.Oil;
		else if(buttonMsg.equals("Goo"))
			patch = PatchType.Goo;
		else if(buttonMsg.equals("Patch_None"))
			patch = PatchType.None;
		else if(buttonMsg.equals("Confirm")){
			game.setTurn(dir, patch);
			window.resetButtons();
			patch = PatchType.None;
			dir = Direction.None;
		}
		else if(buttonMsg.equals("NewGame")){
			window.cleanUp();
			window.show();
			window.setMapImage();
			game.newGame(window.getPlayerNum(), window.getTurns(), window.getMapFile());
		}
			
	}

}
