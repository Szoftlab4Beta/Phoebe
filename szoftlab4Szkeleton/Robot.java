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
		Logger.logCreate(this, "Robot");
		
		if(Logger.isDisabled())
			return;
		
		Logger.logCall(this, "Robot(Tile spawnTile, int startGoo, int startOil)");
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("�rv�nyes-e az �led� mez�? (I/N):\t");
		if(inputScanner.next().equalsIgnoreCase("n"))
			throw new IllegalArgumentException("Invalid spawn point. (Argument is null)");
		
		distance=new VectorClass();
		Logger.logCreate(distance, Logger.getIDOf(this)+"_DistanceVector");
		speed=new VectorClass();
		Logger.logCreate(speed, Logger.getIDOf(this)+"_SpeedVector");
		
		Logger.logReturn(this, "Robot(Tile spawnTile, int startGoo, int startOil)");
	}
	
	public void jump()
	{
		Logger.logCall(this, "jump()");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("A p�ly�n marad a robot? (I/N):\t");
		if(inputScanner.next().equalsIgnoreCase("i"))
		{
			Tile tempt;
			Logger.logMSG("Milyen t�pus� mez�re ugrik a robot? (Ragacs / Olaj / J�t�kT�rV�ge / C�l / Norm�l):\t");
			String answer=inputScanner.next().toLowerCase();
			if(answer.equals("ragacs"))
			{
				Logger.disable();
				tempt=new NormalTile();
				((NormalTile)tempt).setPatch(new Goo());
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("olaj"))
			{
				Logger.disable();
				tempt=new NormalTile();
				((NormalTile)tempt).setPatch(new Oil());
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("j�t�kt�rv�ge"))
			{
				Logger.disable();
				tempt=new EndOfField();
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("c�l"))
			{
				Logger.disable();
				tempt=new Finish();
				Logger.enable();
				
				tempt.accept(this);
			}
			else if(answer.equals("norm�l"))
			{
				Logger.disable();
				tempt=new NormalTile();
				Logger.enable();
				
				tempt.accept(this);
			}
		}
		
		Logger.logReturn(this, "jump()");
	}
	
	public void modifySpeed(Direction d)
	{
		Logger.logCall(this, "modifySpeed(Direction d)");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Volt sebess�gvektor megadva? (I/N):\t");
		if(inputScanner.next().equalsIgnoreCase("i"))
		{
			Logger.logMSG("M�dos�that-e sebess�get? (I/N):\t");
			if(inputScanner.next().equalsIgnoreCase("i"))
				speed.add(d);
		}
		else if(!inputScanner.next().equalsIgnoreCase("n"))
		{
			Logger.logMSG("�rv�nytelen v�lasz!");
		}
		
		Logger.logReturn(this, "modifySpeed(Direction d)");
	}
	
	public void placePatch(PatchType type){
		Logger.logCall(this, "placePatch(PatchType type)");
		
		Patch p = null;
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Milyen folt hagy�sa? (Olaj / Ragacs / Semmilyen):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("olaj"))
		{
			p=new Oil();
		}
		else if(answer.equals("ragacs"))
		{
			p=new Goo();
		}
		else if(answer.equals("semmilyen"))
		{
			Logger.logReturn(this, "placePatch(PatchType type)");
			return;
		}
		else
		{
			Logger.logMSG("�rv�nytelen v�lasz!");
			return;
		}
		
		Logger.logMSG("Van-e ilyen folt a rakt�rban? (I/N):\t");
		if(inputScanner.next().equalsIgnoreCase("i"))
		{
			Logger.disable();
			position=new NormalTile();
			Logger.enable();
			((NormalTile)position).setPatch(p);
		}
		else if(!inputScanner.next().equalsIgnoreCase("n"))
		{
			Logger.logMSG("�rv�nytelen v�lasz!");
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
		Logger.logCall(this, "collide(Oil t)");
		Logger.logReturn(this, "collide(Oil t)");
	}

	@Override
	public void collide(EndOfField t) {
		Logger.logCall(this, "collide(EndOfField t)");
		Logger.logReturn(this, "collide(EndOfField t)");
	}

	@Override
	public void collide(NormalTile t) {
		Logger.logCall(this, "collide(NormalTile t)");
		Logger.logReturn(this, "collide(NormalTile t)");
	}

	@Override
	public void collide(Goo t) {
		Logger.logCall(this, "collide(Goo t)");
		speed.halve();
		Logger.logReturn(this, "collide(Goo t)");
	}

	@Override
	public void collide(Finish t) {
		Logger.logCall(this, "collide(Finish t)");
		Logger.logReturn(this, "collide(Finish t)");
	}
	

}
