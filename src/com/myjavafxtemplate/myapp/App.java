/*
 * 
 */
package com.myjavafxtemplate.myapp;

import com.myjavafxtemplate.myapp.java.utility.AppMemory;
import com.myjavafxtemplate.myapp.java.utility.AppSecurity;
import com.myjavafxtemplate.myapp.java.utility.AppSettings;
import com.myjavafxtemplate.myapp.java.utility.AppTree;
import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;
import com.myjavafxtemplate.myapp.java.utility.database.DbConnect;
import com.myjavafxtemplate.myapp.java.utility.database.DbCreate;
import com.myjavafxtemplate.myapp.java.utility.database.DbRead;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// TODO: Auto-generated Javadoc
/**
 * The Class App.
 */
public class App extends Application {
	

	/** The main fxml. */
	String mainFxml = "App.fxml";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
    	launch(args); 
        LoggerUtil.getLogger().info("Main Closing");
    }
	
    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage)  throws Exception {
    	//LoggerUtil.setupLogging();
    	LoggerUtil.getLogger().info("Start method called");
    	
		setPrimaryStage(primaryStage);
		
		Platform.runLater(() -> {
			
	        //AppTree.printScene(primaryStage.getScene());
	        //AppMemory.printMemoryUsage();
			DbConnect.sharedConnection();	        
    	});
		
		LoggerUtil.getLogger().info("start method finished");
		

		
		// dbcreate
		/*
		List<List<String>> test = new ArrayList<>(List.of(
				List.of("NOM_SPEC","PRENOM_SPEC","DATE_NAIS_SPEC", "TEL_SPEC", "EMAIL_SPEC"),
		        List.of("nomA", "PrenomA", "12/05/2000", "0445789632", "nomAPrenomA@gmail.com"),
		        List.of("nomB", "PrenomB", "07/02/1987", "0666666666", "nomBPrenomB@gmail.com"),
		        List.of("nomC", "PrenomC", "22/11/1998", "0777777777", "nomCPrenomC@gmail.com"),
		        List.of("nomD", "PrenomD", "18/08/2003", "0555555555", "nomDPrenomD@gmail.com"),
		        List.of("nomE", "PrenomE", "01/12/1978", "0444444444", "nomEPrenomE@gmail.com")
		));
		List<Integer> check = List.of(3,4);
		DbCreate.insert("SPECIALISTE", test, check);
		*/

		// dbRead
		/*
		List<String> test = List.of("ID_CLIENT");
		List<?> result = DbRead.read("CLIENT", test, "EMAIL_CLIENT", "ncxedds@gmail.com");
		System.out.println(result);
		System.out.println();
		List<List<?>> fullResult = DbRead.read("CLIENT", "ID_CLIENT");
		System.out.println(fullResult);
		 */
		
		/*
		String test = "--ty_15> ";
		
		test = AppSecurity.sanitize(test);
		System.out.println(test);
*/


    }

    /**
     * Sets the primary stage.
     *
     * @param primaryStage the new primary stage
     */
    private void setPrimaryStage(Stage primaryStage) {
    	
    	try {
    		URL pathUrl = new URL(AppSettings.INSTANCE.appUrlPath + mainFxml);
	    	FXMLLoader loader = new FXMLLoader(pathUrl);
			LoggerUtil.getLogger().info("initialize will be called now");
			Parent root = loader.load();
			LoggerUtil.getLogger().info("fxml loaded");
			root.getStylesheets().add(AppSettings.INSTANCE.cssPath+"styles.css");
			LoggerUtil.getLogger().info("css loaded");
			
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle(mainFxml);
			primaryStage.setMinWidth(1026);
			primaryStage.setMinHeight(600);
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
