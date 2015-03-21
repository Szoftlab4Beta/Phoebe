package szoftlab4Szkeleton;

public class Goo extends Patch{

	public Goo() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(Robot robot) {
		robot.collide(this);
	}

}
