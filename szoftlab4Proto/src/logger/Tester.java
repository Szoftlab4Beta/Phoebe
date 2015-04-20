package logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import szoftlab4Proto.*;
import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

/**
 * Parancsok értelmezésére, és végrehajtására alkalmas osztály
 * A parancsokat fileból fogadja, és a végrehajtás után fájlból beolvasott várt eredménnyel veti össze
 */
public class Tester implements IColliding {

	
	String testedTile, currentLine;
	File cmd, expected;
	public Game game;
	BufferedReader cmdReader, expReader;
	List<JanitorRobot> janitors;
	Tile[][] tiles;
	int height, width;
	
	/**
	 * Beállítja a parancsfile-t és a várt eredményt tartalmazó file-t
	 * @param cmd A parancsfile relatív elérési útvonala
	 * @param expected A várt eredményt tartalmazó file relatív elérési útvonala
	 */
	public void setInOut(String cmd, String expected){
		this.cmd = new File(cmd);
		this.expected = new File(expected);
	}
		
	/**
	 * Sorrendben végrehajtja a parnacsfájlban lévő parancsokat
	 * Ha a parancsok kimenete nem egyezik az elvárt eredménnyel erről parancssoron értesíti a felhasználót
	 * @throws IOException
	 */
	public void run() throws IOException{
		StringBuilder builder = new StringBuilder();
		String cmdOut;
		int lineCount = 0;
		if(game == null){
			throw new NullPointerException("Logger.game has to be assigned");
		}
		janitors = new ArrayList<JanitorRobot>();					//Initialize test specific fields
		cmdReader = new BufferedReader(new FileReader(cmd));
		expReader = new BufferedReader(new FileReader(expected));
		currentLine = cmdReader.readLine();
		while(currentLine != null){									
				cmdOut = doNextCommand();							//Execute command
				if(cmdOut != null){
					builder.append(cmdOut);							//Add the output to the builder
					lineCount++;
				}
				currentLine = cmdReader.readLine();					//Read next command
				
		}
		BufferedReader cmdOutReader = new BufferedReader(new StringReader(builder.toString()));
		for(int i = 0; i < lineCount; i++){							//Evaluation
			currentLine = expReader.readLine();						//Read a line from the expected output
			cmdOut = cmdOutReader.readLine();						//and the program output
			if(currentLine == null || !currentLine.equals(cmdOut)){	//If the expected has no more lines or the two lines doesn't match
				if(currentLine != cmdOut){							//Except when they are both null
					System.out.println("Mismatch in line " + i + "-> command output = " + cmdOut + " expected = " + currentLine);
					return;											//Terminate the method and show mismatch message
				}
			}
		}
		System.out.println("CONGRATS the expected output perfectly matches the output");
	}
	
