import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;

class level{
  // const definitions
  private final int STARTINGPLAYERHEALTH = 5;  //this represents how much health the player starts with
  private final int STARTINGPLAYERDAMAGE = 1;  //this represents how much damage the player's fists deal at the beginning

  // represents a list of all entities and their matching ID; this goes to the switch statement on how they should be spawned when loading a new level
  enum entityTypes{
    EMPTY,
    PLAYER
  };


  ///// map variables /////
	// assumes the top left is the origin point, <0, 0>
  entity player = new player(STARTINGPLAYERHEALTH, STARTINGPLAYERDAMAGE);

	private ArrayList<ArrayList<entity>> curLevel;
  /////////////////////////


  // constructor
	public level(){
		curLevel = new ArrayList<ArrayList<entity>>();
	}


	// loads a map from a level map file
	// returns true if successfully loads the map, otherwise returns false
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
			curLevel.add(currentIndex, new ArrayList<entity>());

      //go through integers in line
			while(lProcess.hasNextInt()){
        //using modulo in case it reads a value bigger than it is supposed to, rather than crashing it will generate garbage tiles
        //may just be better to let it crash but old habits die hard ig
				entityAdd(entityTypes.values()[lProcess.nextInt()% (entityTypes.values().length)], currentIndex);
			}

			//close scanner for the line
			lProcess.close();
			currentIndex++;
		}



		//close scanner objects
		fIn.close();

		return true;
	}

  // adds a tile and its corrosponding entity
  private void entityAdd(entityTypes id, int currentIndex){
    entity tempEntity = null;
    switch (id) {
      case EMPTY:
        //empty tile, leave entity as null therefore

        break;
      case PLAYER:
        //instead of creating a new instance, set the position of the player here and have a reference to the object!


        break;
      default:
        break;
    }
    curLevel.get(currentIndex).add(tempEntity);
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
	public entity getTile(int pos[]){
		return curLevel.get(pos[1]).get(pos[0]);
	}

	//oldPos - tile to be moved {x, y}
	//newPos - posiion for tile to be moved to {x, y}
	public Boolean moveTile(int oldPos[], int newPos[]){
		entity newTile = null;
    entity oldTile = null;
		newTile = curLevel.get(newPos[1]).get(newPos[0]);

		//check if an entity already exists in this tile
    if(newTile != null){
			return false;
    }
    //we know we can move the target entity, first get its reference
    oldTile = curLevel.get(oldPos[1]).get(oldPos[0]);

    //set the new tile to have the entity's reference
    curLevel.get(newPos[1]).set(newPos[0], oldTile);

    //finally, set the old tile to not have an entity in it
    curLevel.get(oldPos[1]).set(oldPos[0], null);

		return true;
	}

  public void setTile(int pos[], entity val){
    curLevel.get(pos[1]).set(pos[0], val);
  }
}
