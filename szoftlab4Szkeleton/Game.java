package szoftlab4Szkeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

/**
 * Az osztály elvégzi a program inicializálását, új játék indítását valamint annak levezénylését.
 * Nyilvántartja a játékosok, robotok, eltelt körök számát valamint az egyéb játékmenethez köthető információkat
 */

public class Game {

	int playerNum;
	int turns;
	int currentTurn;
	int currentRobot;
	List<Robot> robots;
	MapFactory mapFactory;
	
	public static void main(String [] args)
	{
		Game game = new Game();
	}
	
	/**
	 * Új játékot indít a newGame(int playerNum, int turns)-en keresztül.
	 * Kommunikál a felhasználóval és az alapján vezérli a robotokat a setTurn,nextTurn-ön keresztül.
	 * Kör végén lekérdezi nyert-e valaki a testWinCondition()-en keresztül és az alapján jár el.
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
			
			/*
			// TODO Törölni majd a kommentet, vagy visszatenni.
			//
			Lehet felesleges, hisz semmin nem változtat.
			Logger.logMSG("Korok szama? (1-10)");
			in = new Scanner(System.in);
			turns = in.nextInt();
			*/
			turns = 5;
			
			newGame(playerNum, turns);	
			
			int winner = 0;	
			while (winner == 0){
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
	 * Új játékot indít. Beállítja a maximálisan megengedett körök számát (turns), 
	 * játékosok számát (playerNum), majd létrehozza az azokhoz tartozó robotokat, 
	 * valamint betölteti és inicializáltatja a pályát a mapFactory objektummal.
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
	 * A jelenleg soronlévő robot sebességének módosítása és a lerakandó folt típusának
	 * beállítása, annak modifySpeed és placePatch nevű függvényeinek meghívásával.
	 * @param d
	 * @param type
	 */
	
	public void setTurn(Direction d, PatchType type){
		Logger.logCall(this, "setTurn()");
		//Elvileg ez nem teljesul, de szekvencian rajtavolt, bekerni hogy igaz-e szerintem folos.
		if (currentRobot < playerNum){
			Robot robot = robots.get(currentRobot);
			robot.modifySpeed(d);
			robot.placePatch(type);
			currentRobot++;
		}
		Logger.logReturn(this, "setTurn()");
	}
	
	
	/**
	 * Végrehajtja a robotok setTurn-el előre beállított lépéseit (Mozgás, Folt hagyása)
	 * Az összes robotra lefut.
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
	 * Ellenőrzi hogy véget ért-e a játék.
	 * Amennyiben már vége a játékidőnek (utolsó kör is lement) a megtett út alapján dönt a nyertesről,
	 * a legtöbb utat megtett robot nyer.
	 * Ezen kívül ellenőrzi, majd szükség esetén törli az aktív robotok listájáról a körben meghalt robotokat.
	 * @return
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
			    
			    // TODO Törölni majd a kommentet
			    // Ezek azért vannak hogy ne a vissztért értéktől függjön,
			    // De ne is kelljen bekérni robotonként az adatokat
			    
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
