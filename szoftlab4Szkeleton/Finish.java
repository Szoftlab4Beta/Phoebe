package szoftlab4Szkeleton;

public class Finish extends Tile{

	public Finish() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
