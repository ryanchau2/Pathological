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
	public void saveRun(int playerRunID, String playerName, int HP, int MP, int pathFloor, Equipment[] equipment, Consumable[] consumable) {
		System.out.println("inserting player stats");
		insertPlayerRunStats(playerRunID, playerName, HP, MP, pathFloor);
		System.out.println("inserting player Equipment");
		insertEquipment(playerRunID,equipment);
		System.out.println("inserting player consumable");
		insertConsumable(playerRunID,consumable);
	}
	private void insertPlayerRunStats(int playerRunID, String playerName, int HP, int MP, int pathFloor) {
		String insertStatement = "INSERT INTO player_run ";
		insertStatement += "VALUES("+playerRunID+", \""+playerName+"\", "+HP+", "+MP+", "+ pathFloor+");";
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
				insertStatement += "("+playerRunID+", "+x+1+", "+equipment[x].getEq_ID()+")";
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
				insertStatement += "("+playerRunID+", "+x+1+", "+consumable[x].getConsumable_id()+")";
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
