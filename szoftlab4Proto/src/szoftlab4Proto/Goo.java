package szoftlab4Proto;

public class Goo extends Patch{

	static float maxDurability;

	public Goo(Tile position) {
		super(position);
		maxDurability = 4;
		durability = maxDurability;
	}
	
	@Override
	public float decDurabilityBy(float value){
		super.decDurabilityBy(value);
		if(durability < 0)
			this.dispose();
		return durability;
	}
	
	public float getMaxDurability(){
		return maxDurability;
	}

	public void collide(Robot r) {
	}

	@Override
	public void collide(Oil p) {
	}

	@Override
	public void collide(Goo p) {
	}

	@Override
	public void collide(NormalTile t) {
	}

	@Override
	public void collide(Finish t) {
	}

	@Override
	public void collide(EndOfField t) {		
	}

	@Override
	public void collide(JanitorRobot r) {		
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
