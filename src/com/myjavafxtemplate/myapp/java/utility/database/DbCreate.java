package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.AppSecurity;

public class DbCreate {
	
	private static Connection conn = DbConnect.sharedConnection();

	// Private constructor to prevent instantiation
	private DbCreate() {
	    
	}
	
	public static void insert(String tableName, List<List<String>> dataString, List<Integer> checkPositions) {
		tableName = AppSecurity.sanitize(tableName);
		dataString = AppSecurity.sanitizeDouble(dataString);
		
   	 	String columns = "";
   	 	String values = "";

   	 	for (int i = 0; i < dataString.get(0).size(); i++) {
   		 
			columns += dataString.get(0).get(i);
			values += "?";

			if (i < dataString.get(0).size() - 1) {
	            columns += ", ";
	            values += ", ";
	        }
		}
   	 
   	 	String sqlInsert = "INSERT INTO "+ tableName +"("+ columns +") VALUES("+ values +")";
   	 	String checkIfExists = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE ";

   	    for (int position : checkPositions) {
   	        checkIfExists += dataString.get(0).get(position) + " = ? OR ";
   	    }

   	    // Remove the trailing "OR" from the WHERE clause
   	    checkIfExists = checkIfExists.substring(0, checkIfExists.length() - 4);

       try (PreparedStatement pstmtCheck = conn.prepareStatement(checkIfExists);
            PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

            // Set the parameters based on the dataString list
       	  for (int i = 1; i < dataString.size(); i++) {
                 // Set parameters for the WHERE clause based on checkPositions
                 for (int j = 0; j < checkPositions.size(); j++) {
                     pstmtCheck.setString(j + 1, (String) dataString.get(i).get(checkPositions.get(j)));
                 }

                 ResultSet rs = pstmtCheck.executeQuery();
                 
              // Move the cursor to the first row (if any)
                 if (rs.next()) {
                 int count = rs.getInt("count");
                 
                 if (count == 0) {
                     // Set the parameters based on the dataString list
                     for (int j = 0; j < dataString.get(i).size(); j++) {
                         pstmtInsert.setObject(j + 1, dataString.get(i).get(j));
                     }

                     pstmtInsert.executeUpdate();
                     System.out.println(dataString.get(i) + " was inserted successfully!");
                 } else {
                     System.out.println(dataString.get(i) + " already exists");
                 }
                 }
             }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
}
