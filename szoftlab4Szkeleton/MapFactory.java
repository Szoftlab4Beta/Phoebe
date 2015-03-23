package szoftlab4Szkeleton;

import java.util.ArrayList;
import java.util.List;

import szoftlab4Szkeleton.VectorClass.Direction;

public class MapFactory {
	
	List<Tile> spawnTiles;

	/**
	 * A p�lya fel�p�t�s��rt, �s a Robotok kezd�poz�ci�j��rt felel�s oszt�ly
	 */
	public MapFactory() {
		Logger.logCreate(this, "mapFactory");
		spawnTiles = new ArrayList<Tile>();
	}
	
	/**
	 * Fel�p�ti a p�ly�t, amin a j�t�k zajlik majd.
	 * <p>
	 * L�trehozza a p�ly�t alkot� Tile objektumokat, �s be�ll�tja azok szomsz�doss�gi viszony�t.
	 * Elt�rolja a lehets�ges kezd�poz�ci�kat.
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
	 * Visszaad egy olyan Tile objektumot, ami a Robot kezd�poz�ci�j�ul szolg�lhat.
	 * Ameddig tud, az el�z�ekt�l elt�r� mez�vel t�r vissza
	 * 
	 * @return A kezd� mez�.
	 */
	public Tile getNextSpawn(){
		Logger.logCall(this, "getNextSpawn()");
		Logger.logReturn(this, "spawnTiles.pop()");
		return spawnTiles.get(0);
	}
	
}
