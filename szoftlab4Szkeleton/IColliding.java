package szoftlab4Szkeleton;

public interface IColliding {

	public void collide(Oil t);
	public void collide(EndOfField t);
	public void collide(NormalTile t);
	public void collide(Goo t);
	public void collide(Finish t);
}
