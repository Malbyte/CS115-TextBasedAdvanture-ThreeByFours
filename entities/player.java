package entities;
import java.util.ArrayList;
import java.util.Scanner;

import levels.*;
import items.item;

//TODO:
//for user inputs, if expecting there to be a second command argument, need either try catch block for exceptions for out of bounds or check the array length returned from the split command to see if there is a second one
//if not, then retry for user input
public class player extends entity{
  private ArrayList<item> inventory;
  private Scanner keyboard;
  // constructor
  public player(level levelHWND, double health, double damage){
    //set up generic variables
    super(levelHWND, health, damage);

    inventory = new ArrayList<item>();
    keyboard = new Scanner(System.in);
  }

  @Override
  public void update() {
    String line;
    //gets user inputs

    while(true){
      System.out.printf("> ");
      line = keyboard.nextLine();
      if(processInput(line)){
        break;
      }
      //if not exiting, turn has not finished, reprint screen
      System.out.printf("\033[H\033[2J");
      getLevelHWND().printMap();
    }
  }

  // processes a given input
  private Boolean processInput(String line){
    switch (line.split(" ")[0].toLowerCase()) {
      case "m":
      case "go":
        //process go command...
        switch (line.split(" ")[1].toLowerCase()) {
          case "u":
          case "up":
            moveEntity(new int[] {0, 1});

            break;
          case "d":
          case "down":
            moveEntity(new int[] {0, -1});

            break;
          case "l":
          case "left":
            moveEntity(new int[] {1, 0});

            break;
          case "r":
          case "right":
            moveEntity(new int[] {-1, 0});

          break;
          default:
            //invalid direction...

            break;
        }
        return true;

      case "i":
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

        break;
      //add other cases, etc...
      case "inv":
      case "inventory":
        this.accessInventory();

        //break so the user does not waste a turn in their inventory
        break;

      case "command":
      case "commands":
      case "?":
        printCommands();

        break;
      case "exit":
        System.exit(0);
        //game closes here

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

  //add item class to inventory
  public void addInventory(item in){
    inventory.add(in);
  }

  //finds and removes item from inventory
  private Boolean removeInventory(String itemName){
    for(int i = 0; i < inventory.size(); i++){
      if(inventory.get(i).getName().matches(itemName)){
        inventory.remove(i);

        return true;
      }
    }
    return false;
  }

  public ArrayList<item> getInventory() {
      return inventory;
  }
  private void accessInventory(){
    printInventory();
    String line;
    System.out.printf("> ");
    line = keyboard.nextLine();
    while(!line.matches("exit")){
      switch (line.split(" ")[0].toLowerCase()) {

        case "setweapon":
        case "switchweapon":
        case "sw":
          //set weapon

          break;

        case "us":
        case "use":
          //perform's items interact method

          break;

        case "rm":
        case "del":
          removeInventory(line.substring(line.indexOf(" ") + 1));

          break;
        case "e":

          return;
        default:
          //unknown command
          break;
      }
      printInventory();

      System.out.printf("> ");
      line = keyboard.nextLine();
    }
  }
  public void printInventory(){
    System.out.printf("\033[H\033[2J");
    System.out.printf("Inventory:\n");
    for(int i = 0; i < inventory.size(); i++){
      System.out.println(inventory.get(i).getName());
    }
  }
  public void upgradeInventory(int invIndex) {
	  // This method allows for the anvil entity to access the upgrade function of an item in the private inventory ArrayList
	  inventory.get(invIndex).upgrade();
  }
  public void printCommands(){
    //prints all user commands ingame
    System.out.printf("\033[H\033[2J");
    System.out.println("--General Commands--");
    System.out.println("go(m) - move one tile in a given direction\nexamples:\tgo left\n\t\tgo upright\n\t\tgo rightup");
    System.out.println("interact(int) - interact any tile next to the player\nexamples:\tinteract left\n\t\tinteract upright\n\t\tinteract rightup");
    System.out.println("inventory(i) - access the player's inventory");
    System.out.println("exit - exits the game");
    System.out.println("\n--Inventory Commands--");
    System.out.println();
    System.out.println("e - exits the inventory");

    System.out.printf("Press enter to continue...\n");
    keyboard.nextLine();
  }
}
