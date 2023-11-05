package com.myjavafxtemplate.myapp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application {
	
	@FXML private  BorderPane body;
	@FXML private Button buttonNext;
	
	static List<Button> buttonsMenu = new ArrayList<Button>();
	
	String mainFxml = "App.fxml";
	
	
    @Override
    public void start(Stage primaryStage)  throws Exception {
    	System.out.println("start");  
        System.out.println(getClass().getResource("ressources/fxml/"+mainFxml));
        
       

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ressources/fxml/"+mainFxml));
			System.out.println("initialize will be called now");
			Parent root = loader.load();
			System.out.println("fxml loaded");
			root.getStylesheets().add(getClass().getResource("ressources/css/styles.css").toString());

			primaryStage.setScene(new Scene(root));
			 
			primaryStage.setTitle("App.fxml");
			primaryStage.setMinWidth(500);
			primaryStage.setMinHeight(450);
			/*primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setResizable(false); // Prevent window resizing
			primaryStage.setMaximized(true); // Allow maximizing */
	        primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

System.out.println(primaryStage.getScene().getStylesheets().toString());
    }
    
	@FXML
	public void initialize() {
		System.out.println("Initialize method called");
		
		setMenu();
		

	}

    public static void main(String[] args) {
        
    	launch(args); 
    }
    
    @FXML
    public void buttonNextClick() {

    	//loadContent("Test.fxml");
    	
    }
    
    public void setMenu() {

        File directory = null;
		try {
			directory = new File(getClass().getResource("ressources/fxml/content").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Use forward slashes

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".fxml"));

            if (files != null) {
            	double layoutY = 50.0;
                for (File file : files) {
                    System.out.println(file);
                    Button button = new Button(file.getName().substring(0, file.getName().lastIndexOf(".")));

                    button.setLayoutY(layoutY);
                    layoutY += 40;
                    //button.setPrefWidth(150.0);
                    button.setFont(new Font(15));
                    button.setOnAction(event -> {
                        // Perform an action related to the associated file, for example, opening the file
                        // You can use the 'file' variable here to access the associated file
                    	loadContent(file.getName());
                    });
                    buttonsMenu.add(button);
                    
                }               
                VBox menuPane = new VBox();
                
                menuPane.getChildren().addAll(buttonsMenu);
                menuPane.setId("menuPane");
                //menuPane.setStyle("-fx-background-color: #27374D;-fx-padding: 0;");
                body.setLeft(menuPane);

            }
            
            
    }
    
    
    
    public void loadContent(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ressources/fxml/content/"+fxmlName));
            
           VBox test = loader.load();
           test.setId("content");
            
            body.setCenter(test);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
