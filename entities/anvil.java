package entities;
import java.util.Scanner;
import levels.*;
import items.item;
import java.util.ArrayList;

public class anvil extends entity {
	public anvil(level levelHWND){
	    //since it cannot take damage, health and damage is set to 0
	    super(levelHWND, 0, 0);
  }
	@Override
	protected void interact() {
		// select item to upgrade
		ArrayList<item> inv = getLevelHWND().getPlayer().getInventory();
		for (int i = 0; i < inv.size(); i++)
			System.out.println(i + "\t" + inv.get(i).getName()); // prints out the inventory with indexes
		System.out.println("Input the number of the item you wish to upgrade.");
		Scanner keys = new Scanner(System.in);
		int index = keys.nextInt();
		getLevelHWND().getPlayer().upgradeInventory(index); // upgrades item at that index
		keys.nextLine(); // advance to the next line break
		//keys.close(); // closes scanner
		//cannot close scanner! closing any scanner connected to system.in will break any other scanners
		//that are currently connected:
		//https://stackoverflow.com/questions/19766566/java-multiple-scanners
		getLevelHWND().setTile(getPos(), null);
	}
	@Override
	public void drawEntity() {
		System.out.printf("#>");
	}
	public void update() {
		// do nothing
	}
}