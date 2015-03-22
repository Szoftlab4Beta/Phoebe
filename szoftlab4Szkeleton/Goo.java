package szoftlab4Szkeleton;

public class Goo extends Patch{

	public Goo() {
		Logger.logCreate(this, "Goo");
	}
	
	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}

}
