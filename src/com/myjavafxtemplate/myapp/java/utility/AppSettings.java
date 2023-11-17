/*
 * 
 */
package com.myjavafxtemplate.myapp.java.utility;

import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Enum AppSettings.
 */
public enum AppSettings {

	/** The instance. */
	INSTANCE; // Singleton instance
	
///////////////////////////////////////////
//////////////// DB ///////////////////////
///////////////////////////////////////////
	
	/** The db url. */
	public String dbUrl = "jdbc:oracle:thin:@localhost:49161/xe";
	
	/** The db username. */
	public String dbUsername = "MEDICALSTUFF";
	
	/** The db password. */
	public String dbPassword = "MEDICALSTUFF";
	
///////////////////////////////////////////
/////////////// PATHS /////////////////////
///////////////////////////////////////////
	
	/** The path location. */
	public String pathLocation = "com/myjavafxtemplate/myapp/";
    
    /** The views path. */
    public String viewsPath = pathLocation + "java/views/";
    
    /** The content path. */
    public String contentPath = pathLocation + "java/views/content/";
    
    /** The css path. */
    public String cssPath = pathLocation + "ressources/css/";
    
    /** The app url path. */
    public URL appUrlPath = AppSettings.class.getClassLoader().getResource(viewsPath);
    
    /** The app full path. */
    public String appFullPath = appUrlPath.getPath().replaceFirst("^/(.:/)", "$1");

}
