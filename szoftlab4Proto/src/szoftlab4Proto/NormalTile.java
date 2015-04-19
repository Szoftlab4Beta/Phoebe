package szoftlab4Proto;

import java.util.ArrayList;
import java.util.List;

public class NormalTile extends Tile{

	Patch patch;
	List<IAcceptor> objects;
	
	public NormalTile() {
		super();
		objects = new ArrayList<IAcceptor>();
	}
	
	public void setPatch(Patch p){
		patch = p;
	}
	
	public Patch getPatch(){
		return patch;
	}
	
	@Override
	public void addObject(IAcceptor o){
		objects.add(o);
	}
	
	public void removeObject(IAcceptor o){
		objects.remove(o);
	}
	
	public List<IAcceptor> getObjects(){
		return objects;
	}
	
	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
