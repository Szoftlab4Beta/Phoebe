package szoftlab4Proto;

import java.util.ArrayList;
import java.util.List;

/*
 * NormalTile - Norm�lis mez�
 * 
 * Sima mez�, erre l�phet a robot, helyezhet el ragacsfoltot stb.
 * Tile-b�l �r�kl�dik
 */
public class NormalTile extends Tile{

	/*
	 * Patch patch - ebben t�roljuk, hogy van-e rajta,
	 * illetve, ha igen, akkor milyen t�pus� akad�ly.
	 * List<IAcceptor> objects - Azt t�rolja, hogy milyen elemek vannak a mez�n.
	 */
	Patch patch;
	List<IAcceptor> objects;
	
	/*
	 * Konstruktor - objektum n�lk�li mez�t hoz l�tre.
	 */
	public NormalTile() {
		super();
		objects = new ArrayList<IAcceptor>();
	}
	
	/*
	 *  A rajta l�v� akad�ly be�ll�t�sa 
	 *  
	 *  @param Patch p - olaj, ragacs, null=semmilyen
	 */
	public void setPatch(Patch p){
		patch = p;
	}
	
	/*
	 * Lek�rdezz�k a rajta l�v� akad�lyt
	 * 
	 * @return Patch p - az akad�lyt adja vissza
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
	 * mez�n l�v� elemek lek�rdez�se
	 * 
	 * @return - objects param�ter
	 */
	
	public List<IAcceptor> getObjects(){
		return objects;
	}
	
	@Override
	public void accept(IColliding colliding) {
		colliding.collide(this);
	}
	
}
