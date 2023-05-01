//add a quick search using cached row set
//only when you hit refresh does it actually query the db


package application;



import java.sql.Date;
import java.sql.SQLException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class ControllerOrderTable {

    @FXML
    private TextArea  textOrderNote;    
    @FXML
    private ListView<String>  listItems;   
    @FXML
    private TextField textOrderId;
    @FXML
    private TextField keyword;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button bSearch;
    @FXML
    private Button btnItemsList;
    @FXML
    private Button btnCustomers;
    @FXML
    private Label bikeLabel;
    @FXML
    private MenuItem menuReport;
    @FXML
    private CheckBox activeOrders;
    
    @FXML
    private TableView<Order> table;
    @FXML
    private TableColumn<Order, Date> date;
    @FXML
    private TableColumn<Order, Integer> orderId;
    @FXML
    private TableColumn<Order, Float> total;
    @FXML
    private TableColumn<Order, String> firstName;
    @FXML
    private TableColumn<Order, String> lastName;
    @FXML
    private TableColumn<Order, String> status;
    
    OrderDataSingleton data = OrderDataSingleton.getInstance();
    ObservableList<Order> orders;
    //Search all Orders
    @FXML
    private void showAll(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            orders = OrderDAO.showOrders();
            //Populate Employees on TableView
            populateTable(orders);
        } catch (SQLException e){
            throw e;
        }
    }
    
    //Search all Orders initial
    @FXML
    private void showAllInit() throws SQLException, ClassNotFoundException {
    	// this keeps the filters when moving around in different windows
    	if (data.getStartDate() != null || data.getEndDate() != null || data.getKeyword() != null || data.getActiveOrders() != null) {
	    	if (data.getStartDate() != null ) {
	    		startDate.setValue(data.getStartDate());
	    	    }
	    	if (data.getEndDate() != null ) {
	    		endDate.setValue(data.getEndDate());
	    		}
	    	if (data.getKeyword() != null ){
	    		keyword.setText(data.getKeyword());
	    	}
	    	if (data.getActiveOrders() != null) {
	    		activeOrders.setSelected(data.getActiveOrders());
	    	}
    	
    	
    	else {
    		//initialize like normal
	        try {
	            //Get all Employees information
	            orders = OrderDAO.showOrders();
	            //Populate Employees on TableView
	            
	            populateTable(orders);
	        } catch (SQLException e){
	            throw e;
	        }
    	}
    }
    }
    
  // Reset Search Fields
    @FXML
    private void reset(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        keyword.setText("");
        textOrderNote.setText("");
        startDate.setValue(null);
        endDate.setValue(null);
        listItems.getItems().clear();
        bikeLabel.setText("Bike: ");
        showAllInit();
        }
    
    
    //Populate Orders for TableView
    //@FXML
    private void populateTable (ObservableList<Order> orderData) throws ClassNotFoundException {
        //Set items to the employeeTable
        table.setItems(orderData);
    }
    
  
  // Search keyword
    @FXML
    private void searchKeyword (ActionEvent actionEvent) throws ClassNotFoundException, SQLException, Exception {
        try {
            //Get Employee information
        	String start = startDate.getEditor().getText();
        	String end = endDate.getEditor().getText();
        	String[] date;
        	Boolean activeOrd = activeOrders.isSelected();
        	
        	
        	// Rearrange date format for SQL query
        	if (start.length() >= 8) {
	        	date = start.split("/");
	        	start = date[2].concat("/" + date[0]);
	        	start = start.concat("/" + date[1]);
        	}
        	if(end.length() >= 8) {
		    	date = endDate.getEditor().getText().split("/");
		    	end = date[2].concat("/" + date[0]);
		    	int day = Integer.parseInt(date[1]) + 1;
		    	end = end.concat("/" + day);
        	}
        	
        	// FIXME need a check here. only search if valid date
        	
        	ObservableList<Order> orders = OrderDAO.search("'%" + keyword.getText() + "%'", "'" + start + "'", "'" + end + "'", activeOrd);
            //Populate TableView
            populateTable(orders);
            
            

        } catch (SQLException e) {
            e.printStackTrace();
//            textOrderNote.setText("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
//            textOrderNote.setText("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }
    // used with listener.  different concatenation for sql query
    private void searchKeyword (OrderDataSingleton  data) throws ClassNotFoundException, SQLException, Exception {
        try {
            //Get information
        	Boolean activeOrd = activeOrders.isSelected();
//        	System.out.println(activeOrd);
        	String start = "";
        	String end = "";
        	if (data.getStartDate() != null) {
        		start = data.getStartDate().toString();
        	}
        	if (data.getEndDate() != null) {
        		end = data.getEndDate().toString();
        	}

        	String[] date;
        	
        	System.out.println(start);
        	System.out.println(end);
        	// Rearrange date format for SQL query
        	if (start.length() >= 8) {
	        	date = start.split("-");
	        	start = date[0].concat("/" + date[1]);
	        	start = start.concat("/" + date[2]);
        	}
        	if(end.length() >= 8) {
        		date = end.split("-");
        		end = date[0].concat("/" + date[1]);
	        	end = end.concat("/" + date[2]);
        	}
        	
        	// FIXME need a check here. only search if valid date
        	
        	this.orders = OrderDAO.search("'%" + keyword.getText() + "%'", "'" + start + "'", "'" + end + "'", activeOrd);
            //Populate TableView
            populateTable(orders);
            
            

        } catch (SQLException e) {
            e.printStackTrace();
//            textOrderNote.setText("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
//            textOrderNote.setText("Error occurred while getting information from DB.\n" + e);
            throw e;
        }
    }
 // open Work Order on double click of order in the list
    @FXML
    private void openWorkOrder (MouseEvent actionEvent) throws Exception {
    	
    	if(actionEvent.getButton().equals(MouseButton.PRIMARY) && table.getSelectionModel().getSelectedItem() != null){
            if( actionEvent.getClickCount() == 1){
		    	ObservableList<String> orders = FXCollections.observableArrayList();
		    	
		    	
		    	// set selected order
		    	data.setOrderId(table.getSelectionModel().getSelectedItem().getOrderId());
//		    	data.setSelectedOrderIndex(table.getSelectionModel().getSelectedIndex());
//		    	data.setSelectedOrder(table.getSelectionModel().getSelectedItem());

            }
            else if(actionEvent.getClickCount() == 2 ) {
            	System.out.println("------------------OPEN ORDER------------------------------------");
            	
            	Main m = new Main();
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
            	Parent root = loader.load();
            	m.changeTitle("Bike Shop Manager - Edit Work Order");
            	m.changeScene(root);               
            }
    	}
    	
    }
    
 // open Work Order on double click of order in the list
    @FXML
    private void openWorkOrderBtn (ActionEvent event) throws Exception {
    	
    	if(table.getSelectionModel().getSelectedItem() != null){

	    	data.setOrderId(table.getSelectionModel().getSelectedItem().getOrderId());
	    	data.setCustId(table.getSelectionModel().getSelectedItem().getOrderId());
        	Main m = new Main();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
        	Parent root = loader.load();
        	m.changeTitle("Bike Shop Manager - Edit Work Order");
        	m.changeScene(root);               
            
    	}
    }
 // open Work Order on double click of order in the list
    @FXML
    private void openWorkOrderEnter (KeyEvent event) throws Exception {
    	
    	if(table.getSelectionModel().getSelectedItem() != null && event.getCode() == KeyCode.ENTER){

	    	data.setOrderId(table.getSelectionModel().getSelectedItem().getOrderId());
	    	data.setCustId(table.getSelectionModel().getSelectedItem().getOrderId());
        	Main m = new Main();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditOrder.fxml"));
        	Parent root = loader.load();
        	m.changeTitle("Bike Shop Manager - Edit Work Order");
        	m.changeScene(root);               
            
    	}
    }
    
    // open new Work Order 
    @FXML
    private void newWorkOrderBtn (ActionEvent event) throws Exception {
    	
    	// this lets Customer table know we are creating a new order
    	data.setNewOrder(true);
    	
    	// first select a customer
    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersTable.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Select Customer");
    	m.changeScene(root); 
   	
    }
    // delete Work Order 
    @FXML
    private void deleteWorkOrderBtn (ActionEvent event) throws Exception {
    	
    	if(table.getSelectionModel().getSelectedItem() != null){
    		
    		Alert alert = new Alert(AlertType.WARNING, String.format("Are you sure you want to delete work order?\n Id: %s \n Name: %s %s", 
    				table.getSelectionModel().getSelectedItem().getOrderId(),
    				table.getSelectionModel().getSelectedItem().getFirstName(),
    				table.getSelectionModel().getSelectedItem().getLastName()),  ButtonType.YES, ButtonType.NO);
 			alert.showAndWait();
 			
	 		if (alert.getResult() == ButtonType.YES) {
	 			data.setOrderId(table.getSelectionModel().getSelectedItem().getOrderId());	    	
		    	OrderDAO.deleteOrder(data);	    	
		    	showAllInit(); 		    
	 		}
	    	
    	}

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
    	System.out.println("------------------LOAD ORDER TABLE------------------------------------");
    	
    	data.setNewOrder(false);
//    	data.setOrderId(null);
        date.setCellValueFactory(cellData -> cellData.getValue().OrderDateProperty());
        orderId.setCellValueFactory(cellData -> cellData.getValue().OrderIdProperty());
        total.setCellValueFactory(cellData -> cellData.getValue().OrderTotalProperty());
        firstName.setCellValueFactory(cellData -> cellData.getValue().FirstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
//        phone.setCellValueFactory(cellData -> cellData.getValue().PhoneProperty());
//        email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().OrderStatusProperty());
        
        // only show active orders by default
        if (data.getActiveOrders() == null) {
	        activeOrders.setSelected(true);
	        data.setActiveOrders(true);
        }
        
        
        
        // Add listeners for Date and keyword and activeWorkOrders
        startDate.valueProperty().addListener((ov, oldValue, newValue) -> {
			try {
				data.setStartDate(newValue);
				searchKeyword(data);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        endDate.valueProperty().addListener((ov, oldValue, newValue) -> {
			try {
				data.setEndDate(newValue);
				searchKeyword(data);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        keyword.textProperty().addListener((ov, oldValue, newValue) -> {
			try {
				data.setKeyword(newValue);
				searchKeyword(data);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        activeOrders.selectedProperty().addListener((ov, oldValue, newValue) -> {
			try {
				data.setActiveOrders(newValue);
				searchKeyword(data);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
       
        // show all orders
        
        try {
        	
        	System.out.println("call to show all init");
        	showAllInit();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        	 textOrderNote.setText("Error occurred while getting information from DB.\n" + e);
        	 throw e;
        }
        
        
        

        if (this.orders == null) {
        	try {
				searchKeyword(data);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
     // keep the previously selected order selected after returning from editing
        if (data.getOrderId() != null) {
        	int i = 0;
        	for ( Order order : this.orders) {
        		if (order.getOrderId() == data.getOrderId()) {
        			table.getSelectionModel().select(order);
        			table.scrollTo(i);
        			break;
        		}
        		i++;
        	}
        }
        
    }
    
    
    
}
