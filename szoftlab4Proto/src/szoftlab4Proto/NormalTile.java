package szoftlab4Proto;

import java.util.ArrayList;
import java.util.List;

/*
 * NormalTile - Normális mezõ
 * 
 * Sima mezõ, erre léphet a robot, helyezhet el ragacsfoltot stb.
 * Tile-ból öröklõdik
 */
public class NormalTile extends Tile{

	/*
	 * Patch patch - ebben tároljuk, hogy van-e rajta,
	 * illetve, ha igen, akkor milyen típusú akadály.
	 * List<IAcceptor> objects - Azt tárolja, hogy milyen elemek vannak a mezõn.
	 */
	Patch patch;
	List<IAcceptor> objects;
	
	/*
	 * Konstruktor - objektum nélküli mezõt hoz létre.
	 */
	public NormalTile() {
		super();
		objects = new ArrayList<IAcceptor>();
	}
	
	/*
	 *  A rajta lévõ akadály beállítása 
	 *  
	 *  @param Patch p - olaj, ragacs, null=semmilyen
	 */
	public void setPatch(Patch p){
		patch = p;
	}
	
	/*
	 * Lekérdezzük a rajta lévõ akadályt
	 * 
	 * @return Patch p - az akadályt adja vissza
	 */
	public Patch getPatch(){
		return patch;
	}
	
	@Override
	public void addObject(IAcceptor o){
		objects.add(o);
	}
	
	public void removeObject(IAcceptor o){
		objects.remove(o);
	}
	
	/*
	 * mezõn lévõ elemek lekérdezése
	 * 
	 * @return - objects paraméter
	 */
	
	public List<IAcceptor> getObjects(){
		return objects;
	}
	
	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
