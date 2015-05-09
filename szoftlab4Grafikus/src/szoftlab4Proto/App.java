package szoftlab4Proto;

public class App{
	
	public static void main(String[] args){
		try{
			
			@SuppressWarnings("unused")
			Game game = new Game();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String asFilePath(String directory, String fileName, String format){
		return "./"+ directory + "/" + fileName + "." + format;
	}
}