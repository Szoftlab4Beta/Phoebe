package graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;

import graphics.GraphicsController.ImageID;
import szoftlab4Proto.FieldObject;
import szoftlab4Proto.Tile;

public class TileGraphics {
	Tile tile;
	int screenPosX, screenPosY;
	public JLayeredPane panel;
	
	public TileGraphics(Tile tile, int posX, int posY) {
		this.tile = tile;
		this.screenPosX = posX;
		this.screenPosY = posY;
	}
	
	public ImageID[] getSprites(){
		List<ImageID> ret = new ArrayList<ImageID>();
		if(tile.getPatch() != null)
			ret.add(tile.getPatch().getGraphics());
		if(tile.getObjects() != null)
			for(FieldObject object : tile.getObjects())
				ret.add(object.getGraphics());
		if(ret.size() == 0)
			return null;
		return ret.toArray(new ImageID[0]);
	}
	
	public boolean hasChanged(){
		return tile.hasChanged();
	}
	
	public int getX(){
		return screenPosX;
	}
	
	public int getY(){
		return screenPosY;
	}
}
