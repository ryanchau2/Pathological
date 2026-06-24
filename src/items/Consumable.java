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
	private void createConsumable() {
		database = new SQL_Db();
		int r_consumable_selector = (int)(Math.random()*(database.countRows("consumable")))+1;
//		System.out.println("ID Pulled: "+r_consumable_selector);
		itemResultSet = database.pickConsumable(r_consumable_selector);
//		System.out.println("Assigning values");
		try {
			while(itemResultSet.next()) {
//				System.out.println("Starting to get consumable ID");
				consumable_id = itemResultSet.getInt("consumable_id");
//				System.out.println(consumable_id);
	
//				System.out.println("getting name");
				item_name = itemResultSet.getString("consumable_name");
//				System.out.println(item_name);
	
//				System.out.println("Starting to get desc");
				item_description = itemResultSet.getString("consumable_description");
//				System.out.println(item_description);
				
//				System.out.println("Starting to get hp");
				consumable_HP = itemResultSet.getInt("consumable_hp_mod");
//				System.out.println(consumable_HP);
//				System.out.println("Item succesfully created");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with setting attributes of an Consumable");
		}
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
