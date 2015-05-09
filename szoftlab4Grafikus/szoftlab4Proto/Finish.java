package szoftlab4Proto;

public class Finish extends Tile{

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
