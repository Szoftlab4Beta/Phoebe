package szoftlab4Szkeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

/**
 * Az oszt�ly elv�gzi a program inicializ�l�s�t, �j j�t�k ind�t�s�t valamint annak levez�nyl�s�t.
 * Nyilv�ntartja a j�t�kosok, robotok, eltelt k�r�k sz�m�t valamint az egy�b j�t�kmenethez k�thet� inform�ci�kat
 */
public class Game {

	int playerNum;
	int turns;
	int currentTurn;
	int currentRobot;
	List<Robot> robots;
	MapFactory mapFactory;
	
	/**
	 * �j j�t�kot ind�t a newGame(int playerNum, int turns)-en kereszt�l.
	 * Kommunik�l a felhaszn�l�val �s az alapj�n vez�rli a robotokat a setTurn,nextTurn-�n kereszt�l.
	 * K�r v�g�n lek�rdezi nyert-e valaki a testWinCondition()-en kereszt�l �s az alapj�n j�r el.
	 */
	public Game() {
		Logger.logCreate(this, "game");
		Logger.logMSG("########## SZKELETON ##########\r\n");
    	Logger.logMSG("Szeretnel jatszani? (y/n)");
    	
    	boolean run = false;
    	Scanner in = new Scanner(System.in);
    	if (in.nextLine().toLowerCase().equals("y")) {
    		run = true;
    	}
    	
		while (run) {
			Logger.logMSG("Jatekosok szama? (1-10)");
			in = new Scanner(System.in);
			playerNum = in.nextInt();
			turns = 5;
			
			newGame(playerNum, turns);	
			
			int winner = -1;	
			while (winner == -1){
				currentRobot = 1;
				for (Robot element : robots) {
					Logger.logMSG(currentRobot + "-es Robot van soron.");
					
					Logger.logMSG("Merre menjen? 1-Eszak, 2-Kelet, 3-Del, 4-Nyugat, Minden mas => Semerre");
					in = new Scanner(System.in);
					int direction = in.nextInt();
					
					if ( (direction < 1) || (direction > 4)) {
						direction = 0;
					}
					
					Logger.logMSG("Hagyjon foltot? 1-Ragacsfoltot, 2-Olajfoltot Minden mas => Ne hagyjon foltot");
					in = new Scanner(System.in);
					int type = in.nextInt();
					if ( (type < 1) || (type > 2)) {
						type = 0;
					}
					
				    setTurn(
				    		VectorClass.Direction.values()[direction],
				    		Patch.PatchType.values()[type]
				    				);
				}
				
				nextTurn();		
				winner = testWinCondition();
			}
			
			Logger.logMSG("Szeretnel ujat jatszani? (y/n)");
	    	run = false;
	    	in = new Scanner(System.in);
	    	if (in.nextLine().toLowerCase().equals("y")) {
	    		run = true;
	    	}
		}	
		Logger.logMSG("Viszlat!");
	}
		
	
	/**
	 * �j j�t�kot ind�t. Be�ll�tja a maxim�lisan megengedett k�r�k sz�m�t (turns), 
	 * j�t�kosok sz�m�t (playerNum), majd l�trehozza az azokhoz tartoz� robotokat, 
	 * valamint bet�lteti �s inicializ�ltatja a p�ly�t a mapFactory objektummal.
	 * @param playerNum
	 * @param turns
	 */
	public void newGame(int playerNum, int turns){
		Logger.logCall(this, "newGame(" + playerNum + ", " +turns + ")");
		mapFactory = new MapFactory();
		mapFactory.buildMap();
		robots = new ArrayList<Robot>();
		
		for(int i = 0; i < playerNum; i++){
			robots.add( new Robot(mapFactory.getNextSpawn(), 10, 10) );
		}
		currentTurn = 0;
		Logger.logReturn(this, "newGame()");
	}
	
	
	/**
	 * A jelenleg soronl�v� robot sebess�g�nek m�dos�t�sa �s a lerakand� folt t�pus�nak
	 * be�ll�t�sa, annak modifySpeed �s placePatch nev� f�ggv�nyeinek megh�v�s�val.
	 * @param d
	 * @param type
	 */
	public void setTurn(Direction d, PatchType type){
		Logger.logCall(this, "setTurn()");
		if (currentRobot < playerNum){
			Robot robot = robots.get(currentRobot);
			robot.modifySpeed(d);
			robot.placePatch(type);
			currentRobot++;
		}
		Logger.logReturn(this, "setTurn()");
	}
	
	
	/**
	 * V�grehajtja a robotok setTurn-el el�re be�ll�tott l�p�seit (Mozg�s, Folt hagy�sa)
	 * Az �sszes robotra lefut.
	 */
	void nextTurn(){
		Logger.logCall(this, "nextTurn()");
		for (Robot element : robots) {
		    element.jump();
		}
		currentTurn++;
		Logger.logReturn(this, "nextTurn()");
	}
	
	
	/**
	 * Ellen�rzi hogy v�get �rt-e a j�t�k.
	 * Amennyiben m�r v�ge a j�t�kid�nek (utols� k�r is lement) a megtett �t alapj�n d�nt a nyertesr�l,
	 * a legt�bb utat megtett robot nyer.
	 * Ezen k�v�l ellen�rzi, majd sz�ks�g eset�n t�rli az akt�v robotok list�j�r�l a k�rben meghalt robotokat.
	 * @return int (-1|0|index) ahol az index a nyert robot indexe.
	 */
	int testWinCondition(){
	Logger.logCall(this, "testWinCondition()");
		
	Logger.logMSG("Utolso kor? (y/n)");
	Scanner in = new Scanner(System.in);
    	if (in.nextLine().toLowerCase().equals("y")) {
    		for (Robot element : robots) {
			    element.getDistance();
			}
    		
    		Logger.logMSG("Melyik robot nyert? (1-" + playerNum  + "), | Minden mas => nem nyert senki");
		in = new Scanner(System.in);
		int winner = in.nextInt();
    	
		Logger.logReturn(this, "" + winner);
		return winner;
    	} else {
		int index = 1;
		boolean win;
		boolean die;
		
		Logger.logMSG("Melyik robot nyert? (1-" + playerNum  + "), | Minden mas => nem nyert senki");
		in = new Scanner(System.in);
		int winner = in.nextInt();
		
		Logger.logMSG("Melyik robotok haltak?  Robotindexek: Vesszovel elvalasztva (1-" + playerNum  + ")");
		in = new Scanner(System.in);
		List<String> deads = Arrays.asList(in.nextLine().split(","));
		
		
		for (Robot element : robots) {
		    win = element.isAtFinish();
		    die = element.isDead();
		    
		    if (winner == index) {
		    	win = true;
		    }
		    
		    if (Arrays.asList(deads).contains(Integer.toString(index))) {
		    	die = true;
		    	Logger.logMSG("Halott");
		    }
		    
		    if (win){
		    	Logger.logReturn(this, ""+ index);
		    	return index;
		    } 
		    
		    if (die) {
		    	element.dead = true;
		    	robots.remove(index);
		    	playerNum--;
		    }
		    index++;
		}
	}
	Logger.logReturn(this, "-1");
	return -1;
	}
}
