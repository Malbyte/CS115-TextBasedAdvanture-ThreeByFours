package items;
public class stick extends item {
	//////////////////////////////////////////////////////////////////////////
	// basic template for a specific item class without any extra functions
	//////////////////////////////////////////////////////////////////////////
	public stick(int id, int dmg, int dur) {
		super(id, dmg, dur, "Stick");
	}
	// Overloaded constructor for custom stick names
	public stick(int id, int dmg, int dur, String name) {
		super(id, dmg, dur, name);
	}
}
