package szoftlab4Proto;

public interface IColliding {

	public void collide(Oil p);
	public void collide(Goo p);
	public void collide(NormalTile t);
	public void collide(Finish t);
	public void collide(EndOfField t);
	public void collide(Robot r);
	public void collide(JanitorRobot r);
	
}
