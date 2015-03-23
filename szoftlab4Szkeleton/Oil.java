package szoftlab4Szkeleton;

/**
 * Egy speciális Folt(Patch) típus, jelen esetben egy olajcsfolt.
 * Amint a robot a mezõre érkezik, elveszti sebességmódosító képességét.
 */
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
