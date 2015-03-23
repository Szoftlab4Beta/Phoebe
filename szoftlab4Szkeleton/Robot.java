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
	 * Beállítja a robot kezdõállapotát.
	 * Beállítja a ragacs- és olajkészletét, pozícióját,
	 * valamint inicializálja a távolság és sebességvektorait.
	 * <p>
	 * Amennyiben a spawnTile paramétereként null-t kap, IllegalArgumentException-t dob.
	 * 
	 * @param spawnTile Az a mezõ, amelyrõl a robot elindul
	 * @param startGoo A játék kezdetekor a robot számára rendelkezésre álló ragacskészlet
	 * @param startOil A játék kezdetekor a robot számára rendelkezésre álló olajkészlet
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
	 * A robot mozgatását végzi annak position és speed attribútumainak felhasználásával.
	 * A jelenlegi pozícióról mezõnként halad a sebességvektor szerint egészen addig,
	 * amíg meg nem tette az annak megfelelõ távolságot, vagy nem került ki a pályán kívülre.
	 * <p>
	 * Abban az esetben, ha a robot a pályáról letért a haladás közben, beállítja a dead változóját,
	 * ezzel jelezve, hogy nem vehet részt a jelenlegi játék további köreiben.
	 * Az ugrás utáni új pozíciót elérve az alatta lévõ mezõ típusától függõen viselkedik.
	 * Sikeres ugrást követõen növeli a robot által összesen megtett távolságot.
	 */
	public void jump()
	{
		Logger.logCall(this, "jump()");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("A pályán marad a robot? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Tile tempt;
			Logger.logMSG("Milyen típusú mezõre ugrik a robot? (1-JátékTérVége / 2-Cél / 3-Normál):\t");
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
	
	/**
	 * A robot sebességét módosítja a megadott irány szerint.
	 * Abban az esetben, ha paraméterként Direction.None volt megadva és/vagy nem módosíthatja a sebességét,
	 * nem történik sebességváltoztatás.
	 * 
	 * @param d Az az irány, amelynek egységvektorával módosítani kívánjuk a robot sebességét
	 */
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
	
	/**
	 * Lehelyez a robot alatti mezõre egy a paraméterében megadott típusú foltot.
	 * Nem történik foltlerakás, amennyiben a típusnak megfelelõ foltból kifogyott a készlet.
	 * <p>
	 * Amennyiben a mezõn már volt folt, és a lerakandó folt típusa nem PatchType.None,
	 * a mezõn lévõ folt a lerakandó folt típusára változik.
	 * 
	 * @param type A robot alatti mezõre helyezendõ folt típusa
	 */
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
	
	/**
	 * Visszaadja a jelenlegi játék alatt a robot által megtett össztávolságot mezõkben.
	 * <p>
	 * A távolság a robot distance attribútumában található irányokhoz rendelt számok összeadásával számolódik ki.
	 * 
	 * @return A robot által megtett távolság mezõkben mérve
	 */
	public int getDistance(){
		Logger.logCall(this, "getDistance()");
		Logger.logReturn(this, "getDistance() returns "+0);
		
		return 0;
	}
	
	/**
	 * Visszadja, hogy a robot halott-e, azaz kiesett a játékból vagy sem.
	 * 
	 * @return A robot halott-e
	 */
	public boolean isDead(){
		Logger.logCall(this, "isDead()");
		Logger.logReturn(this, "isDead() returns "+dead);
		return dead;
	}
	
	
	/**
	 * Visszadja, hogy a robot célmezõn van-e.
	 * 
	 * @return A robot célmezõn van-e
	 */
	public boolean isAtFinish(){
		Logger.logCall(this, "isAtFinish()");
		Logger.logReturn(this, "isAtFinish() returns "+atFinish);
		return atFinish;
	}
	
	
	/**
	 * Meghatározza, hogy mi történik abban az esetben, ha a robot olyan mezõre lépett, amelyen olajfolt van.
	 * Beállítja a robot megfelelõ attribútumát úgy, hogy a robot ne tudja a következõ körben a sebességét módosítani.
	 * 
	 * @param t Az az Oil típusú objektum, amely a robot ezen metódusát hívta a Visitor pattern szerint
	 */
	@Override
	public void collide(Oil t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghatározza, hogy mi történik abban az esetben, ha a robot EndOfField típusú (pályához nem tartozó) mezõre lépett.
	 * Beállítja a robot dead attribútumát true-ra, ezzel jelezve hogy kiesett a játékból.
	 * 
	 * @param t Az az EndOfField típusú objektum, amely a robot ezen metódusát hívta a Visitor pattern szerint
	 */
	@Override
	public void collide(EndOfField t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghatározza, hogy mi történik abban az esetben, ha a robot NormalTile típusú mezõre lépett.
	 * <p>
	 * Attól függõen, hogy a mezõn van-e valamilyen folt, továbbhív a mezõnlévõ folton a Visitor mintának megfelelõen.
	 * Ez fogja majd eldönteni hogy mi történik a mezõre lépés következtében.
	 * Abban az esetben, ha a mezõn nincs folt, a függvény mindenféle állapotváltoztatás nélkül visszatér.
	 * 
	 * @param t Az a NormalTile típusú objektum, amely a robot ezen metódusát hívta a Visitor pattern szerint
	 */
	@Override
	public void collide(NormalTile t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		
		Scanner inputScanner=new Scanner(System.in);
		Logger.logMSG("Van folt a mezõn? (I/N):\t");
		String answer=inputScanner.next().toLowerCase();
		if(answer.equals("i"))
		{
			Patch p;
			Logger.logMSG("Milyen folt van a mezõn? (1-Ragacs / 2-Olaj):\t");
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
		
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghatározza, hogy mi történik abban az esetben, ha a robot olyan mezõre lépett, amelyen ragacsfolt van.
	 * A robot sebességvektorát lefelezi a speed attribútum megfelelõ függvényének meghívásával.
	 * 
	 * @param t Az a Goo típusú objektum, amely a robot ezen metódusát hívta a Visitor pattern szerint
	 */
	@Override
	public void collide(Goo t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		speed.halve();
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
	
	/**
	 * Meghatározza, hogy mi történik abban az esetben, ha a robot Finish típusú mezõre (célmezõre) lépett.
	 * Beállítja true-ra a robot azon attribútumát, amely jelzi, hogy a robot célmezõn tartózkodik.
	 * 
	 * @param t Az a Finish típusú objektum, amely a robot ezen metódusát hívta a Visitor pattern szerint
	 */
	@Override
	public void collide(Finish t) {
		Logger.logCall(this, "collide(" + Logger.getIDOf(t) + ")");
		Logger.logReturn(this, "collide(" + Logger.getIDOf(t) + ")");
	}
}
