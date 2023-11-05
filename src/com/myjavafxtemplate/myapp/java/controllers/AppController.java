package com.myjavafxtemplate.myapp.java.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.LoggerUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppController {
    @FXML private BorderPane body;
    @FXML private VBox menuPane;

    static List<Button> buttonsMenu = new ArrayList<Button>();

    /*private Model model;

    public void setModel(Model model) {
        this.model = model;
    }*/

    @FXML
    public void initialize() {
        //titleLabel.setText(model.getTitle());
    	LoggerUtil.getLogger().info("Initialize method called");
        setMenu();
        LoggerUtil.getLogger().info("Initialize done");
    }

    public void setMenu() {
        File directory = null;
        try {
        	//System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
            directory = new File(getClass().getResource("/com/myjavafxtemplate/myapp/java/views/content").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".fxml"));

        if (files != null) {
            
            for (File file : files) {
                Button button = new Button(file.getName().substring(0, file.getName().lastIndexOf(".")));
                button.setOnAction(event -> loadContent(
                		file.getName()
                		
                ));
                buttonsMenu.add(button);
            }
            menuPane.getChildren().addAll(buttonsMenu);
        }else {
        	System.out.println("there are no fxml to load");
        }
    }

    public void loadContent(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myjavafxtemplate/myapp/java/views/content/" + fxmlName));
            VBox content = loader.load();
            content.setId("content");
            body.setCenter(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
