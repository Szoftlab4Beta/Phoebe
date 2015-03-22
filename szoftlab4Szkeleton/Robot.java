package szoftlab4Szkeleton;

import java.util.Scanner;

import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

public class Robot implements IColliding {

	int gooAmount, oilAmount;
	boolean dead, canChangeSpeed, atFinish;
	Tile position;
	VectorClass speed, distance;
	
	public Robot(Tile spawnTile, int startGoo, int startOil)
	{
		Logger.logCreate(this, "Robot", "(Tile spawnTile, int startGoo, int startOil)");
		
		try
		{
			if(spawnTile==null)
				throw new IllegalArgumentException("Invalid spawn point. (Argument is null)");
		
			distance=new VectorClass();
			Logger.logCreate(distance, Logger.getIDOf(this)+"_DistanceVector");
			speed=new VectorClass();
			Logger.logCreate(speed, Logger.getIDOf(this)+"_SpeedVector");
		}
		catch(Exception e)
		{
			Logger.logMSG(e.getMessage()+"\n");
		}
	}
	
	public void jump()
	{
		Logger.logCall(this, "jump()");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("A pályán marad a robot? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Tile tempt;
			Logger.logMSG("Milyen típusú mezőre ugrik a robot? (1-JátékTérVége / 2-Cél / 3-Normál):\t");
			answer =inputScanner.next().toLowerCase();
			if(answer.equals("1"))
			{
				Logger.disable();
				tempt=new EndOfField();
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("2"))
			{
				Logger.disable();
				tempt=new Finish();
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("3"))
			{
				Logger.disable();
				tempt=new NormalTile();
				Logger.enable();
				
				tempt.accept(this);
			}
			else
			{
				Logger.logMSG("Érvénytelen válasz!\n");
			}
		}
		else if(!answer.equals("n"))
		{
			Logger.logMSG("Érvénytelen válasz!\n");
		}
		
		Logger.logReturn(this, "jump()");
	}
	
	public void modifySpeed(Direction d)
	{
		Logger.logCall(this, "modifySpeed(Direction d)");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Módosíthat-e sebességet? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
			speed.add(d);
		else if(!answer.equals("n"))
			Logger.logMSG("Érvénytelen válasz!\n");
		
		Logger.logReturn(this, "modifySpeed(Direction d)");
	}
	
	public void placePatch(PatchType type){
		Logger.logCall(this, "placePatch(PatchType type)");
		
		if(type!=PatchType.None)
		{
			Scanner inputScanner=new Scanner(System.in);
			Logger.logMSG("Van-e ilyen folt a raktárban? (I/N):\t");
			String answer=inputScanner.next().toLowerCase();
			if(answer.equals("i"))
			{
				Logger.disable();
				position=new NormalTile();
				Logger.enable();
				Patch p=null;
				switch(type)
				{
				case Goo:
					Logger.disable();
					p=new Goo();
					Logger.enable();
					break;
				case Oil:
					Logger.disable();
					p=new Oil();
					Logger.enable();
					break;
				case None:
					//Ha ráugrik egy foltra, akkor az eltűnik?
					break;
				default:
					break;
				}
				((NormalTile)position).setPatch(p);
			}
			else if(!answer.equals("n"))
			{
				Logger.logMSG("Érvénytelen válasz!\n");
			}
		}
		
		Logger.logReturn(this, "placePatch(PatchType type)");
	}
	
	public int getDistance(){
		Logger.logCall(this, "getDistance()");
		Logger.logReturn(this, "getDistance() returns "+0);
		
		return 0;
	}
	
	public boolean isDead(){
		Logger.logCall(this, "isDead()");
		Logger.logReturn(this, "isDead() returns "+dead);
		return dead;
	}
	
	public boolean isAtFinish(){
		Logger.logCall(this, "isAtFinish()");
		Logger.logReturn(this, "isAtFinish() returns "+atFinish);
		return atFinish;
	}

	@Override
	public void collide(Oil t) {
		Logger.logCall(this, "collide(" + t + ")");
		Logger.logReturn(this, "collide(" + t + ")");
	}

	@Override
	public void collide(EndOfField t) {
		Logger.logCall(this, "collide(" + t + ")");
		Logger.logReturn(this, "collide(" + t + ")");
	}

	@Override
	public void collide(NormalTile t) {
		Logger.logCall(this, "collide(" + t + ")");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Van folt a mezőn? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Patch p;
			Logger.logMSG("Milyen folt van a mezőn? (1-Ragacs / 2-Olaj):\t");
			answer=inputScanner.next().toLowerCase();
			if(answer.equals("1"))
			{
				Logger.disable();
				p=new Goo();
				Logger.enable();
				p.accept(this);
			}
			else if(answer.equals("2"))
			{
				Logger.disable();
				p=new Oil();
				Logger.enable();
				p.accept(this);
			}
			else
			{
				Logger.logMSG("Érvénytelen válasz!\n");
			}
		}
		else if(!answer.equals("n"))
		{
			Logger.logMSG("Érvénytelen válasz!\n");
		}
		
		Logger.logReturn(this, "collide(" + t + ")");
	}

	@Override
	public void collide(Goo t) {
		Logger.logCall(this, "collide(" + t + ")");
		speed.halve();
		Logger.logReturn(this, "collide(" + t + ")");
	}

	@Override
	public void collide(Finish t) {
		Logger.logCall(this, "collide(" + t + ")");
		Logger.logReturn(this, "collide(" + t + ")");
	}
	

}
