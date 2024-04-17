import java.util.Scanner;
import levels.*;
import items.item;

public class anvil extends entity {
	public anvil(level levelHWND){
	    //since it cannot take damage, health and damage is set to 0
	    super(levelHWND, 0, 0);
  }
	@Override
	protected void interact() {
		// select item to upgrade
		Arraylist<item> inv = getLevelHWND().getPlayer().getInventory();
		for (int i; i < inv.length; i++)
			System.out.println(i + "\t" + inv.getName()); // prints out the inventory with indexes
		System.out.println("Input the number of the item you wish to upgrade.");
		Scanner keys = new Scanner(System.in);
		int index = keys.nextInt();
		getLevelHWND().getPlayer().upgradeInventory(index); // upgrades item at that index
		keys.close(); // closes scanner
		getLevelHWND().setTile(getPos(), null);
	}
	@Override
	protected void drawEntity() {
		System.out.printf("#>");
	}
}