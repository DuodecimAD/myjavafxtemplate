/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.AppSecurity;

// TODO: Auto-generated Javadoc
/**
 * The Class DbCreate.
 */
public class DbCreate {
	
	/** The conn. */
	private static Connection conn = DbConnect.sharedConnection();

	/**
	 * Instantiates a new db create.
	 */
	// Private constructor to prevent instantiation
	private DbCreate() {
	    
	}
	
	
	public static void insert(String tableName, List<String> columnsList, List<Object> valuesList) throws SQLException {
		tableName = AppSecurity.sanitize(tableName);
		columnsList = AppSecurity.sanitize(columnsList);
		
   	 	String columns = "";
   	 	String values = "";
   	 	int columnsLength = 0;

   	 	for (int i = 0; i < columnsList.size(); i++) {
   		 
			columns += columnsList.get(i);
			values += valuesList.get(i).toString();
			columnsLength++;

			if (i < columnsList.size() - 1) {
	            columns += ", ";
	            values += ", ";
	        }
		}
   	 	
   	 	
   	 	
   	/* 	
   	 	System.out.println("table Name : " + tableName);
	   	System.out.println("columns Name : " + columns);
	   	System.out.println("columns length : " + columnsLength);
	   	System.out.println("values : " + values);
	   	System.out.println("checkIfExist : " + checkIfExist);
*/
   	 	
   	 	String call = "{call InsertIfNotExists(?, ?, ?, ?)}";
   	 	

       try (CallableStatement callableStatement = conn.prepareCall(call)) {


    	// Set parameter values
           callableStatement.setString(1, tableName);
           callableStatement.setString(2, columns);
           callableStatement.setInt(3, columnsLength);
           callableStatement.setString(4, values);


           // Execute the stored procedure
           callableStatement.execute();
           
           System.out.println(valuesList + " was inserted successfully!");
  
       } catch (SQLException e) {
    	   
           throw e;
       }
	
   }
	
	/**
	 * Insert.
	 * @param tableName the table name
	 * @param List of columns names - Strings
	 * @param List of values - any Type
	 * @param checkPositions the check positions LIST
	 * @return 
	 * @throws SQLException 
	 */
	/*public static void insert(String tableName, List<String> columnsList, List<Object> valuesList, List<Integer> checkPositions) throws SQLException {
		tableName = AppSecurity.sanitize(tableName);
		columnsList = AppSecurity.sanitize(columnsList);
		
   	 	String columns = "";
   	 	String values = "";

   	 	for (int i = 0; i < columnsList.size(); i++) {
   		 
			columns += columnsList.get(i);
			values += "?";

			if (i < columnsList.size() - 1) {
	            columns += ", ";
	            values += ", ";
	        }
		}
   	 
   	 	String sqlInsert = "INSERT INTO "+ tableName +"("+ columns +") VALUES("+ values +")";
   	 	String checkIfExists = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE ";

   	    for (int position : checkPositions) {
   	        checkIfExists += columnsList.get(position) + " = ? OR ";
   	    }

   	    // Remove the trailing "OR" from the WHERE clause
   	    checkIfExists = checkIfExists.substring(0, checkIfExists.length() - 4);

       try (PreparedStatement pstmtCheck = conn.prepareStatement(checkIfExists);
            PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {


	         // Set parameters for the WHERE clause based on checkPositions
	         for (int j = 0; j < checkPositions.size(); j++) {
	             pstmtCheck.setString(j + 1, (String) valuesList.get(checkPositions.get(j)));
	         }
	
	         ResultSet rs = pstmtCheck.executeQuery();
	         

	         if (rs.next()) {
	             int count = rs.getInt("count");
	             
	             if (count == 0) {
	                 // Set the parameters based on the dataString list
	                 for (int j = 0; j < valuesList.size(); j++) {
	                     pstmtInsert.setObject(j + 1, valuesList.get(j));
	                 }
	
	                 pstmtInsert.executeUpdate();
	                 System.out.println(valuesList + " was inserted successfully!");
	             } else {
	                 throw new SQLException("tel or email already exists");
	             }
	         }
             
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           throw e;
       }
	
   }*/
}
