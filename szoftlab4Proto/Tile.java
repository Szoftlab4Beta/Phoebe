package szoftlab4Proto;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import szoftlab4Proto.VectorClass.Direction;

public abstract class Tile implements IAcceptor{
	
	public Map<Direction, Tile> sides; //TODO: Biztos public?
	List<IAcceptor> objects;
	
	public Tile(){
		sides = new HashMap<Direction, Tile>();
		objects = new ArrayList<IAcceptor>();
	}
	
	public void setSide(Direction d, Tile tile){
		sides.put(d, tile);
	}
	
	public Tile getTile(Direction d){
		return sides.get(d);
	}
	public void addObject(IAcceptor o){
		objects.add(o);
	}
	
	public void removeObject(IAcceptor o){
		objects.remove(o);
	}

	@Override
	public void accept(IColliding colliding) {
		// TODO Auto-generated method stub
		
	}
}
