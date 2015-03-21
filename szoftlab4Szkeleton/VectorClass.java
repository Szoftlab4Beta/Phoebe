package szoftlab4Szkeleton;

import java.util.Map;

public class VectorClass {

	
	public enum Direction{None, North, East, South, West};
	
	public Map<Direction, Integer> vector;
	
	public VectorClass() {
		Logger.logCreate(this, "VectorClass");
	}
	
	public void add(Direction d){
		Logger.logCall(this, "add(" + d + ")");
		Logger.logReturn(this, "add()");
	}
	
	public void add(VectorClass v){
		Logger.logCall(this, "add(" + Logger.getIDOf(v) + ")");
		Logger.logReturn(this, "add()");
	}
	
	public void halve(){
		Logger.logCall(this, "halve()");
		Logger.logReturn(this, "halve()");
	}
}
