package szoftlab4Szkeleton;

import java.util.ArrayList;
import java.util.List;

import szoftlab4Szkeleton.VectorClass.Direction;

public class MapFactory {
	
	List<Tile> spawnTiles;

	/**
	 * A pálya felépítéséért, és a Robotok kezdőpozíciójáért felelős osztály
	 */
	public MapFactory() {
		Logger.logCreate(this, "mapFactory");
		spawnTiles = new ArrayList<Tile>();
	}
	
	/**
	 * Felépíti a pályát, amin a játék zajlik majd.
	 * <p>
	 * Létrehozza a pályát alkotó Tile objektumokat, és beállítja azok szomszédossági viszonyát.
	 * Eltárolja a lehetséges kezdőpozíciókat.
	 */
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
	
	/**
	 * Visszaad egy olyan Tile objektumot, ami a Robot kezdőpozíciójául szolgálhat.
	 * Ameddig tud, az előzőektől eltérő mezővel tér vissza
	 * 
	 * @return A kezdő mező.
	 */
	public Tile getNextSpawn(){
		Logger.logCall(this, "getNextSpawn()");
		Logger.logReturn(this, "spawnTiles.pop()");
		return spawnTiles.get(0);
	}
	
}
