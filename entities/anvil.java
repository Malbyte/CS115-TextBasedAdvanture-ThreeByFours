import java.util.Scanner;
import levels.*;
import items.item;

public class anvil extends entity {
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