import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class level{
	//assumes the top left is the origin point, <0, 0>
	private ArrayList<ArrayList<Integer>> curLevel;

	public level(){
		curLevel = new ArrayList<ArrayList<Integer>>();
	}

	//loads a map from a level map file
	//returns true if successfully loads the map, otherwise returns false
	public Boolean loadMap(String mapName){
		//declarations of variables
		String line;
		File fp = null;
		Scanner fIn = null;
		Scanner lProcess = null;
		int currentIndex = 0;

    //make sure there is no old map data currently in the array
    curLevel.clear();

		//try to open given file
		fp = new File(mapName);


		//if file does not exist, return false
		if(!fp.exists()){

			return false;
		}


		//try to open a scanner to read map file
		try{
		fIn = new Scanner(fp);
		}
		catch (IOException e){
			//failed to create scanner

			return false;
		}



		//read data into arrayList
		while(fIn.hasNext()){
			line = fIn.nextLine();

			//if has hashtag, treat line as a comment
			if(line.contains("#")){

				continue;
			}

			//replace "," with " " to ensure there is a space between integers
			line = line.replaceAll(",", " ");

      //initialize temporary scanner to retrieve integers from line while ignoring anything else
			lProcess = new Scanner(line);

			//if not a comment line, then is data line
			curLevel.add(currentIndex, new ArrayList<Integer>());

      //go through integers in line
			while(lProcess.hasNextInt()){
				curLevel.get(currentIndex).add(lProcess.nextInt());
			}

			//close scanner for the line
			lProcess.close();
			currentIndex++;
		}



		//close scanner objects
		fIn.close();

		return true;
	}

	//prints the current map to the screen
	public void printMap(){
		//clears the screen and displays a viewport of the level
    for(int y = 0; y < curLevel.size(); y++){
      for(int x = 0; x < curLevel.size(); x++){
        System.out.printf("%d ", curLevel.get(y).get(x));
      }
      System.out.println();
    }
	}

	//pos - position to be returned {x, y}
	public int getTile(int pos[]){
		return curLevel.get(pos[1]).get(pos[0]);
	}

	//oldPos - tile to be moved {x, y}
	//newPos - posiion for tile to be moved to {x, y}
	public Boolean moveTile(int oldPos[], int newPos[]){
		int newTile = 0;
    int oldTile = 0;
		newTile = curLevel.get(newPos[1]).get(newPos[0]);
		if(newTile != 0){
			return false;
    }
    oldTile = curLevel.get(oldPos[1]).get(oldPos[0]);
		curLevel.get(newPos[1]).set(newPos[0], oldTile);
    curLevel.get(oldPos[1]).set(oldPos[0], 0);
		return true;
	}

  public void setTile(int pos[], int val){
    curLevel.get(pos[1]).set(pos[0], val);
  }
}
