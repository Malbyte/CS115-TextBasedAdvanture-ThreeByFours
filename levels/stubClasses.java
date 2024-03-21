import java.util.Scanner;

// These classes are temporary to allow me to set up the level class (which will become the general map object for the game)
// note: none of these are at all final designs of the classes; this is merely to have a gist of what it may look like
class entity{

  private double health;
  private double damage;
  private int pos[];

  ///// constructors /////
  public entity(double health, double damage, int pos[]){
    this.health = health;
    this.damage = damage;
    if(pos != null){
      this.pos[0] = pos[0];
      this.pos[1] = pos[1];
    }
  }

  public entity(double health, double damage){
    this.health = health;
    this.damage = damage;
    //entity will need position to be specified later with a set method
    this.pos = null;
  }
  ////////////////////////

  // called every turn (after player makes their move)
  public void update(){

  }

  // method is called when attacked, healed, etc.
  public void changeHealth(double health){

  }

  //method is called when entity is dead
  protected void die(){

  }

  // method tries to move the entity by an offset (unofficial) vec2 variable
  // note: to pass a vec2 (array size 2) literal, pass new int[] {x, y}
  protected void moveEntity(int pos[]){

  }

  // prints out a 2x1 character representing the entity
  // this is called for every entity in order from the level class to print out the map
  public void drawEntity(){

  }

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
  //////////////////////////
}



class player extends entity{
  Scanner keyboard;
  // constructor
  public player(double health, double damage){
    //set up generic variables
    super(health, damage);

    keyboard = new Scanner(System.in);
  }

  @Override
  public void update() {
    //gets user inputs

  }
}
