package szoftlab4Szkeleton;

/**
 * Egy speci�lis mez�(Tile) t�pus, 
 * mely jelenleg a p�lya sz�leit reprezent�lja.
 */
public class EndOfField extends Tile{

	public EndOfField() {
		Logger.logCreate(this, "EndOfFiled");
	}
	
	@Override
	public void accept(IColliding colliding) {
		Logger.logCall(this, "accept()");
		colliding.collide(this);
		Logger.logReturn(this, "accept()");
	}

}
