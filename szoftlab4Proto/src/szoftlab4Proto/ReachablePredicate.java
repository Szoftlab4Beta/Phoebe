package szoftlab4Proto;

import java.util.function.Predicate;

class ReachablePredicate implements Predicate<Tile>, IColliding
{
	private boolean reachable;
	@Override
	public boolean test(Tile t)
	{
		if(t==null)
			return false;
		
		t.accept(this);
		
		return reachable;
	}

	@Override
	public void collide(Oil p){}
	@Override
	public void collide(Goo p) {}
	@Override
	public void collide(NormalTile t) {reachable=true;}
	@Override
	public void collide(Finish t) {reachable=false;}
	@Override
	public void collide(EndOfField t) {reachable=false;}
	@Override
	public void collide(Robot r) {}
	@Override
	public void collide(JanitorRobot r) {}
}
