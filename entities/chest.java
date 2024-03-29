package entities;

import levels.*;
import items.item;

//this represents a loot chest that can be found, allowing the player to get a new item(s)
public class chest extends entity{
  public chest(level levelHWND){
    //since it cannot take damage, health and damage is set to 0
    super(levelHWND, 0, 0);
  }
  @Override
  protected void interact() {
    //open chest and write to inventory
    getLevelHWND().getPlayer().addInventory(new item(-1, -1, -1, "TEST ITEM"));
    getLevelHWND().setTile(getPos(), null);
  }
  @Override
  public void update() {
      //chest won't do anything on update
  }
  @Override
  public void drawEntity() {
      System.out.printf("CH");
  }
}
