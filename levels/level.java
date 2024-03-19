import java.util.ArrayList;
import java.io.*;

class level {
	private ArrayList<int> curLevel;
	
	public void level(){
		curLevel = null;
	}
	public void loadMap(String mapName){
		
	}
	
	public void printMap(){
		//clears the screen and displays a viewport of the level
	}
	public int getTile(int pos[2]){
		return curLevel.get(y).get(x);
	}
	public Boolean moveTile(int oldPos[2], int newPos[2]){
		int tileVal = 0;
		
		tileVal = curLevel.get(y).get(x);
		if(tileVal != 0)
			return false;
		
		curLevel.get(y).set(x, tileVal);
		return true;
	}
}