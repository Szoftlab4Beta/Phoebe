
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFactory {

    private int remaingingSpawns; // Hány nem használt induló mezőnk van még
    String fileName;
    List<Tile> spawnTiles;

    public MapFactory(){ // Érték nélküli konstruktor
        spawnTiles = new ArrayList<Tile>();
    }


    public MapFactory(String s){ // Értékátadós konstruktor
        fileName = s;
        spawnTiles = new ArrayList<Tile>();
    }

    public void buildMap() throws IOException {
        int horizontal;
        int vertical;
        String[] s;
        char[][] map;
        Tile[][] tileSet;

        BufferedReader textReader = new BufferedReader(new FileReader(fileName));
        String line = textReader.readLine();

        horizontal = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(">"))); // A pálya szélességének kiolvasása

        vertical = Integer.parseInt(line.substring(line.indexOf("<", line.indexOf(">")) + 1, line.indexOf(">", (line.indexOf("<", line.indexOf(">")))))); // A pálya magasságának kiolvasása

        line = textReader.readLine();
        remaingingSpawns = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(">"))); // A pályán található indulópontok számának kiolvasása

        map = new char[vertical][horizontal];


        line = textReader.readLine();

        for (int i = 0; line != null; i++) { // A pálya beolvasása egy 2d char tömbbe
            for (int j = 0; j < horizontal; j++)
                map[i][j] = line.charAt(1 + (3 * j));
            line = textReader.readLine();
        }

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
                        tileSet[i][j] = new NormalTile();
                        tileSet[i][j].setPatch(new Oil());
                        break;
                    case 'G':
                        tileSet[i][j] = new NormalTile();
                        tileSet[i][j].setPatch(new Goo());
                        break;
                }
            }
        }

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (i > 0)
                    tileSet[i][j].setSide(Direction.North, tileSet[i - 1][j]);
                if (j < horizontal - 1)
                    tileSet[i][j].setSide(Direction.East, tileSet[i][j + 1]);
                if (i < vertical - 1)
                    tileSet[i][j].setSide(Direction.South, tileSet[i + 1][j]);
                if (j > 0)
                    tileSet[i][j].setSide(Direction.West, tileSet[i][j - 1]);
            }
        }
    }

    public void setFile(String s){
        fileName = s;
    }

    public Tile getNextSpawn(){
        return spawnTiles.remove(spawnTiles.size());
    }

}