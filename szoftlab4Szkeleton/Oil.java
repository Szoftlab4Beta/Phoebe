package szoftlab4Szkeleton;

/**
 * Egy speci�lis Folt(Patch) t�pus, jelen esetben egy olajcsfolt.
 * Amint a robot a mez�re �rkezik, elveszti sebess�gm�dos�t� k�pess�g�t.
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
