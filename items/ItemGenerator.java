public class ItemGenerator {
	// Main method for testing purposes
	public static void main(String[] args) {
		item stck1 = generate(0);
		item stck2 = generate(1);
		System.out.println(stck1.getName() + " does "+ stck1.getDamage());
		System.out.println(stck2.getName() + " does "+ stck2.getDamage());
		stck2.upgrade(); // Upgrades Stick of Power
		System.out.println(stck2.getName() + " does "+ stck2.getDamage());
	}

	/////////////////////////////////////////////////////////
	// Function for generating an item of the specified id
	/////////////////////////////////////////////////////////
	public static item generate(int id) {
		item i = null;
		switch (id){
			case 0: // a basic stick
				i = new stick(id, -15, 5); // itemName(itemId, healingOrDamage, durability)
				break;
			case 1: // a special stick
				i = new stick(id, -20, 5, "Stick of Power"); // itemName(itemId, healingOrDamage, durability, name)
				break;
			case 2: // a basic sword
				i = new sword(id, -20, 10);
				break;
			case 3: // a basic potion
				i = new potion(id, 20, 5);
				break;
		}
		return i;
	}
}