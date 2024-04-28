package entities;

import java.util.Random;
import levels.*;
import items.item;
import items.ItemGenerator;

//this represents a loot chest that can be found, allowing the player to get a new item(s)
public class chest extends entity{
  public chest(level levelHWND){
    //since it cannot take damage, health and damage is set to 0
    super(levelHWND, 0, 0);
  }
  @Override
  protected void interact() {
	// generator to make random item
	Random gen = new Random();
    //open chest and write to inventory
    item pickUpItem = ItemGenerator.generate(gen.nextInt(4));
    getLevelHWND().getPlayer().addInventory(pickUpItem);
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
