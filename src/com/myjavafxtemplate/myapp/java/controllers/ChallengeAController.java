package com.myjavafxtemplate.myapp.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ChallengeAController {
    
    @FXML
    private Button ButtonAzerty;
    
    @FXML
    private Label LabelA;

    @FXML
    public void initialize() {

        ButtonAzerty.setOnMouseClicked(event -> {
            // Get the root of the current controller's scene
            Parent root = ButtonAzerty.getScene().getRoot();
            
            Label labelB = (Label) root.lookup("#LabelB");
            labelB.setText("New value from ChallengeA");
            labelB.setStyle("-fx-background-color : yellow; -fx-font-size: 40px; -fx-text-fill : red;");
        });
    }

}