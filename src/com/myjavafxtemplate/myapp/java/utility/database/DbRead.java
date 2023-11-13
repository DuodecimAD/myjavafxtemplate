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

public class DbRead {
	
	private static Connection conn = DbConnect.sharedConnection();

	// Private constructor to prevent instantiation
	private DbRead() {
	    
	}
	
	public static List<List<?>> read(String tableName, String sortBy) {
		tableName = AppSecurity.sanitize(tableName);
		sortBy = AppSecurity.sanitize(sortBy);
		

        String sql = "SELECT * FROM " + tableName  + " ORDER BY " + sortBy;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<List<?>> resultList = new ArrayList<>();
            
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
	
	
	public static List<?> read(String tableName, List<String> dataString, String key, String value) {
		tableName = AppSecurity.sanitize(tableName);
		dataString = AppSecurity.sanitize(dataString);
		key = AppSecurity.sanitize(key);
		value = AppSecurity.sanitize(value);

    	String columns = "";

	   	 for (int i = 0; i < dataString.size(); i++) {
			columns += dataString.get(i);
			if (i < dataString.size() - 1) {
	            columns += ", ";
	        }
		}
    	
        String sql = "SELECT "+ columns +" FROM "+ tableName +" WHERE "+ key +" = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, value);
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
