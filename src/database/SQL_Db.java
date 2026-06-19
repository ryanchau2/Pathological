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
		int rows = -1;
		String rowCount = "SELECT COUNT(*) FROM ";
		rowCount+= table + ";";
		try {
			rs_rCount = statement.executeQuery(rowCount);
			rows = rs_rCount.getInt("COUNT(*)");
			
		} catch (SQLException e) {
			System.out.println("Could not get total amount of rows");
		}
		return rows;
	}
	public Object pickItem(String table, int row) {
		ResultSet rs_pickItem;
		String pickItem_Query =
				"SELECT * FROM equipment"
				+ "WHERE equipment_id = ";
		pickItem_Query+=row +";";
		
		return null;
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
