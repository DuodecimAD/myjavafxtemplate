package com.myjavafxtemplate.myapp.java.controllers;

import java.util.List;
import com.myjavafxtemplate.myapp.java.utility.AppSettings;
import com.myjavafxtemplate.myapp.java.utility.database.DbRead;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class ClientController {
	
	/** The body. */
    @FXML private StackPane ClientBody;
	
	@FXML
	private TableView<List<String>> Table_Client;
	
	@FXML
	private TableColumn<List<String>, String> ID_Client;
    @FXML
    private TableColumn<List<String>, String> Name_Client;
    @FXML
    private TableColumn<List<String>, String> Surname_Client;
    @FXML
   	private TableColumn<List<String>, String> Birthday_Client;
    @FXML
    private TableColumn<List<String>, String> Tel_Client;
    @FXML
    private TableColumn<List<String>, String> Email_Client;
    @FXML
    private Button Update_button;

	
	public ClientController() {
		
	}
	
	public void initialize() {
		
		// Load the loading GIF
        Image loadingImage = new Image(getClass().getResourceAsStream(AppSettings.INSTANCE.imagePath+"loading.gif"));
        ImageView loadingImageView = new ImageView(loadingImage);

        // Set the loading GIF as the custom placeholder
        Table_Client.setPlaceholder(loadingImageView);
        
		// Fetch data from the database in the background
        new Thread(() -> {
            List<List<String>> fullResult = DbRead.read("CLIENT", "ID_CLIENT");
            //System.out.println(fullResult);

            // Update TableView data on the JavaFX Application Thread
            Platform.runLater(() -> updateTableView(fullResult));
        }).start();
		
		
      //  Update_button.setOnAction(event -> openOverlay(ClientBody));
        
        // Set the table row factory to handle row selection
        Table_Client.setRowFactory(tv -> {
            TableRow<List<String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    List<String> rowData = row.getItem();
                    openOverlay(ClientBody, rowData);
                }
            });
            return row;
        });
        
        
	}
	
	private void updateTableView(List<List<String>> fullResult) {
        ObservableList<List<String>> data = FXCollections.observableArrayList();

        data.addAll(fullResult);

        // Set the items with the correct data type
        Table_Client.setItems(data);

        // Map your columns to the data
        ID_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        Name_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        Surname_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        Birthday_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3).split(" ")[0]));
        Tel_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));
        Email_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(5)));        
        
    }
	
	private void openOverlay(StackPane stackPane, List<String> rowData) {
	     // Create a darkened overlay pane
		Pane overlayPane = new Pane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent black background
        overlayPane.setPrefSize(stackPane.getWidth(), stackPane.getHeight());

        // Create your content pane
        BorderPane contentPane = new BorderPane();
        contentPane.setId("overlayContentPane");
        contentPane.setPrefSize(500, 500);

        
     
        // Populate the content pane with your content (you can load it from an FXML file if needed)
        Label idLabel = new Label("ID");
        TextField idField = new TextField();
        idField.setText(rowData.get(0));
        
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        nameField.setText(rowData.get(1));
        
        Label SurnameLabel = new Label("Surname");
        TextField SurnameField = new TextField();
        SurnameField.setText(rowData.get(2));
        
        Label birthdayLabel = new Label("Birthday");
        TextField birthdayField = new TextField();
        birthdayField.setText(rowData.get(3).split(" ")[0]);
        
        Label telLabel = new Label("Tel");
        TextField telField = new TextField();
        telField.setText(rowData.get(4));
        
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setText(rowData.get(5));
        
        VBox content = new VBox();
        content.getChildren().addAll(idLabel,idField,nameLabel,nameField,SurnameLabel,SurnameField,birthdayLabel,birthdayField,telLabel,telField,emailLabel,emailField);
        
        
        Button buttonOk = new Button("ok");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
        	closeOverlay(stackPane);
        });
        
       HBox buttons = new HBox();
       buttons.getChildren().addAll(buttonOk, buttonCancel);
       
       
       contentPane.setCenter(content);
       contentPane.setBottom(buttons);
        
        
        // Ensure overlayPane is visible and on top
        overlayPane.setVisible(true);
        overlayPane.toFront();
        // Add the content pane to the overlay pane
        overlayPane.getChildren().add(contentPane);

        // Add the overlay pane to the root StackPane
        stackPane.getChildren().add(overlayPane);
        
     // Set the dimensions after the stage is shown
        contentPane.setLayoutX((stackPane.getWidth() - contentPane.getPrefWidth()) / 2);
        contentPane.setLayoutY(12.5);
        
    }
	
	private void closeOverlay(StackPane stackPane) {
        // Remove the last added overlay pane
        int lastIndex = ClientBody.getChildren().size() - 1;
        if (lastIndex >= 0) {
        	ClientBody.getChildren().remove(lastIndex);
        }
    }
}
