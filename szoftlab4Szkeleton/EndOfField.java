package szoftlab4Szkeleton;

/**
 * Egy speciális mező(Tile) típus, 
 * mely jelenleg a pálya széleit reprezentálja.
 */
public class EndOfField extends Tile{

	public EndOfField() {
		Logger.logCreate(this, "EndOfFiled");
	}
	
	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}

}
