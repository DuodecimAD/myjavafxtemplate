/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.AppSecurity;

import oracle.jdbc.OracleTypes;

// TODO: Auto-generated Javadoc
/**
 * The Class DbDelete.
 */
public class DbDelete {
	
	/** The conn. */
	private static Connection conn = DbConnect.sharedConnection();

	/**
	 * Instantiates a new db delete.
	 */
	// Private constructor to prevent instantiation
	private DbDelete() {
	    
	}
	
	public static void setToIsDeleted(String tableName, String column, String value) {
		tableName = AppSecurity.sanitize(tableName);
		column = AppSecurity.sanitize(column);
		value = AppSecurity.sanitize(value);
		
		conn = DbConnect.sharedConnection();
		
		String call = "{call SetToIsDeleted(?, ?, ?)}";

        try (CallableStatement callableStatement = conn.prepareCall(call)) {
        	
        	callableStatement.setString(1, tableName);
            callableStatement.setString(2, column);
            callableStatement.setString(3, value);
            
            // Execute the stored procedure
            callableStatement.execute();


            System.out.println("Based on value :" + value + ", the row has been set to isDeleted");
            
        } catch (SQLException e) {
            e.getMessage();
            System.out.println("no row has been found in table " + tableName + " with value " + value + " in column : " + column);
        }


    }


}
