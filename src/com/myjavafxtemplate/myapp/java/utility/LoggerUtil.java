package com.myjavafxtemplate.myapp.java.utility;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	
	private static Logger logger = Logger.getLogger(LoggerUtil.class.getName());;

    public static Logger getLogger() {

        return logger;
    }

	
    public static void setupLogging() {
    	
    	/*
    	File logFolder = new File(appPath);
        if (!logFolder.exists()) {
            if (logFolder.mkdirs()) {
                logger.info("Log folder created");
            } else {
                logger.warning("Failed to create log folder");
            }
        }
        */
    	
        try {
        	
            // Create a file handler
        	// String fullPath = LoggerUtil.class.getClassLoader().getResource("com/myjavafxtemplate/myapp/").getPath().replaceFirst("^/(.:/)", "$1");
            FileHandler fileHandler = new FileHandler(AppPaths.INSTANCE.appFullPath+"output/log%g.log", 50000, 20, true);
            
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            // Add the handlers to the root logger
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
