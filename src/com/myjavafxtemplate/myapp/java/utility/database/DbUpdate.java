/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import com.myjavafxtemplate.myapp.java.utility.AppSecurity;

// TODO: Auto-generated Javadoc
/**
 * The Class DbUpdate.
 */
public class DbUpdate {
	
	/** The conn. */
	private static Connection conn;

	/**
	 * Instantiates a new db update.
	 */
	// Private constructor to prevent instantiation
	private DbUpdate() {
	    
	}
	
	public static void update(String tableName, String column, Object value, String checkColumn, String checkValue) throws SQLException {
		column = AppSecurity.sanitize(column);
		String newvalue = AppSecurity.sanitize(value.toString());
		
		System.out.println(newvalue);
		
		conn =  DbConnect.sharedConnection();
		
		String call = "{call updateData(?, ?, ?, ?, ?)}";

        try (CallableStatement callableStatement = conn.prepareCall(call)) {
        	
        	callableStatement.setString(1, tableName);
            callableStatement.setString(2, column);
    		callableStatement.setString(3, newvalue);
            callableStatement.setString(4, checkColumn);
            callableStatement.setString(5, checkValue);
            
            // Execute the stored procedure
            callableStatement.execute();


            System.out.println("column : " + column + " has been updated with value : "+ value + " based on the column : " + checkColumn + " with value : " + checkValue);
            
        } catch (SQLException e) {
        	throw e;
        }
		
		
		
	}

}
