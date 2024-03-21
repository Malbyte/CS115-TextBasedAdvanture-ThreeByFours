public class item{
	protected int itemId, itemDamage, itemDurability;

	public item(int id, int) {

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