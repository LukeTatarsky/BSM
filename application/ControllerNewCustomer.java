package application;

/**
 * @author Frank Obedi
 * @version Apr 1, 2023
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
//import java.sql.SQLException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ControllerNewCustomer {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtEmail;	
	@FXML
	private TextField txtPhone;
	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblLastName;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblPhone;
	@FXML
    private ListView<Bike> lstBikes;
	@FXML
    private TextArea txtAreaNote;
	
	Stage stage;
	
	// Constructor
	public ControllerNewCustomer() throws ClassNotFoundException{
		
	}
	
	
	//Cancel Button
	public void cancel(ActionEvent event) throws IOException{
//		(((Node) event.getSource())).getScene().getWindow().hide();
		stage = (Stage)((Button) event.getSource()).getScene().getWindow(); // better
		stage.close();
		data.setEditingCustomer(false);
	}
	
	public boolean validator(TextField f, String type, String label) {
		type = type.toLowerCase();
		boolean valid = false;
		Matcher matcher;
		
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
		final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z\s]+", Pattern.CASE_INSENSITIVE);
		switch(type) {
			case "name":
//				valid =  ((f.getText() != null) && (!f.getText().equals(""))
//						&& (f.getText().matches("^[a-zA-Z]*$")));
				matcher = VALID_NAME_REGEX.matcher(f.getText());
				valid = (!f.getText().isBlank() && matcher.matches());
				break;
			case "email":
				matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(f.getText());
				valid = matcher.matches();
				break;
			case "phone":
				matcher = VALID_PHONE_NUMBER_REGEX.matcher(f.getText());
				valid = matcher.matches();
		}
		
		if(valid) {
			// change color of label back to white if it was turned to red
			Color color = Color.WHITE;
			changeLabelColor(color, label);
			return true;
		}					
			
		// change the label color to red to indicate error
		Color color = Color.RED;
		changeLabelColor(color, label);
		return false;
		
		 
	}
	
	public void validateCustomer() {
	
		if(txtFirstName.getText().isBlank() != false) {
			System.out.println("Customer Saved");
//			return true;
		}
		
		AnchorPane pane = new AnchorPane();
		
		for(Node node: pane.getChildren()) {
			if(node instanceof  TextField) {
				if (node.getId() == "txtFirstName") {
					System.out.println(node.getId());
				}
			}
		}
		
	}
	
	private void changeLabelColor(Color color, String label) {
		switch(label) {
			case "lblFirstName":
				lblFirstName.setTextFill(color);
				break;
			case "lblLastName":
				lblLastName.setTextFill(color);
				break;
			case "lblEmail":
				lblEmail.setTextFill(color);
				break;
			case "lblPhone":
				lblPhone.setTextFill(color);
				break;
			default: break;
		}
	}
	
	// Save new customer to DB
	public void saveCustomer(ActionEvent event) {
		data.setNewCustomer(null);
		Customer customer = new Customer();
		boolean emailValid =  false;
		boolean phoneValid = false;
		boolean fnameValid = false;
		boolean lnameValid = false;
		
		List<TextField> textFields = Arrays.asList(txtFirstName,txtLastName, txtEmail, txtPhone);
		for (TextField field : textFields) {
			int n = 3;
			String txt = field.getId();
			String replacement = "lbl";
			String label = replacement + txt.substring(n);
			// check that first and last names are alphabetic characters only
			if((field.getId().equals("txtFirstName") || field.getId().equals("txtLastName")) && validator(field, "name", label)) {				
				if(field.getId().equals("txtFirstName")) {
					fnameValid = true;
				}
				else {
					lnameValid = true;
				}
				
			}else if(field.getId().equals("txtEmail") && validator(field, "email", label)) { // validate email				
				emailValid = true;
			}
			else { // validate phone number				
				if(validator(field, "phone", label)) {
					phoneValid = true;
				}
			}
		}
		
		//Check if all fields have valid data -> when when invalidCount = 0
		if((fnameValid || lnameValid) && (emailValid || phoneValid)) {
			try {
			System.out.println("All fields valid");
			// assign the values to new customer
			customer.setFirstName(txtFirstName.getText());
			customer.setLastName(txtLastName.getText());
			customer.setEmail(txtEmail.getText());
			customer.setPhone(txtPhone.getText());
			customer.setNote(txtAreaNote.getText());
			
			// customer created successfully
			System.out.println("item created " + customer.toString());
			
			Integer newCustomerId = 0;
			
			// if editing we update, if not insert new
			if (data.getEditingCustomer()) {
				newCustomerId = data.getEditCustomer().getCustId();
				customer.setCustId(newCustomerId);
				OrderDAO.updateCustomer(customer);
				data.setEditCustomer(customer);
				// if its null, this means we opened customer data from work order.
				// we dont have an editing index that the customer table would give.
				// will need to search customer list for the customer. 
				// this can be done much better. 
				if (data.getEditingIndex() == null) {
					int i = 0;
					for (Customer cust : data.getAllCustomers()) {
						if (cust.getCustId() == customer.getCustId()) {
							data.getAllCustomers().set(i, customer);
							break;
						}
						i++;
					}
				}
				
				else {
				data.getAllCustomers().set(data.getEditingIndex(), customer);
				}
				
				data.setEditingCustomer(false);
			}
			else {
				// push item to DB Items table and get its ID
				newCustomerId = OrderDAO.insertCustomer(customer);
				
				
				// set id and add to data all customers
				customer.setCustId(newCustomerId);
				data.getAllCustomers().add(customer);
			}
			
			data.setNewCustomer(customer);
			data.setEditingIndex(null);
			
			
			// if we are creating a new order and a new customer. 
			// go directly to work order
			if (data.getNewOrder()) {			
				data.setSelectedCustomer(customer);				
				System.out.println("selected " + data.getSelectedCustomer());				
				Main m = new Main();
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
	        	m.changeTitle("Bike Shop Manager - New Work Order");
	        	Parent root = loader.load();
	        	m.changeScene(root); 
			}			
			
			// leave window			
			cancel(event);
			
			
			}catch (Exception e){
				System.out.println("Error: Unable to add new customer");

				if (data.getSqlError()) {
					Alert alert = new Alert(AlertType.ERROR, "Error: Unable to add new customer.\n" + data.getSqlErrorString(),  ButtonType.OK);
					alert.showAndWait();
					data.setSqlError(false);
				}
				e.printStackTrace();
				return;
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR, "1 or more fields highlighted in red contains invalid data, please try again",  ButtonType.OK);
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void showBikeInfo(MouseEvent event) throws IOException {
		
	
		if(event.getButton().equals(MouseButton.PRIMARY) && lstBikes.getSelectionModel().getSelectedItem() != null && lstBikes.getSelectionModel().getSelectedIndex() != 0){
			
			if( event.getClickCount() == 2){
				data.setSelectedBikeIndex(lstBikes.getSelectionModel().getSelectedIndex());
				Bike bike = new Bike();
				data.setOrderFname(data.getEditCustomer().getFirstName());
				data.setOrderLname(data.getEditCustomer().getLastName());
				bike.setBikeCustId(data.getCustId());
				bike.setBikeId(lstBikes.getSelectionModel().getSelectedItem().getBikeId());
				data.setOrderBikeObj(bike);
				// Edit Item Window
		    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("BikeInfo.fxml"));
		    	Parent rootItem = loaderItem.load();
		    	
		    	Stage stage = new Stage();
		    	stage.initModality(Modality.APPLICATION_MODAL);
//		    	stage.setResizable(false);
		    	stage.setScene(new Scene(rootItem));
		    	stage.setTitle("Bike Info");
		    	
		    	// show and wait for updated item values in data singleton
		    	stage.showAndWait();
		    	
		    	// update the bike name on the order window
		    	data.getCustomerBikes().set(data.getSelectedBikeIndex(), data.getOrderBikeObj());

			}		
					
		}
	}
	
	
	// not yet implemented
	@FXML
	public void createNewBike(ActionEvent event) throws IOException {

			data.setCreatingNewBike(true);
			data.setSelectedBikeIndex(lstBikes.getSelectionModel().getSelectedIndex());
			Bike bike = new Bike();
			data.setOrderFname(data.getEditCustomer().getFirstName());
			data.setOrderLname(data.getEditCustomer().getLastName());
			bike.setBikeCustId(data.getCustId());
//			bike.setBikeId(lstBikes.getSelectionModel().getSelectedItem().getBikeId());
			data.setOrderBikeObj(bike);
			// Edit Item Window
	    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("BikeInfo.fxml"));
	    	Parent rootItem = loaderItem.load();
	    	
	    	Stage stage = new Stage();
	    	stage.initModality(Modality.APPLICATION_MODAL);
//	    	stage.setResizable(false);
	    	stage.setScene(new Scene(rootItem));
	    	stage.setTitle("Bike Info");
	    	
	    	// show and wait for updated item values in data singleton
	    	stage.showAndWait();
	    	
	    	// update the bike name on the order window
	    	data.getCustomerBikes().set(data.getSelectedBikeIndex(), data.getOrderBikeObj());

	}
	
	// if editing customer load up their data
		public void editCustomer() throws ClassNotFoundException, SQLException {
			
			System.out.println("------------------LOAD Customer for edit------------------------------------");
			data.setCustomerBikes(OrderDAO.getCustomerBikes(data.getCustId()));
//			data.getCustomerBikes().remove(0);	
			lstBikes.setItems(data.getCustomerBikes());
			
    		txtFirstName.setText(data.getEditCustomer().getFirstName());
    		txtLastName.setText(data.getEditCustomer().getLastName());
    		txtEmail.setText(data.getEditCustomer().getEmail());
    		txtPhone.setText(data.getEditCustomer().getPhone());
			txtAreaNote.setText(data.getEditCustomer().getNote());
			
		}
	
	
	 //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () throws ClassNotFoundException, SQLException {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Employee objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe
        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
    	System.out.println("------------------ADD / EDIT CUSTOMER------------------------------------");
    	data.setCreatingNewBike(false);
    	
    	
    	if (data.getEditCustomer() != null && data.getEditingCustomer() == true) {
    		editCustomer();
    	}
       
        }


}
