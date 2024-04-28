package entities;
import java.util.ArrayList;
import java.util.Random;

import levels.*;
//this represents a basic enemy that chases the player if they are
//within a certain range
//for now this example will be of a skeleton
public class enemy extends entity{
  private enum enemyState{
    WANDER,
    CHASE
  };
  private enemyState curState = enemyState.CHASE;
  public enemy(level levelHWND, double health, double damage){
    super(levelHWND, health, damage);
  }
  @Override
  protected void interact() {
    //read stats, allow player to input another thing maybe or smthn, idk
  }
  @Override
  public void update() {
    switch (curState) {
      case WANDER:
        //check if player is within "eyesight" or range (need level method to first check distance, then check if there is a tile in the way of line of sight)

        //generate a random #, between 1-9, if 5 stay in place ofc
        Random randomizer = new Random();
        int tile = randomizer.nextInt(0, 9);
        int offset[] = {(tile % 3) - 1,  (tile / 3) - 1};
        moveEntity(offset);


        break;
      case CHASE:
        //check if player is still within range

        //if so, use pathing method to get the next tile (expensive to do, but otherwise the enemy won't update when player updates)

        //first get updated array of pathing to player
        ArrayList<int[]> path = getLevelHWND().getPath(getLevelHWND().getPlayer().getPos(), getPos());
        //player is too far away for aggro
        if (path != null && path.size() > 8){
          curState = enemyState.WANDER;
        }
        //first check that there still exists a path, if not then can attack
        if(path == null){
          //attack player
          getLevelHWND().getPlayer().changeHealth(-this.getDamage());
          break;
        }
        else{
        if(getLevelHWND().moveTile(getPos(), path.get(0))){
          setPos(path.get(0));
        }
        break;
        }
    }
  }
  @Override
  protected void die(){
    getLevelHWND().setTile(getPos(), null);
  }
  @Override
  public void assignArgument (String arg){
    //take the two ints with a delimeter of |, first is health, second is attack
    setHealth(Double.parseDouble(arg.split("|")[0]));
    setDamage(Double.parseDouble(arg.split("|")[2]));
  }
  @Override
  public Boolean takesArgument(){

    return true;
  }
  @Override
  public void drawEntity() {
    System.out.printf("\\s");
  }
}

