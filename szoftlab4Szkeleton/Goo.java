package szoftlab4Szkeleton;

/**
 * Egy speci�lis Folt(Patch) t�pus, jelen esetben egy ragacsfolt.
 * Amint a robot a mez�re �rkezik, elveszti sebess�g�nek fel�t.
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
