package com.myjavafxtemplate.myapp.java.utility;

import java.net.URL;

public enum AppPaths {
	 INSTANCE; // Singleton instance

    public String appPath = "/com/myjavafxtemplate/myapp/";
    public URL appUrlPath = AppPaths.class.getClassLoader().getResource("com/myjavafxtemplate/myapp/");
    public String appFullPath = appUrlPath.getPath().replaceFirst("^/(.:/)", "$1");
}
