package szoftlab4Proto;

import java.util.ArrayList;
import szoftlab4Proto.VectorClass.Direction;

public class StartNode extends Node
{
	ArrayList<Tile> tileList;
	
	public StartNode(Tile t)
	{
		super(null, Direction.None);
		tile=t;
		level=0;
	}
	
	//@Override
	public boolean isAdded(Tile t)
	{
		for(Tile x : tileList)
			if(x==t)
				return true;
		
		return false;
	}
	
	@Override
	public boolean addToRootList(Tile t)
	{
		if(!isAdded(t))
		{
			tileList.add(t);
			return true;
		}
		
		return false;
	}
}
