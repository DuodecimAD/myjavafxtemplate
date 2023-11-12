package com.myjavafxtemplate.myapp.java.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AppConnect {
	private Connection conn;
	
	public AppConnect() {
	
	try {
        // Provide the connection URL, username, and password
        String url = "jdbc:oracle:thin:@localhost:49161/xe";
        String username = "MEDICALSTUFF";
        String password = "MEDICALSTUFF";

        // Load the Oracle JDBC driver
        Class.forName("oracle.jdbc.OracleDriver");

        // Establish the database connection
        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connection to Oracle Database has been established.");
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("Class Not Found Exception: " + e.getMessage());
    }
}
	
	

    public void dbCreate(String tableName, List<List<?>> dataString, List<Integer> checkPositions) {
    	    	
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
                      System.out.println("Insert was inserted successfully!");
                  } else {
                      System.out.println(dataString.get(i) + " already exists");
                  }
                  }
              }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public Character getCharacter(String characterName) {
        String sql = "SELECT name, level, experience_points, hit_points FROM characters WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, characterName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int expPoints = rs.getInt("experience_points");
                int hitPoints = rs.getInt("hit_points");
                return new Character(name, level, expPoints, hitPoints);
            } else {
                return null; // character not found
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    */
}
