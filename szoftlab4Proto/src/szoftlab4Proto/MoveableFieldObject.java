package szoftlab4Proto;

public abstract class MoveableFieldObject extends FieldObject{

	public MoveableFieldObject(Tile position) {
		super(position);
		position.addObject(this);
	}
	
	public abstract void move();

}
