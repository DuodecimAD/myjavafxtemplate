/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility.database;

import java.sql.Connection;

// TODO: Auto-generated Javadoc
/**
 * The Class DbUpdate.
 */
public class DbUpdate {
	
	/** The conn. */
	private static Connection conn = DbConnect.sharedConnection();

	/**
	 * Instantiates a new db update.
	 */
	// Private constructor to prevent instantiation
	private DbUpdate() {
	    
	}

}
