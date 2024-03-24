import java.util.Scanner;

// These classes are temporary to allow me to set up the level class (which will become the general map object for the game)
// note: none of these are at all final designs of the classes; this is merely to have a gist of what it may look like

abstract class entity{
  private level levelHWND;
  private double health;
  private double damage;
  private int pos[];

  ///// constructors /////
  public entity(level levelHWND, double health, double damage, int pos[]){
    this.levelHWND = levelHWND;
    this.health = health;
    this.damage = damage;
    this.pos = null;
    if(pos != null){
      this.pos[0] = pos[0];
      this.pos[1] = pos[1];
    }
  }

  public entity(level levelHWND, double health, double damage){
    this.levelHWND = levelHWND;
    this.health = health;
    this.damage = damage;
    //entity will need position to be specified later with a set method
    this.pos = null;
  }
  ////////////////////////

  // called every turn (after player makes their move)
  abstract public void update();

  // method is called when attacked, healed, etc.
  protected void changeHealth(double health){
    this.health += health;
    if(health <= 0){
      this.die();
    }
  }

  // method is called when player tries to interact with
  abstract protected void interact();

  //method is called when entity is dead
  protected void die(){
    //generic die just does nothing

  }

  // called whenever assigning a value via map argument
  public void assignArgument(String arg){

  }

  // method tries to move the entity by an offset (unofficial) vec2 variable
  // note: to pass a vec2 (array size 2) literal, pass new int[] {x, y}
  protected void moveEntity(int pos[]){
    pos = new int[] {this.pos[0] - pos[0], this.pos[1] - pos[1]};
    if(levelHWND.moveTile(this.pos, pos)){
      this.setPos(pos);
    }
  }

  // prints out a 2x1 character representing the entity
  // this is called for every entity in order from the level class to print out the map
  abstract public void drawEntity();

  ///// setter methods /////
  public void setHealth(double health) {
      this.health = health;
  }
  public void setDamage(double damage) {
      this.damage = damage;
  }
  public void setPos(int[] pos) {
      this.pos = pos;
  }
  //////////////////////////

  ///// getter methods /////
  public double getHealth() {
      return health;
  }
  public double getDamage() {
      return damage;
  }
  public int[] getPos() {
      return pos;
  }
  protected level getLevelHWND() {
      return levelHWND;
  }
  //////////////////////////

  public Boolean takesArgument(){
    //most entities won't take an argument, if they do they will override this
    return false;
  }
}



class player extends entity{
  Scanner keyboard;
  // constructor
  public player(level levelHWND, double health, double damage){
    //set up generic variables
    super(levelHWND, health, damage);

    keyboard = new Scanner(System.in);
  }

  @Override
  public void update() {
    String line;
    //gets user inputs

    while(true){
      line = keyboard.nextLine();
      if(processInput(line)){
        break;
      }
    }
  }

  // processes a given input
  private Boolean processInput(String line){
    switch (line.split(" ")[0].toLowerCase()) {
      case "go":
        //process go command...
        switch (line.split(" ")[1].toLowerCase()) {
          case "up":
            moveEntity(new int[] {0, 1});

            break;
          case "down":
            moveEntity(new int[] {0, -1});

            break;
          case "left":
            moveEntity(new int[] {1, 0});

            break;
          case "right":
            moveEntity(new int[] {-1, 0});

          break;
          default:
            //invalid direction...

            break;
        }
        return true;
      case "interact":
      entity temp = null;
      switch (line.split(" ")[1].toLowerCase()) {
        case "up":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] - 1});

          break;
        case "down":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] + 1});

          break;
        case "left":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] + 0});

          break;
        case "right":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] + 0});

        break;
        default:
          //invalid direction...

          break;
      }
      if(temp != null){
        temp.interact();

        return true;
      }

        break;
      case "position":
        //this is just for debugging
        System.out.printf("(%d, %d)\n", this.getPos()[0], this.getPos()[1]);
        System.out.println("Press enter to continue...");
        keyboard.nextLine();

      return true;
      //add other cases, etc...
      default:
        //print an error message and get another input or something

        break;
    }

  return false;
  }
  @Override
  protected void interact() {
      //not possible...

  }
  @Override
  protected void die() {
    //player has died...
    //end game and reset
  }
  @Override
  public void drawEntity() {
      System.out.printf("o/");
  }
}

class wall extends entity{
  public wall(level levelHWND){
    //set up generic variables
    //wall is unkillable (not breakable) and won't hurt you
    super(levelHWND, -1, 0);
  }
  @Override
  protected void interact() {
      //print some snarky text or something
  }
  @Override
  public void update() {
      //nothing needs to update, therefore do nothing
  }
  @Override
  public void drawEntity() {
      System.out.printf("[]");
  }
}

class door extends entity{
  String targetRoom = null;

  public door(level levelHWND){
    super(levelHWND, 0, 0);
  }
  @Override
  protected void interact() {
    //ldepending on what is specified in map file, may go to new map, or may represent a subdivision of rooms in map
    if (targetRoom != null){
      getLevelHWND().loadMap(targetRoom);

      return;
    }
    //for now at least, when open door and targetroom is null, turn tile into null
    getLevelHWND().setTile(getPos(), null);
  }
  @Override
  public void drawEntity() {
      System.out.printf("{}");
  }
  @Override
  public void update() {
      //do nothing
  }
  @Override
  public void assignArgument (String arg){
    if(arg.compareTo("Normal") == 0){
      //normal door, when interact with door opens
      targetRoom = null;

      return;
    }
    //otherwise string corrosponds to filename of next room
    targetRoom = arg.replace("\"", "");
  }
  @Override
  public Boolean takesArgument(){

    return true;
  }
}

//this entity is the A-star node, will have to derive from entity so that it can easily, could make another abstract class that is named tile, allowing
//this to be cleaner, but for now since it is like 1 am and I'm just writing code to later refactor and clean, this'll be fine
class AStarNode extends entity{
  int gCost;            //this represents the cost of the path from the beginning to this tile
  int hCost;            //this represents the cost of the direct path from this tile to the end
  int fCost;            //this represents the total cost of the tile
  int TargetNodePos[];  //this represents the target position to find the optimal route to
  int lastNodePos[];    //this represents the cheapest node connected to this; use this if this tile is apart of the chosen path to find the tile beforehand, retracing the program's steps
  //takes: level object reference, the position of the target, the tile's G cost (G cost is calculated via taking the last tile's G cost plus the direction's cost)
  AStarNode(level levelHWND, int posTargetNode[], int aTGC){
    super(levelHWND, 0, 0);
    TargetNodePos[0] = posTargetNode[0];
    TargetNodePos[1] = posTargetNode[1];
    gCost = aTGC;

    //calculate this new tile's h cost
    calcH();
    fCost = gCost + hCost;
  }

  //this method calculates the tile's current Hcost from the given targetNode
  private void calcH(){

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
    System.out.printf("EE");
  }
}
