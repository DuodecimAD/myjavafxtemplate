package com.myjavafxtemplate.myapp.java.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import com.myjavafxtemplate.myapp.java.models.Client;
import com.myjavafxtemplate.myapp.java.utility.AppSettings;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
	
	private ObservableList<Client> clientsObsList = FXCollections.observableArrayList();
	/** The body. */
    @FXML 
    private StackPane ClientBody;
	@FXML
	private TableView<Client> Table_Client;
	@FXML
	private TableColumn<Client, String> Name_Client;
	@FXML
	private TableColumn<Client, String> Surname_Client;
	@FXML
	private TableColumn<Client, String> Date_Nais_Client;
	@FXML
	private TableColumn<Client, String> Tel_Client;
	@FXML
	private TableColumn<Client, String> Email_Client;
	@FXML
    private Button createClientButton;
	@FXML
    private TextField searchField;

	
	public ClientController() {
		
	}
	
	public void initialize() {
		
		loadingTableIcon();
		

		// Fetch data from the database in the background
        new Thread(() -> {
            Platform.runLater(() -> updateTableView(getAllClients()));
        }).start();
        
        filterTable();
		
        
        // When data's row is clicked, open overlay with data from that row
        openOverlayPopulateData();
        
        // Create a new Client
        openOverlayNewClient();
        
        
	}
	
	private ObservableList<Client> getAllClients() {
		
	    // Get raw data from the Client model
	    List<List<Object>> rawClientData = Client.getAllClientsData();

	    for (List<Object> row : rawClientData) {
	    	BigDecimal idBigDecimal = (BigDecimal) row.get(0);
	        int id = idBigDecimal.intValue();
	        String nom = (String) row.get(1);
	        String prenom = (String) row.get(2);
	     // Convert date to java.time.LocalDate
	        java.sql.Timestamp timestamp = (java.sql.Timestamp) row.get(3);
	        LocalDate date_nais = timestamp.toLocalDateTime().toLocalDate();;
	        String tel = (String) row.get(4);
	        String email = (String) row.get(5);
	        // Create a Client object and add to the list
	        clientsObsList.add(new Client(nom, prenom, date_nais, tel, email));
	    }
	    return clientsObsList;
	}
	
	
	private void loadingTableIcon() {
		// Load the loading GIF
        Image loadingImage = new Image(getClass().getResourceAsStream(AppSettings.INSTANCE.imagesPath+"loading.gif"));
        ImageView loadingImageView = new ImageView(loadingImage);

        // Set the loading GIF as the custom placeholder
        Table_Client.setPlaceholder(loadingImageView);
	}
	
	private void updateTableView(ObservableList<Client> allClients) {

        // Set the items with the correct data type
		Table_Client.setItems(allClients);
        
        // Populate columns of TableView with the data
        Name_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNOM_CLIENT()));
        Surname_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPRENOM_CLIENT()));
        Date_Nais_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDATE_NAIS_CLIENT().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        Tel_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTEL_CLIENT()));
        Email_Client.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEMAIL_CLIENT()));

    }
	
	private void openOverlayPopulateData() {
        Table_Client.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Client rowData = row.getItem();
                    openOverlayWithClientData(rowData);
                }
            });
            return row;
        });
    }
	
	private void openOverlayNewClient() {
        createClientButton.setOnMouseClicked(event -> {
        	openOverlayForNewClient();
        });
    }
	
	private void createOverlay(StackPane stackPane, Consumer<BorderPane> contentPopulationCallback) {
	     // Create a darkened overlay pane
		Pane overlayPane = new Pane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);"); // Semi-transparent black background
        overlayPane.setPrefSize(stackPane.getWidth(), stackPane.getHeight());

        // Create your content pane
        BorderPane contentPane = new BorderPane();
        contentPane.setId("overlayContentPane");
        contentPane.setPrefSize(500, 500);
        
        // Use the callback to populate the content
        contentPopulationCallback.accept(contentPane);
     
        // Ensure overlayPane is visible and on top
        overlayPane.setVisible(true);
        overlayPane.toFront();
        // Add the content pane to the overlay pane
        overlayPane.getChildren().add(contentPane);

        // Add the overlay pane to the root StackPane
        stackPane.getChildren().add(overlayPane);
        
        // Set the dimensions after the stage is shown
        contentPane.setLayoutX((stackPane.getWidth() - contentPane.getPrefWidth()) / 2);
        contentPane.setLayoutY(70);
        
    }
	
	// Example usage for creating an overlay with client data
	private void openOverlayWithClientData(Client rowData) {
	    createOverlay(ClientBody, contentPane -> populateOverlayContent(contentPane, rowData));
	}

	// Example usage for creating an overlay for a new client
	private void openOverlayForNewClient() {
	    createOverlay(ClientBody, contentPane -> populateOverlayForNewClient(contentPane));
	}
	
	// Example usage for populating content with client data
	private void populateOverlayContent(BorderPane contentPane, Client rowData) {
	    // Populate the content pane with input fields for client information
	    // Add labels, text fields, and buttons for client information input

		 // Populate the content pane with your content (you can load it from an FXML file if needed)
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        nameField.setText(rowData.getNOM_CLIENT());
        
        Label surnameLabel = new Label("Surname");
        TextField surnameField = new TextField();
        surnameField.setText(rowData.getPRENOM_CLIENT());
        
        Label date_naisLabel = new Label("Date_Nais");
        DatePicker date_naisField = new DatePicker();
        date_naisField.setValue(rowData.getDATE_NAIS_CLIENT());
        
        Label telLabel = new Label("Tel");
        TextField telField = new TextField();
        telField.setText(rowData.getTEL_CLIENT());
        
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setText(rowData.getEMAIL_CLIENT());
        
        VBox overLayContent = new VBox();
        overLayContent.setId("overLayContent");
        overLayContent.getChildren().addAll(nameLabel, nameField, surnameLabel, surnameField, date_naisLabel, date_naisField, telLabel, telField, emailLabel, emailField);
        
        Button buttonDelete = new Button("Delete");
        buttonDelete.setId("DeleteButton");
        Button buttonOk = new Button("ok");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
        	closeOverlay(ClientBody);
        });
        
        buttonOk.setOnAction(e -> {
        	// to do
        });
        
        buttonDelete.setOnAction(e -> {
        	// to do
        });
        
        HBox overlayBottomButtons = new HBox();
        overlayBottomButtons.setId("overlayBottomButtons");
        overlayBottomButtons.getChildren().addAll(buttonOk, buttonCancel);
        
        HBox overlayTopDelete = new HBox();
        overlayTopDelete.setId("overlayTopDelete");
        overlayTopDelete.getChildren().addAll(buttonDelete);
       
        contentPane.setTop(overlayTopDelete);
        contentPane.setCenter(overLayContent);
        contentPane.setBottom(overlayBottomButtons);
	}
	

	// Callback for populating content for a new client
	private void populateOverlayForNewClient(BorderPane contentPane) {
	    // Populate the content pane with input fields for a new client
	    // Add labels, text fields, and buttons for client information input
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        
        Label surnameLabel = new Label("Surname");
        TextField surnameField = new TextField();
        
        Label date_naisLabel = new Label("Date_Nais");
        //TextField date_naisField = new TextField();
        DatePicker date_naisField = new DatePicker();
        
        Label telLabel = new Label("Tel");
        TextField telField = new TextField();
        
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        
        Label errorLabel = new Label("");
        errorLabel.setId("errorLabelnewClient");
        
        VBox overLayContent = new VBox();
        overLayContent.setId("overLayContent");
        overLayContent.getChildren().addAll(nameLabel, nameField, surnameLabel, surnameField, date_naisLabel, date_naisField, telLabel, telField, emailLabel, emailField, errorLabel);
        
        Button buttonOk = new Button("ok");
        buttonOk.setOnAction(e -> {
        	String newClientOK = createNewClient(nameField.getText(), surnameField.getText(), date_naisField.getValue(), telField.getText(), emailField.getText());
        	
        	if(newClientOK == "") {
        		closeOverlay(ClientBody);
        	}else {
        		errorLabel.setText(newClientOK);
        	}
        	
        });
        
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
        	closeOverlay(ClientBody);
        });
        
        

        
        HBox overlayBottomButtons = new HBox();
        overlayBottomButtons.setId("overlayBottomButtons");
        overlayBottomButtons.getChildren().addAll(buttonOk, buttonCancel);
       
       contentPane.setCenter(overLayContent);
       contentPane.setBottom(overlayBottomButtons);
	}
	
	private String createNewClient(String nameField, String SurnameField, LocalDate date_naisField, String telField, String emailField) {

		Client newClient = new Client(nameField, SurnameField, date_naisField, telField, emailField);

		try {
			Client.insertIntoDatabase(newClient);
			System.out.println(newClient.toString() + " added to database without problem");
			getClientsObsList().add(newClient);
		} catch (SQLException e) {
			String errorMessage = e.getMessage();
	           int startIndex = errorMessage.indexOf("ORA-20001: ");
	           if (startIndex != -1) {
	               // Extract the substring from the index to the end of the line
	               int endIndex = errorMessage.indexOf('\n', startIndex);
	               String cleanErrorMessage;
	               if (endIndex != -1) {
	            	   cleanErrorMessage = errorMessage.substring(startIndex + "ORA-20001: ".length(), endIndex).trim();
	                   System.out.println(cleanErrorMessage);
	                   return cleanErrorMessage;
	               } else {
	            	   cleanErrorMessage = errorMessage.substring(startIndex + "ORA-20001: ".length()).trim();
	                   System.out.println(cleanErrorMessage);
	                   return cleanErrorMessage;
	               }
	           } else {
	               System.out.println(errorMessage);
	               return errorMessage;
	           }
			
		}
		return "";
	}
	
	public ObservableList<Client> getClientsObsList() {
        return clientsObsList;
    }
	
	
	private void closeOverlay(StackPane stackPane) {
        // Remove the last added overlay pane
        int lastIndex = ClientBody.getChildren().size() - 1;
        if (lastIndex >= 0) {
        	ClientBody.getChildren().remove(lastIndex);
        }
    }
	
	
	/* 
	 * Works out of the box, thanks chatGPT
	 */
	
	private void filterTable() {
		FilteredList<Client> filteredData = new FilteredList<>(clientsObsList, p -> true);
		
		// Add listener to the searchField text property
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Create a filtered list to apply the search
            

            // Set the predicate for the filter
            filteredData.setPredicate(client -> {
                // If filter text is empty, display all clients
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert client information to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any of the client attributes contain the filter text
                return client.getNOM_CLIENT().toLowerCase().contains(lowerCaseFilter)
                        || client.getPRENOM_CLIENT().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(client.getDATE_NAIS_CLIENT()).toLowerCase().contains(lowerCaseFilter)
                        || client.getTEL_CLIENT().toLowerCase().contains(lowerCaseFilter)
                        || client.getEMAIL_CLIENT().toLowerCase().contains(lowerCaseFilter);
            });

            // Wrap the FilteredList in a SortedList 
            SortedList<Client> sortedData = new SortedList<>(filteredData);

            // Bind the SortedList comparator to the TableView comparator
            sortedData.comparatorProperty().bind(Table_Client.comparatorProperty());

            // Set the items in the TableView with the filtered and sorted data
            Table_Client.setItems(sortedData);
            
            // Display a message when there are no items in the table
            if (sortedData.isEmpty()) {
                // Set a message in the TableView or somewhere appropriate
                // For example, assuming you have a label to display messages:
            	Table_Client.setPlaceholder(new Label("Try something else, nothing left to see here !!!"));
            }
        });
        
        // Add event handler to clear the searchField when clicked
        searchField.setOnMouseClicked(event -> {
            // Clear the searchField text
            searchField.clear();
        });
	}
}
