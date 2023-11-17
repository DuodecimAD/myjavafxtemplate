package com.myjavafxtemplate.myapp.java.controllers;


import java.util.Arrays;
import java.util.List;

import com.myjavafxtemplate.myapp.java.utility.database.DbRead;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;




public class ClientController {
	
	@FXML
	private TableView<List<String>> Table_Client;

    @FXML
    private TableColumn<List<String>, String> Name_Client;

    @FXML
    private TableColumn<List<String>, String> Surname_Client;

	
	public ClientController() {
		
	}
	
	public void initialize() {
		
		
		List<List<String>> fullResult = DbRead.read("CLIENT", "ID_CLIENT");
		System.out.println(fullResult);
		
	
		ObservableList<List<String>> data = FXCollections.observableArrayList();

        for (int i = 0; i < fullResult.size(); i++) {
            data.add(Arrays.asList(fullResult.get(i).get(1), fullResult.get(i).get(2)));

        }

        // Set the items with the correct data type
        Table_Client.setItems(data);

        // Map your columns to the data
        Name_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        Surname_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
	
	}
	
}
