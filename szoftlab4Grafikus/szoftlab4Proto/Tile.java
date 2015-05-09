package szoftlab4Proto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import szoftlab4Proto.VectorClass.Direction;

public abstract class Tile implements IAcceptor{
	
	public Map<Direction, Tile> sides; //TODO: Biztos public?
	boolean changed = true;
	
	public Tile(){
		sides = new HashMap<Direction, Tile>();
	}
	
	public void setSide(Direction d, Tile tile){
		sides.put(d, tile);
	}
	
	public boolean isNeighbourOf(Tile t){
		for(Direction d : Direction.values()){
			if(t.equals(sides.get(d)))
				return true;
		}
		return false;
	}
	
	public Tile getTile(Direction d){		//TODO: fuck its getSide well w/e
		return sides.get(d);
	}
	
	public void addObject(FieldObject o){
		
	}
	
	public void removeObject(FieldObject o){

	}

	@Override
	public void accept(IColliding colliding) {

	}

	public boolean hasChanged() {
		return changed;
	}

	public List<FieldObject> getObjects() {
		return null;
	}

	public Patch getPatch() {
		return null;
	}
}
