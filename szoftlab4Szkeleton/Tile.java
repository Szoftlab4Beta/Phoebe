package szoftlab4Szkeleton;

import java.util.Map;

import szoftlab4Szkeleton.VectorClass.Direction;

public abstract class Tile implements IAcceptor{

	Map<Direction, Tile> sides;
	
	public Tile() {
        Logger.logCreate(this, "tile");
		
	}
	
	public void setSide(Direction d, Tile tile){
        Logger.logCall(this, "setSide(" + d + tile +")");
        Logger.logReturn(this, "setSide()");
		
	}
	
	public Tile getSide(Direction d){
		return null;
	}

}
