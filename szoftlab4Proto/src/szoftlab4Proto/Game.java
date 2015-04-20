package szoftlab4Proto;

import java.util.ArrayList;
import java.util.List;

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
	
	public Game(){
		
	}
	
//	void mainLoop() {						//TODO asszem ezt jelenleg a testFileok irányítják (nincs rá igazán szükség csak osztályba foglaltam)
//		int winner = testWinConditions();
//		while (winner < 0){
//			currentRobot = 0;
//			
//			for (Robot element : robots) {
//				int direction = -1;
//				int type = -1;
//								
//			    setTurn(
//			    		VectorClass.Direction.values()[direction],
//			    		Patch.PatchType.values()[type]
//			    				);
//			    currentRobot++;
//			}
//			nextTurn();
//		    winner = testWinConditions();
//		    ereaseDeadObjects();
//			spawnJanitor(mapFactory.getNextSpawn());
//		}
//	}
		
	public Tile[][] newGame(int playerNum, int turns, String mapFile){
		Tile[][] ret = null;
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
			ret = mapFactory.buildMap(updateables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.playerNum = playerNum;
		this.turns = turns;
		
		robots = new ArrayList<Robot>();
		Robot r;
		for(int i = 0; i < this.playerNum; i++){
			r = new Robot(mapFactory.getNextSpawn(), startGoo, startOil);
			robots.add(r);
			updateables.add(r);
			moveables.add(r);
		}
		//mainLoop(); //TODO same
		return ret;
	}
	
	public void setTurn(Direction d, PatchType p){
		Robot robot = robots.get(currentRobot);
		if (d != VectorClass.Direction.None) {
			robot.modifySpeed(d);
		}
		if (p == Patch.PatchType.Goo) {
			robot.placePatch(p);
		}
		else if (p == Patch.PatchType.Oil) {
			Oil o = (Oil)robot.placePatch(p);
			if(o != null)
				updateables.add(o);
		}
		currentRobot++;
	}
	
	public void nextTurn(){
		for (MoveableFieldObject element : moveables) {
		    element.move();
		}
		int maxIndex = updateables.size();
		IUpdateable element;
		for (int index = 0; index < maxIndex; index++) {
			element = updateables.get(index);
			IUpdateable.UpdateReturnCode status = element.update();
		    if (status != IUpdateable.UpdateReturnCode.Alive){
		    	if (status == IUpdateable.UpdateReturnCode.Died){
		    		updateables.remove(index);
		    		deadObjects.add(element);
			    }
		    	else if (status == IUpdateable.UpdateReturnCode.JanitorDied){
		    		moveables.remove(element);
		    		updateables.remove(index);
		    		janitorCount--;
			    }
		    	else if (status == IUpdateable.UpdateReturnCode.RobotDied){
		    		moveables.remove(element);
		    		updateables.remove(index);
		    		robots.remove(element);
		    		playerNum--;
			    }
		    	index--;
		    	maxIndex = updateables.size();
		    }
		}
	    currentRobot = 0;
		currentTurn++;
	}
	
	public int testWinConditions(){
		int winner = -1;
		int index = 0;
		int max = -1;
		
		
		if (currentTurn == turns){
			for (Robot element : robots) {
			    if (max < element.getDistance()){
			    	winner = index;
			    	max = element.getDistance();
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
	
	public JanitorRobot spawnJanitor(Tile spawnTile){
		JanitorRobot r = new JanitorRobot(spawnTile);
		updateables.add(r);
		moveables.add(r);
		janitorCount++;
		
		return r;
	}
	
	void ereaseDeadObjects(){
		deadObjects.clear();
	}
	
	public int getPlayerNum(){
		return playerNum;
	}
	
	public Robot getRobot(int idx){
		return robots.get(idx);
	}

}
