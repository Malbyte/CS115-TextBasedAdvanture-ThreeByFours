package entities;

import levels.*;

public class door extends entity{
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
