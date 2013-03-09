package com.frss.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	/**
	 * Get a DB Connection.
	 * 
	 * @param driver
	 * @param dataSource
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driver, String dataSource) throws ClassNotFoundException,SQLException{
		Class.forName(driver);			
		return DriverManager.getConnection(dataSource);
	}
	
	/**
	 * Get a DB Connection.
	 * 
	 * @param driver
	 * @param url
	 * @param userName
	 * @param pwd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driver, String url, String userName, String pwd) throws ClassNotFoundException,SQLException, Exception{			
		Class.forName(driver);			
		return DriverManager.getConnection(url, userName, pwd);
	}
	
	/**
	 * Close database resources.
	 * 
	 * @param rest
	 * @param pstmt
	 * @param conn
	 */
	public static void close(ResultSet rest, Statement pstmt, Connection conn){
		try{
			if(rest!=null)
				rest.close();
			if(pstmt!=null)
				pstmt.close();
			if(conn!=null && !conn.isClosed())
				conn.close();
		
		}catch(Exception e){				

			e.printStackTrace();
		}		
	}

	/**
	 * Close database resources.
	 * 
	 * @param pstmt
	 * @param conn
	 */
	public static void close(PreparedStatement pstmt, Connection conn){
		try{
			if(pstmt!=null)
				pstmt.close();
			if(conn!=null && !conn.isClosed())
				conn.close();
		
		}catch(Exception e){				

			e.printStackTrace();
		}		
	}

	/**
	 * Close database resources.
	 * 
	 * @param conn
	 */
	public static void close(Connection conn){
		try{
			if(conn!=null && !conn.isClosed())
				conn.close();
		
		}catch(Exception e){				
			e.printStackTrace();
		}		
		
	}
}
