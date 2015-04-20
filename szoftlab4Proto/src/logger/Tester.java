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

public class Tester implements IColliding {

	String testedTile, currentLine;
	File cmd, expected;
	public Game game;
	BufferedReader cmdReader, expReader;
	List<JanitorRobot> janitors = new ArrayList<JanitorRobot>();
	Tile[][] tiles;
	int height, width;
	
	public void setInOut(String cmd, String expected){
		this.cmd = new File(cmd);
		this.expected = new File(expected);
	}
		
	public void run() throws IOException{
		StringBuilder builder = new StringBuilder();
		String cmdOut;
		int lineCount = 0;
		if(game == null){
			throw new NullPointerException("Logger.game has to be assigned");
		}
		cmdReader = new BufferedReader(new FileReader(cmd));
		expReader = new BufferedReader(new FileReader(expected));
		currentLine = cmdReader.readLine();
		while(currentLine != null){
				cmdOut = doNextCommand();
				if(cmdOut != null){
					builder.append(cmdOut);
					lineCount++;
				}
				currentLine = cmdReader.readLine();
				
		}
		BufferedReader cmdOutReader = new BufferedReader(new StringReader(builder.toString()));
		for(int i = 0; i < lineCount; i++){
			currentLine = expReader.readLine();
			cmdOut = cmdOutReader.readLine();
			if(currentLine == null || !currentLine.equals(cmdOut)){
				if(currentLine != cmdOut){
					System.out.println("Mismatch in line " + i + "-> command output = " + cmdOut + " expected = " + currentLine);
					return;
				}
			}
		}
		System.out.println("CONGRATS the expected output perfectly matches the output");
	}
	
	String doNextCommand() throws NumberFormatException, IOException {
		String[] cmdValues;
		cmdValues = currentLine.split("[(,)]");
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
	
	void newGame(int playerNum, int turns, String mapFile) throws IOException{
		tiles = game.newGame(playerNum, turns, mapFile);
		BufferedReader textReader = new BufferedReader(new FileReader(App.asFilePath("MapFiles", mapFile)));
        String line = textReader.readLine();
        height = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(">")));
        width = Integer.parseInt(line.substring(line.indexOf("<", line.indexOf(">")) + 1, line.indexOf(">", (line.indexOf("<", line.indexOf(">"))))));
        textReader.close();        
	}
	
	void setTurn(String dir, String patch){
		Direction d = Direction.valueOf(parseDirectionCmd(dir));
		PatchType p = PatchType.None;
		if(patch.equals("O"))
			p = PatchType.Oil;
		else if(patch.equals("G"))
			p = PatchType.Goo;
		
		game.setTurn(d, p);			
	}
	
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

	String listJanitor(){
		StringBuilder builder = new StringBuilder();
		int maxIndex = janitors.size();
		for(int i = 0; i < maxIndex;){
			JanitorRobot r = janitors.get(i);
			if(r.isDead())
				janitors.remove(i);
			else 
			{
				NormalTile d = (NormalTile)r.getDestination();
				builder.append("<" + i + ">" + getTilePos(r.getPosition()) + "<" + d.getPatch().getDurability() + ">" + getTilePos(d));
			}
		}
		return builder.toString();
	}
	
	String getTileName(Tile tile){
		tile.accept(this);
		return testedTile + "\n";
	}
	
	String getTilePos(Tile tile){
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
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
