package szoftlab4Proto;

import graphics.GraphicsObject;
import graphics.GraphicsController.ImageID;

/**
 * Absztrakt ősosztály az összes olyan Osztály egységbe foglalására, amik mezőkön helyezkedhetnek el
 * 
 *
 */
public abstract class FieldObject implements IAcceptor{

	Tile position;		//Tile which holds this Object
	boolean dead;
	protected GraphicsObject graphics;
	
	/**
	 * Konstruktor, ami a megadott mezőn létrehozza az Objektumot (a mezőhöz nem regisztrálja be, az a leszármazottak felelőssége)
	 * @param position
	 */
	public FieldObject(Tile position){
		this.position = position;
		dead = false;
	}
	
	protected void setPosition(Tile t){
		position = t;
	}
	
	/**
	 * Visszaadja az objektum pozícióját
	 * @return
	 * @throws NullPointerException
	 */
	public Tile getPosition() throws NullPointerException{
		if(position == null)
			throw new NullPointerException();
		return position;
	}

	protected void setDead(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	/**
	 * Eltávolítja az objektumot a tartalmazó mezőről
	 */
	public void dispose(){
		position.removeObject(this);
	}
	
	public ImageID getGraphics(){
		return graphics.image;
	}
	
}
