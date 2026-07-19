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
	private int runID;
	
	
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
		setRunID();
		setStats();
		setEntity_sprite("file:images/sprites/player_idle.gif");
		
//		returnCurrentStats();
	}
	public Player(int runID, int atk, int def, int hp, int mp, int cHP, int cMP) {
		this.runID = runID;
		setAtk(atk);
		setDef(def);
		setMaxHP(hp);
		setMaxMP(mp);
		setCurrentHP(cHP);
		setCurrentMP(cMP);
		System.out.println("ID: " + runID);
		setEntity_sprite("file:images/sprites/player_idle.gif");
		returnCurrentStats();
	}
	private void setRunID() {
		database = new SQL_Db();
		runID = database.setRunID();
		database.close();
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
		database.saveRun(runID, getAtk(), getDef(), getMaxHP(), getMaxMP(), pathFloor, getCurrentMP(), currentEquipment, currentConsumables);
		database.close();
	}
	public void battleReward() {
		setMaxHP(this.getMaxHP()+6);
		setMaxMP(this.getMaxMP()+2);
		setAtk(this.getAtk()+3);
		setDef(this.getDef()+2);
	}
	public String changeAttackSprite() {
		return "file:images/sprites/player_attack.gif";
	}
	public String changeIdleSprite() {
		return "file:images/sprites/player_idle.gif";
	}
	public int getRunID() {
		return runID;
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
