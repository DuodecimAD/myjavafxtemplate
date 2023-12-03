/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AppSecurity.
 */
public class AppSecurity {

	/**
	 * Instantiates a new app security.
	 */
	private AppSecurity() {
		
	}
	
	/**
	 * Sanitize.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String sanitize(String input) {
	    // Keep only letters (a-z, A-Z) and numbers (0-9)
	    return input.replaceAll("[^a-zA-Z0-9_./@-]", "");
	}
	
	/**
	 * Sanitize.
	 *
	 * @param input the input
	 * @return the list
	 */
	public static List<String> sanitize(List<String> input) {
	    List<String> sanitizedInput = new ArrayList<>();

	    // Remove non-alphanumeric characters from each string
	    for (String str : input) {
	        String sanitizedString = str.replaceAll("[^a-zA-Z0-9_./@-]", "");
	        sanitizedInput.add(sanitizedString);
	    }

	    return sanitizedInput;
	}
	
	
}
