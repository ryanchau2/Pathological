package items;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQL_Db;

public class Consumable extends Item{
	private SQL_Db database;
	ResultSet itemResultSet;
	private int consumable_id;
	private int consumable_HP;
	
	
	public Consumable() {
		createConsumable();
	}
	//overwrites original constructor (used for previous runs)
	public Consumable(int itemID) {
		database = new SQL_Db();
		itemResultSet = database.pickConsumable(itemID);
		try {
			while(itemResultSet.next()) {
//				System.out.println("Starting to get consumable ID");
				consumable_id = itemResultSet.getInt("consumable_id");
				item_name = itemResultSet.getString("consumable_name");
				item_description = itemResultSet.getString("consumable_description");
				consumable_HP = itemResultSet.getInt("consumable_hp_mod");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with setting attributes of an Consumable");
		}
		setSprites();
	}
	private void createConsumable() {
		database = new SQL_Db();
		int r_consumable_selector = (int)(Math.random()*(database.countRows("consumable")))+1;
		itemResultSet = database.pickConsumable(r_consumable_selector);
		try {
			while(itemResultSet.next()) {
//				System.out.println("Starting to get consumable ID");
				consumable_id = itemResultSet.getInt("consumable_id");
				item_name = itemResultSet.getString("consumable_name");
				item_description = itemResultSet.getString("consumable_description");
				consumable_HP = itemResultSet.getInt("consumable_hp_mod");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with setting attributes of an Consumable");
		}
		setSprites();
	}
	public void setSprites() {
		switch(consumable_id) {
		case 1:
			setItem_sprite("file:images/sprites/small_HPpotion.png");
			break;
		case 2:
			setItem_sprite("file:images/sprites/medium_HPpotion.png");
			break;
		case 3:
			setItem_sprite("file:images/sprites/large_HPpotion.png");
			break;
		}
		database.close();
	}
	public int getConsumable_id() {
		return consumable_id;
	}
	public String getItemName() {
		return item_name;
	}
	public String getItemDesc() {
		return item_description;
	}
	public int getConsumable_HP() {
		return consumable_HP;
	}
}
