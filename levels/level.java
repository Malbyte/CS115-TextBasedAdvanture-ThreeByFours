import java.util.ArrayList;
import java.io.*;

class level {
	private ArrayList<int> curLevel;
	
	public void level(){
		curLevel = null;
	}
	
	//loads a map from a level map file
	//returns true if successfully loads the map, otherwise returns false
	public Boolean loadMap(String mapName){
		//
	}
	
	//prints the current map to the screen
	public void printMap(){
		//clears the screen and displays a viewport of the level
	}
	
	//pos - position to be returned {x, y}
	public int getTile(int pos[2]){
		return curLevel.get(pos[1]).get(pos[0]);
	}
	
	//oldPos - tile to be moved {x, y}
	//newPos - posiion for tile to be moved to {x, y}
	public Boolean moveTile(int oldPos[2], int newPos[2]){
		int tileVal = 0;
		
		tileVal = curLevel.get(oldPos[1]).get(oldPos[0]);
		if(tileVal != 0)
			return false;
		
		curLevel.get(newPos[1]).set(newPos[0], tileVal);
		return true;
	}
}