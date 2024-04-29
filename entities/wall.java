package entities;

import levels.*;

public class wall extends entity{
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
