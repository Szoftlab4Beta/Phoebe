package szoftlab4Szkeleton;

import java.util.Map;

import szoftlab4Szkeleton.VectorClass.Direction;

public abstract class Tile implements IAcceptor{

	Map<Direction, Tile> sides;
	
	public Tile() {
		
	}
	
	public void setSide(Direction d, Tile tile){
		
	}
	
	public Tile getSide(Direction d){
		return null;
	}

}
