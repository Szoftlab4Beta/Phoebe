package szoftlab4Szkeleton;

/**
 * Egy speciális mező(Tile) típus, 
 * mely jelenleg a játékban a pálya Célját reprezentálja
 * amelyik robot ezen a mezőn fejezi be a körét, az nyer.
 */
public class Finish extends Tile{

	public Finish() {
        Logger.logCreate(this, "finishTile");

	}

	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}
	
}
