import levels.*;
import entities.*;

public class main {
	public static void main(String args[]) {
		// Level and Entity setup
		level curLevel = new level();
		entity curEntity = null;
		final String startingMap = "example.lmp";

		// load the beginning level
		if(!curLevel.loadMap(startingMap)){
			//failed to load map, exit program!
			System.out.println("Error: failed to load starting map!");
			return;
		}

		//after loading map, enter main game loop
		while (true) {
			//first draw the map so the player can see what is happening
			curLevel.printMap();

			//then update all entities, including player

			//first update player method before any other creature
			curLevel.getPlayer().update();

			//afterwords, loop through all other entities, skipping over the player, and update them
			for(int y = 0; y < curLevel.mapSize()[1]; y++){
				for(int x = 0; x < curLevel.mapSize()[0]; x++){
					curEntity = curLevel.getTile(new int[] {x, y});
					if(curEntity != null && curEntity != curLevel.getPlayer()){
						curEntity.update();
					}
				}
			}
		}
	}
}