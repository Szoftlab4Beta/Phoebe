package szoftlab4Proto;

import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

public class Robot extends MoveableFieldObject implements IColliding, IUpdateable{

	VectorClass speed, distance;
	int gooAmount, oilAmount;
	boolean canChangeSpeed  = true;
	boolean atFinish = false;
	
	public Robot(Tile spawnTile, int startGoo, int startOil){
		super(spawnTile);
		gooAmount = startGoo;
		oilAmount = startOil;
		distance = new VectorClass();
		speed = new VectorClass();
	}
	
	public void modifySpeed(Direction d){
		if(canChangeSpeed)
			speed.add(d);
	}
	
	public Patch placePatch(PatchType type){
		Patch patch = null;	
		switch (type) {
		case Goo:
			if(gooAmount > 0){
				patch = new Goo(position);
				gooAmount--;
				((NormalTile)position).setPatch(patch);
			}
			break;
		case Oil:
			if(oilAmount > 0){
				patch = new Oil(position);
				oilAmount--;
				((NormalTile)position).setPatch(patch);
			}
			break;
		default:
			return null;
		}
		return patch;
	}
	
	public int getDistance(){
		return distance.length();
	}
	
	public VectorClass getSpeedVector(){
		return speed;
	}
	
	public boolean isAtFinish(){
		return atFinish;
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}

	@Override
	public void collide(Oil p) {
		canChangeSpeed = false;
	}

	@Override
	public void collide(Goo p) {
		speed.halve();
		p.decDurabilityBy(1);
	}

	@Override
	public void collide(NormalTile t) {
		Patch p = t.getPatch();
		if(p != null)
			p.accept(this);
		for(IAcceptor object : t.getObjects()){
			object.accept(this);
		}
		t.addObject(this);
	}

	@Override
	public void collide(Finish t) {
		atFinish = true;
	}

	@Override
	public void collide(EndOfField t) {
		dead = true;
	}

	@Override
	public void collide(Robot r) {
		if(VectorClass.less(speed, r.speed)){
			dead = true;
		} else {
			speed = VectorClass.average(speed, r.speed);
		}
	}

	@Override
	public void collide(JanitorRobot r) {
	}

	@Override
	public void move() {
		position.removeObject(this);
		canChangeSpeed = true;
		for (Direction d : speed.vector.keySet()){
			for(int i = 0; d != Direction.None && i < speed.vector.get(d); i++)
				position = position.getTile(d);
		}
		distance.add(speed);
	}

	@Override
	public UpdateReturnCode update() {
		position.accept(this);		//TODO: vagy itt vagy a move-ban
		if(dead)
			return UpdateReturnCode.RobotDied;
		return UpdateReturnCode.Alive;
	}
	
	public String getLogString(){
		return "<" + oilAmount + "><" + gooAmount + "><" + distance.length() + "><" + speed.length() + ">";
	}
	
}
