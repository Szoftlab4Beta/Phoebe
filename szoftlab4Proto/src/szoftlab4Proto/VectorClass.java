package szoftlab4Proto;

import java.util.HashMap;
import java.util.Map;

/* VectorClass
 * 
 * Ebben az oszt�lyban vannak az ir�nyok. Egy mez� szomsz�dait ezzel lehet le�rni,
 * illetve az objektumok sebess�g�t.
 * 
 */

public class VectorClass {
	/*
	 * enum-mal soroljuk fel a lehets�ges ir�nyokat.
	 */
	public enum Direction {
		None, North, East, South, West;
		
		/*
		 * Egy ir�ny ellentetj�t adja vissza.
		 * P�ld�ul a takar�t�robot haszn�lja, hogy visszaforduljon.
		 * 
		 * @return Direction - Ellent�tes ir�ny
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
	 * Egy Map-on t�roljuk az adott ir�nyhoz tartoz� �rt�ket.
	 * Ez azt jelenti, hogy az adott ir�nyba mennyi a "sebess�g".
	 */
	
	public Map<Direction, Integer> vector;

	/*
	 * Konstruktor, csak default konstruktora van, 
	 * ez egy minden ir�nyban 0 �rt�kkel hozza l�tre a VectorClass-t
	 */
	
	public VectorClass() {
		vector = new HashMap<Direction, Integer>();
		for (Direction d : Direction.values())
			vector.put(d, 0);
	}
	
	/*
	 * Seg�df�ggv�ny, hogy az ellent�tes ir�nyok k�z�l legal�bb az egyik 0 legyen.
	 * P�ld�ul d�l fel� �s �szak fel� nem mutathat egyszerre a VectorClass.
	 * Ezt k�v�lr�l nem lehet el�rni, mert felesleges is lenne.
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
	 * K�t VectorClass-t tudunk �sszegezni.
	 * Az els�b�l megh�vjuk ezt a f�ggv�nyt, 
	 * �s akkor hozz�ad�dik a m�sodik.
	 * 
	 * @param VectorClass v - Ezt adjuk hozz� ahhoz, amib�l h�vtuk.
	 */
	
	public void add(VectorClass v) {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) + v.vector.get(d));
		}
		this.normalize();
	}
	
	/*
	 * �j ir�nyt adunk hozz�.
	 * Hasonl� az el�z�h�z, mintha egy 1 hossz� VectorClass lenne.
	 * 
	 * @param Direction d
	 */
	
	public void add(Direction d) {
		vector.put(d, vector.get(d) + 1);
		this.normalize();
	}

	/*
	 * Megfelezi a VectorClass �rt�keit.
	 * Lefel� kerek�t.
	 */
	
	public void halve() {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) / 2);
		}
	}

	/*
	 * VectorClass hossza. Ez a t�vols�g kisz�m�t�s�hoz kell.
	 * Tulajdonk�ppen �sszeadja a 4 ir�nyba mutat� sebess�get,
	 * �s a None-t.
	 * Term�szetesen ebb�l maximum 2 lesz �rt�kes.
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
	 * K�t vektor �tlag�t sz�molja ki �s adja vissza.
	 * 
	 * @param VectorClass v1
	 * @param VectorClass v2
	 * @return average - az �tlag.
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
	 * �sszehasonl�t� f�ggv�ny.
	 * Ha az els� param�terk�nt kapott sebess�g kisebb, igazzal t�r vissza.
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
