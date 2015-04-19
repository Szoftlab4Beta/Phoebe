package szoftlab4Proto;

import java.util.Scanner;

import javax.management.openmbean.InvalidKeyException;

import logger.Tester;

public class App {

	public static void main(String[] args) {
		try{
		Scanner in = new Scanner(System.in);
		Tester tester = new Tester();
		Game game = new Game();
		boolean exit = false;
		System.out.println("Válassz tesztesetet:\n"
				+ "-Új játék kezdése (1)\n-Győztes robot tesztelése (2)\n-Kör léptetése (3)\n-Ragacsfolt/olajfolt hagyása (4)\n"
				+ "-Robotok mozgatása (5)\n-Takarító robotok mozgatása (6)\n-Leesés (7)\n-Folt feltakarítása (8)\n"
				+ "-Olajfoltra lépés (9)\n-Ragacsfoltra lépés (10)\n-Robot->Robot ütközés (11)\n-Robot->takarító robot ütközés (12)\n"
				+ "-Takarító robot->takarító robot ütközés (13)\n-Kilépés (0)");
		String cmdFile = "", expectedOut = "";
		int testCase;
		while(!exit){
			testCase = in.nextInt();
			switch (testCase) {
			case 0:
				exit = true;
				break;
			case 1:
				cmdFile = "newGameTest";
				expectedOut = "EnewGameTest";
				break;
			case 2:
				cmdFile = "winnerTest";
				expectedOut = "EwinnerTest";
				break;
			case 3:
				cmdFile = "nextTurnTest";
				expectedOut = "EnextTurnTest";
				break;
			case 4:
				cmdFile = "placePatchTest";
				expectedOut = "EplacePatchTest";
				break;
			case 5:
				cmdFile = "moveRobotsTest";
				expectedOut = "EmoveRobotsTest";
				break;
			case 6:
				cmdFile = "moveJanitorsTest";
				expectedOut = "EmoveJanitorsTest";
				break;
			case 7:
				cmdFile = "fallTest";
				expectedOut = "EfallTest";
				break;
			case 8:
				cmdFile = "removePatchTest";
				expectedOut = "EremovePatchTest";
				break;
			case 9:
				cmdFile = "robotToOilTest";
				expectedOut = "ErobotToOilTest";
				break;
			case 10:
				cmdFile = "robotToGooTest";
				expectedOut = "ErobotToGooTest";
				break;
			case 11:
				cmdFile = "robotToRobotTest";
				expectedOut = "ErobotToRobotTest";
				break;
			case 12:
				cmdFile = "robotToJanitorTest";
				expectedOut = "ErobotToJanitorTest";
				break;
			case 13:
				cmdFile = "janitorToJanitorTest";
				expectedOut = "EjanitorToJanitorTest";
				break;
				
			default:
				throw new InvalidKeyException("WTF happened?!?");
			}
			if(!exit){
				tester.setInOut(asFilePath("cmds", cmdFile), asFilePath("cmds", expectedOut));
				tester.game = game;
				tester.run();
			}
		}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public static String asFilePath(String directory, String fileName){
		return "./"+ directory + "/" + fileName + ".dat";
	}

}
