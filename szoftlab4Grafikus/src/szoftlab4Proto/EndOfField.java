package szoftlab4Proto;

public class EndOfField extends Tile {

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
