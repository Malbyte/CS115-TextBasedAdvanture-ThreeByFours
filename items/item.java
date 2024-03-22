public class item{
	protected int itemId, itemDamage, itemDurability;
	/* itemId is the id # of the item,
	itemDamage is the effect on an entity's health (positive for healing, negative for damage)
	itemDurability is the durability of the item*/
	protected String itemName; // Name of the item

	public item(int id, int dmg, int dur, String name) {
		itemId = id;
		itemDamage = dmg;
		itemDurability = dur;
		itemName = name;
	}
	////////////////////////////////////////////
	// Getters
	public void damageDurability(int dmg) {
		itemDurability -= dmg;
	}
	public int getId() {
		return itemId;
	}
	public int getDamage() {
		return itemDamage;
	}
	public int getDurability() {
		return itemDurability;
	}
	public String getName() {
		return itemName;
	}
	/////////////////////////////////////////////
}