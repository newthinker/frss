package servlet;

import java.io.*;

import javax.servlet.ServletContextEvent;

public class ContextListener implements javax.servlet.ServletContextListener {

	/**
	 * This method is invoked when the Web Application has been removed and is
	 * no longer able to accept requests.
	 * @param event
	 */
	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * This method is invoked when the Web Application is ready to service requests. 
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			// load the driver
			Class.forName("org.hsqldb.jdbcDriver");
			// create the table and add sample data
			InputStreamReader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("db.sql"));
			BufferedReader reader = new BufferedReader(in);
			DBUtils.setupDatabase(reader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
