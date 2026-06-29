package entity;

import database.SQL_Db;
import items.Consumable;
import items.Equipment;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Player extends Entity{
	private Equipment[] currentEquipment = new Equipment[4];
	private Consumable[] currentConsumables = new Consumable[10];
	private int pathFloor;
	
	private int equipmentCount = 0;
	private int consumableCount = 0;
	VBox replaceWindow;
	VBox replaceTextContainer;
	Text replaceText;
	HBox replaceContainer;
	
	SQL_Db database;
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
	public void addToConsumables(Consumable c, BorderPane window) {
		for(int x=0; x<currentConsumables.length; x++) {
			if(currentConsumables[x]==null)
			{
				currentConsumables[x] = c;
				consumableCount++;
				break;
			}
		}
		return;
	}
	public void replaceConsumable(int index, Consumable consumable) {
		currentConsumables[index] = consumable;
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
	public void replaceEquipment(int index, Equipment equipment) {
		setAtk(this.getAtk()-currentEquipment[index].getEq_atk()+equipment.getEq_atk());
		setDef(this.getDef()-currentEquipment[index].getEq_def()+equipment.getEq_def());
		setMaxHP(this.getMaxHP()-currentEquipment[index].getEq_HP()+equipment.getEq_HP());
		currentEquipment[index] = equipment;
	}
	public void useConsumable(Consumable c) {
		if(getCurrentHP()+c.getConsumable_HP()>getMaxHP())
			setCurrentHP(getMaxHP());
		else
			setCurrentHP(getCurrentHP()+c.getConsumable_HP());
		return;
	}
	
//	Save Stats to SQLDB
	public void saveStats(int pathFloor) {
		this.pathFloor = pathFloor;
		database = new SQL_Db();
		database.saveRun(pathFloor, "test", getMaxHP(), getMaxMP(), pathFloor, currentEquipment, currentConsumables);
		database.close();
	}
	public Consumable[] getConsumableList() {
		return currentConsumables;
	}
	public int getConsumableTotal() {
		return consumableCount;
	}
	public Equipment[] getEquipmentList() {
		return currentEquipment;
	}
	public int getEquipmentTotal() {
		return equipmentCount;
	}
}
