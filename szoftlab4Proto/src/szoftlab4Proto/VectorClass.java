package szoftlab4Proto;

import java.util.HashMap;
import java.util.Map;

public class VectorClass {

	public enum Direction {
		None, North, East, South, West;

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

	public Map<Direction, Integer> vector;

	public VectorClass() {
		vector = new HashMap<Direction, Integer>();
		for (Direction d : Direction.values())
			vector.put(d, 0);
	}

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

	public void add(VectorClass v) {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) + v.vector.get(d));
		}
		this.normalize();
	}

	public void add(Direction d) {
		vector.put(d, vector.get(d) + 1);
		this.normalize();
	}

	public void halve() {
		for (Direction d : vector.keySet()) {
			vector.put(d, vector.get(d) / 2);
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
			tmp.vector.put(d, v1.vector.get(d) + v2.vector.get(d));
		}
		tmp.normalize();
		for (Direction d: Direction.values()) {
			tmp.vector.put(d, tmp.vector.get(d)/2);
		}
		return tmp;
	}

	public static boolean less(VectorClass v1, VectorClass v2) {
		if (v1.length() < v2.length())
			return true;
		return false;
	}
}
