package szoftlab4Proto;

import java.util.ArrayList;
import java.util.List;

public class NormalTile extends Tile{

	Patch patch;
	List<FieldObject> objects;
	
	public NormalTile() {
		super();
		objects = new ArrayList<FieldObject>();
	}
	
	public void setPatch(Patch p){
		changed = true;
		patch = p;
	}
	
	@Override
	public Patch getPatch(){
		return patch;
	}
	
	@Override
	public void addObject(FieldObject o){
		changed = true;
		objects.add(o);
	}
	
	public void removeObject(FieldObject o){
		changed = true;
		objects.remove(o);
	}
	
	@Override
	public List<FieldObject> getObjects(){
		return objects;
	}
	
	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
