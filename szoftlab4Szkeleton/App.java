package szoftlab4Szkeleton;

import java.util.Scanner;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		while (true) {
			Logger.disable();
			Tile spawnTile = new NormalTile();
			Robot robot = new Robot(spawnTile,1,0);
			Logger.enable();
			Logger.logMSG("Válasszon lehetőséget:\n");
			Logger.logMSG("1 - Új játék indítása\n");
			Logger.logMSG("2 - Sebesség beállítása\n");
			Logger.logMSG("3 - Folt hagyása\n");
			Logger.logMSG("4 - Ugrás\n");
			Logger.logMSG("5 - Játék vége\n");
			Logger.logMSG("6 - Robotok létrehozása, elhelyezése\n");
			Scanner in = new Scanner(System.in);
			switch (in.next()) {
			case "1" :
				Game game1 = new Game();
				break;
			case "2" :
				Logger.logMSG("Merre menjünk? (N - North, E - East, S - South, W - West) ");
				switch (in.next().toLowerCase()) {
				case "n" :
					robot.modifySpeed(VectorClass.Direction.North);
					break;
				case "e" :
					robot.modifySpeed(VectorClass.Direction.East);
					break;
				case "s" :
					robot.modifySpeed(VectorClass.Direction.South);
					break;
				case "w" :
					robot.modifySpeed(VectorClass.Direction.West);
					break;
				default :
					Logger.logMSG("Rossz bemenet!\n");
					break;
				}
				break;
			case "3" :
				Logger.logMSG("Milyen foltot hagyna? (O - Olaj, R - Ragacs, S - Semmilyen) ");
				switch (in.next().toLowerCase()) {
				case "o" :
					robot.placePatch(Patch.PatchType.Oil);
					break;
				case "r" :
					robot.placePatch(Patch.PatchType.Goo);
					break;
				case "s" :
					robot.placePatch(Patch.PatchType.None);
					break;
				default :
					Logger.logMSG("Rossz bemenet!");
					break;
				}
				break;
			case "4" :
				robot.jump();
				break;
			case "5" :
				//TODO: testWinCondition()-t nem tudjuk meghívni :(
				break;
			case "6" :
				Logger.logMSG("Legyen érvényes kezdőmező? (I - igen, N - nem");
				switch (in.next().toLowerCase()) {
				case "i" :
					Robot robot1 = new Robot(spawnTile,1,0);
					break;
				default :
					Robot robot2 = new Robot(null,1,0);
					break;
				}
				break;
			default :
				Logger.logMSG("Rossz bemenet!");
			}
		}
	}
}