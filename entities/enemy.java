package entities;
import java.util.ArrayList;
import java.util.Random;

import levels.*;
//this represents a basic enemy that chases the player if they are
//within a certain range
//for now this example will be of a skeleton

public class enemy extends entity{
  // track enemies killed
  private static int enemiesKilled = 0;
  private int lockDistance = 8;
  private enum enemyState{
    WANDER,
    CHASE
  };
  private enemyState curState = enemyState.WANDER;
  public enemy(level levelHWND, double health, double damage){
    super(levelHWND, health, damage);
  }
  @Override
  protected void interact() {
    //read stats, allow player to input another thing maybe or smthn, idk
  }
  @Override
  public void update() {
    ArrayList<int[]> path = null;
    switch (curState) {
      case WANDER:
        //check if player is within "eyesight" or range (need level method to first check distance, then check if there is a tile in the way of line of sight)

        //generate a random #, between 1-9, if 5 stay in place ofc
        Random randomizer = new Random();
        int offset[] = {randomizer.nextInt(0, 3) - 1,  randomizer.nextInt(0, 3) - 1};
        moveEntity(offset);
        
        path = getLevelHWND().getPath(getLevelHWND().getPlayer().getPos(), getPos());
        if (path != null && path.size() < lockDistance){
          curState = enemyState.CHASE;
        }

        break;
      case CHASE:
        //check if player is still within range

        //if so, use pathing method to get the next tile (expensive to do, but otherwise the enemy won't update when player updates)

        //first get updated array of pathing to player
        path = getLevelHWND().getPath(getLevelHWND().getPlayer().getPos(), getPos());
        //player is too far away for aggro
        if (path != null && path.size() > lockDistance){
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
    enemy.enemiesKilled += 1;
    getLevelHWND().setTile(getPos(), null);
  }
  @Override
  public void assignArgument (String arg){
    //take the two ints with a delimeter of |, first is health, second is attack
    setHealth(Double.parseDouble(arg.split("[|]")[0]));
    setDamage(Double.parseDouble(arg.split("[|]")[1]));
  }
  @Override
  public Boolean takesArgument(){

    return true;
  }
  @Override
  public void drawEntity() {
    System.out.printf("\\s");
  }
  public static int getKills() {
	  return enemiesKilled;
  }
}

