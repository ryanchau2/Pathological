package entity;

import items.Consumable;
import items.Equipment;

public class Player extends Entity{
	private Equipment[] currentEquipment = new Equipment[4];
	private Consumable[] currentConsumables = new Consumable[10];
//	inventory and equipment
	public Player() {
		setStats();
		setEntity_sprite("file:images/sprites/player_test1.png");
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
	public void addToEquipment(Equipment e) {
		for(int x=0; x<currentEquipment.length; x++) {
			if(currentEquipment[x]==null) {
				setAtk(getAtk()+e.getEq_atk());
				setDef(getDef()+e.getEq_def());
				setMaxHP(getMaxHP()+e.getEq_HP());
				return;
			}
		}
//		replace an item in your equipment message, please
		System.out.println("There is an error, there is no space in your backpack, please select an item you would like to replace");
	}
	public void replaceEquipment(Equipment e, int oldE) {
//		choice being an int is a placeholder, but effectively will be the button choice that is selected
	}
	public void addToConsumables(Consumable c) {
		for (Consumable co : currentConsumables) {
			if(co==null) {
				co=c;
				return;
			}
		}
//		replace an item in your consumable message, please
		System.out.println("There is an error, there is no space in your backpack, please select an item you would like to replace");
	}
	public void replaceConsumable(Consumable c, int oldC) {
		
	}
}
