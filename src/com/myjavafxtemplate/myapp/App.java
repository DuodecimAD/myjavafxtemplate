package com.myjavafxtemplate.myapp;

import com.myjavafxtemplate.myapp.java.utility.AppMemory;
import com.myjavafxtemplate.myapp.java.utility.AppPaths;
import com.myjavafxtemplate.myapp.java.utility.AppTree;
import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
	

	String mainFxml = "App.fxml";

	public static void main(String[] args) {

    	launch(args); 
        LoggerUtil.getLogger().info("Main Closing");
    }
	
    @Override
    public void start(Stage primaryStage)  throws Exception {
    	LoggerUtil.setupLogging();
    	LoggerUtil.getLogger().info("Start method called");
    	
		setPrimaryStage(primaryStage);
		
		Platform.runLater(() -> {
			System.out.println("///////////////Tree//////////////////");
	        AppTree.printScene(primaryStage.getScene());
	        System.out.println("//////////////Memory/////////////////");
	        AppMemory.printMemoryUsage();
    	});
		
		LoggerUtil.getLogger().info("start method finished");

    }

    private void setPrimaryStage(Stage primaryStage) {
    	
    	try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(AppPaths.INSTANCE.appPath+"java/views/"+mainFxml));
			LoggerUtil.getLogger().info("initialize will be called now");
			Parent root = loader.load();
			LoggerUtil.getLogger().info("fxml loaded");
			root.getStylesheets().add(AppPaths.INSTANCE.appPath+"ressources/css/styles.css");
			LoggerUtil.getLogger().info("css loaded");
			
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle(mainFxml);
			primaryStage.setMinWidth(500);
			primaryStage.setMinHeight(450);
			/*primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setResizable(false); // Prevent window resizing
			primaryStage.setMaximized(true); // Allow maximizing */
	        primaryStage.show();
	        LoggerUtil.getLogger().info("primaryStage loaded");	        

    	} catch (IOException e) {
			e.printStackTrace();
			LoggerUtil.getLogger().severe(e.getMessage());
		}
    }

}
