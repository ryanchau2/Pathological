package entity;

import items.Consumable;
import items.Equipment;

public class Player extends Entity{
	private static int runID = 0;
	private Equipment[] currentEquipment;
	private Consumable[] currentConsumables;
//	inventory and equipment
	public Player() {
		runID=runID+1;
	}
}
