package com.myjavafxtemplate.myapp.java.controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.myjavafxtemplate.myapp.java.utility.AppSettings;
import com.myjavafxtemplate.myapp.java.utility.database.DbConnect;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChallengeController {
	@FXML 
    private Pane ChallengeA;
	@FXML
	private Pane ChallengeB;
	
	public ChallengeController() {

	}
	
	
	public void initialize() {
		

		try {
			URL pathUrl = new URL(AppSettings.INSTANCE.appUrlPath + "content/ChallengeA.fxml");
			FXMLLoader loader = new FXMLLoader(pathUrl);
			try {
				VBox vboxA = loader.load();
				vboxA.setId("vboxA");
		        ChallengeA.getChildren().add(vboxA);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			URL pathUrl = new URL(AppSettings.INSTANCE.appUrlPath + "content/ChallengeB.fxml");
			FXMLLoader loader = new FXMLLoader(pathUrl);
			try {
				VBox vboxB = loader.load();
				vboxB.setId("vboxA");
		        ChallengeB.getChildren().add(vboxB);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		        
        
	}
	
	
	

}