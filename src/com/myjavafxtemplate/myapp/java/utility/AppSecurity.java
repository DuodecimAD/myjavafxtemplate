package com.myjavafxtemplate.myapp.java.utility;

import java.util.ArrayList;
import java.util.List;

public class AppSecurity {

	private AppSecurity() {
		
	}
	
	public static String sanitize(String input) {
	    // Keep only letters (a-z, A-Z) and numbers (0-9)
	    return input.replaceAll("[^a-zA-Z0-9_./@]", "");
	}
	
	public static List<String> sanitize(List<String> input) {
	    List<String> sanitizedInput = new ArrayList<>();

	    // Remove non-alphanumeric characters from each string
	    for (String str : input) {
	        String sanitizedString = str.replaceAll("[^a-zA-Z0-9_./@]", "");
	        sanitizedInput.add(sanitizedString);
	    }

	    return sanitizedInput;
	}
	
	
	public static List<List<String>> sanitizeDouble(List<List<String>> input) {

		    List<List<String>> sanitizedInput = new ArrayList<>();

		    // Remove non-alphanumeric characters from each string
		    for (List<String> innerList : input) {
		        List<String> sanitizedList = new ArrayList<>();
		        for (String str : innerList) {
		            String sanitizedString = str.replaceAll("[^a-zA-Z0-9_./@]", "");
		            sanitizedList.add(sanitizedString);
		        }
		        sanitizedInput.add(sanitizedList);
		    }

		    return sanitizedInput;
	}
}
