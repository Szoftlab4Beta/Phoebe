package szoftlab4Proto;

import graphics.GraphicsObject;
import graphics.GraphicsController.ImageID;
import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

/**
 *
 */
public class Robot extends MoveableFieldObject implements IColliding, IUpdateable{

	VectorClass speed, distance;
	int gooAmount, oilAmount;
	boolean canChangeSpeed  = true;
	boolean atFinish = false;
	String name;
	
	public Robot(Tile spawnTile, int startGoo, int startOil, String name){
		super(spawnTile);
		gooAmount = startGoo;
		oilAmount = startOil;
		this.name = name;
		distance = new VectorClass();
		speed = new VectorClass();
		graphics = new GraphicsObject(ImageID.Robot);
	}
	
	/**
	 * Hozzáadja a Robot sebességvektorához a megadott irányt (ha a robot sebessége változtatható)
	 * (Ha a megadott irány "None", a Robot sebessége változatlan marad)
	 * @param d	
	 */
	public void modifySpeed(Direction d){
		if(canChangeSpeed)
			speed.add(d);
	}
	
	/**
	 * Egy foltot hagy a robot által elfoglalt mezőn, eltűntetve onnan bármely korábban ott lévő foltot
	 * (Kivéve, ha a robotnak már nincs készleten a megadott típusú foltból)
	 * @param type A folt típusa (ha "None" akkor nem rakunk le foltot)
	 * @return Referencia a lerakott foltra (vagy null, ha nem volt ilyen)
	 */
	public Patch placePatch(PatchType type){
		Patch patch = null;	
		switch (type) {
		case Goo:
			if(gooAmount > 0){							//If the robot carries any
				patch = new Goo(position);				//Create a new patch
				gooAmount--;							//decrease the carried amount
				((NormalTile)position).setPatch(patch);	//Add the Patch to the tile
			}
			break;
		case Oil:
			if(oilAmount > 0){
				patch = new Oil(position);
				oilAmount--;
				((NormalTile)position).setPatch(patch);
			}
			break;
		default:
			return null;
		}
		return patch;
	}
	
	/**
	 * Visszaadja a robot által megtett távolságot
	 * @return
	 */
	public int getDistance(){
		return distance.length();
	}
	
	/**
	 * Visszaadja a robot sebességvektorát
	 * @return
	 */
	public VectorClass getSpeedVector(){
		return speed;
	}
	
	/**
	 * Megadja, hogy a robot célba ért-e
	 * @return
	 */
	public boolean isAtFinish(){
		return atFinish;
	}

	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}

	/**
	 * Megadja a Robot viselkedését, ha Olajfoltra lép
	 * Akkor hívódik meg, ha a robot olyan mezőre lépett, amin olajfolt van
	 * Ezután a következő körig a robot sebessége nem változtatható
	 * @param p Az olajfolt amire a robot lépett
	 */
	@Override
	public void collide(Oil p) {
		canChangeSpeed = false;
	}

	/**
	 * Megadja a Robot viselkedését, ha Ragacsfoltra lép
	 * Akkor hívódik meg, ha a robot olyan mezőre lépett, amin ragacsfolt van
	 * Ezután robot sebessége a felére csökken
	 * @param p A ragacsfolt amire a robot lépett
	 */
	@Override
	public void collide(Goo p) {
		speed.halve();
		p.decDurabilityBy(1);
	}

	/**
	 * Megadja a Robot viselkedését, ha Sima mezőre lép
	 * Akkor hívódik meg, ha a robot NormalTile típusú mezőre lépett
	 * Ez meghívja a mezőn található foltokra, és egyéb FieldObject -ekre az ütközést
	 * @param t A mező amire a Robot lépett
	 */
	@Override
	public void collide(NormalTile t) {
		Patch p = t.getPatch();
		if(p != null)
			p.accept(this);
		for(IAcceptor object : t.getObjects()){
			if(!object.equals(this))
				object.accept(this);
		}
	}

	/**
	 * Megadja a Robot viselkedését, ha célmezőre lép
	 * Akkor hívódik meg, ha a robot Finish típusú mezőre lépett
	 * @param t A mező amire a Robot lépett
	 */
	@Override
	public void collide(Finish t) {
		atFinish = true;
	}

	/**
	 * Megadja a Robot viselkedését, ha a pálya szélén kívülre lép
	 * Akkor hívódik meg, ha a robot EndOfField típusú mezőre lépett
	 * Ilyenkor a robot leesik, és meghal
	 * @param t A mező amire a Robot lépett
	 */
	@Override
	public void collide(EndOfField t) {
		dead = true;
	}

	/**
	 * Megadja a Robot viselkedését, ha másik robottal ütközik
	 * Akkor hívódik meg, ha a robot olyan mezőre lépett, amin egy másik robot tartózkodik
	 * A két robot közül a lassabbik meghal, a gyorsabbik pedig a kettő robot sebességátlagával halad tovább
	 * @param r A másik robot
	 */
	@Override
	public void collide(Robot r) {
		if(VectorClass.less(speed, r.speed)){
			dead = true;
		} else {
			speed = VectorClass.average(speed, r.speed);
		}
	}

	/**
	 * Megadja a Robot viselkedését, ha takarító robottal ütközik
	 * Akkor hívódik meg, ha a robot olyan mezőre lépett, amin egy takarító robot tartózkodik
	 * A robotnak ebben az interakcióban nincs felelőssége
	 * @param r A takarító robot
	 */
	@Override
	public void collide(JanitorRobot r) {
	}

	/**
	 * A robot mozgatásáért felelős metódus
	 * A robotot eltávolítja a jelenlegi pozíciójáról, és a sebességvektorának megfelelő mezőre teszi
	 * Ez a metódus módosítja a robot által megtett távolságot, és a robotot újra képessé teszi sebességének változtatására
	 */
	@Override
	public void move() {
		position.removeObject(this);
		canChangeSpeed = true;
		for (Direction d : speed.vector.keySet()){
			for(int i = 0; d != Direction.None && i < speed.vector.get(d); i++){
				position = position.getTile(d);
				if(position == null){
					dead = true;
					return;
				}
			}
		}
		position.addObject(this);
		distance.add(speed);
	}

	/**
	 * A robot kör végén elvégzendő feladatait megvalósító metódus
	 * A robot interakcióba lép a mezővel amin áll.
	 * @return RobotDied, ha a robot az interakció közben meghal, egyébként Alive
	 */
	@Override
	public UpdateReturnCode update() {
		position.accept(this);		
		if(dead){
			dispose();
			return UpdateReturnCode.RobotDied;
		}
		return UpdateReturnCode.Alive;
	}
	
	/**
	 * Visszaad egy formázott stringet a robot kiíratásához szükséges paraméterekkel
	 * @return
	 */
	public String getLogString(){
		return "<" + oilAmount + "><" + gooAmount + "><" + distance.length() + "><" + speed.length() + ">";
	}
	
}
