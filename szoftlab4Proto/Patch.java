package szoftlab4Proto;

public abstract class Patch extends FieldObject implements IColliding{

	public enum PatchType{None, Goo, Oil};
	
	float durability;
	static int cleanTime;
	
	public Patch(Tile position) {
		super(position);
		// TODO Auto-generated constructor stub
	}
	
	public static int getCleanTime(){
		return 0;
	}
	
	public float getDurability(){
		return 0;
	}
	
	public float decDurabilityBy(float value){
		return 0;
	}

}
