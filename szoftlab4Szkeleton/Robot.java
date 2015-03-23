package szoftlab4Szkeleton;

import java.util.Scanner;

import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

public class Robot implements IColliding {

	int gooAmount, oilAmount;
	boolean dead, canChangeSpeed, atFinish;
	Tile position;
	VectorClass speed, distance;
	
	/**
	 * Be�ll�tja a robot kezd��llapot�t.
	 * Be�ll�tja a ragacs- �s olajk�szlet�t, poz�ci�j�t,
	 * valamint inicializ�lja a t�vols�g �s sebess�gvektorait.
	 * <p>
	 * Amennyiben a spawnTile param�terek�nt null-t kap, IllegalArgumentException-t dob.
	 * 
	 * @param spawnTile Az a mez�, amelyr�l a robot elindul
	 * @param startGoo A j�t�k kezdetekor a robot sz�m�ra rendelkez�sre �ll� ragacsk�szlet
	 * @param startOil A j�t�k kezdetekor a robot sz�m�ra rendelkez�sre �ll� olajk�szlet
	 */
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
	
	/**
	 * A robot mozgat�s�t v�gzi annak position �s speed attrib�tumainak felhaszn�l�s�val.
	 * A jelenlegi poz�ci�r�l mez�nk�nt halad a sebess�gvektor szerint eg�szen addig,
	 * am�g meg nem tette az annak megfelel� t�vols�got, vagy nem ker�lt ki a p�ly�n k�v�lre.
	 * <p>
	 * Abban az esetben, ha a robot a p�ly�r�l let�rt a halad�s k�zben, be�ll�tja a dead v�ltoz�j�t,
	 * ezzel jelezve, hogy nem vehet r�szt a jelenlegi j�t�k tov�bbi k�reiben.
	 * Az ugr�s ut�ni �j poz�ci�t el�rve az alatta l�v� mez� t�pus�t�l f�gg�en viselkedik.
	 * Sikeres ugr�st k�vet�en n�veli a robot �ltal �sszesen megtett t�vols�got.
	 */
	public void jump()
	{
		Logger.logCall(this, "jump()");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("A p�ly�n marad a robot? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Tile tempt;
			Logger.logMSG("Milyen t�pus� mez�re ugrik a robot? (1-J�t�kT�rV�ge / 2-C�l / 3-Norm�l):\t");
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
				Logger.logMSG("�rv�nytelen v�lasz!\n");
			}
		}
		else if(!answer.equals("n"))
		{
			Logger.logMSG("�rv�nytelen v�lasz!\n");
		}
		
