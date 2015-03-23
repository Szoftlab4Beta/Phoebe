package szoftlab4Szkeleton;

/**
 * Ez egy �ltal�nos mez� objektum  amire ugorhat a robot.
 * Erre ker�lnek r� a k�l�nf�le foltok, Goo, Oil (Ragacs,Olaj)
 */
public class NormalTile extends Tile {

	Patch p;
	
	public NormalTile() {
        Logger.logCreate(this, "normalTile");
	}
	
	/**
	 * Be�ll�tja a mez�n l�v� foltot a param�terben kapott foltra.
	 * @param p
	 */
	public void setPatch(Patch p){
        Logger.logCall(this, "setPatch(" + Logger.getIDOf(p) + ")");
        Logger.logReturn(this, "setPatch()");
		
	}
	
	/**
	 * Visszaadja a mez�n l�v� foltot, vagy ha nincs akkor null-t.
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
