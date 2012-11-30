package cis550;
import java.sql.*;
/**
 * 
 * this class is used to connect to the remote DB, maybe finally we need to put all the
 * work related to the DB here.
 *
 */
public class DBConnection {
	//connect to the remote DB
	public static Connection connectDB(){
		Connection connection = null;
		try {
			 
			Class.forName("com.mysql.jdbc.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
 
		}
 
		System.out.println("MySQL JDBC Driver Registered!");
 
		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://pi.spica.im:3306/olympeeks",
							"root", "4AJ6S50x28Ngh36s8lDrVrA6q");
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}		
		return connection;
	}
}
