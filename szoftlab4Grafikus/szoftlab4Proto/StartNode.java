package szoftlab4Proto;

import java.util.HashSet;
import java.util.Set;

import szoftlab4Proto.VectorClass.Direction;

public class StartNode extends Node
{
	Set<Tile> tileSet; //Azon mezők halmaza, amelyekhez már hozzá van rendelve egy Node a gráfon belül.
	
	/**
	 * Létrehoz egy gyökércsomópontot, és inicializálja azt.
	 * 
	 * A gyökér szintje mindig 0.
	 * 
	 * @param t
	 */
	public StartNode(Tile t)
	{
		super(null, Direction.None);
		tile=t;
		level=0;
		tileSet=new HashSet<Tile>();
	}
	
	/**
	 * Hozzáadja a Node-ok által képviselt mezők halmazához a paramétereként kapott mezőt, amennyiben az még nem szerepelt abban.
	 * 
	 * @param t A listához adandó mező
	 * @return A hozzáadás sikeressége (true - a mező hozzáadásra került, false - a mező már szerepelt a halmazban)
	 */
	@Override
	public boolean addToRootList(Tile t)
	{
		return tileSet.add(t);
	}
}
