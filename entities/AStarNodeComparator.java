package entities;
//class to implement a custom comparison for the object class AStarNode, used in the A star algorithm

import java.util.Comparator;

public class AStarNodeComparator implements Comparator<AStarNode>{
  @Override
  public int compare(AStarNode o1, AStarNode o2) {
    if(o1.getfCost() == o2.getfCost())
      return 0;
    return (o1.getfCost() - o2.getfCost() > 0) ? 1 : -1;
  }
}
