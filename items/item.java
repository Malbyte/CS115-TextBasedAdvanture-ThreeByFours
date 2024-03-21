public class item{
	protected int itemId, itemDamage, itemDurability;

	public item(int id, int dmg, int dur) {
		itemId = id;
		itemDamage = dmg;
		itemDurablity = dur;
	}
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
}