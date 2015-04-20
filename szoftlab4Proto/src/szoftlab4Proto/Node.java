package szoftlab4Proto;

import java.util.ArrayList;
import java.util.Stack;

import szoftlab4Proto.VectorClass.Direction;

public class Node
{
	protected Node parent;
	protected Direction dirToParent;
	protected ArrayList<Node> childList;
	
	protected Tile tile;
	protected int level;
	protected boolean extensionAttempted;
	
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

	public boolean addToRootList(Tile t)
	{
		return parent.addToRootList(t);
	}
	
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
						ret.add(dirToParent);
					
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
