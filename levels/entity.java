abstract class entity{
  private Boolean hasUpdated;
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

    hasUpdated = false;
  }

  public entity(level levelHWND, double health, double damage){
    this.levelHWND = levelHWND;
    this.health = health;
    this.damage = damage;
    //entity will need position to be specified later with a set method
    this.pos = null;

    hasUpdated = false;
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
  public void setHasUpdated(Boolean hasUpdated) {
      this.hasUpdated = hasUpdated;
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
  public Boolean getHasUpdated() {
      return hasUpdated;
  }
  //////////////////////////

  public Boolean takesArgument(){
    //most entities won't take an argument, if they do they will override this
    return false;
  }
}
