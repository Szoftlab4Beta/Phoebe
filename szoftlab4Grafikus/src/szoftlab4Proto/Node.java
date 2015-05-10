package szoftlab4Proto;

import java.util.ArrayList;
import java.util.Stack;

import szoftlab4Proto.VectorClass.Direction;

public class Node
{
	protected Node parent;
	protected Direction dirToParent; //Az az irány, amelyben a node parent-node-ja elhelyezkedik hozzá képest
	protected ArrayList<Node> childList; //Az ebből a Node-ból elérhető gyermek-node-ok listája
	
	protected Tile tile; //Az a mező, amelyet a Node reprezentál
	protected int level; //A csomópont távolsága a gyökértől (1+azon csúcsok száma, amelyeken keresztül eljuthatunk ebbe a csomópont-ba)
	protected boolean extensionAttempted; //Megmutatja, hogy volt-e már kísérlet a gráf következő szintre való kibővítésére
	
	
	/**
	 * Létrehoz egy csomópontot a megadott szülővel, és inicializálja azt.
	 * 
	 * A csomópont szintje (level) a szülő szintje+1
	 * A tile értéke a parent által reprezentált mező dir-nek megfelelő szomszédja lesz.
	 * 
	 * @param parent A szülő node
	 * @param dir Az az irány ez a node elhelyezkedik a szülőjéhez képest
	 */
	public Node(Node parent, Direction dir)
	{
		this.parent=parent;
		dirToParent=dir.getOpposite();
		childList=new ArrayList<Node>();
		if(parent!=null)
		{
			tile=parent.tile.getTile(dir);
			level=parent.level+1;
		}
		extensionAttempted=false;
	}

	/**
	 * Megkísérel hozzáadni egy mezőt a gyökérben található listához.
	 * 
	 * @param t A listához adandó mező
	 * @return Ha a mező már szerepelt a listában, az érték false
	 */
	public boolean addToRootList(Tile t)
	{
		return parent.addToRootList(t);
	}
	
	/**
	 * Kiterjeszti a gráfot a következő szintre, amennyiben ezen a szinten még nem volt kiterjesztés.
	 * Ellenkező esetben a gyermek-node-okra meghívja ugyanezt a függvényt.
	 * 
	 * Beveszi a jelenlegi gráf legnagyobb szintjein lévő csomópontokhoz kapcsolódó node-okat,
	 * ha azok megfelelnek a kritériumnak (lásd ReachablePredicate),
	 * majd ezután beállítja az extensionAttempted változó értékét true-ra.
	 * 
	 * @return Az első olyan mezőhöz vezető út (irányokból összerakva, és a szülőhöz vezető iránnyal kiegészítve),
	 * amely megfelelt a feltételnek (lásd HasPatchPredicate),
	 * vagy null abban az esetben, ha ilyen nem volt
	 */
	public Stack<Direction> extendToNextLevel()
	{
		if(extensionAttempted)
		{
			for(Node n : childList)
			{
				Stack<Direction> ret=n.extendToNextLevel();
				
				if(ret!=null)
				{
					if(dirToParent!=Direction.None)
						ret.add(dirToParent.getOpposite());//ret.add(dirToParent); FIX1 - így már a helyes utat adja vissza
					
					return ret;
				}
			}
			
			return null;
		}
		
		ReachablePredicate rp=new ReachablePredicate();
		HasPatchPredicate hpp=new HasPatchPredicate();
		for(Direction d : Direction.values())
		{
			if(d!=dirToParent)
			{
				Tile t=tile.getTile(d);
				if(rp.test(t))
				{
					if(!addToRootList(t))
						continue;
						
					if(hpp.test((NormalTile)t))
					{
						Stack<Direction> ret=new Stack<Direction>();
						ret.push(d);
						if(dirToParent!=Direction.None)
							ret.push(dirToParent.getOpposite());
						
						return ret;
					}
					
					childList.add(new Node(this, d));
				}
			}
		}
		extensionAttempted=true;
		
		return null;
	}
	
	/**
	 * Visszaadja, ha a gráf még kiterjeszthető, azaz van olyan mező, amelyik még elérhető a gráfból, és nincs hozzá tartozó reprezentáns Node.
	 * 
	 * @return True, ha a gráf kiterjeszthető, false egyébként
	 */
	public boolean canBeExtended()
	{
		for(Node n : childList)
		{
			if(n.canBeExtended())
				return true;
		}
		
		return !extensionAttempted;
	}
}
