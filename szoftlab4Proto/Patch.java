package szoftlab4Proto;

public abstract class Patch extends FieldObject implements IColliding
{
	public enum PatchType{None, Goo, Oil};
	
	float durability;
	static int cleanTime;
	
	public Patch(Tile position)
	{
		super(position);
	}
	
	public static int getCleanTime()
	{
		return cleanTime;
	}
	
	public float getDurability()
	{
		return durability;
	}
	
	public float decDurabilityBy(float value)
	{
		durability-=value;
		return durability;
	}
}
