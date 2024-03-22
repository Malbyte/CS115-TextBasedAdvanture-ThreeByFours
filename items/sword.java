public class sword extends item {
	//////////////////////////////////////////////////////////////////////////
	// Creates a sword item
	//////////////////////////////////////////////////////////////////////////
	public stick(int id, int dmg, int dur) {
		super(id, dmg, dur, "Sword");
	}
	// Overloaded constructor for custom sword names
	public stick(int id, int dmg, int dur, String name) {
		super(id, dmg, dur, name);
	}
}