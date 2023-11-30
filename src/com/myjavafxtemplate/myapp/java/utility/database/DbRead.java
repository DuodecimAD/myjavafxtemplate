/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.AppSecurity;

// TODO: Auto-generated Javadoc
/**
 * The Class DbRead.
 */
public class DbRead {
	
	/** The conn. */
	private static Connection conn;

	/**
	 * Instantiates a new db read.
	 */
	// Private constructor to prevent instantiation
	private DbRead() {
	    
	}
	
	/**
	 * Read.
	 *
	 * @param tableName the table name
	 * @param sortBy the sort by
	 * @return the list
	 */
	public static List<List<Object>> read(String tableName, String sortBy) {
		tableName = AppSecurity.sanitize(tableName);
		sortBy = AppSecurity.sanitize(sortBy);
		
		conn = DbConnect.sharedConnection();
		

        String sql = "SELECT * FROM " + tableName  + " ORDER BY " + sortBy;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<List<Object>> resultList = new ArrayList<>();
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
            	List<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                resultList.add(row);
            }

            if (!resultList.isEmpty()) {
                return resultList;
            } else {
                System.out.println("No records found in the table.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList(); // Return an empty list if there's an error or no results
    }
	
	
	/**
	 * Read.
	 *
	 * @param tableName the table name
	 * @param dataString the data string
	 * @param keyExist check from this column if valueExist already exist
	 * @param valueExist check with this value if it's already exist
	 * @return the list
	 */
	public static List<Object> read(String tableName, List<String> dataString, String keyExist, String valueExist) {
		tableName = AppSecurity.sanitize(tableName);
		dataString = AppSecurity.sanitize(dataString);
		keyExist = AppSecurity.sanitize(keyExist);
		valueExist = AppSecurity.sanitize(valueExist);

    	String columns = "";

	   	 for (int i = 0; i < dataString.size(); i++) {
			columns += dataString.get(i);
			if (i < dataString.size() - 1) {
	            columns += ", ";
	        }
		}
    	
        String sql = "SELECT "+ columns +" FROM "+ tableName +" WHERE "+ keyExist +" = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, valueExist);
            ResultSet rs = pstmt.executeQuery();

            List<Object> resultList = new ArrayList<>();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    resultList.add(rs.getObject(i));
                }
            }

            if (!resultList.isEmpty()) {
                return resultList;
            } else {
                System.out.println("No matching records found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return Collections.emptyList(); // Return an empty list if there's an error or no results
    }
}
