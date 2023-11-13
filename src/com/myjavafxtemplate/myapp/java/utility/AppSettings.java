package com.myjavafxtemplate.myapp.java.utility;

import java.net.URL;

public enum AppSettings {

	INSTANCE; // Singleton instance
	
	// DB
	public String dbUrl = "jdbc:oracle:thin:@localhost:49161/xe";
	public String dbUsername = "MEDICALSTUFF";
	public String dbPassword = "MEDICALSTUFF";
	
	// PATHS
	public String pathLocation = "com/myjavafxtemplate/myapp/";
    public String viewsPath = pathLocation + "java/views/";
    public String contentPath = pathLocation + "java/views/content/";
    public String cssPath = pathLocation + "ressources/css/";
    public URL appUrlPath = AppSettings.class.getClassLoader().getResource(viewsPath);
    public String appFullPath = appUrlPath.getPath().replaceFirst("^/(.:/)", "$1");

}
