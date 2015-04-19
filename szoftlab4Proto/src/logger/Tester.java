package logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import szoftlab4Proto.*;
import szoftlab4Proto.Patch.PatchType;
import szoftlab4Proto.VectorClass.Direction;

public class Tester implements IColliding {

	String testedTile, currentLine;
	File cmd, expected;
	public Game game;
	BufferedReader cmdReader, expReader;
	Tile[][] tiles;
	int height, width;
	
	public void setInOut(String cmd, String expected){
		this.cmd = new File(cmd);
		this.expected = new File(expected);
	}
		
	public void run() throws IOException{
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
				currentLine = expReader.readLine();
				if(cmdOut != null && !cmdOut.equals(currentLine)){
					System.out.println("Mismatch in line " + lineCount + " command output = " + cmdOut + " expected = " + currentLine);
				}
				currentLine = cmdReader.readLine();
				lineCount++;
		}
		System.out.println("CONGRATS the expected output perfectly matches the output");
	}
	
	String doNextCommand() {
		String[] cmdValues;
		cmdValues = currentLine.split("[(,)]");
		if(cmdValues[0].equals("newGame")){
			game.newGame(Integer.parseInt(cmdValues[1]), Integer.parseInt(cmdValues[2]), cmdValues[3]);//TODO: Nincs playerCount paraméter
		} else if(cmdValues.equals("setTurn")){
			setTurn(cmdValues[1], cmdValues[2]);
		} else if(cmdValues.equals("nextTurn")){
			game.nextTurn();
		} else if(cmdValues.equals("getWinner")){
			return "" + game.testWinConditions();
		} else if(cmdValues.equals("getMapTile")){
			return getTileName(tiles[Integer.parseInt(cmdValues[1])][Integer.parseInt(cmdValues[2])]);
		} else if(cmdValues.equals("listRobot")){
			return listRobot();
		} else if(cmdValues.equals("spawnJanitor")){
			//TODO: ????
		} else if(cmdValues.equals("listJanitor")){
			//listJanitor(); TODO ?
		}
		return null;
	}
	
	void setTurn(String dir, String patch){
		Direction d = Direction.valueOf(dir);
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

//	void listJanitor(){
//		StringBuilder builder = new StringBuilder();
//		for(int i = 0; i < game.getJanitorCount(); i++){
//			
//		}
//	}
	
	String getTileName(Tile tile){
		tile.accept(this);
		return testedTile;
	}
	
	String getTilePos(Tile tile){
		for(int i = 0; i < height - 1; i++){
			for(int j = 0; j < width - 1; j++){
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
	
	
	
}
