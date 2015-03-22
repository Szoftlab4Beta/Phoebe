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
		Logger.logMSG("########## SZKELETON ##########");
    	Logger.logMSG("");
    	Logger.logMSG("SzeretnĂ©l jĂˇtszani? (i/n)");
    	
    	boolean run = false;
    	Scanner in = new Scanner(System.in);
    	if (in.nextLine().toLowerCase() == "i") {
    		run = true;
    	}
    	
		while (run) {
			Logger.logMSG("JĂˇtĂ©kosok szĂˇma? (0-10)");
			in = new Scanner(System.in);
			playerNum = in.nextInt();
			
			Logger.logMSG("KĂ¶rĂ¶k szĂˇma? (0-10)");
			in = new Scanner(System.in);
			turns = in.nextInt();
			
			newGame(playerNum, turns);	
			
			int winner = 0;	
			while (winner == 0){
				currentRobot = 1;
				for (Robot element : robots) {
					// Ă–ssze visza mennek, jelenleg, hogy legyen?
					///MEG KELL KÉRDEZNI A USER-T MELYIK IRÁNYBA MENJENEK SWITCH... ?
					///mMEG KELL KÉRDEZNI AKAR E FOLTOT HAGYNI (ÉS MILYET)
				    setTurn(
				    		VectorClass.Direction.values()[1 + (int)(Math.random()*5)],
				    		Patch.PatchType.values()[1 + (int)(Math.random()*5)]
				    				);
				}
				
				nextTurn();		
				winner = testWinCondition();
				
				Logger.logMSG("Melyik robot nyert? (0-" + playerNum  + "), | -1 senki -> JĂˇtĂ©k vĂ©ge | 0 senki, foyltatĂˇs");
				in = new Scanner(System.in);
				winner = in.nextInt();
			}
			
			Logger.logMSG("SzeretnĂ©l Ăşjat jĂˇtszani? (i/n)");
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
			// KĂ©szletek bekĂ©rĂ©se, kell-e ?
			///NEM KELL, EZT 
			robots.add( new Robot(mapFactory.getNextSpawn(), 10, 10) );
		}
		currentTurn = 0;
		Logger.logReturn(this, "newGame()");
	}
	
	public void setTurn(Direction d, PatchType type){
		Logger.logCall(this, "setTurn()");
		if (currentRobot < playerNum){ // Ă�tĂ­r ernyĹ‘re
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
		if (currentTurn == turns) { // Ă�tĂ­rni ernyĹ‘re
			for (Robot element : robots) {
			    element.getDistance();
			}
			//BekĂ©ne kĂ©rni nyert-e valaki?
			///IGEN
			Logger.logReturn(this, "0");
			return 0;
		} else {
			int index = 1;
			boolean win;
			boolean die;
			for (Robot element : robots) {
			    win = element.isAtFinish();
			    die = element.isDead();
			    //BekĂ©ri Finishelt-e ?
			    ///PONTOSAN
			    if (win){
			    	Logger.logReturn(this, ""+ index);
			    	return index;
			    }
			    //BekĂ©ri meghalt-e ?
			    ///SZINTÉN
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
