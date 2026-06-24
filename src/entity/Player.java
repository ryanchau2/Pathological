package entity;

import events.replaceEquipment;
import items.Consumable;
import items.Equipment;
import items.Item;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Player extends Entity{
	private Equipment[] currentEquipment = new Equipment[4];
	private Consumable[] currentConsumables = new Consumable[10];
	
	private int equipmentCount = 0;
	VBox replaceWindow;
	VBox replaceTextContainer;
	Text replaceText;
	HBox replaceContainer;
//	private Skills[] skills = new Skills[4];
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
	public void addToEquipment(Equipment e, BorderPane window) {
		for(int x=0; x<currentEquipment.length; x++) {
			if(currentEquipment[x]==null)
			{
				currentEquipment[x] = e;
				setAtk(this.getAtk()+e.getEq_atk());
				setDef(this.getDef()+e.getEq_def());
				setMaxHP(this.getMaxHP()+e.getEq_HP());
				equipmentCount++;
				break;
			}
		}
		return;
	}
	public void addToConsumables(Consumable c, BorderPane window) {
		System.out.println("adding");
		for (Consumable co : currentConsumables) {
			if(co==null) {
				co=c;
				System.out.println("added");
				return;
			}
		}
//		replace an item in your consumable message, please
		System.out.println("There is an error, there is no space in your backpack, please select an item you would like to replace");
	}
	public void replaceEquipment(int index, Equipment equipment) {
		setAtk(this.getAtk()-currentEquipment[index].getEq_atk()+equipment.getEq_atk());
		setDef(this.getDef()-currentEquipment[index].getEq_def()+equipment.getEq_def());
		setMaxHP(this.getMaxHP()-currentEquipment[index].getEq_HP()+equipment.getEq_HP());
		currentEquipment[index] = equipment;
	}
	public Equipment[] getEquipmentList() {
		return currentEquipment;
	}
	public int getEquipmentTotal() {
		return equipmentCount;
	}
}
