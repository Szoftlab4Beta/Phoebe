package szoftlab4Szkeleton;

import java.util.ArrayList;
import java.util.List;

import szoftlab4Szkeleton.VectorClass.Direction;

public class MapFactory {
	
	List<Tile> spawnTiles;

	public MapFactory() {
		Logger.logCreate(this, "mapFactory");
		spawnTiles = new ArrayList<Tile>();
	}
	
	public void buildMap(){
		Logger.logCall(this, "buildMap()");
		Tile middleTile = new NormalTile();
		Tile northTile = new Finish();
		Tile southTile = new EndOfField();
		Tile westTile = new EndOfField();
		Tile eastTile = new EndOfField();
		middleTile.setSide(Direction.North, northTile);
		northTile.setSide(Direction.South, middleTile);
		middleTile.setSide(Direction.South, southTile);
		southTile.setSide(Direction.North, middleTile);
		middleTile.setSide(Direction.West, westTile);
		westTile.setSide(Direction.East, middleTile);
		middleTile.setSide(Direction.East, eastTile);
		eastTile.setSide(Direction.West, middleTile);
		spawnTiles.add(middleTile);
		Logger.logReturn(this, "buildMap()");
	}
	
	public void setFile(String file){
		Logger.logCall(this, "setFile(" + file + ")");
		Logger.logReturn(this, "setFile()");
	}
	
	public Tile getNextSpawn(){
		Logger.logCall(this, "getNextSpawn()");
		Logger.logReturn(this, "spawnTiles.pop()");
		return spawnTiles.get(0);
	}
	
}
