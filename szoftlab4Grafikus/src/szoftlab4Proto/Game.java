package szoftlab4Proto;

import graphics.GUIButtonActionListener;
import graphics.GraphicsController;

import java.util.ArrayList;
import java.util.List;

import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;
import szoftlab4Proto.MapFactory;
import szoftlab4Proto.Robot;

/**
 * Game Osztály
 * 
 * Felelősség: A program inicializálása, új játék indítása,
 * valamint annak levezénylése. A játékosok számának, robotjainak nyilvántartása
 * valamint egyéb a játékmenethez köthető információk tárolása, kezelése.
 */
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
	GraphicsController graphicsController;
	
	public Game(){
		GUIButtonActionListener listener = new GUIButtonActionListener(this);
		graphicsController = new GraphicsController(listener);
		listener.setMainWindow(graphicsController);

	}//end Game()
		
	
	/**
	 * Új játék indítása
	 * 
	 * Beállítja a maximálisan megengedett körök számát (turns), 
	 * játékosok számát (playerNum), létrehozza az azokhoz tartozó robotokat,
	 * valamint betölteti és inicializáltatja a pályát a mapFactory objektummal.
	 * 
	 * @param playerNum - A játékosok száma
	 * @param turns - Játék köreinek száma
	 * @param mapFile - A térkép fájl neve
	 * @return Tile[][] - A pálya mezői
	 */
	public void newGame(int playerNum, int turns, String mapFile){
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
		graphicsController.setMap(ret, mapFactory.horizontal, mapFactory.vertical);

		this.playerNum = playerNum;
		this.turns = turns;
		
		robots = new ArrayList<Robot>();
		Robot r;
		for(int i = 0; i < this.playerNum; i++){
			r = new Robot(mapFactory.getNextSpawn(), startGoo, startOil, "Robot " + i);
			robots.add(r);
			updateables.add(r);
			moveables.add(r);
		}
		graphicsController.redrawMap();
	}//end newGame()
	
	/**
	 * Kör akcióinak beállítása
	 * 
	 * A jelenleg soronlévő (currentRobot) robot sebességének módosítása és a
	 * lerakandó folt típusának beállítása a kapott fügvény paramétereken keresztül,
	 * a robot modifySpeed és placePatch nevű függvényeinek meghívásával.
	 * 
	 * @param d - Direction
	 * @param p - PatchType
	 */
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
		graphicsController.setCurrentRobot(robots.get(currentRobot).name);
		if(currentRobot == robots.size())
			nextTurn();
	}//end setTurn()
	
	
	/**
	 * Kör léptetése
	 * 
	 * A kör végén hívódik meg, elvégzi a kör végi módosításokat,
	 * Frissíti az updateables, deadOjects listákat
	 * lépteti a robotokat - MoveableFieldObject.move()
	 * olajfoltokat szárítja - IUpdateable.update()
	 */
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
	    graphicsController.setCurrentRobot("Robot " + currentRobot);
		currentTurn++;
		graphicsController.redrawMap();
	}//end nextTurn()
	
	
	/**
	 * Győzelmi feltételek teztelése
	 * 
	 * Megvizsgálja, hogy valamelyik robot teljesítette-e a győzelem valamely feltételét
	 * (Célba ért/Lejárt az idő és a legnagyobb távot tette meg) 
	 * majd visszatér a győztes robot indexével. Ha pedig nem volt győztes -1el
	 * 
	 * @return int - Winner, Győztes robot indexe (vagy -1)
	 */
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
	}//end testWinConditions()
	
	
	/**
	 * Janitor robotot hoz létre a paraméterül kapott mezőn majd viszszatér
	 * a janitorRobot objektumával
	 * 
	 * @param spawnTile - Egy mező ahol éledhet a JanitorRobot
	 * @return JanitorRobot r - A létrehozott JanitorRobot
	 */
	public JanitorRobot spawnJanitor(Tile spawnTile){
		JanitorRobot r = new JanitorRobot(spawnTile);
		updateables.add(r);
		moveables.add(r);
		janitorCount++;
		
		return r;
	} // end spawnJanitor()
	
	
	/**
	 *  Halott objektumok törlése
	 */
	void ereaseDeadObjects(){
		deadObjects.clear();
	}// end ereaseDeadObjects()
	
	
	/**
	 * Visszaadja a játékosok számát.
	 * @return int playerNum - Játékosok száma
	 */
	public int getPlayerNum(){
		return playerNum;
	}//end getPlayerNum()
	
	/**
	 * Visszaadja a paraméterül kapott indexü robot objektumát.
	 * @param idx
	 * @return Robot - Adott indexű robot
	 */
	public Robot getRobot(int idx){
		return robots.get(idx);
	}//end getRobot()

}
