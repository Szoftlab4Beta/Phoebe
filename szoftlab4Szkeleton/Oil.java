package szoftlab4Szkeleton;

public class Oil extends Patch{

	public Oil() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Robot robot) {
		robot.collide(this);
	}
	
}
