package szoftlab4Szkeleton;

import java.util.Map;

/**
 * A j�t�kban egy ir�nyvektort reprezent�l� oszt�ly.
 * <p>
 * Minden ir�nyhoz t�rol egy l�p�ssz�mot. Ezek �sszess�ge adja meg a vektor ir�ny�t �s nagys�g�t.
 * 
 */
public class VectorClass {

	public enum Direction{None, North, East, South, West};
	
	public Map<Direction, Integer> vector;
	
	public VectorClass() {
		//a LogCreate-et a l�trehoz� objektum h�vja
	}
	
	/**
	 * Hozz�adja ehhez a vektorhoz a megadott ir�nyt.
	 * <p>
	 * Ha a vektornak m�r van ellent�tes ir�ny� komponense, akkor azt cs�kkenti, 
	 * egy�bk�nt a megadott ir�ny� komponenst n�veli.
	 * 
	 * @param d A vektorhoz adand� ir�ny
	 */
	public void add(Direction d){
		Logger.logCall(this, "add(" + d + ")");
		Logger.logReturn(this, "add()");
	}
	
	/**
	 * Hozz�adja ehhez a vektorhoz a kapott vektort.
	 * <p>
	 * A megadott vektor �sszes komponens�t hozz�adja ehhez a vektorhoz.
	 * 
	 * @param v A hozz�adand� vektor.
	 */
	public void add(VectorClass v){
		Logger.logCall(this, "add(" + Logger.getIDOf(v) + ")");
		Logger.logReturn(this, "add()");
	}
	
	/**
	 * Elfelezi a vektor m�ret�t.
	 * <p>
	 * A vektor �sszes komponens�nek m�ret�t fel�re cs�kkenti.
	 */
	public void halve(){
		Logger.logCall(this, "halve()");
		Logger.logReturn(this, "halve()");
	}
}
