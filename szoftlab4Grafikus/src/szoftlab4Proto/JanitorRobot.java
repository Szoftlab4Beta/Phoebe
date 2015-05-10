package szoftlab4Proto;

import graphics.GraphicsObject;
import graphics.GraphicsController.ImageID;

import java.util.Stack;

import szoftlab4Proto.VectorClass.Direction;

public class JanitorRobot extends MoveableFieldObject implements IColliding, IUpdateable
{
	public enum JanitorWorkState{Searching, WorkStarted, Working};
	
	JanitorWorkState workState; //A takarítórobot állapota, amely jelzi, hogy épp milyen tevékenységet végez
	Stack<Direction> pathQueue; //A legközelebbi folthoz vezető utat irányok szerint eltároló lista
	boolean bounced; //Megmutatja, hogy a jelenlegi kör alatt visszapattant-e már a takarítórobot
	Tile destination; //A legközelebbi foltot tartalmazó mezőre mutató referencia
	
	/**
	 * Létrehoz egy takarítórobotot, és inicializálja azt.
	 * 
	 * A robot a létrejöttekor foltkereső (Searching) módban van.
	 * 
	 * @param position Az a pozíció, amelyen a robot a létrejötte után tartózkodik
	 */
	public JanitorRobot(Tile position)
	{
		super(position);
		workState=JanitorWorkState.Searching;
		pathQueue=new Stack<Direction>();
		bounced=false;
		destination=position;
		graphics = new GraphicsObject(ImageID.Janitor);
		searchNearestPatch();
	}
	
	/**
	 * Megkeresi a tisztítórobothoz legközelebb lévő foltot, és eltárolja az ahhoz vezető útvonalat, valamint a célmezőt (amelyen a folt van).
	 */
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
	
	/**
	 * Frissíti a robot állapotát, elvégzi az esetleges interakciók lebonyolítását a pozícióján lévő többi objektummal.
	 * 
	 * Amennyiben a robot leesett a pályáról vagy érvénytelen mezőre került, meghal.
	 * Akkor, ha a robot elérte a destination mezőt, elkezdi a mezőn lévő folt feltakarítását (és Searching módból WorkStarted módba vált).
	 * A robot Working módba vált, ha előtte WorkStarted módban volt.
	 * 
	 * @return Információ arra vonatkozóan, hogy a takarítórobot túlélte-e a kört.
	 */
	@Override
	public UpdateReturnCode update()
	{
		if(position==null)
		{
			setDead();
			return UpdateReturnCode.JanitorDied;
		}
		
		if(!pathQueue.isEmpty()) //FIX 2 - feljebb mozgatva
			pathQueue.pop();
		
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
		
		/*if(!pathQueue.isEmpty())
			pathQueue.pop();*/
		
		bounced=false;
		
		return isDead() ? UpdateReturnCode.JanitorDied : UpdateReturnCode.Alive;
	}
	
	/**
	 * Takarítja a megadott olajfoltot, és amennyiben a folt eltűnt ennek következtében, új takarítandó foltot keres.
	 * 
	 * @param p Az az olajfolt, amellyel a robot interakcióba lép
	 */
	@Override
	public void collide(Oil p)
	{
		float decreaseAmount=p.getMaxDurability()/Patch.getCleanTime();
		p.decDurabilityBy(decreaseAmount);
		/*if(p.decDurabilityBy(decreaseAmount)<=0)
		{
			workState=JanitorWorkState.Searching;
			searchNearestPatch();
		}*/
	}

	
	/**
	 * Takarítja a megadott ragacsfoltot, és amennyiben a folt eltűnt ennek következtében, új takarítandó foltot keres.
	 * 
	 * @param p Az a ragacsfolt, amellyel a robot interakcióba lép
	 */
	@Override
	public void collide(Goo p)
	{
		float decreaseAmount=p.getMaxDurability()/Patch.getCleanTime();
		p.decDurabilityBy(decreaseAmount);
		/*if(p.decDurabilityBy(decreaseAmount)<=0)
		{
			workState=JanitorWorkState.Searching;
			searchNearestPatch();
		}*/
	}
	
	
	/**
	 * Interakcióba lép a mezőn lévő objektumokkal, és ha dolgozó (WorkStarted | Working) módban van, feltakarítja a pozícióján lévő foltot.
	 * 
	 * @param t Az a mező, amellyel a robot interakcióba lép
	 */
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
	
	/**
	 * Amennyiben a takarítórobot ilyen mezőre lép, meghal.
	 * 
	 * @param p Az az EndOfField típusú mező, amellyel a robot interakcióba lép
	 */
	@Override
	public void collide(EndOfField t)
	{
		setDead();
	}
	
	/**
	 * Elvégzi egy robottal való ütközés lebonyolítását.
	 * 
	 * Abban az esetben, ha dolgozó (Working) állapotban volt vagy már visszapattant ebben a körben, megsemmisül az ütközés következtében.
	 * Egyébként visszapattan az előző pozíciójába.
	 * 
	 * @param r Az a robot, amellyel a takarítórobot interakcióba lép
	 */
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
	
	/**
	 * Elvégzi egy másik tisztítórobottal való ütközés lebonyolítását.
	 * 
	 * Az ütközés következtében visszapattan az előző pozíciójába, vagy amennyiben már ütközött ebben a körben, megsemmisül.
	 * 
	 * @param r Az a tisztítórobot, amellyel ez interakcióba lép
	 */
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
	
	/**
	 * Elvégzi a tisztítórobot mozgatását a legközelebbi folthoz vezető eltárolt út alapján.
	 * Amennyiben nincs ilyen út, a robot a jelenlegi pozícióján marad.
	 */
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
	
	/**
	 * Eltávolítja a robotot a pozíciójának megfelelő mezőn lévő objektumok listájából.
	 */
	@Override
	public void dispose()
	{
		position.removeObject(this);
	}
}
