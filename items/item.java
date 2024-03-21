public class item{
	protected int itemId = 0;
	protected int itemDamage = 0;
	protected int itemDurability = 1;

	public void damageDurability(int dmg) {
		itemDurability -= dmg;
	}
	public int getId() {
		return itemId;
	}
	public  int getDamage() {
		return itemDamage;
	}
	public int getDurability() {
		return itemDurability;
	}
}