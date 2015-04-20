package szoftlab4Proto;

public class Oil extends Patch implements IUpdateable{

	static float maxDurability;
	
	public Oil(Tile position) {
		super(position);
		maxDurability = 4;
		durability = maxDurability;
	}

	public float getMaxDurability(){
		return maxDurability;
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
	public void collide(Robot r) {
	}

	@Override
	public void collide(JanitorRobot r) {
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}

	@Override
	public UpdateReturnCode update() {
		decDurabilityBy(maxDurability/cleanTime);
		if(durability <= 0) {
			dispose();
			return UpdateReturnCode.Died;
		}
		return UpdateReturnCode.Alive;
	}
	
	@Override
	public void dispose() {
		if(((NormalTile)position).getPatch()==this)
			((NormalTile)position).setPatch(null);
	}
	
	
}
