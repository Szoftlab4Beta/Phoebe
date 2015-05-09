package szoftlab4Proto;

import graphics.GraphicsObject;
import graphics.GraphicsController.ImageID;

/**
 * Goo - Ragacs
 * 
 * Felelősség : Ez a ragacs osztálya, ha ilyen mezőre érkezik a robot, 
 * akkor elveszti sebességének a felét.
 */
public class Goo extends Patch{

	static float maxDurability;

	/**
	 * Létrehozza a paraméterül kapott mezőn a ragacsfoltot, majd inicializálja
	 * annak értékeit.
	 * @param Tile position
	 */
	public Goo(Tile position) {
		super(position);
		maxDurability = 4;
		durability = maxDurability;
		graphics = new GraphicsObject(ImageID.Goo0);
	}
	
	/**
	 * Csökkenti a Ragacsfolt élettartamát a paraméterül (value) kapott értékkel.
	 * Amennyiben az élettartalma 0, megszünteti azt.
	 * Ezekután visszatér az 'élettartam' értékével (durability)
	 * 
	 * @param float value
	 * @return float durability 
	 */
	@Override
	public float decDurabilityBy(float value){
		super.decDurabilityBy(value);
		if(durability < 0)
			this.dispose();
		else if(durability < 1)
			graphics.image = ImageID.Goo2;
		else if(durability < 2)
			graphics.image = ImageID.Goo1;
		return durability;
	}
	
	/**
	 * Visszaadja a ragacsfolt lehetséges maximális 'élettertamát'.
	 * 
	 * @return float maxDurability - Maximális élettartam
	 */
	public float getMaxDurability(){
		return maxDurability;
	}

	public void collide(Robot r) {
	}

	@Override
	public void collide(Oil p) {
	}

	@Override
	public void collide(Goo p) {
	}

	@Override
	public void collide(NormalTile t) {
	}

	@Override
	public void collide(Finish t) {
	}

	@Override
	public void collide(EndOfField t) {		
	}

	@Override
	public void collide(JanitorRobot r) {		
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
