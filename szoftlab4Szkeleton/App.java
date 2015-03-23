package szoftlab4Szkeleton;

import java.util.Scanner;

public class App {

	public App() {
		
	}
	
	public static void main(String[] args) {
		while (true) {
			Logger.disable();
			Tile spawnTile = new NormalTile();
			Robot robot = new Robot(spawnTile,1,0);
			Logger.enable();
			Logger.logMSG("V�lasszon lehet�s�get:\n");
			Logger.logMSG("1 - �j j�t�k ind�t�sa\n");
			Logger.logMSG("2 - Sebess�g be�ll�t�sa\n");
			Logger.logMSG("3 - Folt hagy�sa\n");
			Logger.logMSG("4 - Ugr�s\n");
			Logger.logMSG("5 - J�t�k v�ge\n");
			Logger.logMSG("6 - Robotok l�trehoz�sa, elhelyez�se\n");
			Scanner in = new Scanner(System.in);
			switch (in.next().toLowerCase().charAt(0)) {
			case '1' :
				Game game1 = new Game();
				break;
			case '2' :
				Logger.logMSG("Merre menj�nk? (N - North, E - East, S - South, W - West, V - V�ltozatlan sebess�g) ");
				switch (in.next().toLowerCase().charAt(0)) {
				case 'n' :
					robot.modifySpeed(VectorClass.Direction.North);
					break;
				case 'e' :
					robot.modifySpeed(VectorClass.Direction.East);
					break;
				case 's' :
					robot.modifySpeed(VectorClass.Direction.South);
					break;
				case 'w' :
					robot.modifySpeed(VectorClass.Direction.West);
					break;
				case 'v' :
					robot.modifySpeed(VectorClass.Direction.None);
					break;
				default :
					Logger.logMSG("Rossz bemenet!\n");
					break;
				}
				break;
			case '3' :
				Logger.logMSG("Milyen foltot hagyna? (O - Olaj, R - Ragacs, S - Semmilyen) ");
				switch (in.next().toLowerCase().charAt(0)) {
				case 'o' :
					robot.placePatch(Patch.PatchType.Oil);
					break;
				case 'r' :
					robot.placePatch(Patch.PatchType.Goo);
					break;
				case 's' :
					robot.placePatch(Patch.PatchType.None);
					break;
				default :
					Logger.logMSG("Rossz bemenet!");
					break;
				}
				break;
			case '4' :
				robot.jump();
				break;
			case '5' :
				//TODO: testWinCondition()-t nem tudjuk megh�vni :(
				break;
			case '6' :
				Logger.logMSG("Legyen �rv�nyes kezd�mez�? (I - igen, N - nem) ");
				switch (in.next().toLowerCase().charAt(0)) {
				case 'i' :
					Robot robot1 = new Robot(spawnTile,1,0);
					break;
				case 'n' :
					Robot robot2 = new Robot(null,1,0);
				default :
					Logger.logMSG("Rossz bemenet!");
					break;
				}
				break;
			default :
				Logger.logMSG("Rossz bemenet!");
			}
		}
	}
}