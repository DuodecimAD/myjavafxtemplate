package com.myjavafxtemplate.myapp.java.utility;

import java.net.URL;

public enum AppPaths {
	 INSTANCE; // Singleton instance
	
	public String pathLocation = "com/myjavafxtemplate/myapp/";
    public String viewsLocation = "java/views/";
    public String cssLocation = "ressources/css/";
    public String appPath = pathLocation + viewsLocation;
    public String cssPath = pathLocation + cssLocation;
    public URL appUrlPath = AppPaths.class.getClassLoader().getResource(appPath);
    public String appFullPath = appUrlPath.getPath().replaceFirst("^/(.:/)", "$1");
}
