package szoftlab4Proto;

import java.util.Stack;

import szoftlab4Proto.VectorClass.Direction;

public class JanitorRobot extends MoveableFieldObject implements IColliding, IUpdateable
{
	public enum JanitorWorkState{Searching, WorkStarted, Working};
	
	JanitorWorkState workState;
	Stack<Direction> pathQueue;
	boolean bounced;
	Tile destination;
	
	public JanitorRobot(Tile position)
	{
		super(position);
		workState=JanitorWorkState.Searching;
		pathQueue=new Stack<Direction>();
		bounced=false;
		destination=position;
		searchNearestPatch();
	}
	
	private void searchNearestPatch()
	{
		StartNode s=new StartNode(position);
		
		Stack<Direction> path=null;
		while(s.canBeExtended() && path==null)
		{
			path=s.extendToNextLevel();
		}
		if(path!=null)
		{
			pathQueue=path;
			
			destination=position;
			for(int i=0;i<pathQueue.size();++i)
				destination=destination.getTile(pathQueue.get(i));
		}
	}
	
	public JanitorWorkState getWorkState()
	{
		return workState;
	}
	
	public Tile getDestination()
	{
		return destination;
	}

	@Override
	public void accept(IColliding colliding)
	{
		colliding.collide(this);
	}

	@Override
	public UpdateReturnCode update()
	{
		if(position==null)
		{
			setDead();
			return UpdateReturnCode.JanitorDied;
		}
		
		if(((NormalTile)destination).getPatch()==null)
		{
			searchNearestPatch();
			workState=JanitorWorkState.Searching;
		}
		else if(position==destination)
		{
			if(workState==JanitorWorkState.Searching)
				workState=JanitorWorkState.WorkStarted;
			else if(workState==JanitorWorkState.WorkStarted)
				workState=JanitorWorkState.Working;
		}
		
		position.accept(this);
		
		if(!pathQueue.isEmpty())
			pathQueue.pop();
		
		bounced=false;
		
		return isDead() ? UpdateReturnCode.JanitorDied : UpdateReturnCode.Alive;
	}

	@Override
	public void collide(Oil p)
	{
		float decreaseAmount=p.getMaxDurability()/Patch.getCleanTime();
		if(p.decDurabilityBy(decreaseAmount)<=0)
		{
			workState=JanitorWorkState.Searching;
			searchNearestPatch();
		}
	}

	@Override
	public void collide(Goo p)
	{
		float decreaseAmount=p.getMaxDurability()/Patch.getCleanTime();
		if(p.decDurabilityBy(decreaseAmount)<=0)
		{
			workState=JanitorWorkState.Searching;
			searchNearestPatch();
		}
	}

	@Override
	public void collide(NormalTile t)
	{
		if(workState!=JanitorWorkState.Searching)
		{
			Patch p=t.getPatch();
			if(p!=null)
			{
				p.accept(this);
			}
			else
			{
				searchNearestPatch();
				workState=JanitorWorkState.Searching;
			}
		}
		
		for(IAcceptor o : t.getObjects())
		{
			if(o==this)
				continue;
			
			o.accept(this);
			if(position!=t)
				break;
		}
	}

	@Override
	public void collide(Finish t)
	{
		//Nem létezik ilyen eset
	}

	@Override
	public void collide(EndOfField t)
	{
		setDead();
	}

	@Override
	public void collide(Robot r)
	{
		if(workState==JanitorWorkState.Working || bounced)
		{
			setDead();
			((NormalTile)position).setPatch(new Oil(position));
		}
		else
		{
			bounced=true;
			
			Direction nextDir=pathQueue.peek();
			Direction opp=nextDir.getOpposite();
			pathQueue.push(opp);
			
			position.removeObject(this);
			position=position.getTile(opp);
			position.addObject(this);
			position.accept(this);
		}
	}

	@Override
	public void collide(JanitorRobot r)
	{
		if(workState==JanitorWorkState.Searching)
		{
			if(bounced)
			{
				setDead();
				return;
			}
			
			bounced=true;
			
			Direction nextDir=pathQueue.peek();
			Direction opp=nextDir.getOpposite();
			pathQueue.push(opp);
			
			position.removeObject(this);
			position=position.getTile(opp);
			position.addObject(this);
			position.getTile(opp).accept(this);
		}
	}

	@Override
	public void move()
	{
		if(workState!=JanitorWorkState.Searching || pathQueue.isEmpty())
			return;
			
		((NormalTile)position).removeObject(this);
		Direction d=pathQueue.peek();
		Tile newPos=position.getTile(d);
		setPosition(newPos);
		if(newPos!=null)
			((NormalTile)newPos).addObject(this);
	}
	
	@Override
	public void dispose()
	{
		position.removeObject(this);
	}
}
