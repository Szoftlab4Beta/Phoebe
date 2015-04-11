package szoftlab4Proto;

public abstract class FieldObject implements IAcceptor{

	Tile position;
	boolean dead;
	
	public FieldObject(Tile position){
		
	}
	
	protected void setPosition(Tile t){
		
	}
	
	public Tile getPosition(){
		return null;
	}

	protected void setDead(){
		
	}
	
	public boolean isDead(){
		return false;
	}
	
}
