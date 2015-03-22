package szoftlab4Szkeleton;

public class Finish extends Tile{

	public Finish() {
        Logger.logCreate(this, "finishTile");

		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}
	
}
