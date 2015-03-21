package szoftlab4Szkeleton;

public class EndOfField extends Tile{

	public EndOfField() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Robot robot) {
		robot.collide(this);
	}

}
