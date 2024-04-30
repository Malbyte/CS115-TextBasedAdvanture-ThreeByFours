package entities;

import java.util.Scanner;

import levels.*;

public class door extends entity{
  protected String targetRoom = null;

  public door(level levelHWND){
    super(levelHWND, 0, 0);
  }
  @Override
  protected void interact() {
    //ldepending on what is specified in map file, may go to new map, or may represent a subdivision of rooms in map
    if(targetRoom == null){
      // normal door, therefore get rid of it when opened
      getLevelHWND().setTile(getPos(), null);
      return;
    }
    if (!targetRoom.matches("__LOCKED__")){
      getLevelHWND().loadMap(targetRoom);

      return;
    }
    else{
      System.out.println("This door seems to be locked...\npress any key to continue.");
      Scanner keyboard = new Scanner(System.in);
      keyboard.nextLine();
    }
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
    if(arg.compareTo("Locked") == 0){
      //normal door, when interact with door opens
      targetRoom = "__LOCKED__";

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
