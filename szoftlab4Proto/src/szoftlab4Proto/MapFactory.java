package szoftlab4Proto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import szoftlab4Proto.VectorClass.Direction;

public class MapFactory {

    File mapFile;
    List<Tile> spawnTiles;
    int currentSpawnTile = -1;

    public MapFactory(){ // Érték nélküli konstruktor
    	spawnTiles = new ArrayList<Tile>();
    }

    public MapFactory(String fileName){ // Értékátadós konstruktor
        mapFile = new File(App.asFilePath("MapFiles", fileName));
        spawnTiles = new ArrayList<Tile>();
    }

    public Tile[][] buildMap() throws IOException {
        int horizontal;
        int vertical;
        char[][] map;
        Tile[][] tileSet;

        BufferedReader textReader = new BufferedReader(new FileReader(mapFile));
        String line = textReader.readLine();

        horizontal = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(">"))); // A pálya szélességének kiolvasása

        vertical = Integer.parseInt(line.substring(line.indexOf("<", line.indexOf(">")) + 1, line.indexOf(">", (line.indexOf("<", line.indexOf(">")))))); // A pálya magasságának kiolvasása

        map = new char[vertical][horizontal];

        line = textReader.readLine();

        for (int i = 0; line != null; i++) { // A pálya beolvasása egy 2d char tömbbe
            for (int j = 0; j < horizontal; j++)
                map[i][j] = line.charAt(1 + (3 * j));
            line = textReader.readLine();
        }

        textReader.close();
        tileSet = new Tile[vertical][horizontal];

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                switch (map[i][j]) {
                    case 'N':
                        tileSet[i][j] = new NormalTile();
                        break;
                    case 'F':
                        tileSet[i][j] = new Finish();
                        break;
                    case 'S':
                        tileSet[i][j] = new NormalTile();
                        spawnTiles.add(tileSet[i][j]);
                        break;
                    case 'E':
                        tileSet[i][j] = new EndOfField();
                        break;
                    case 'O':
                        NormalTile no = new NormalTile();
                        tileSet[i][j] = no;
                        Oil o = new Oil(no);
                        no.setPatch(o);
                        break;
                    case 'G':
                        NormalTile ng = new NormalTile();
                        tileSet[i][j] = ng;
                        Goo g = new Goo(ng);
                        ng.setPatch(g);
                        break;
                }
            }
        }

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (i > 0) {
                    tileSet[i][j].setSide(Direction.North, tileSet[i - 1][j]);
                    tileSet[i - 1][j].setSide(Direction.South, tileSet[i][j]);
                }
                if (j < horizontal - 2) {
                    tileSet[i][j].setSide(Direction.East, tileSet[i][j + 1]);
                    tileSet[i][j + 1].setSide(Direction.West, tileSet[i][j]);
                }
                if (i < vertical - 2) {
                    tileSet[i][j].setSide(Direction.South, tileSet[i + 1][j]);
                    tileSet[i + 1][j].setSide(Direction.North, tileSet[i][j]);
                }
                if (j > 0) {
                    tileSet[i][j].setSide(Direction.West, tileSet[i][j - 1]);
                    tileSet[i][j - 1].setSide(Direction.East, tileSet[i][j]);
                }
            }
        }
        return tileSet;
    }

    public void setFile(String fileName){
        mapFile = new File(App.asFilePath("MapFiles", fileName));
    }

    public Tile getNextSpawn(){
        currentSpawnTile = (currentSpawnTile + 1) % spawnTiles.size();
        return spawnTiles.get(currentSpawnTile);
    }

}
