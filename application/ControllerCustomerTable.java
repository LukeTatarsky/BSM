package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControllerCustomerTable {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	
	
	@FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;
    
    @FXML
    private MenuItem menuEditCustomer;
    
    @FXML
    private TableView<Customer> table;
    
    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, String> colFirstName;

    @FXML
    private TableColumn<Customer, String> colLastName;

    @FXML
    private TableColumn<Customer, String> colPhone;

    

    @FXML
    private TextField txtKeyword;
	 
	//Constructor
	public ControllerCustomerTable() {
		data.setEditingCustomer(false);
		
		
	}
		
	public void cancel(ActionEvent event ) throws IOException {		 
		
		 Main m = new Main();
		 m.changeTitle("Bike Shop Manager - Work Orders");
	     m.changeScene("WorkOrders.fxml");
	}
	
	public void addCustomerBtn(ActionEvent event ) throws Exception {		 
		
		FXMLLoader loaderCustomer = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
		Parent rootCustomer = loaderCustomer.load();
		data.setEditingCustomer(false);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(rootCustomer));
		stage.setTitle("Add New Customer");
		// show and wait for updated item values in data singleton
    	stage.showAndWait();
    	
    	
    	
    	
	}
	
	public void editCustomerBtn(ActionEvent event ) throws Exception {		 
			
		if (table.getSelectionModel().getSelectedItem() != null) {
			data.setEditCustomer(table.getSelectionModel().getSelectedItem());
			data.setEditingIndex(table.getSelectionModel().getSelectedIndex());
			data.setEditingCustomer(true);
			FXMLLoader loaderCustomer = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
			Parent rootCustomer = loaderCustomer.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(rootCustomer));
			stage.setTitle("Edit Customer");
			
			// show and wait for updated item values in data singleton
	    	stage.showAndWait();
		}
	}
	// return new list of customers using keyword
	public void findCustomerBtn(ActionEvent event ) throws Exception {		 
		
		if (txtKeyword.getText().isEmpty()) {
			table.setItems(data.getAllCustomers());
		}
		else {
			table.setItems(OrderDAO.findCustomer("'%" + txtKeyword.getText() + "%'"));
		}
		
		
	}
	
	
	
	public void editCustomerMouse(MouseEvent event ) throws Exception {		 
		
		if(event.getButton().equals(MouseButton.PRIMARY) && table.getSelectionModel().getSelectedItem() != null){
			
			data.setCustId(table.getSelectionModel().getSelectedItem().getCustId());
			
			// if viewing customers and double click on customer in table then open new order
			if( event.getClickCount() == 2 && data.getNewOrder() == false){
				data.setCustId(table.getSelectionModel().getSelectedItem().getCustId());
				
				data.setEditCustomer(OrderDAO.getCustomer(data.getCustId()));
				data.setEditingIndex(table.getSelectionModel().getSelectedIndex());
				data.setEditingCustomer(true);
				FXMLLoader loaderCustomer = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
				Parent rootCustomer = loaderCustomer.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.setScene(new Scene(rootCustomer));
				stage.setTitle("Edit Customer");
				
				// show and wait for updated item values in data singleton
		    	stage.showAndWait();
			}
			
			// if creating a new order and double click on customer in table then open new order
			else if (event.getClickCount() == 2 && data.getNewOrder() == true) {
				data.setSelectedCustomer(table.getSelectionModel().getSelectedItem());
				
				System.out.println("selected " + data.getSelectedCustomer());
				
				Main m = new Main();
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
	        	m.changeTitle("Bike Shop Manager - New Work Order");
	        	Parent root = loader.load();
	        	m.changeScene(root); 
			}
	    	
		}
		
	}
	// if creating a new work order and click select button use the selected customer
	public void selectCustomerBtn(ActionEvent event ) throws Exception {		 
		
		if (table.getSelectionModel().getSelectedItem() != null) {
			data.setSelectedCustomer(table.getSelectionModel().getSelectedItem());
			
			System.out.println("selected " + data.getSelectedCustomer());
			
			Main m = new Main();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
        	m.changeTitle("Bike Shop Manager - New Work Order");
        	Parent root = loader.load();
        	m.changeScene(root); 
			
		}
		
	}
	 
	 
    
    private void showAllInit() throws SQLException, ClassNotFoundException {
        //Get all Employees information
//        	ObservableList<Customer> customers = OrderDAO.showCustomers();
		data.setAllCustomers(data.getAllCustomers());
		
		//Populate Employees on TableView
		populateFields(data.getAllCustomers());
		
//		if(data.getNewOrder()) {
//			btnSelect.setVisible(true);
//		}
    }
	 
    private void populateFields (ObservableList<Customer> customerData) throws ClassNotFoundException {
        //Set items to the employeeTable
        table.setItems(customerData);
    }
	 
 // open Work Order on double click of order in the list
    @FXML
    private void openCustomersBtn (ActionEvent event) throws Exception {

    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersTable.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Customers");
    	m.changeScene(root);               

    }
    
 // open Work Order on double click of order in the list
    @FXML
    private void openReportBtn (ActionEvent event) throws Exception {
    	

    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Reports");
    	m.changeScene(root);               
            
    	
    }
    
    // view Items List
    @FXML
    private void viewItemsList (ActionEvent event) throws Exception {
    	
    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemsList.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Items");
    	m.changeScene(root);
    	
    }
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
		
		
		 
		System.out.println("------------------LOAD CUSTOMER TABLE------------------------------------");
		if (data.getNewOrder()) {
			menuEditCustomer.setDisable(true);
		}
		// tell the columns what they should display
        colFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        showAllInit();
	 }
}
