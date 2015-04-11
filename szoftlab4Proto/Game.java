package szoftlab4Proto;

import java.util.List;

import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

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
	
	public void setTurn(Direction d, PatchType p){
		
	}
	
	public void newGame(int playerNum, int turns, String mapFile){
		
	}
	
	void nextTurn(){
		
	}
	
	int testWinConditions(){
		return 0;
	}
	
	void spawnJanitor(){
		
	}
	
	void ereaseDeadObjects(){
		
	}

}
