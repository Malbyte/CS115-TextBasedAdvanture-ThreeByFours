package entities;

import levels.*;

//this entity is the A-star node, will have to derive from entity so that it can easily, could make another abstract class that is named tile, allowing
//this to be cleaner, but for now since it is like 1 am and I'm just writing code to later refactor and clean, this'll be fine
public class AStarNode extends entity{
  private int gCost = 0;               //this represents the cost of the path from the beginning to this tile
  private int hCost = 0;               //this represents the cost of the direct path from this tile to the end
  private int fCost = 0;               //this represents the total cost of the tile
  private int targetNodePos[] = {-1, -1};  //this represents the target position to find the optimal route to
  private int lastNodePos[] = {-1, -1};    //this represents the cheapest node connected to this; use this if this tile is apart of the chosen path to find the tile beforehand, retracing the program's steps
  private Boolean Locked = false;
  //takes: level object reference, the position of the target, the tile's G cost (G cost is calculated via taking the last tile's G cost plus the direction's cost)
  public AStarNode(level levelHWND, int nodePos[], int posTargetNode[], int aTGC, int lastNodePos[]){
    super(levelHWND, 0, 0);
    this.setPos(nodePos);
    //set target node position
    targetNodePos[0] = posTargetNode[0];
    targetNodePos[1] = posTargetNode[1];

    //set starter last node position
    this.lastNodePos[0] = lastNodePos[0];
    this.lastNodePos[1] = lastNodePos[1];
    gCost = aTGC;

    //calculate this new tile's h cost
    calcH();
    fCost = gCost + hCost;
  }

  //this method calculates the tile's current Hcost from the given targetNode
  private void calcH(){
    int deltaX = 0;
    int deltaY = 0;
    int deltaDiagonal = 0;
    int deltaCardinal = 0;

    //hard code diagonal as 14 and 10 in the cardinal directions
    //on reading and watching about A* algorithm, this is the easiest and cheapest
    //method for calculating the values

    //first find the delta X and Y in terms of tiles from target
    deltaX = Math.abs(this.getPos()[0] - targetNodePos[0]);
    deltaY = Math.abs(this.getPos()[1] - targetNodePos[1]);


    //doing it this convaluted looking way because doing a bunch of sqrts and then truncating
    //could maybe cause weird cases with larger distances to cover.  besides, addition and subtraction is much faster overall
    //(this method actually kind of reminds me how rasterizers draw lines lol, when they calculate their slope they do something kinda similar looking)

    //gets the amount of tiles away from being directly diagonal, then the amount of diagonal tiles away
    if(deltaX > deltaY){
      //get the total amount of diagonal tiles away first
      deltaCardinal = deltaX - deltaY;

      //now get the total amount of cardinal tiles left
      deltaDiagonal = deltaX - deltaCardinal;
    }
    else{
      //get the total amount of cardinal tiles away first
      deltaCardinal = deltaY - deltaX;

      //now get the total amount of diagonal tiles left
      deltaDiagonal = deltaY - deltaCardinal;
    }

    //now calculate the total cost of this tile from the target
    hCost = (deltaDiagonal * 14) + (deltaCardinal * 10);
  }

  //checks if new gCost is less than original, if so update
  public void updateCost(int newGCost, int lastNodePos[]){
    if(gCost > newGCost){
      //new value is cheaper, this is a better route therefore

      //update last node position as this one is better
      this.lastNodePos[0] = lastNodePos[0];
      this.lastNodePos[1] = lastNodePos[1];

      //set new values and calculate new costs
      gCost = newGCost;
      fCost = gCost + hCost;
    }
  }

  //useless functions required by the entity class for this subclass to exist...
  //gotta change the level's array from entity class, to a tile class with entity class being a subclass of tile.
  //this'll let us clean up this class to be more readable.
  @Override
  protected void interact() {
      //impossible to interact with since it will be deleted after a route is calculated
  }
  @Override
  public void update() {
      //will not be seen within update loop
  }
  @Override
  public void drawEntity() {
    System.out.printf("()");
  }

  public int getfCost() {
      return fCost;
  }
  public int getgCost() {
      return gCost;
  }
  public int[] getLastNodePos() {
      return lastNodePos;
  }
  public Boolean getLocked() {
      return Locked;
  }
  public void setLocked(Boolean locked) {
      Locked = locked;
  }
}
