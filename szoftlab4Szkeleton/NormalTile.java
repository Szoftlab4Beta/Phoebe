package szoftlab4Szkeleton;

public class NormalTile extends Tile {

	Patch p;
	
	public NormalTile() {
        Logger.logCreate(this, "normalTile");
	}
	
	public void setPatch(Patch p){
        Logger.logCall(this, "setPatch(" + p + ")");
        Logger.logReturn(this, "setPatch()");
		
	}
	
	public Patch getPatch(){
        Logger.logCall(this, "getPatch()");
        Logger.logReturn(this, "getPatch()");

        return null;
	}
	
	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}

}
