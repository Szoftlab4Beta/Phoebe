package szoftlab4Proto;

public class Goo extends Patch{

	static float maxDurability;

	public Goo(Tile position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	public float getMaxDurability(){
		return 0;
	}

	@Override
	public void collide(Oil p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Goo p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(NormalTile t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Finish t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(EndOfField t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Robot r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(JanitorRobot r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(IColliding colliding) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
