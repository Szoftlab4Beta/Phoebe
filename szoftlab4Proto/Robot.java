package szoftlab4Proto;

import szoftlab4Proto.VectorClass.Direction;

public class Robot extends MoveableFieldObject implements IColliding, IUpdateable{

	VectorClass speed, distance;
	int gooAmount, oilAmount;
	boolean canChangeSpeed;
	boolean atFinish;
	
	public Robot(Tile spawnTile, int startGoo, int startOil){
		super(spawnTile);
	}
	
	public void modifySpeed(Direction d){
		
	}
	
	public Patch placePatch(){ //TODO biztosan így van ???
		return null;
	}
	
	public int getDistance(){
		return 0;
	}
	
	public VectorClass getSpeedVector(){
		return null;
	}
	
	public boolean isAtFinish(){
		return false;
	}

	@Override
	public void accept(IColliding colliding) {
		// TODO Auto-generated method stub
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
	public void move() {
		// TODO Auto-generated method stub
	}

	@Override
	public UpdateReturnCode update() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
