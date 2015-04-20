package szoftlab4Proto;

class ReachablePredicate implements Predicate<Tile>, IColliding
{
	private boolean reachable;
	
	/**
	 * @return Az érték true, ha a paraméterként kapott mezőre ráléphet egy JanitorRobot a folt keresése közben, és false, ha nem
	 */
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
