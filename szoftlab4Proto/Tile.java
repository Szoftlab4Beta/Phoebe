package szoftlab4Proto;

import java.util.Map;

import szoftlab4Proto.VectorClass.Direction;

public abstract class Tile implements IAcceptor{
	
	public Map<Direction, Tile> sides; //TODO: Biztos public?
	
	public void setSide(Direction d, Tile tile){
		
	}
	
	public Tile getTile(Direction d){
		return null;
	}

	@Override
	public void accept(IColliding colliding) {
		// TODO Auto-generated method stub
		
	}
}
