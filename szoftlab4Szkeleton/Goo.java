package szoftlab4Szkeleton;

/**
 * Egy speciális mező(Tile) típus, jelen esetben egy ragacsfolt.
 * Amint a robot a mezőre érkezik, elveszti sebességének felét.
 */
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
