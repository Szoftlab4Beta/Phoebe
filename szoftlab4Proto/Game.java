package szoftlab4Proto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;
import szoftlab4Proto.MapFactory;
import szoftlab4Proto.Robot;

public class Game {
	
	MapFactory mapFactory;
	List<IUpdateable> deadObjects;
	List<IUpdateable> updateables;
	List<MoveableFieldObject> moveables;
	List<Robot> robots;
	int playerNum;
	int turns;
	int currentTurn;
	int currentRobot;
	int janitorSpawnInterval;
	int janitorCount;
	
	public static void main(String [] args)
	{
		Game game = new Game();
	}
	
	public Game() {
		int playerNum = 0;
		int turns = 0;
		int map = -1;
		
		Scanner sc = new Scanner(System.in);
		
		File folder = new File("mapFiles/");
		File[] listOfFiles = folder.listFiles();

		int map_count = 0;
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String name = file.getName().substring(0, file.getName().lastIndexOf("."));
		        System.out.println(map_count++ + " - " + name);
		    }
		}
		
		while(map < 0 || map > map_count){
			System.out.println("Választott pálya száma?");
		    while ((!sc.hasNextInt())) sc.next();
		    map = sc.nextInt();
		    if (map < 0 || map > map_count) System.out.println("Kérlek 0 és " + map_count + " közötti zámot adj meg");
		}
		
		String mapFile = String.format("mapFiles/%s", listOfFiles[map].getName());
		
		while(playerNum < 1){
			System.out.println("Játékosok száma? (Egész érték, 0 fölött)");
		    while ((!sc.hasNextInt())) sc.next();
		    playerNum = sc.nextInt();
		    if (playerNum < 1) System.out.println("Kérlek pozitív számot adj meg.");
		}
		
		while(turns < 1){
			System.out.println("Körök száma? (Egész érték, 0 fölött)");
		    while ((!sc.hasNextInt())) sc.next();
		    turns = sc.nextInt();
		    if (turns < 1) System.out.println("Kérlek pozitív számot adj meg.");
		}
		newGame(playerNum, turns, mapFile);	
		
		int winner = testWinConditions();
		while (winner < 0){
			currentRobot = 0;
			
			for (Robot element : robots) {
				int direction = -1;
				int type = -1;
				
				while(direction < 0 || direction > 4){
					System.out.println("Merre menjen? 1-Eszak, 2-Kelet, 3-Del, 4-Nyugat, 0-Semerre");
					while ((!sc.hasNextInt())) sc.next();
					direction = sc.nextInt();
					if (direction < 0 || direction > 4) {
						System.out.println("Kérlek 0-5 közötti számot adj meg.");
					}
				}
				
				while(type < 0 || type > 2){
					System.out.println("Hagyjon foltot? 1-Ragacsfoltot, 2-Olajfoltot, 0-Semmit");
					while ((!sc.hasNextInt())) sc.next();
					type = sc.nextInt();
					if (type < 0 || type > 2) {
						System.out.println("Kérlek 0-2 közötti számot adj meg.");
					}
				}
				
			    setTurn(
			    		VectorClass.Direction.values()[direction],
			    		Patch.PatchType.values()[type]
			    				);
			    
			    nextTurn();
			    winner = testWinConditions();
			    ereaseDeadObjects();
			    currentRobot++;
			}
		}
		
	}
		
	public void newGame(int playerNum, int turns, String mapFile){
		int startGoo = 3;
		int startOil = 3;
		currentTurn = 0;
		janitorCount = 0;
		janitorSpawnInterval = 4;
		
		mapFactory = new MapFactory();
		updateables = new ArrayList<IUpdateable>();
		deadObjects = new ArrayList<IUpdateable>();
		moveables = new ArrayList<MoveableFieldObject>();
		
		try {
			mapFactory.setFile(mapFile);
			mapFactory.buildMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.playerNum = playerNum;
		this.turns = turns;
		
		robots = new ArrayList<Robot>();
		for(int i = 0; i < this.playerNum; i++){
			robots.add( new Robot(mapFactory.getNextSpawn(), startGoo, startOil) );
		}
	}
	
	public void setTurn(Direction d, PatchType p){
		Robot robot = robots.get(currentRobot);
		if (d != VectorClass.Direction.None) {
			robot.modifySpeed(d);
		}
		if (p == Patch.PatchType.None) {
		}
		else if (p != Patch.PatchType.Goo) {
			//updateables.add(robot.placePatch(p));
		} else {
			robot.placePatch(p);
		}
		currentRobot++;
	}
	
	void nextTurn(){
		for (Robot element : robots) {
		    element.move();
		}
		int index = 0;
		for (IUpdateable element : updateables) {
			IUpdateable.UpdateReturnCode status = element.update();
		    if (status != IUpdateable.UpdateReturnCode.Alive){
		    	if (status == IUpdateable.UpdateReturnCode.Died){
		    		updateables.remove(index);
		    		deadObjects.add(element);
			    }
		    	else if (status == IUpdateable.UpdateReturnCode.JanitorDied){
		    		moveables.remove(element);
		    		janitorCount--;
			    }
		    	else if (status == IUpdateable.UpdateReturnCode.RobotDied){
		    		moveables.remove(element);
		    		playerNum--;
			    }
		    }
		    index++;
		}
		
		spawnJanitor();		
		currentTurn++;
	}
	
	int testWinConditions(){
		int winner = -1;
		int index = 0;
		int max = 0;
		
		
		if (currentTurn == turns){
			for (Robot element : robots) {
			    if (max < element.getDistance()){
			    	winner = index;
			    }
			    index++;
			}
		} else {
			for (Robot element : robots) {
			    if (element.isAtFinish()){
			    	winner = index;
			    	break;
			    }
			    index++;
			}
		}
		
		return winner;
	}
	
	void spawnJanitor(){
		if ((currentTurn % janitorSpawnInterval) == 0){
			new JanitorRobot(mapFactory.getNextSpawn());
			janitorCount++;
		}
	}
	
	void ereaseDeadObjects(){
		deadObjects.clear();
	}

}
