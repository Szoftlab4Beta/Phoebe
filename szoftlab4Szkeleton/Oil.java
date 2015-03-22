package szoftlab4Szkeleton;

public class Oil extends Patch{

	public Oil() {
		Logger.logCreate(this, "Oil");
	}

	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}
	
}
