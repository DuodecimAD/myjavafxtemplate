package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.Connection;

public class DbUpdate {
	
	private static Connection conn = DbConnect.sharedConnection();

	// Private constructor to prevent instantiation
	private DbUpdate() {
	    
	}

}
