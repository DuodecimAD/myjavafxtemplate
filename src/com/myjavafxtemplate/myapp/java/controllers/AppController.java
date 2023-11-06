package com.myjavafxtemplate.myapp.java.controllers;

import com.myjavafxtemplate.myapp.java.utility.AppPaths;
import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppController {
    @FXML private BorderPane body;
    @FXML private BorderPane menuPane;

    static List<Button> buttonsMenu = new ArrayList<Button>(); 

    @FXML
    public void initialize() {
    	LoggerUtil.getLogger().info("Initialize method called");

    	setMenu();
    	
    	/* to stop having a button focused while content is not connected to it */
    	Platform.runLater(() -> {
    		body.requestFocus();
    	});
    	
        LoggerUtil.getLogger().info("Initialize done");
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    
    private void setMenu() {
    	setMenuContentButtons();
    	setMenuSettingsButton();
    	menuButtonsAction();
    }
    
    private void setMenuContentButtons() {


        File directory = new File(AppPaths.INSTANCE.appFullPath+"content");


        File[] files = directory.listFiles((dir, name) -> name.endsWith(".fxml"));

        if (files != null) {
            
            for (File file : files) {
                Button button = new Button(file.getName().substring(0, file.getName().lastIndexOf(".")));
                
                button.setOnAction(event -> loadContent("content/"+file.getName()));
                
                buttonsMenu.add(button);
            }
            VBox menuButtons = new VBox();
            menuButtons.setId("menuButtons");
            menuButtons.getChildren().addAll(buttonsMenu);
            menuPane.setCenter(menuButtons);
            

        }else {
        	System.out.println("File isn't working, so probably within a jar : ");

        	// change to app/ for install
        	//try (JarFile jarFile = new JarFile("app/myjavafxtemplate.jar")) {
        	try (JarFile jarFile = new JarFile("myjavafxtemplate.jar")) {
        	    Enumeration<JarEntry> entries = jarFile.entries();
        	    
        	    long startTime = System.currentTimeMillis();
        	    
        	    while (entries.hasMoreElements()) {
        	        JarEntry entry = entries.nextElement();
        	        String path = entry.getName();

        	        if (path.startsWith(AppPaths.INSTANCE.appPath+"content/") && path.endsWith(".fxml")) {
        	            
        	            String fileName = path.substring((AppPaths.INSTANCE.appPath+"content/").length(), path.length() - (".fxml").length());
        	            System.out.println(fileName);
        	            Button button = new Button(fileName);
                        
                        button.setOnAction(event -> loadContent("content/"+fileName+".fxml"));
                        
                        buttonsMenu.add(button);

        	        }
        	    }
        	    long endTime = System.currentTimeMillis();
        	    long totalTime = endTime - startTime;

        	    // print the total time to the console
        	    System.out.println("Total time: " + totalTime + " milliseconds");
        	    
        	    VBox menuButtons = new VBox();
                menuButtons.setId("menuButtons");
                menuButtons.getChildren().addAll(buttonsMenu);
                menuPane.setCenter(menuButtons);
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}
        	
        }
    }

    private void setMenuSettingsButton() {
    	Button settingsButton = new Button();
        settingsButton.setText("Settings");
        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(event -> loadContent("Settings.fxml"));
        menuPane.setBottom(settingsButton);

    }
    
    public void loadContent(String fxmlName) {
        try {
        	URL pathUrl = new URL(AppPaths.INSTANCE.appUrlPath + fxmlName);
        	System.out.println("loading "+pathUrl);
            FXMLLoader loader = new FXMLLoader(pathUrl);
            VBox content = loader.load();
            content.setId("content");
            body.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuButtonsAction() {
    	
    }
    
}
