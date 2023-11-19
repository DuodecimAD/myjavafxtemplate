package com.myjavafxtemplate.myapp.java.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.myjavafxtemplate.myapp.java.models.Client;
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
    @FXML 
    private StackPane ClientBody;
	@FXML
	private TableView<Client> Table_Client;
	@FXML
	private TableColumn<Client, String> ID_Client;
	@FXML
	private TableColumn<Client, String> Name_Client;
	@FXML
	private TableColumn<Client, String> Surname_Client;
	@FXML
	private TableColumn<Client, String> Birthday_Client;
	@FXML
	private TableColumn<Client, String> Tel_Client;
	@FXML
	private TableColumn<Client, String> Email_Client;
	@FXML
    private Button createClientButton;

	
	public ClientController() {
		
	}
	
	public void initialize() {
		
		loadingTableIcon();

		// Fetch data from the database in the background
        new Thread(() -> {
            Platform.runLater(() -> updateTableView(getAllClients()));
        }).start();
		
        // When data's row is clicked, open overlay with data from that row
        openOverlayPopulateData();
        
	}
	
	
	private ObservableList<Client> getAllClients() {
	    // Get raw data from the Client model
	    List<List<Object>> rawClientData = Client.getAllClientsData();

	    // Convert raw data to ObservableList<Client>
	    ObservableList<Client> clientsObsList = FXCollections.observableArrayList();
	    for (List<Object> row : rawClientData) {
	    	BigDecimal idBigDecimal = (BigDecimal) row.get(0);
	        int id = idBigDecimal.intValue();
	        String nom = (String) row.get(1);
	        String prenom = (String) row.get(2);
	        // Convert date to java.util.Date
	        Date date_nais = (Date) row.get(3);
	        String tel = (String) row.get(4);
	        String email = (String) row.get(5);
	        // Create a Client object and add to the list
	        clientsObsList.add(new Client(id, nom, prenom, date_nais, tel, email));
	    }
	    return clientsObsList;
	}
	
	/*
	 * could have replaced previous method with this but then need to use observable list 
	 * which is javafx into the model which is better not to, but left as reference
	 *
	 * 	private ObservableList<Client> getAllClients() {
	 * 		return FXCollections.observableArrayList(Client.getAllClientsData());
	 *	}
	 *
	 */
	
	private void loadingTableIcon() {
		// Load the loading GIF
        Image loadingImage = new Image(getClass().getResourceAsStream(AppSettings.INSTANCE.imagePath+"loading.gif"));
        ImageView loadingImageView = new ImageView(loadingImage);

        // Set the loading GIF as the custom placeholder
        Table_Client.setPlaceholder(loadingImageView);
	}
	
	private void updateTableView(ObservableList<Client> allClients) {

        // Set the items with the correct data type
		Table_Client.setItems(allClients);
        
        // Populate columns of TableView with the data
        ID_Client.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getID_CLIENT())));
        Name_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNOM_CLIENT()));
        Surname_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPRENOM_CLIENT()));
        Birthday_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDATE_NAIS_CLIENT().toString()));
        Tel_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTEL_CLIENT()));
        Email_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEMAIL_CLIENT()));

    }
	
	private void openOverlayPopulateData() {
        Table_Client.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Client rowData = row.getItem();
                    createOverlay(ClientBody, rowData);
                }
            });
            return row;
        });
    }
	
	private void createOverlay(StackPane stackPane, Client rowData) {
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
        idField.setText(Integer.toString(rowData.getID_CLIENT()));
        
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        nameField.setText(rowData.getNOM_CLIENT());
        
        Label SurnameLabel = new Label("Surname");
        TextField SurnameField = new TextField();
        SurnameField.setText(rowData.getPRENOM_CLIENT());
        
        Label birthdayLabel = new Label("Birthday");
        TextField birthdayField = new TextField();
        birthdayField.setText(rowData.getDATE_NAIS_CLIENT().toString());
        
        Label telLabel = new Label("Tel");
        TextField telField = new TextField();
        telField.setText(rowData.getTEL_CLIENT());
        
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setText(rowData.getEMAIL_CLIENT());
        
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
