package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class DBUtils {

	/*
	 * Creates the sample data (table and records). 
	 */
	public static void setupDatabase(BufferedReader reader) {
		Connection c = null;
		Statement stmt = null;
		try {
			c = openConnection();
			stmt = c.createStatement();
			// reads the file with the SQL statements
			String line;
			while ((line = reader.readLine()) != null) {
				stmt.execute(line);
			}
			stmt.close();
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Opens a database connection. 
	 */
	public static Connection openConnection() throws SQLException {
		Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:dojotree", "sa", "");
		return c;
	}
	
}
