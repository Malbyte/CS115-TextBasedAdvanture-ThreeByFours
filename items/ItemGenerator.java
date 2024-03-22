public class ItemGenerator {
	// Main methood for testing purposes
	public static void main(String[] args) {
	}

	public static item generate(int id) {
		item i;
		switch id:
			case 0:
				i = new stick(id, -1, 1); // itemName(itemId, healing/damage, durability)
				break;
		return i;
	}
}