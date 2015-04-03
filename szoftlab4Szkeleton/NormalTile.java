package szoftlab4Szkeleton;

/**
 * Ez egy általános mező objektum  amire ugorhat a robot.
 * Erre kerülnek rá a különféle foltok, Goo, Oil (Ragacs,Olaj)
 */
public class NormalTile extends Tile {

	Patch p;
	
	public NormalTile() {
        Logger.logCreate(this, "normalTile");
	}
	
	/**
	 * Beállítja a mezőn lévő foltot a paraméterben kapott foltra.
	 * @param p
	 */
	public void setPatch(Patch p){
        Logger.logCall(this, "setPatch(" + Logger.getIDOf(p) + ")");
        Logger.logReturn(this, "setPatch()");
		
	}
	
	/**
	 * Visszaadja a mezőn lévő foltot, vagy ha nincs akkor null-t.
	 * @return
	 */
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
