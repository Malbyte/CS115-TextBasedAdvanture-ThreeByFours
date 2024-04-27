package levels;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import entities.*;

public class level{
  // const definitions
  private final int STARTINGPLAYERHEALTH = 5;  //this represents how much health the player starts with
  private final int STARTINGPLAYERDAMAGE = 1;  //this represents how much damage the player's fists deal at the beginning

  // represents a list of all entities and their matching ID; this goes to the switch statement on how they should be spawned when loading a new level
  enum entityTypes{
    EMPTY,
    PLAYER,
    WALL,
    DOOR,
    CHEST,
    SQUELICKLY,  //really weak skeleton enemy, just a test enemy entity
    ANVIL
  };


  ///// map variables /////
	// assumes the top left is the origin point, <0, 0>
  private player player = new player(this, STARTINGPLAYERHEALTH, STARTINGPLAYERDAMAGE);

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
		fp = new File(String.join("", "levels/", mapName));


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
				entityAdd(entityTypes.values()[lProcess.nextInt() % entityTypes.values().length], currentIndex);
			}


      //check if has arguments after array
      int i = 0;
      while(lProcess.hasNext()){
        //yeah I'm instantiating the same variable like 100 times, sue me
        String subLine = lProcess.next();
        subLine = subLine.replace("\"", "");
        subLine = subLine.replace(" ", "");

        //find entity that is recieving argument
        for(; i < curLevel.get(currentIndex).size(); i++){

          if(curLevel.get(currentIndex).get(i) != null  && curLevel.get(currentIndex).get(i).takesArgument()){
            //found corrosponding entity, give argument and continue variable assignments
            curLevel.get(currentIndex).get(i++).assignArgument(subLine);

            break;
          }
        }
        if(i == curLevel.get(currentIndex).size()){
          //no entity corrosponding, throw error!

          System.out.printf("ERROR: Extraneous variable %s!\n", subLine);

          //extra variable, may be configured wrong
          System.exit(-1);
        }
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
        curLevel.get(currentIndex).add(tempEntity);

        return;
      case PLAYER:
        //instead of creating a new instance, set the position of the player here and have a reference to the object!
        tempEntity = player;

        break;
        case WALL:
        tempEntity = new wall(this);

        break;
        case DOOR:
        tempEntity = new door(this);

        break;
        case CHEST:
          tempEntity = new chest(this);
        break;
        case SQUELICKLY:
          tempEntity = new enemy(this, 1, 0);
        break;
        case ANVIL:
		  tempEntity = new anvil(this);
        break;
      default:
        //unkown entity, draw error entity
        break;
    }
    tempEntity.setPos(new int[] {curLevel.get(currentIndex).size(), currentIndex});
    curLevel.get(currentIndex).add(tempEntity);
  }
	//prints the current map to the screen
	public void printMap(){
		//clears the screen and displays a viewport of the level with basic player info
    entity curEntity = null;

    System.out.print("\033[H\033[2J");
    System.out.println("Goal: Escape through 3 rooms");
    System.out.println("Health:\t" + player.getHealth() + "\nItems:\t" + player.getInventory().size());

    for(int y = 0; y < curLevel.size(); y++){
      for(int x = 0; x < curLevel.get(y).size(); x++){
        curEntity = curLevel.get(y).get(x);
        if(curEntity != null){
          curEntity.drawEntity();
        }
        else{
          //draw empty area
          System.out.printf("``");
        }
      }
      System.out.println();
    }
	}

	//pos - position to be returned {x, y}
	public entity getTile(int pos[]){
		return curLevel.get(pos[1]).get(pos[0]);
	}

  //tries to move an entity to a given position
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

  //returns reference to player object
  public player getPlayer() {
      return player;
  }

  //since the maps expected are always going to be the shape of a rectangle, this is acceptable
  public int[] mapSize(){
    return new int[] {curLevel.get(0).size(), curLevel.size()};
  }

  //returns an array of the shortest path from posA to posB.
  //uses the A* algorithm
  public ArrayList<int[]> getPath(int posA[], int posB[]){
    ArrayList<AStarNode> currentNodeList = new ArrayList<AStarNode>();
    ArrayList<int[]> finalPath = new ArrayList<int[]>();
    //first start off before main loop by setting up all tiles surrounding posA
    //(since posA will contain an entity and therefore a node cannot coexist on this tile)
    if(expandTilePath(currentNodeList, posA, posB)){
      //clear all tiles that may exist and return
      for(int i = 0; i < currentNodeList.size(); i++){
        this.setTile(currentNodeList.get(i).getPos(), null);
      }

      return null;
    }
    //have initialized all nodes surrounding starting point. can now run main
    //algorithm loop
    while(true){
      //printmap tool to help debug and ensure pathing looks like how algorithm should behave
      //this.printMap();

      //first start iteration by organizing the nodes to find least value.
      currentNodeList.sort(new AStarNodeComparator());
      int cheapestIndex = 0;
      //find cheapest node that is not locked
      for(; cheapestIndex < currentNodeList.size(); cheapestIndex++){
        if(currentNodeList.get(cheapestIndex).getLocked() == false){
          //lock it so it won't be selected again
          currentNodeList.get(cheapestIndex).setLocked(true);

          break;
        }
      }


      //no more tiles exist, no possible pathing!
      if(cheapestIndex == currentNodeList.size()){
        //clear all tiles that may exist and return
      for(int i = 0; i < currentNodeList.size(); i++){
        this.setTile(currentNodeList.get(i).getPos(), null);
      }

        return null;
      }


      //afterwords, take the smallest node and expand off of it, check if it is adjacent
      if(expandTilePath(currentNodeList, currentNodeList.get(cheapestIndex).getPos(), posB)){
        //take current node, trace all previous cheapest nodes and put all into final returned array
        AStarNode tempNode = currentNodeList.get(cheapestIndex);

        while((tempNode.getLastNodePos()[0] != posA[0]) || (tempNode.getLastNodePos()[1] != posA[1])){
          finalPath.add(tempNode.getPos());

          //update the tempNode
          tempNode = (AStarNode) getTile(tempNode.getLastNodePos());
        }
        //add final tile
        finalPath.add(tempNode.getPos());

        break;
      }
      //if not reached goal, expand the next cheapest node
    }
  //clear all tiles that may exist and return
  for(int i = 0; i < currentNodeList.size(); i++){
    this.setTile(currentNodeList.get(i).getPos(), null);
  }

    return finalPath;
  }

  //returns true if one of the tiles is the target node
  private Boolean expandTilePath(ArrayList<AStarNode> currentNodeList, int posIntermediary[], int posTarget[]){
    for(int y = -1; y <= 1; y++){
      for(int x = -1; x <= 1; x++){

        //go over all 9 tiles in area, if it is null, put a tile and it's initial value in
        //first check if the current tile exists
        if((posIntermediary[0] + x >= 0 && posIntermediary[0] + x < this.curLevel.get(posIntermediary[1]+y).size()) && (posIntermediary[1] + y >= 0 && posIntermediary[1] + y < this.curLevel.size())){
          //tile exists!

          //now check if tile is not occupied
          if(getTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y}) == null){
            //can create a node here
            AStarNode tempNode = new AStarNode(this, new int[]{posIntermediary[0] + x, posIntermediary[1] + y}, posTarget, ((x+y == 1) ? 10 : 14), posIntermediary);

            setTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y}, tempNode);
            currentNodeList.add(tempNode);
          }
          else{
            //maybe node already exists, if it does, see if gCost can be decreased
            if(getTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y}) instanceof AStarNode){
              //is a node, try updating
              AStarNode currentNode = (AStarNode) getTile(posIntermediary);
              AStarNode updateNode = (AStarNode) getTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y});
              updateNode.updateCost(currentNode.getfCost() + ((x+y == 1) ? 10 : 14), currentNode.getPos());
            }
            if(getTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y}).getPos()[0] == posTarget[0] && getTile(new int[] {posIntermediary[0] + x, posIntermediary[1] + y}).getPos()[1] == posTarget[1]){
              //this is the target tile, return true
              //since tiles cannot overlap, will not update list to include target tile, only tile next to it

              return true;
            }
          }
        }
      }
    }

    return false;
  }
}
