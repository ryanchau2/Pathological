package entity;

import items.Consumable;
import items.Equipment;

public class Player extends Entity{
	private Equipment[] currentEquipment;
	private Consumable[] currentConsumables;
//	inventory and equipment
	public Player() {
		setStats();
		System.out.println("Player Successfully Created");
		returnCurrentStats();
	}
	private void setStats() {
		setAtk(10);
		setDef(5);
		setMaxHP(20);
		setMaxMP(15);
		setCurrentHP(getMaxHP());
		setCurrentMP(getMaxMP());
	}
}
