package com.myjavafxtemplate.myapp.java.controllers;

import com.myjavafxtemplate.myapp.java.utility.AppPaths;
import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
        File directory = null;
        try {
        	//System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
            directory = new File(getClass().getResource(AppPaths.INSTANCE.appPath+"java/views/content").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
        	System.out.println("there are no fxml to load");
        }
    }

    private void setMenuSettingsButton() {
    	Button settingsButton = new Button();
        settingsButton.setText("Settings");
        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(event -> loadContent("settings.fxml"));
        menuPane.setBottom(settingsButton);

    }
    
    public void loadContent(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(AppPaths.INSTANCE.appPath+"java/views/" + fxmlName));
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
