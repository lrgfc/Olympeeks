package cis550;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class DBListener
 * it is used to connect to the db at the context and shut it down.
 *
 */
@WebListener
public class DBListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DBListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
       // create the database connection, when connect to the internet
    	ServletContext ctx = sce.getServletContext();
    	connection = DBConnection.connectDB();
        ctx.setAttribute("dbConnection", connection);           
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
       try {
		connection.close();
       } catch (SQLException e) {
    	   e.printStackTrace();
       } 
    }
    
    private Connection connection;
	
}