	/**
	 * Végrehajt egy sornyi parancsot, és a kimenetével tér vissza
	 * @return Ha a parancs lekérdezési parancs volt, a lekérdezés eredménye, egyébként null
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	String doNextCommand() throws NumberFormatException, IOException {
		String[] cmdValues;
		cmdValues = currentLine.split("[(,)]");					//Split the command line into command, and parameters
		if(cmdValues[0].equals("newGame")){
			newGame(Integer.parseInt(cmdValues[1]), Integer.parseInt(cmdValues[2]), cmdValues[3]);
		} else if(cmdValues[0].equals("setTurn")){
			setTurn(cmdValues[1], cmdValues[2]);
		} else if(cmdValues[0].equals("nextTurn")){
			game.nextTurn();
		} else if(cmdValues[0].equals("getWinner")){
			return game.testWinConditions() + "\n";
		} else if(cmdValues[0].equals("getMapTile")){
			return getTileName(tiles[Integer.parseInt(cmdValues[2])][Integer.parseInt(cmdValues[1])]);
		} else if(cmdValues[0].equals("listRobot")){
			return listRobot();
		} else if(cmdValues[0].equals("spawnJanitor")){
			janitors.add(game.spawnJanitor(tiles[Integer.parseInt(cmdValues[2])][Integer.parseInt(cmdValues[1])]));
		} else if(cmdValues[0].equals("listJanitor")){
			return listJanitor();
		}
		return null;
	}
	
	/**
	 * Végrehajtja a newGame parancsot
	 * @param playerNum	A játékosok száma
	 * @param turns	A játék végéig lejátszandó körök száma
	 * @param mapFile A pályafile neve
	 * @throws IOException
	 */
	void newGame(int playerNum, int turns, String mapFile) throws IOException{
		tiles = game.newGame(playerNum, turns, mapFile);					//Gets the tiles from the mapFactory
		BufferedReader textReader = new BufferedReader(new FileReader(App.asFilePath("MapFiles", mapFile)));
        String line = textReader.readLine();								//Reads the size of the map from the file
        height = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(">")));
        width = Integer.parseInt(line.substring(line.indexOf("<", line.indexOf(">")) + 1, line.indexOf(">", (line.indexOf("<", line.indexOf(">"))))));
        textReader.close();        
	}
	
	/**
	 * Végrehajtja a setTurn parancsot
	 * @param dir A robot sebességéhez adandó irány
	 * @param patch	A robot által lerakandó folt típusa (G/O), bármilyen más bemenetre nem rak le foltot
	 */
	void setTurn(String dir, String patch){
		Direction d = Direction.valueOf(parseDirectionCmd(dir));
		PatchType p = PatchType.None;
		if(patch.equals("O"))
			p = PatchType.Oil;
		else if(patch.equals("G"))
			p = PatchType.Goo;
		
		game.setTurn(d, p);			
	}
	
	/**
	 * Kilistázza az összes játékban meradt robot tulajdonságát <Index><Pozíció><Ragacskészlet><Olajkészlet><Megtett távolság><Sebesség>
	 * @return A robotok adatai, egy robot soronként
	 */
	String listRobot(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < game.getPlayerNum(); i++){
			Robot r = game.getRobot(i);
			builder.append("<" + i + ">");
			builder.append(getTilePos(r.getPosition()));
			builder.append(r.getLogString());
			builder.append("\n");
		}
		return builder.toString();
	}

	/**
	 * Kilistázza az összes játékban meradt takarító robot tulajdonságát <Index><Pozíció><A célfolt durability-je><A célmező pozíciója>
	 * @return	A takarítórobotok adatai, egy robot soronként
	 */
	String listJanitor(){
		StringBuilder builder = new StringBuilder();
		int maxIndex = janitors.size();
		for(int i = 0; i < maxIndex;){				//for every janitor
			JanitorRobot r = janitors.get(i);
			if(r.isDead())							//if Dead
			{
				janitors.remove(i);					//Remove from the list
			}
			else 
			{										//create output line
				NormalTile d = (NormalTile)r.getDestination();
				builder.append("<" + i + ">" + getTilePos(r.getPosition()) + "<" + 
						(d.getPatch() == null ? 0 : (int)d.getPatch().getDurability()) + ">" + getTilePos(d) + "\n");
				i++;
			}
			maxIndex = janitors.size();
		}
		return builder.toString();
	}
	
	/**
	 * Visszaadja a megadott mező típusát (N/E/F/O/G)
	 * @param tile
	 * @return A mező típusa (Folttal rendelkező NormalTile esetén a folt típusa)
	 */
	String getTileName(Tile tile){
		tile.accept(this);
		return testedTile + "\n";
	}
	
	/**
	 * Visszaadja a megadott mező pozícióját a pályán
	 * @param tile
	 * @return A mező pozíciója <függőleges,vízszintes>
	 */
	String getTilePos(Tile tile){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(tile.equals(tiles[i][j]))
					return "<" + j + "," + i + ">";
			}
		}
		return null;
	}
	
	@Override
	public void collide(Oil p) {
		testedTile = "<O>";
	}

	@Override
	public void collide(Goo p) {
		testedTile = "<G>";
	}

	@Override
	public void collide(NormalTile t) {
		testedTile = "<N>";
		if(t.getPatch() != null)
			t.getPatch().accept(this);
	}

	@Override
	public void collide(Finish t) {
		testedTile = "<F>";
	}

	@Override
	public void collide(EndOfField t) {
		testedTile = "<E>";
	}

	@Override
	public void collide(Robot r) {		
	}

	@Override
	public void collide(JanitorRobot r) {		
	}
	
	public String parseDirectionCmd(String cmd){
		if(cmd.equals("E"))
			return "East";
		if(cmd.equals("W"))
			return "West";
		if(cmd.equals("N"))
			return "North";
		if(cmd.equals("S"))
			return "South";
		if(cmd.equals("0"))
			return "None";
		return null;
	}
	
}
