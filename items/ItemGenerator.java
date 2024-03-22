public class ItemGenerator {
	// Main methood for testing purposes
	public static void main(String[] args) {
		item stck = generate(0);
		System.out.println(stck.getDamage());
	}

	public static item generate(int id) {
		item i = null;
		switch (id){
			case 0:
				i = new stick(id, -1, 1); // itemName(itemId, healingOrDamage, durability)
				break;
		}
		return i;
	}
}