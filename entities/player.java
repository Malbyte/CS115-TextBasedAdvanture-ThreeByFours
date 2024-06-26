package entities;
import java.util.ArrayList;
import java.util.Scanner;

import levels.*;
import items.ItemGenerator;
import items.item;

//TODO:
//for user inputs, if expecting there to be a second command argument, need either try catch block for exceptions for out of bounds or check the array length returned from the split command to see if there is a second one
//if not, then retry for user input
public class player extends entity{
  private ArrayList<item> inventory;
  private /*static*/ Scanner keyboard = new Scanner(System.in);
  private item curWeapon = ItemGenerator.generate(6); //when first starting, player is using fists

  // constructor
  public player(level levelHWND, double health, double damage){
    //set up generic variables
    super(levelHWND, health, damage);

    inventory = new ArrayList<item>();
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
      case "a":
      case "attack":
      if(line.split(" ").length < 2){
        System.out.printf("\033[H\033[2J");
        System.out.println("error: no preceding operand after " + line.split(" ")[0] + "!\npress any key to continue...");
        keyboard.nextLine();
        break;
      }
      entity target = null;
      switch (line.split(" ")[1].toLowerCase()) {
        case "ul":
        case "lu":
        case "leftup":
        case "upleft":
          target = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] - 1});
            
          break;
        case "ur":
        case "ru":
        case "rightup":
        case "upright":
          target = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] - 1});

          break;
        case "dl":
        case "ld":
        case "leftdown":
        case "downleft":
          target = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] + 1});

          break;
        case "dr":
        case "rd":
        case "rightdown":
        case "downright":
          target = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] + 1});

          break;
      case "u":
      case "up":
        target = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] - 1});

        break;
      case "d":
      case "down":
        target = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] + 1});

        break;
      case "l":
      case "left":
        target = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] + 0});

      break;
      case "r":
      case "right":
        target = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] + 0});

      break;
      default:
        //invalid direction...

        break;
      }

      if(target != null){
        target.changeHealth(curWeapon.getDamage());
      }
      return true;
      case "m":
      case "go":
        //process go command...
        if(line.split(" ").length < 2){
          System.out.printf("\033[H\033[2J");
          System.out.println("error: no preceding operand after " + line.split(" ")[0] + "!\npress any key to continue...");
          keyboard.nextLine();
          return false;
        }
        switch (line.split(" ")[1].toLowerCase()) {
          case "ul":
          case "lu":
          case "leftup":
          case "upleft":
            moveEntity(new int[] {1, 1});
            
            break;
          case "ur":
          case "ru":
          case "rightup":
          case "upright":
          moveEntity(new int[] {-1, 1});

            break;
          case "dl":
          case "ld":
          case "leftdown":
          case "downleft":
            moveEntity(new int[] {1, -1});

            break;
          case "dr":
          case "rd":
          case "rightdown":
          case "downright":
            moveEntity(new int[] {-1, -1});

            break;
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
      //check t make sure that there is a preceding command argument
      if(line.split(" ").length < 2){
        System.out.printf("\033[H\033[2J");
        System.out.println("error: no preceding operand after " + line.split(" ")[0] + "!\npress any key to continue...");
        keyboard.nextLine();
        return false;
      }
      switch (line.split(" ")[1].toLowerCase()) {
          case "ul":
          case "lu":
          case "leftup":
          case "upleft":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] - 1});

          break;
          case "ur":
          case "ru":
          case "rightup":
          case "upright":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] - 1});

          break;
          case "dl":
          case "ld":
          case "leftdown":
          case "downleft":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] + 1});

          break;
          case "dr":
          case "rd":
          case "rightdown":
          case "downright":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 1, getPos()[1] + 1});

          break;
        case "u":
        case "up":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] - 1});

          break;
        case "d":
        case "down":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] + 0, getPos()[1] + 1});

          break;
        case "l":
        case "left":
          temp = getLevelHWND().getTile(new int[] {getPos()[0] - 1, getPos()[1] + 0});

          break;
        case "r":
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
      case "help":
      case "command":
      case "commands":
      case "?":
        System.out.printf("\033[H\033[2J");
        printCommands();
        System.out.printf("Press enter to continue...\n");
        keyboard.nextLine();

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

  public item getWeapon() {
	  return curWeapon;
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
        case "equip":
        case "setweapon":
        case "switchweapon":
        case "sw":
          //set weapon
          if(line.split(" ").length < 2){
            System.out.printf("\033[H\033[2J");
            System.out.println("error: no preceding operand after " + line.split(" ")[0] + "!\npress any key to continue...");
            keyboard.nextLine();
            break;
          }
          //will equip the last known version of this item in the list
          for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getName().equalsIgnoreCase(line.substring(line.indexOf(" ") + 1))){
              curWeapon = inventory.get(i);
            }
          }
          break;
        case "u":
        case "us":
        case "use":
          // uses item (changes health based on item)
          for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getName().equalsIgnoreCase(line.substring(line.indexOf(" ") + 1))){
              //do something or something in the future if want to expand on or something, idk anymore I'm not really invested in this project now
              changeHealth(inventory.get(i).getDamage());
              //removeInventory(line.substring(line.indexOf(" ") + 1));
              if(curWeapon == inventory.get(i)){
                curWeapon = ItemGenerator.generate(6);  //when item is consumed, go back to using fists!
              }
              inventory.remove(i);
              
              break;
            }
          }
          break;

        case "rm":
        case "del":
          removeInventory(line.substring(line.indexOf(" ") + 1));

          break;
        case "e":

          return;
        case "help":
        case "command":
        case "commands":
        case "?":
          System.out.printf("\033[H\033[2J");
          printCommands();
          System.out.printf("Press enter to continue...\n");
          keyboard.nextLine();

          break;
        default:
          //unknown command
          System.out.printf("\033[H\033[2J");
          System.out.println("unkown command " + line + "!\npress any key to continue...");
          keyboard.nextLine();
          break;
      }
      printInventory();

      System.out.printf("> ");
      line = keyboard.nextLine();
    }
  }
  public void printInventory(){
    System.out.printf("\033[H\033[2J");
    System.out.println("Equipped Weapon: " + curWeapon.getName());
    System.out.printf("Inventory:\n");
    for(int i = 0; i < inventory.size(); i++){
      System.out.println(inventory.get(i).getName());
    }
  }
  public void upgradeInventory(int invIndex) {
	  // This method allows for the anvil entity to access the upgrade function of an item in the private inventory ArrayList

    //check to make sure index exists
    if (invIndex < (inventory.size())){
      inventory.get(invIndex).upgrade();
    }
  }
  public void printCommands(){
    //prints all user commands ingame
    System.out.println("--General Commands--");
    System.out.println("go(m) - move one tile in a given direction\nexamples:\tgo upleft\n\t\tgo up\n\t\tgo right");
    System.out.println("interact(i) - interact any tile adjacent to the player\nexamples:\tinteract left\n\t\tinteract upright\n\t\tinteract rightup");
    System.out.println("attack (a) - attack any enemy adjacent to the player\nexamples:\tattack left\n\t\tattack up\n\t\tattack right");
    System.out.println("inventory(inv) - access the player's inventory");
    System.out.println("commands(?) - show commands");
    System.out.println("exit - exits the game");
    System.out.println("\n--Inventory Commands--");
    System.out.println("use(us) - uses item (NOTE: Using an item adjusts your health based on that item.\n\t\tUsing a weapon will damage you. Do not do it)\nexamples:\tuse Potion (heals you)");
    System.out.println("setweapon(sw) - sets weapon\nexamples:\tsetweapon stick (sets weapon to a stick item in the list)");
    System.out.println("e - exits the inventory");
  }
}
