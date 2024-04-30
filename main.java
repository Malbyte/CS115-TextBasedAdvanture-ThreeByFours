import levels.*;
import levels.level;
import entities.anvil;
import entities.AStarNode;
import entities.AStarNodeComparator;
import entities.chest;
import entities.door;
import entities.enemy;
import entities.entity;
import entities.player;
import entities.wall;
import items.item;
import items.ItemGenerator;
// The giant list of imports is needed to make everything compile correctly. Thanks Java.
import java.util.Scanner;

public class main {
	public static void main(String args[]) {
		// Level and Entity setup
		level curLevel = new level();
		entity curEntity = null;
		final String startingMap = "room1.lmp";
		Scanner keyboard = new Scanner(System.in);

		gameSetup(curLevel, keyboard, startingMap);

		//after loading map, enter main game loop
		while (true) {
			//first draw the map so the player can see what is happening
			curLevel.printMap();

			//then update all entities, including player

			//first update player method before any other creature
			curLevel.getPlayer().update();
			curLevel.getPlayer().setHasUpdated(true);
			//afterwords, loop through all other entities, skipping over the player, and update them
			for(int y = 0; y < curLevel.mapSize()[1]; y++){
				for(int x = 0; x < curLevel.mapSize()[0]; x++){
					curEntity = curLevel.getTile(new int[] {x, y});
					if(curEntity != null && !curEntity.getHasUpdated()){
						curEntity.update();
						curEntity.setHasUpdated(true);
					}
				}
			}
			//unlock all entities after actions
			for(int y = 0; y < curLevel.mapSize()[1]; y++){
				for(int x = 0; x < curLevel.mapSize()[0]; x++){
					curEntity = curLevel.getTile(new int[] {x, y});
					if(curEntity != null){
						curEntity.setHasUpdated(false);
					}
				}
			}
			if(curLevel.getPlayer().getHealth() <= 0){
				//player has died at some point during this game loop!
				System.out.printf("\033[H\033[2J");
				System.out.println("...");
				System.out.println("You Died");
				System.out.println("...");
				System.out.println("Press any key to continue...");
				keyboard.nextLine();

				//reset level
				curLevel = new level();
				gameSetup(curLevel, keyboard, startingMap);
			}
		}
	}

	static private void gameSetup(level curLevel, Scanner keyboard, String startingMap){
		// Intro Menu
		System.out.printf("\033[H\033[2J");
		System.out.println("##################################################################################################   \r\n" + //
						   "###            #   #     #    #                    #   #           ###        #   #            ###   \r\n" + //
						   "#  #  ##  ###  #  ####   #    #  ###  # #   ##    #### #     ##    #  #  ##  ####    ##  ###  #      \r\n" + //
						   "#  # #  # #  #     #     ###  # #  # # # # ####    #   ###  ####   ###  #  #  #   # #  # #  #  ##    \r\n" + //
						   "#  # #  # #  #     #     #  # # #  # #   # #       #   #  # #      #    #  #  #   # #  # #  #    #   \r\n" + //
						   "###   ##  #  #     #     ###  #  ### #   #  ##     #   #  #  ##    #     ##   #   #  ##  #  # ###    \r\n" + //
						   "##################################################################################################");
		curLevel.getPlayer().printCommands();
		System.out.println("\n\nGoal: Escape through three rooms without dying\n\nPress Enter to start...");
		String line = keyboard.nextLine();

		// load the beginning level
		if(!curLevel.loadMap(startingMap)){
			//failed to load map, exit program!
			System.out.println("Error: failed to load starting map!");
			return;
		}
	}
}