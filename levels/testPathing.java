public class testPathing {
  public static void main(String[] args){
    String startingMap = "example3.lmp";
    // first, you would want to handle setting up stuff...
    level curLevel = new level();
    entity curEntity = null;

    // load the beginning level
    if(!curLevel.loadMap(startingMap)){
      //failed to load map, exit program!
      System.out.println("Error: failed to load starting map!");

      return;
    }
    //test getPath method
    //curLevel.getPath(new int[]{1, 1}, new int[] {11, 13});


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

      //the main loop should be done, reprint the map and start all over again
    }
  }
}
