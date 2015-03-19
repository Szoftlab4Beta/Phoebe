package szoftlab4Szkeleton;

import szoftlab4Szkeleton.Patch.PatchType;
import szoftlab4Szkeleton.VectorClass.Direction;

public class Robot implements IColliding {

	int gooAmount, oilAmount;
	boolean dead, canChangeSpeed, atFinish;
	Tile position;
	VectorClass speed, distance;
	
	public Robot(Tile spawnTile, int startGoo, int startOil) {
	}
	
	public void jump(){
		
	}
	
	public void modifySpeed(Direction d){
		
	}
	
	public void placePatch(PatchType type){
		
	}
	
	public int getDistance(){
		return 0;
	}
	
	public boolean isDead(){
		return false;
	}
	
	public boolean isAtFinish(){
		return false;
	}

	@Override
	public void collide(Oil t) {
	}

	@Override
	public void collide(EndOfField t) {
	}

	@Override
	public void collide(NormalTile t) {
	}

	@Override
	public void collide(Goo t) {		
	}

	@Override
	public void collide(Finish t) {		
	}
	

}
