package szoftlab4Szkeleton;

import java.util.List;
import java.util.Scanner;
import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

public class Game {

	int playerNum;
	int turns;
	int currentTurn;
	int currentRobot;
	List<Robot> robots;
	MapFactory mapFactory;
	
	public Game() {
		System.out.println("########## SZKELETON ##########");
    	System.out.println();
    	System.out.println("Szeretnél játszani? (i/n)");
    	
    	boolean run = false;
    	Scanner in = new Scanner(System.in);
    	if (in.nextLine().toLowerCase() == "i") {
    		run = true;
    	}
    	
		while (run) {
			System.out.println("Játékosok száma? (0-10)");
			in = new Scanner(System.in);
			playerNum = in.nextInt();
			
			System.out.println("Körök száma? (0-10)");
			in = new Scanner(System.in);
			turns = in.nextInt();
			
			newGame(playerNum, turns);	
			
			int winner = 0;	
			while (winner == 0){
				currentRobot = 1;
				for (Robot element : robots) {
					// Össze visza mennek, jelenleg, hogy legyen?
				    setTurn(
				    		VectorClass.Direction.values()[1 + (int)(Math.random()*5)],
				    		Patch.PatchType.values()[1 + (int)(Math.random()*5)]
				    				);
				}
				
				nextTurn();		
				winner = testWinCondition();
				
				System.out.println("Melyik robot nyert? (0-" + playerNum  + "), | -1 senki -> Játék vége | 0 senki, foyltatás");
				in = new Scanner(System.in);
				winner = in.nextInt();
			}
			
			System.out.println("Szeretnél újat játszani? (i/n)");
	    	run = false;
	    	in = new Scanner(System.in);
	    	if (in.nextLine().toLowerCase() == "i") {
	    		run = true;
	    	}
		}	
	}
		
	public void newGame(int playerNum, int turns){
		Logger.logCall(this, "newGame(" + playerNum + ", " +turns + ")");
		mapFactory = new MapFactory();
		mapFactory.buildMap();
		for(int i = 0; i < playerNum; i++){
			// Készletek bekérése, kell-e ?
			robots.add( new Robot(mapFactory.getNextSpawn(), 10, 10) );
		}
		currentTurn = 0;
		Logger.logReturn(this, "newGame()");
	}
	
	public void setTurn(Direction d, PatchType type){
		Logger.logCall(this, "setTurn()");
		if (currentRobot < playerNum){ // Átír ernyőre
			Robot robot = robots.get(currentRobot);
			robot.modifySpeed(d);
			robot.placePatch(type);
			currentRobot++;
		}
		Logger.logReturn(this, "setTurn()");
	}
	
	void nextTurn(){
		Logger.logCall(this, "nextTurn()");
		for (Robot element : robots) {
		    element.jump();
		}
		currentTurn++;
		Logger.logReturn(this, "nextTurn()");
	}
	
	int testWinCondition(){
		Logger.logCall(this, "testWinCondition()");
		if (currentTurn == turns) { // Átírni ernyőre
			for (Robot element : robots) {
			    element.getDistance();
			}
			//Bekéne kérni nyert-e valaki?
			Logger.logReturn(this, "0");
			return 0;
		} else {
			int index = 1;
			boolean win;
			boolean die;
			for (Robot element : robots) {
			    win = element.isAtFinish();
			    die = element.isDead();
			    //Bekéri Finishelt-e ?
			    if (win){
			    	Logger.logReturn(this, ""+ index);
			    	return index;
			    }
			    //Bekéri meghalt-e ?
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
