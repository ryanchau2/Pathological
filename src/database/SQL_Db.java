package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_Db {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	public SQL_Db() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded Succesfully");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathological","root","root");
			System.out.println("Database connected successfully");
			
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("Statement object created successfully");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		}
		catch(SQLException e) {
			System.out.println("Something Went Wrong");
		}
	}
//	Insert into player inventory
	
//	Insert into player equipment
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
