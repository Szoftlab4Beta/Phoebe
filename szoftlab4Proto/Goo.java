package szoftlab4Proto;

public class Goo extends Patch{

	static float maxDurability;

	public Goo(Tile position) {
		super(position);
		maxDurability = 4;
	}
	
	public float getMaxDurability(){
		return maxDurability;
	}

	public void collide(Robot r) {
		r.speed.halve();
		this.decDurabilityBy(1f);
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
	}
	
}