		Logger.logReturn(this, "jump()");
	}
	
	/**
	 * A robot sebess�g�t m�dos�tja a megadott ir�ny szerint.
	 * Abban az esetben, ha param�terk�nt Direction.None volt megadva �s/vagy nem m�dos�thatja a sebess�g�t,
	 * nem t�rt�nik sebess�gv�ltoztat�s.
	 * 
	 * @param d Az az ir�ny, amelynek egys�gvektor�val m�dos�tani k�v�njuk a robot sebess�g�t
	 */
	public void modifySpeed(Direction d)
	{
		Logger.logCall(this, "modifySpeed(Direction d)");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("M�dos�that-e sebess�get? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
			speed.add(d);
		else if(!answer.equals("n"))
			Logger.logMSG("�rv�nytelen v�lasz!\n");
		
		Logger.logReturn(this, "modifySpeed(Direction d)");
	}
	
	/**
	 * Lehelyez a robot alatti mez�re egy a param�ter�ben megadott t�pus� foltot.
	 * Nem t�rt�nik foltlerak�s, amennyiben a t�pusnak megfelel� foltb�l kifogyott a k�szlet.
	 * <p>
	 * Amennyiben a mez�n m�r volt folt, �s a lerakand� folt t�pusa nem PatchType.None,
	 * a mez�n l�v� folt a lerakand� folt t�pus�ra v�ltozik.
	 * 
	 * @param type A robot alatti mez�re helyezend� folt t�pusa
	 */
	public void placePatch(PatchType type){
		Logger.logCall(this, "placePatch(PatchType type)");
		
		if(type!=PatchType.None)
		{
			Scanner inputScanner=new Scanner(System.in);
			Logger.logMSG("Van-e ilyen folt a rakt�rban? (I/N):\t");
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
					break;
				default:
					break;
				}
				((NormalTile)position).setPatch(p);
			}
			else if(!answer.equals("n"))
			{
				Logger.logMSG("�rv�nytelen v�lasz!\n");
			}
		}
		
		Logger.logReturn(this, "placePatch(PatchType type)");
	}
	
	/**
	 * Visszaadja a jelenlegi j�t�k alatt a robot �ltal megtett �sszt�vols�got mez�kben.
	 * <p>
	 * A t�vols�g a robot distance attrib�tum�ban tal�lhat� ir�nyokhoz rendelt sz�mok �sszead�s�val sz�mol�dik ki.
	 * 
	 * @return A robot �ltal megtett t�vols�g mez�kben m�rve
	 */
	public int getDistance(){
		Logger.logCall(this, "getDistance()");
		Logger.logReturn(this, "getDistance() returns "+0);
		
		return 0;
	}
	
	/**
	 * Visszadja, hogy a robot halott-e, azaz kiesett a j�t�kb�l vagy sem.
	 * 
	 * @return A robot halott-e
	 */
	public boolean isDead(){
		Logger.logCall(this, "isDead()");
		Logger.logReturn(this, "isDead() returns "+dead);
		return dead;
	}
	
	
	/**
	 * Visszadja, hogy a robot c�lmez�n van-e.
	 * 
	 * @return A robot c�lmez�n van-e
	 */
	public boolean isAtFinish(){
		Logger.logCall(this, "isAtFinish()");
		Logger.logReturn(this, "isAtFinish() returns "+atFinish);
		return atFinish;
	}
	
	
	/**
	 * Meghat�rozza, hogy mi t�rt�nik abban az esetben, ha a robot olyan mez�re l�pett, amelyen olajfolt van.
	 * Be�ll�tja a robot megfelel� attrib�tum�t �gy, hogy a robot ne tudja a k�vetkez� k�rben a sebess�g�t m�dos�tani.
	 * 
	 * @param t Az az Oil t�pus� objektum, amely a robot ezen met�dus�t h�vta a Visitor pattern szerint
	 */
	@Override
	public void collide(Oil t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghat�rozza, hogy mi t�rt�nik abban az esetben, ha a robot EndOfField t�pus� (p�ly�hoz nem tartoz�) mez�re l�pett.
	 * Be�ll�tja a robot dead attrib�tum�t true-ra, ezzel jelezve hogy kiesett a j�t�kb�l.
	 * 
	 * @param t Az az EndOfField t�pus� objektum, amely a robot ezen met�dus�t h�vta a Visitor pattern szerint
	 */
	@Override
	public void collide(EndOfField t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghat�rozza, hogy mi t�rt�nik abban az esetben, ha a robot NormalTile t�pus� mez�re l�pett.
	 * <p>
	 * Att�l f�gg�en, hogy a mez�n van-e valamilyen folt, tov�bbh�v a mez�nl�v� folton a Visitor mint�nak megfelel�en.
	 * Ez fogja majd eld�nteni hogy mi t�rt�nik a mez�re l�p�s k�vetkezt�ben.
	 * Abban az esetben, ha a mez�n nincs folt, a f�ggv�ny mindenf�le �llapotv�ltoztat�s n�lk�l visszat�r.
	 * 
	 * @param t Az a NormalTile t�pus� objektum, amely a robot ezen met�dus�t h�vta a Visitor pattern szerint
	 */
	@Override
	public void collide(NormalTile t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Van folt a mez�n? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Patch p;
			Logger.logMSG("Milyen folt van a mez�n? (1-Ragacs / 2-Olaj):\t");
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
				Logger.logMSG("�rv�nytelen v�lasz!\n");
			}
		}
		else if(!answer.equals("n"))
		{
			Logger.logMSG("�rv�nytelen v�lasz!\n");
		}
		
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghat�rozza, hogy mi t�rt�nik abban az esetben, ha a robot olyan mez�re l�pett, amelyen ragacsfolt van.
	 * A robot sebess�gvektor�t lefelezi a speed attrib�tum megfelel� f�ggv�ny�nek megh�v�s�val.
	 * 
	 * @param t Az a Goo t�pus� objektum, amely a robot ezen met�dus�t h�vta a Visitor pattern szerint
	 */
	@Override
	public void collide(Goo t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		speed.halve();
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghat�rozza, hogy mi t�rt�nik abban az esetben, ha a robot Finish t�pus� mez�re (c�lmez�re) l�pett.
	 * Be�ll�tja true-ra a robot azon attrib�tum�t, amely jelzi, hogy a robot c�lmez�n tart�zkodik.
	 * 
	 * @param t Az a Finish t�pus� objektum, amely a robot ezen met�dus�t h�vta a Visitor pattern szerint
	 */
	@Override
	public void collide(Finish t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
}
