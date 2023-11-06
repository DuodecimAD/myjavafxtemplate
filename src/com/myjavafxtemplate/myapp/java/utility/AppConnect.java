package com.myjavafxtemplate.myapp.java.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	/*

    public void insert(String characterName, int typeId) {
        String sqlCheck = "SELECT COUNT(*) AS count FROM Perso WHERE name_character = ?";
        String sqlInsert = "INSERT INTO Perso(name_character, id_type_character) VALUES(?, ?)";
        int count = 0;

        try (PreparedStatement pstmt1 = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlInsert)) {

            pstmt1.setString(1, characterName);
            ResultSet rs = pstmt1.executeQuery();
            count = rs.getInt("count");

            if (count > 0) {
                System.out.println("Character already created");
            } else {
                pstmt2.setString(1, characterName);
                pstmt2.setInt(2, typeId);
                pstmt2.executeUpdate();
                System.out.println("A new character was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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
