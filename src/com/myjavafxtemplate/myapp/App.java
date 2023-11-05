package com.myjavafxtemplate.myapp;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.myjavafxtemplate.myapp.java.utility.AppPaths;
import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;


public class App extends Application {
	
	
	String mainFxml = "App.fxml";

	
    @Override
    public void start(Stage primaryStage)  throws Exception {
    	LoggerUtil.getLogger().info("Starting");
    	
    	//System.out.println(System.getProperty("user.home"));
        //System.out.println(getClass().getResource("/com/myjavafxtemplate/myapp/java/views/"+mainFxml));

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(AppPaths.INSTANCE.appPath+"java/views/"+mainFxml));
			LoggerUtil.getLogger().info("initialize will be called now");
			Parent root = loader.load();
			LoggerUtil.getLogger().info("fxml loaded");
			root.getStylesheets().add(AppPaths.INSTANCE.appPath+"ressources/css/styles.css");
			LoggerUtil.getLogger().info("css loaded");
			//AppController controller = loader.getController();
			//Model model = new Model();
			//controller.setModel(model);
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("App.fxml");
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

    public static void main(String[] args) {
    	LoggerUtil.setupLogging();

    	launch(args); 
        
        LoggerUtil.getLogger().info("Closing");
    }
    

}
