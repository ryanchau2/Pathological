package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import items.Consumable;
import items.Equipment;

public class SQL_Db {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	public SQL_Db() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("Driver Loaded Succesfully");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathological","root","root");
//			System.out.println("Database connected successfully");
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			System.out.println("Statement object created successfully");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		}
		catch(SQLException e) {
			System.out.println("Something Went Wrong");
		}
	}
	public String[] getPlayerRun(int runID) {
		ResultSet returnpRun;
		String[] returnStats = new String[7];
		String selectStatement = "SELECT * FROM player_run WHERE run_id = " + runID + ";";
		try {
			returnpRun = statement.executeQuery(selectStatement);
			while(returnpRun.next()) {
				returnStats[0] = "ID: " + String.valueOf(returnpRun.getInt("run_id"));
				returnStats[1] = "Atk: " + String.valueOf(returnpRun.getInt("atk"));
				returnStats[2] = "Def: " + String.valueOf(returnpRun.getInt("def"));
				returnStats[3] = "HP: " + String.valueOf(returnpRun.getInt("hp"));
				returnStats[4] = "MP: "+ String.valueOf(returnpRun.getInt("mp"));
				returnStats[5] = "Deepest Path: " + String.valueOf(returnpRun.getInt("pathFloor"));
				returnStats[6] = "Status: " + returnpRun.getString("activeStatus");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong trying to retreive the playerRun info from ID" + runID);
		}
		return returnStats;
	}
	public String[] getEquipmentHistory(int runID) {
		ResultSet returnEquip;
		int equipCount = countIDRows(runID, "current_equipment");
		String[] returnEquips = new String[equipCount];
		String selectStatement = "SELECT equipment_name FROM current_equipment ce JOIN equipment e ON ce.equipment_equipment_id = e.equipment_id WHERE run_id = " + runID + ";";
		try {
			returnEquip = statement.executeQuery(selectStatement);
			int eqIterator = 0;
			while(returnEquip.next()) {
				System.out.println(returnEquip.getString("equipment_name"));
				returnEquips[eqIterator] = returnEquip.getString("equipment_name");
				eqIterator++;
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with GETTING EQUIPMENT HISTORY for id: " + runID);
		}
		return returnEquips;
//		return null;
	}
	public String[] getConsumableHistory(int runID) {
		ResultSet returnConsumables;
		int consumableCount = countIDRows(runID, "inventory");
		String[] returnInventory = new String[consumableCount];
		String selectStatement = "SELECT consumable_name FROM inventory i JOIN consumable c ON i.items_item_id = c.consumable_id WHERE run_id = " + runID + ";";
		try {
			returnConsumables = statement.executeQuery(selectStatement);
			int cIterator = 0;
			while(returnConsumables.next()) {
				returnInventory[cIterator] = returnConsumables.getString("consumable_name");
				cIterator++;
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with GETTING INVENTORY HISTORY for id: " + runID);
		}
		return returnInventory;
	}
	public int countIDRows(int runID, String table) {
		ResultSet returnRowCount;
		int rows = 0;
		String countQuery = "SELECT COUNT(*) FROM " + table + ";";
		try {
			returnRowCount = statement.executeQuery(countQuery);
			while(returnRowCount.next()) {
				rows += returnRowCount.getInt("COUNT(*)");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with counting rows for id: " + runID + "on table: " + table);
		}
		return rows;
	}
	public int setRunID() {
		ResultSet rs_runCt;
		int runID = 1;
		String selectStatement = "SELECT COUNT(*) FROM player_run;";
		try {
			rs_runCt = statement.executeQuery(selectStatement);
			while(rs_runCt.next()) {
				runID = rs_runCt.getInt("COUNT(*)") + 1;
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with the Select statement trying to retrieve runID");
		}
		return runID;
	}
	public void saveRun(int playerRunID, int atk, int def, int HP, int MP, int pathFloor, int cMP, Equipment[] equipment, Consumable[] consumable) {
		System.out.println("inserting player stats");
		insertPlayerRunStats(playerRunID, atk, def, HP, MP, pathFloor, "Complete", 0 , cMP);
		System.out.println("inserting player Equipment");
		insertEquipment(playerRunID,equipment);
		System.out.println("inserting player consumable");
		insertConsumable(playerRunID,consumable);
	}
	public void saveTempRun(int playerRunID, int atk, int def, int HP, int MP, int pathFloor, int cHP, int cMP, Equipment[] equipment, Consumable[] consumable) {
		System.out.println("inserting player stats");
		insertPlayerRunStats(playerRunID, atk, def, HP, MP, pathFloor, "Incomplete", cHP, cMP);
		System.out.println("inserting player Equipment");
		insertEquipment(playerRunID,equipment);
		System.out.println("inserting player consumable");
		insertConsumable(playerRunID,consumable);
	}
	private void insertPlayerRunStats(int playerRunID, int atk, int def, int HP, int MP, int pathFloor, String status, int cHP, int cMP) {
		String insertStatement = "INSERT INTO player_run ";
		insertStatement += "VALUES("+playerRunID+", "+ atk + ", "+ def +", "+HP+", "+MP+", "+ pathFloor+", \"" + status +"\", " + cHP + ", " + cMP + ");";
		try {
			statement.executeUpdate(insertStatement);
		}
		catch (SQLException e) {
			System.out.println("Insert PlayerRunStats Unsuccessful");
		}
		System.out.println(insertStatement);
		System.out.println("finished inserting player stats");
	}
//	Insert into player equipment
	private void insertEquipment(int playerRunID, Equipment[] equipment) {
		String insertStatement = "INSERT INTO current_equipment VALUES";
		for(int x=0; x<equipment.length; x++) {
			if(equipment[x]!=null) {
				insertStatement += "("+playerRunID+", "+(x+1)+", "+equipment[x].getEq_ID()+")";
				if(x+1==equipment.length|| equipment[x+1]==null) {
					break;
				}
			}
			else if(equipment[x]==null & x==0) {
				return;
			}
			else {
				break;
			}
			insertStatement+=", ";
		}
		insertStatement += ";";
//		System.out.println(insertStatement);
		try {
			statement.executeUpdate(insertStatement);
		}
		catch(SQLException e) {
			System.out.println("Insert Equipment unsuccessful");
		}
		System.out.println(insertStatement);
	}
//	Insert into player inventory
	private void insertConsumable(int playerRunID, Consumable[] consumable) {
		String insertStatement = "INSERT INTO inventory VALUES";
		for(int x=0; x<consumable.length; x++) {
			if(consumable[x]!=null) {
				insertStatement += "("+playerRunID+", "+(x+1)+", "+consumable[x].getConsumable_id()+", 'ACTIVE')";
				if(x+1==consumable.length|| consumable[x+1]==null) {
					break;
				}
			}
			else if(consumable[x]==null & x==0) {
				return;
			}
			else {
				break;
			}
			insertStatement+=", ";
		}
		insertStatement += ";";
		try {
			statement.executeUpdate(insertStatement);
		}
		catch(SQLException e) {
			System.out.println("Insert Consumable unsuccessful");
		}
		System.out.println(insertStatement);
	}
//	Insert into player skills
//	Get number of rows
	public int countRows(String table) {
		ResultSet rs_rCount;
		int rows = 0;
		String countQuery = "SELECT COUNT(*) FROM " + table + ";";
		try {
			rs_rCount = statement.executeQuery(countQuery);
			while(rs_rCount.next()) {
				rows += rs_rCount.getInt("COUNT(*)");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with counting rows");
		}
		return rows;
	}
	public int countRowsbyID(String table, int runID) {
		ResultSet rs_rCount;
		int rows = 0;
		String countQuery = "SELECT COUNT(*) FROM " + table + " WHERE run_id = " + runID + ";";
		try {
			rs_rCount = statement.executeQuery(countQuery);
			while(rs_rCount.next()) {
				rows += rs_rCount.getInt("COUNT(*)");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with counting rows at table " + table + " ID: " + runID);
		}
		return rows;
	}
	public ResultSet pickConsumable(int cs_id) {
		ResultSet rs_pickItem;
		String pickItem_Query =
				"SELECT * FROM consumable"
				+ " WHERE consumable_id = "+cs_id+";";
		try {
			rs_pickItem = statement.executeQuery(pickItem_Query);
			return rs_pickItem;
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with acquiring the Consumable");
		}
		return rs_pickItem=null;
	}
	public ResultSet pickEquipment(int eq_id) {
		ResultSet rs_pickItem;
		String pickItem_Query =
				"SELECT * FROM equipment"
				+ " WHERE equipment_id = "+eq_id+";";
		try {
			rs_pickItem = statement.executeQuery(pickItem_Query);
			return rs_pickItem;
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with acquiring the Equipment");
		}
		return rs_pickItem=null;
	}
	public int getIncompleteRunsDB() {
		ResultSet rs_incompleteID;
		int incompleteID = 0;
		String unfinishedQuery = "SELECT run_id FROM player_run WHERE activeStatus=\"Incomplete\";";
		try {
			rs_incompleteID = statement.executeQuery(unfinishedQuery);
			while(rs_incompleteID.next()) {
				incompleteID = rs_incompleteID.getInt("run_id");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with finding the unfinishedrunID");
		}
		return incompleteID;
	}
	public int[] setPlayerStats(int runID) {
		int[] returnStats = new int[8];
		ResultSet rs_returnStats;
		String returnStats_Query = "SELECT run_id, atk, def, hp, mp, remainingHP, remainingMP, pathFloor FROM player_run WHERE run_id=" + runID;
		try {
			rs_returnStats = statement.executeQuery(returnStats_Query);
			while(rs_returnStats.next()){
				returnStats[0] = rs_returnStats.getInt("run_id");
				returnStats[1] = rs_returnStats.getInt("atk");
				returnStats[2] = rs_returnStats.getInt("def");
				returnStats[3] = rs_returnStats.getInt("hp");
				returnStats[4] = rs_returnStats.getInt("mp");
				returnStats[5] = rs_returnStats.getInt("remainingHP");
				returnStats[6] = rs_returnStats.getInt("remainingMP");
				returnStats[7] = rs_returnStats.getInt("pathFloor");
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with pulling the player's stats from player run");
		}
		return returnStats;
	}
	public int[] pullPrevRunEquipment(int runID) {
		int[] returnEquip = new int[countRowsbyID("current_equipment", runID)];
		String returnEquipQuery = "SELECT equipment_equipment_id FROM current_equipment WHERE run_id = " + runID + ";";
		ResultSet rs_ReturnEquip;
		int x=0;
		try {
			rs_ReturnEquip = statement.executeQuery(returnEquipQuery);
			while(rs_ReturnEquip.next()) {
				returnEquip[x] = rs_ReturnEquip.getInt("equipment_equipment_id");
				x++;
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with pulling the player's equipment from currentEquipment");
		}
		return returnEquip;
	}
	public int[] pullPrevRunConsumable(int runID) {
		int[] returnConsumable = new int[countRowsbyID("inventory", runID)];
		String returnConsumableQuery = "SELECT items_item_id FROM inventory WHERE run_id = " + runID + ";";
		ResultSet rs_ReturnConsum;
		int x=0;
		try {
			rs_ReturnConsum = statement.executeQuery(returnConsumableQuery);
			while(rs_ReturnConsum.next()) {
				returnConsumable[x] = rs_ReturnConsum.getInt("items_item_id");
				x++;
			}
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with pulling the player's consumables from inventory");
		}
		return returnConsumable;
	}
	//removes records of "incomplete run" to continue after setting new run
	public void continueRun(int runID) {
		String deleteCurrentEquipment = "DELETE FROM current_equipment WHERE run_id = " + runID + ";";
		String deleteInventory = "DELETE FROM inventory WHERE run_id = " + runID + ";";
		String deleteRun = "DELETE FROM player_run WHERE run_id = " + runID + ";";
		try {
			statement.execute(deleteCurrentEquipment);
			statement.execute(deleteInventory);
			statement.execute(deleteRun);
		}
		catch(SQLException e) {
			System.out.println("Something went wrong with deleting the saved record");
		}
		
	}
	public void close() {
		try {
			connection.close();
			statement.close();
			System.out.println("Connection Closed Successfully");
		}
		catch(SQLException e) {
			System.out.println("Something went wrong and could not close the connection");
		}
	}
}
