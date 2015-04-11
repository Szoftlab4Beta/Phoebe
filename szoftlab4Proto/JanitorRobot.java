package szoftlab4Proto;

import java.util.List;

import szoftlab4Proto.VectorClass.Direction;

public class JanitorRobot extends MoveableFieldObject implements IColliding, IUpdateable{

	public enum JanitorWorkState{ Searching, WorkStarted, Working};
	
	JanitorWorkState workState;
	List<Direction> pathQueue;
	
	public JanitorRobot(Tile position) {
		super(position);
			// TODO Auto-generated constructor stub
	}
	
	void searchNearestPatch(){
		
	}
	
	public JanitorWorkState getWorkState(){
		return null;
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
	
}
