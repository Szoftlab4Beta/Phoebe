package szoftlab4Proto;

public abstract class Patch extends FieldObject implements IColliding
{
	public enum PatchType{None, Goo, Oil};
	
	protected float durability;
	public static int cleanTime = 4;
	
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
		if(durability<=0)
			setDead();
		return durability;
	}
	
	public void dispose()
	{
		if(((NormalTile)position).getPatch()==this)
			((NormalTile)position).setPatch(null);
	}
}
