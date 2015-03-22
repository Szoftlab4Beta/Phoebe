package szoftlab4Szkeleton;

public class NormalTile extends Tile {

	Patch p;
	
	public NormalTile() {
	}
	
	public void setPatch(Patch p){
		
	}
	
	public Patch getPatch(){
		return null;
	}
	
	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}

}
