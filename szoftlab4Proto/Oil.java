package szoftlab4Proto;

public class Oil extends Patch implements IUpdateable{

	static float maxDurability;
	
	public Oil(Tile position) {
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

	@Override
	public UpdateReturnCode update() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
