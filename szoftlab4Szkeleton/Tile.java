package szoftlab4Szkeleton;

import java.util.Map;

import szoftlab4Szkeleton.VectorClass.Direction;

/**
 * Nyilv�ntartja a szomsz�dos mez�ket, csere eset�n pedig fenntartja a p�lya konzisztenci�j�t.
 */
public abstract class Tile implements IAcceptor{

	Map<Direction, Tile> sides;
	
	public Tile() {
	}
	
	/**
	 * Be�ll�tja a megadott d ir�nyban l�v� szomsz�dj�t a t param�terk�nt kapott mez�re.
	 * @param d
	 * @param tile
	 */
	public void setSide(Direction d, Tile tile){
        Logger.logCall(this, "setSide(" + d + " " + Logger.getIDOf(tile) +")");
        Logger.logReturn(this, "setSide()");
		
	}
	
	/**
	 * A param�terk�nt kapott ir�nyban l�v� mez�t adja vissza.
	 * @param d
	 * @return Tile
	 */
	public Tile getSide(Direction d){
		Logger.logCall(this, "getSide(" + d + ")");
		Logger.logReturn(this, d + " tile");
		return null;
	}

}
