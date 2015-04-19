package szoftlab4Proto;

import java.util.List;

public class NormalTile extends Tile{

	Patch patch;
	List<IAcceptor> objects;
	
	public void setPatch(Patch p){
		patch = p;
	}
	
	public Patch getPatch(){
		return patch;
	}
	
	public void addObject(IAcceptor o){
		objects.add(o);
	}
	
	public void removeObject(IAcceptor o){
		objects.remove(o);
	}
	
	public List<IAcceptor> getObjects(){
		return objects;
	}
	
}
