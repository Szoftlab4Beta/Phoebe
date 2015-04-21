package szoftlab4Proto;

import java.util.HashMap;
import java.util.Map;

/* VectorClass
 * 
 * Ebben az osztályban vannak az irányok. Egy mezõ szomszédait ezzel lehet leírni,
 * illetve az objektumok sebességét.
 * 
 */

public class VectorClass {
	/*
	 * enum-mal soroljuk fel a lehetséges irányokat.
	 */
	public enum Direction {
		None, North, East, South, West;
		
		/*
		 * Egy irány ellentetjét adja vissza.
		 * Például a takarítórobot használja, hogy visszaforduljon.
		 * 
		 * @return Direction - Ellentétes irány
		 */
		
		public Direction getOpposite() {
			switch (this) {
			case North:
				return South;
			case East:
				return West;
			case South:
				return North;
			case West:
				return East;
			default:
				return None;
			}
		}
	};
	
	/*
	 * Egy Map-on tároljuk az adott irányhoz tartozó értéket.
	 * Ez azt jelenti, hogy az adott irányba mennyi a "sebesség".
	 */
	
	public Map<Direction, Integer> vector;

	/*
	 * Konstruktor, csak default konstruktora van, 
	 * ez egy minden irányban 0 értékkel hozza létre a VectorClass-t
	 */
	
	public VectorClass() {
		vector = new HashMap<Direction, Integer>();
		for (Direction d : Direction.values())
			vector.put(d, 0);
	}
	
	/*
	 * Segédfüggvény, hogy az ellentétes irányok közül legalább az egyik 0 legyen.
	 * Például dél felé és észak felé nem mutathat egyszerre a VectorClass.
	 * Ezt kívülrõl nem lehet elérni, mert felesleges is lenne.
	 */

	private void normalize() {
		if (vector.get(Direction.North) != 0
				&& vector.get(Direction.South) != 0) {
			if (vector.get(Direction.North) > vector.get(Direction.South)) {
				vector.put(Direction.North, vector.get(Direction.North)
						- vector.get(Direction.South));
				vector.put(Direction.South, 0);
			} else {
				vector.put(Direction.South, vector.get(Direction.South)
						- vector.get(Direction.North));
				vector.put(Direction.North, 0);
			}

		}
		if (vector.get(Direction.East) != 0
				&& vector.get(Direction.West) != 0) {
			if (vector.get(Direction.East) > vector.get(Direction.West)) {
				vector.put(Direction.East, vector.get(Direction.East)
						- vector.get(Direction.West));
				vector.put(Direction.West, 0);
			} else {
				vector.put(Direction.West, vector.get(Direction.West)
						- vector.get(Direction.East));
				vector.put(Direction.East, 0);
			}

		}
	}
	
	/*
	 * Két VectorClass-t tudunk összegezni.
	 * Az elsõbõl meghívjuk ezt a függvényt, 
	 * és akkor hozzáadódik a második.
	 * 
	 * @param VectorClass v - Ezt adjuk hozzá ahhoz, amibõl hívtuk.
	 */
	
	public void add(VectorClass v) {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) + v.vector.get(d));
		}
		this.normalize();
	}
	
	/*
	 * Új irányt adunk hozzá.
	 * Hasonló az elõzõhöz, mintha egy 1 hosszú VectorClass lenne.
	 * 
	 * @param Direction d
	 */
	
	public void add(Direction d) {
		vector.put(d, vector.get(d) + 1);
		this.normalize();
	}

	/*
	 * Megfelezi a VectorClass értékeit.
	 * Lefelé kerekít.
	 */
	
	public void halve() {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) / 2);
		}
	}

	/*
	 * VectorClass hossza. Ez a távolság kiszámításához kell.
	 * Tulajdonképpen összeadja a 4 irányba mutató sebességet,
	 * és a None-t.
	 * Természetesen ebbõl maximum 2 lesz értékes.
	 * 
	 * @return int length - a hossz.
	 */
	
	public int length() {
		int result = 0;
		for (Direction d : vector.keySet())
			result += vector.get(d);
		return result;
	}
	
	/*
	 * Két vektor átlagát számolja ki és adja vissza.
	 * 
	 * @param VectorClass v1
	 * @param VectorClass v2
	 * @return average - az átlag.
	 */
	
	public static VectorClass average(VectorClass v1, VectorClass v2) {
		VectorClass tmp = new VectorClass();
		for (Direction d : Direction.values()) {
			tmp.vector.put(d, v1.vector.get(d) + v2.vector.get(d));
		}
		tmp.normalize();
		for (Direction d: Direction.values()) {
			tmp.vector.put(d, tmp.vector.get(d)/2);
		}
		return tmp;
	}
	
	/*
	 * Összehasonlító függvény.
	 * Ha az elsõ paraméterként kapott sebesség kisebb, igazzal tér vissza.
	 * 
	 * @param VectorClass v1
	 * @param VectorClass v2
	 * @return boolean
	 */
	
	public static boolean less(VectorClass v1, VectorClass v2) {
		if (v1.length() < v2.length())
			return true;
		return false;
	}
}
