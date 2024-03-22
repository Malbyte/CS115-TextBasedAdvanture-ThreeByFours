public class potion extends item {
	//////////////////////////////////////////////////////////////////////////
	// Creates a sword item
	//////////////////////////////////////////////////////////////////////////
	public potion(int id, int dmg, int dur) {
		super(id, dmg, dur, "Potion");
	}
	// Overloaded constructor for custom sword names
	public potion(int id, int dmg, int dur, String name) {
		super(id, dmg, dur, name);
	}
}