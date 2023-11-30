/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import com.myjavafxtemplate.myapp.java.utility.AppSettings;

import javafx.application.Platform;


// TODO: Auto-generated Javadoc
/**
 * The Class DbConnect.
 */
public class DbConnect {
	
	/** The conn. */
	private static Connection conn;
	private static boolean timerActive = false;
	
	/**
	 * Instantiates a new db connect.
	 */
	private DbConnect() {
	
		try {
	        // Provide the connection URL, username, and password
	        String url = AppSettings.INSTANCE.dbUrl;
	        String username = AppSettings.INSTANCE.dbUsername;
	        String password = AppSettings.INSTANCE.dbPassword;
	
	        // Load the Oracle JDBC driver
	        Class.forName("oracle.jdbc.OracleDriver");
	
	        // Establish the database connection
	        conn = DriverManager.getConnection(url, username, password);
	        System.out.println("Connection to Oracle Database has been established.");
	    } catch (SQLException e) {
	        System.out.println("SQL Exception: " + e.getMessage());
	        handleConnectionError();
	    } catch (ClassNotFoundException e) {
	        System.out.println("Class Not Found Exception: " + e.getMessage());
	    }

	}
	
	private void handleConnectionError() {
		final int[] seconds = {30}; // Initial countdown value
		timerActive = true;
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		    public void run() {
		        Platform.runLater(() -> {
		        	System.out.println("Connection to database failed. Trying again in " + seconds[0] + " sec.");
		            seconds[0]--;

		            if (seconds[0] < 0) {
		                timer.cancel(); // Stop the timer when the countdown reaches zero
		                timerActive = false;
		                sharedConnection(); // Optionally, trigger another attempt here
		            }
		        });
		    }
		}, 0, 1000);
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	private static Connection getConnection() {
        if (conn == null && timerActive == false) {
            new DbConnect(); // Initialize the connection if it's not already done
        }
        return conn;
    }

    /**
     * Shared connection.
     *
     * @return the connection
     */
    public static Connection sharedConnection() {
        return getConnection();
    }
    
    /**
     * Close the connection.
     */
    public static void closeConnection() {
        try {
            Connection connection = sharedConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection to Oracle Database has been closed.");
                conn = null; // Set the connection variable to null after closing
            } else {
                System.out.println("Connection is already closed or null.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	

    
    
/*
    public void delete(String characterName) {
        String sql = "DELETE FROM Perso WHERE name_character = '" + characterName + "'";

        try (Statement stmt  = conn.createStatement()){
             int rowsDeleted = stmt.executeUpdate(sql);
             if (rowsDeleted > 0) {
                System.out.println("Character with id " + characterName + " was deleted successfully!");
             } else {
                System.out.println("No character found with id " + characterName);
             }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void newCharacter(String name, int type){

            String sql = "INSERT INTO Perso (name_character, id_type_character) VALUES ('"+name+"', "+type+");";

        if(!checkExist(name)) {
            System.out.println("Name does not exist in the database.");
            try (Statement stmt  = conn.createStatement()){

                int rowsInserted = stmt.executeUpdate(sql);
                if (rowsInserted > 0) {
                    System.out.println("A new character was inserted successfully!");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage()+ " WTF");
            }
        }else {
             System.out.println("Name already exists in the database.\n->Connection closed");
        }
    }
    
    public boolean checkExist(String name) {
    	String sql = "SELECT * FROM Perso WHERE name_character = '"+name+"'";
   
    
    	try (Statement stmt  = conn.createStatement();
    		ResultSet rs    = stmt.executeQuery(sql)){

    	//next true or false
            //System.out.println("Name already exists in the database.");
            // System.out.println("Name does not exist in the database.");
            return rs.next();
          
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
		return false;
   }
*/
    
    
}
