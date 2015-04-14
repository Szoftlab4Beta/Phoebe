package szoftlab4Proto;

public abstract class FieldObject implements IAcceptor{

	Tile position;
	boolean dead;
	
	public FieldObject(Tile position){
		this.position = position;
		dead = false;
	}
	
	protected void setPosition(Tile t){
		position = t;
	}
	
	public Tile getPosition() throws NullPointerException{
		if(position == null)		//TODO: lehet itt kell dead = true; ? majd kiderül
			throw new NullPointerException();
		return position;
	}

	protected void setDead(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
}
