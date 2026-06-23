package items;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQL_Db;

public class Equipment extends Item {
	private SQL_Db database;
	
	ResultSet itemResultSet;
	private int equipment_id;
//	private String equipmentName;
//	private String equipmentDesc;
	
	private int eq_HP;
	private int eq_atk;
	private int eq_def;
	
	public Equipment() {
		createEquipment();
	}
	private void createEquipment() {
		database = new SQL_Db();
		int r_eq_selector = (int)(Math.random()*(database.countRows("equipment")))+1;
		System.out.println("ID Pulled: "+r_eq_selector);
		itemResultSet = database.pickEquipment(r_eq_selector);
		System.out.println("Assigning values");
		try {
			while(itemResultSet.next()) {
				System.out.println("Starting to get equipment ID");
				equipment_id = itemResultSet.getInt("equipment_id");
				System.out.println(equipment_id);
	
				System.out.println("getting name");
				item_name = itemResultSet.getString("equipment_name");
				System.out.println(item_name);
	
				System.out.println("Starting to get desc");
				item_description = itemResultSet.getString("equipment_description");
				System.out.println(item_description);
				
				System.out.println("Starting to get hp");
				eq_HP = itemResultSet.getInt("equipment_hp");
				System.out.println(eq_HP);
				
				System.out.println("Starting to get atk");
				eq_atk = itemResultSet.getInt("equipment_atk");
				System.out.println(eq_atk);
				
				System.out.println("Starting to get def");
				eq_def = itemResultSet.getInt("equipment_def");
				System.out.println(eq_def);
				System.out.println("Item succesfully created");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with setting attributes of an Equipment");
		}
	}
	public int getEq_ID() {
		return equipment_id;
	}
	public int getEq_HP() {
		return eq_HP;
	}

	public void setEq_HP(int eq_HP) {
		this.eq_HP = eq_HP;
	}

	public int getEq_atk() {
		return eq_atk;
	}

	public void setEq_atk(int eq_atk) {
		this.eq_atk = eq_atk;
	}

	public int getEq_def() {
		return eq_def;
	}

	public void setEq_def(int eq_def) {
		this.eq_def = eq_def;
	}
}
