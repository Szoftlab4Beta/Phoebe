package szoftlab4Szkeleton;

import java.util.Map;

import szoftlab4Szkeleton.VectorClass.Direction;

/**
 * Nyilvántartja a szomszédos mezõket, csere esetén pedig fenntartja a pálya konzisztenciáját.
 */
public abstract class Tile implements IAcceptor{

	Map<Direction, Tile> sides;
	
	public Tile() {
	}
	
	/**
	 * Beállítja a megadott d irányban lévõ szomszédját a t paraméterként kapott mezõre.
	 * @param d
	 * @param tile
	 */
	public void setSide(Direction d, Tile tile){
        Logger.logCall(this, "setSide(" + d + " " + Logger.getIDOf(tile) +")");
        Logger.logReturn(this, "setSide()");
		
	}
	
	/**
	 * A paraméterként kapott irányban lévõ mezõt adja vissza.
	 * @param d
	 * @return Tile
	 */
	public Tile getSide(Direction d){
		Logger.logCall(this, "getSide(" + d + ")");
		Logger.logReturn(this, d + " tile");
		return null;
	}

}
