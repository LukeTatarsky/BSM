package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerItemsList {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	@FXML
	 private TextField txtKeyword;

	@FXML
	private Button btnCustomers;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnItemsList;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnReturn;
    @FXML
    private MenuItem menuAddItem;

    @FXML
    private MenuItem menuDeleteItem;

    @FXML
    private MenuItem menuEditItem;

    @FXML
    private MenuItem menuReport;

	@FXML
	private TableView<Item> table;
	@FXML
	private TableColumn<Item, String> itemDesc;
	@FXML
	private TableColumn<Item, Number> itemPrice;
	
	Stage stage;


	//Constructor
		public ControllerItemsList() throws ClassNotFoundException{
			
		}

		public void returnToOrders(ActionEvent event ) throws IOException  {
			
			// if viewing items list from work orders screen then normal return.
			if (data.getAddingItemFromItemsList() == false) {
				Main m = new Main();
				m.changeTitle("Bike Shop Manager - Work Orders");
		        m.changeScene("WorkOrders.fxml");
			}
			// if adding a new item from work order, then close window return
			else {
				stage = (Stage)((Button) event.getSource()).getScene().getWindow(); // better
				stage.close();
				
			}
			

		 }
	
		 // open item editing screen with click
	    @FXML
	    private void editItem (MouseEvent actionEvent) throws Exception {
	    		    	
	    	Item selectedItem = table.getSelectionModel().getSelectedItem();
	    	Integer i = table.getSelectionModel().getSelectedIndex();
	    	data.setItemIndex(i);
	    	
	    	if(actionEvent.getButton().equals(MouseButton.PRIMARY)){
	    			
	    			if(actionEvent.getClickCount() == 2 && selectedItem != null ) {

	    			System.out.println("--editing "+ selectedItem.toString() );
	    			
	            	// saving the selected item
	            	Item item = new Item();
	            	item.setItemId(selectedItem.getItemId());
	            	item.setItemDesc(selectedItem.getItemDesc());
	            	item.setItemPrice(selectedItem.getItemPrice());
	            	item.setItemCost(selectedItem.getItemCost());
	            	item.setItemLb(selectedItem.getItemLb());
	            	data.setNewItem(item);
	            	
	            	// if we are not adding an item to a work order, then edit on double click. otherwise set the new item and return to work order
	    	    	if (data.getAddingItemFromItemsList() == false) {
		            	// Edit Item Window
		            	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemListEdit.fxml"));
		            	Parent rootItem = loaderItem.load();
		            	
		            	Stage stage = new Stage();
		            	stage.initModality(Modality.APPLICATION_MODAL);
		            	stage.setScene(new Scene(rootItem));
		            	stage.setTitle("Edit Item");
		            	
		            	// show and wait for updated item values in data singleton
		            	stage.showAndWait();
	    	    	}
	    	    	else {
	    	    		data.getNewItem().setItemQty(1);
	    	    		data.getNewItem().setItemTotal(data.getNewItem().getItemPrice());
	    	    		cancel(actionEvent);
	    	    	}
	            }
    
	    	}
	    }
	    // open item editing screen with enter
	    @FXML
	    private void editItemEnter (KeyEvent actionEvent) throws Exception {
	    		    	
	    	Item selectedItem = table.getSelectionModel().getSelectedItem();
	    	Integer i = table.getSelectionModel().getSelectedIndex();
	    	data.setItemIndex(i);
	    	if(actionEvent.getCode().equals(KeyCode.ENTER) && selectedItem != null){

	    			System.out.println("--editing "+ selectedItem.toString() );
	    			
	            	// saving the selected item
	            	Item item = new Item();
	            	item.setItemId(selectedItem.getItemId());
	            	item.setItemDesc(selectedItem.getItemDesc());
	            	item.setItemPrice(selectedItem.getItemPrice());
	            	item.setItemCost(selectedItem.getItemCost());
	            	item.setItemLb(selectedItem.getItemLb());
	            	data.setNewItem(item);
	            	
	            	// if we are not adding an item to a work order, then edit on double click. otherwise set the new item and return to work order
	    	    	if (data.getAddingItemFromItemsList() == false) {
		            	// Edit Item Window
		            	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemListEdit.fxml"));
		            	Parent rootItem = loaderItem.load();
		            	
		            	Stage stage = new Stage();
		            	stage.initModality(Modality.APPLICATION_MODAL);
		            	stage.setScene(new Scene(rootItem));
		            	stage.setTitle("Edit Item");
		            	
		            	// show and wait for updated item values in data singleton
		            	stage.showAndWait();
	    	    	}
	    	    	else {
	    	    		data.getNewItem().setItemQty(1);
	    	    		data.getNewItem().setItemTotal(data.getNewItem().getItemPrice());
	    	    		cancel(actionEvent);
	    	    	}
	            
    
	    	}
	    }
	    
	    // Create new Item
	    @FXML
	    private void newItem (ActionEvent event) throws Exception {
	    	
	    	// Edit Item Window
	    	
	    	// set that the new item is being created from the items list. to avoid a whole new controller
	    	data.setNewItemFromItemsList(true);
	    	
	    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemNew.fxml"));
	    	Parent rootItem = loaderItem.load();
	    	
	    	Stage stage = new Stage();
	    	stage.initModality(Modality.APPLICATION_MODAL);
	    	stage.setScene(new Scene(rootItem));
	    	stage.setTitle("Create New Item");
	    	
	    	// show and wait for updated item values in data singleton
	    	stage.showAndWait();
	    	
	    	data.setNewItemFromItemsList(false);
	    	
	    	if (data.getNewItem() != null) {
	    		System.out.println("new item " + data.getNewItem().toString());
	    	}
	    }
	    // open item editing screen
	    @FXML
	    private void editItemBtn (ActionEvent event) throws Exception {
	    	Item selectedItem = table.getSelectionModel().getSelectedItem();
	    	Integer i = table.getSelectionModel().getSelectedIndex();
	    	data.setItemIndex(i);
	    	System.out.println("--edit btn  ");
    			if(selectedItem != null ) {
    			
    				// saving the selected item
	            	Item item = new Item();
	            	item.setItemId(selectedItem.getItemId());
	            	item.setItemDesc(selectedItem.getItemDesc());
	            	item.setItemPrice(selectedItem.getItemPrice());
	            	item.setItemCost(selectedItem.getItemCost());
	            	item.setItemLb(selectedItem.getItemLb());
	            	data.setNewItem(item);
	            	
	            	
	            	// Edit Item Window
	            	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemListEdit.fxml"));
	            	Parent rootItem = loaderItem.load();
	            	
	            	Stage stage = new Stage();
	            	stage.initModality(Modality.APPLICATION_MODAL);
	            	stage.setScene(new Scene(rootItem));
	            	stage.setTitle("Edit Item");
	            	
	            	// show and wait for updated item values in data singleton
	            	stage.showAndWait();

	    	}
	    }

	public void deleteItem(ActionEvent event )  {
		if (table.getSelectionModel().getSelectedItem() != null) {
			Integer itemId = table.getSelectionModel().getSelectedItem().getItemId();
			int itemIndex = table.getSelectionModel().getSelectedIndex();
			//Warn that an item with the same name already exists.
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this item?",  ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			
			if(alert.getResult() == ButtonType.NO) {
				System.out.println("_-__ Declined to delete item in DB_-__");
				return;
			}
			
			try {
				OrderDAO.deleteItem(itemId);
				data.getAllItems().remove(itemIndex);
				data.getAllItemsString().remove(itemIndex);
				table.setItems(data.getAllItems());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
    
 // Searches for item using keyword
    @FXML
    private void searchItems (ActionEvent event) throws Exception {
    	

		if (txtKeyword.getText().isEmpty()) {
			table.setItems(data.getAllItems());
		}
		else {
			table.setItems(OrderDAO.findItems("'%" + txtKeyword.getText() + "%'"));
		}             
    }
 // Searches for item using keyword (listener)
    private void searchItems (String key) throws Exception {
    	
		if (key.isBlank()) {
			table.setItems(data.getAllItems());
		}
		else {
			table.setItems(OrderDAO.findItems("'%" + key + "%'"));
		}             
    }
    
	// exiting the window
	public void cancel(ActionEvent event ) throws IOException {
		 
		 (((Node) event.getSource())).getScene().getWindow().hide();
	 }
	
	public void cancel(Event event ) throws IOException {
		 
		 (((Node) event.getSource())).getScene().getWindow().hide();
	 }

	 
	 public void populateFields() throws ClassNotFoundException, SQLException  {
		 table.setItems(data.getAllItems());
	}
	  
	 
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
		 
//		 table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);    // interesting but this is going to make things a little more complicated...
		 itemDesc.setCellValueFactory(cellData -> cellData.getValue().itemDescProperty());
		 itemPrice.setCellValueFactory(cellData -> cellData.getValue().itemPriceProperty());
		 
		 txtKeyword.textProperty().addListener((observable, oldValue, newValue) -> {
			 try {
				searchItems(newValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			});
		 
		 populateFields();
	 }
}
