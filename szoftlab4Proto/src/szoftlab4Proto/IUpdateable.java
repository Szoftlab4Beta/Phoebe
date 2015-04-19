package szoftlab4Proto;

public interface IUpdateable {
	
	public enum UpdateReturnCode{Alive, Died, JanitorDied, RobotDied};
	
	public UpdateReturnCode update();
}
