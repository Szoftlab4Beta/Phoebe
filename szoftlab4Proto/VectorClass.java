package szoftlab4Proto;

import java.util.Map;

public class VectorClass {

	public enum Direction {
		None, North, East, South, West
	};

	public Map<Direction, Integer> vector;

	public VectorClass() {
		for (Direction d : Direction.values())
			vector.put(d, 0);
	}

	public void add(VectorClass v) {
		for (Direction d : vector.keySet()) {
			vector.replace(d, vector.get(d) + v.vector.get(d));
		}
	}

	public void add(Direction d) {
		vector.replace(d, vector.get(d) + 1);
	}

	public void halve() {
		for (Direction d : vector.keySet()) {
			vector.replace(d, vector.get(d) / 2);
		}
	}

	public int length() {
		int result = 0;
		for (Direction d : vector.keySet())
			result += vector.get(d);
		return result;
	}

	public static VectorClass average(VectorClass v1, VectorClass v2) {
		VectorClass tmp = new VectorClass();
		for (Direction d : Direction.values()) {
			tmp.vector.replace(d, (v1.vector.get(d) + v1.vector.get(d)) / 2);
		}
		return tmp;
	}

	public static boolean less(VectorClass v1, VectorClass v2) {
		if (v1.length() < v2.length())
			return true;
		return false;
	}
}
