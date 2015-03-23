package szoftlab4Szkeleton;

import java.util.Map;

/**
 * A játékban egy irányvektort reprezentáló osztály.
 * <p>
 * Minden irányhoz tárol egy lépésszámot. Ezek összessége adja meg a vektor irányát és nagyságát.
 * 
 */
public class VectorClass {

	public enum Direction{None, North, East, South, West};
	
	public Map<Direction, Integer> vector;
	
	public VectorClass() {
		//a LogCreate-et a létrehozó objektum hívja
	}
	
	/**
	 * Hozzáadja ehhez a vektorhoz a megadott irányt.
	 * <p>
	 * Ha a vektornak már van ellentétes irányú komponense, akkor azt csökkenti, 
	 * egyébként a megadott irányú komponenst növeli.
	 * 
	 * @param d A vektorhoz adandó irány
	 */
	public void add(Direction d){
		Logger.logCall(this, "add(" + d + ")");
		Logger.logReturn(this, "add()");
	}
	
	/**
	 * Hozzáadja ehhez a vektorhoz a kapott vektort.
	 * <p>
	 * A megadott vektor összes komponensét hozzáadja ehhez a vektorhoz.
	 * 
	 * @param v A hozzáadandó vektor.
	 */
	public void add(VectorClass v){
		Logger.logCall(this, "add(" + Logger.getIDOf(v) + ")");
		Logger.logReturn(this, "add()");
	}
	
	/**
	 * Elfelezi a vektor méretét.
	 * <p>
	 * A vektor összes komponensének méretét felére csökkenti.
	 */
	public void halve(){
		Logger.logCall(this, "halve()");
		Logger.logReturn(this, "halve()");
	}
}
