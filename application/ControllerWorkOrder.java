package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerWorkOrder {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	
	
	@FXML
    private MenuItem menuBikeInfo;
	 @FXML
	 private Button btnCancel;
	 @FXML
	 private Button btnBikeInfo;
	 @FXML
	 private Button btnSaveOrder;
	 @FXML
	 private Button btnCustomers;
	 @FXML
	 private Button btnReports;
	 @FXML
	 private Button btnCustomerInfo;
	 @FXML
	 private TextField txtBike;
	 @FXML
	 private TextArea txtComment;
	 @FXML
	 private DatePicker dateBox;
	 @FXML
	 private ChoiceBox<String> cbStatus;
//	 @FXML
//	 private ComboBox<String> cbAllItems;
	 @FXML
	 private ChoiceBox<Bike> cbBikes;
	 @FXML
	 private Label lblCustName;
	 @FXML
	 private Label lblOrderTotal;
	 @FXML
	 private Label lblItemInList;
	 @FXML
	 private CheckBox chkPrivNote;
	 @FXML
	 private TextArea txtPrivNote;
	 @FXML
	 private Button btnAddItem;
	 @FXML
	 private Button btnRemoveItem;
	 @FXML
	 private Button btnNewItem;
	 @FXML
	 private VBox vBoxItemBtns;
	 @FXML
	 private TableView<Item> tblItems;
	 @FXML
	 private TableColumn<Item, String> colDesc;
	 @FXML
	 private TableColumn<Item, Number> colPrice;
	 @FXML
	 private TableColumn<Item, Number> colQty;
	 @FXML
	 private TableColumn<Item, Number> colTotal;
	 @FXML
	 private CheckBox receipt_mode;


	private ObservableList<Item> allItems;
	private List<Integer> removedItemsId;
	private Boolean itemsEdited;
	
	ObservableList<String> orderInfo;


//	private Boolean safeToSave;
	 
	//Constructor
		public ControllerWorkOrder() throws ClassNotFoundException, SQLException {
			
//			allItems = data.getAllItems();
			
//			cbBikes.setItems();
			removedItemsId = new ArrayList<Integer>();
			itemsEdited = false;
//			safeToSave = false;
//			keys = data.getStatusKeys();
			
			 



//			this.note = new SimpleStringProperty();
		}
	public int containsItem(ObservableList<Item> Items, Item item) {
		System.out.println("_Contains check__");
		 int i = 0;
		 	for (Item itemInList : Items) {
		 		if (itemInList.compareId(itemInList, item) == 0) {
		 			return i;
		 		}
		 		i++;
		 	}
		 	i = -1;
		 	return i;
	    	
	    }
	 public void cancel(ActionEvent event ) throws IOException {
		 data.setNewOrder(false);
		
		 Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to discard changes?",  ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
		    return;
		}
		else {
			data.setBikeId(null);
	    	Main m = new Main();
	    	m.changeTitle("Bike Shop Manager - Work Orders");
	        m.changeScene("WorkOrders.fxml");
		}
	 }
	 
	 public void removeItem(ObservableList<Item> Items, Item item) {
		 int i = 0;
		 	for (Item itemInList : Items) {
		 		if (itemInList.compareId(itemInList, item) == 0) {
		 			System.out.println("___________");
		 			Items.remove(i);
		 			return;
		 		}
		 		i++;
		 	}
	    	
	    }
	 
	public void showBikeInfo(ActionEvent event ) throws ClassNotFoundException, IOException, SQLException {
		
		if (data.getBikeId() != null) {
		
			Bike bike = new Bike();
			bike.setBikeCustId(data.getCustId());
			bike.setBikeId(data.getBikeId());
			data.setOrderBikeObj(bike);
			// Edit Item Window
	    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("BikeInfo.fxml"));
	    	Parent rootItem = loaderItem.load();
	    	
	    	Stage stage = new Stage();
	    	stage.setScene(new Scene(rootItem));
	    	stage.setTitle("Bike Info");
//	    	stage.setResizable(false);
	    	// show and wait for updated item values in data singleton
	    	stage.showAndWait();
	    	
	    	// update the bike name on the order window
	    	data.getCustomerBikes().set(data.getSelectedBikeIndex(), data.getOrderBikeObj());
	    	cbBikes.getSelectionModel().clearAndSelect(data.getSelectedBikeIndex());
		}
	}
	 
	
	
	 public void saveOrderBtn(ActionEvent event ) throws ClassNotFoundException, IOException, SQLException {
		 
		 if (data.getNewOrder()) {
			 saveOrderNew(event);
		 }
		 else {
			 saveOrder(event);
		 }
		 
	 }
	 
	 public void saveOrderNew(ActionEvent event ) throws IOException, ClassNotFoundException {
			System.out.println("\n------------------SAVING New ORDER ------------------------------------");
			

			data.setOrderStatus(cbStatus.getValue());

			data.setOrderBike(txtBike.getText());
			
			if (cbBikes.getSelectionModel().getSelectedIndex() == 0) {
				data.setOrderBike(txtBike.getText());
				if (txtBike.getText().isBlank()) {
					Alert alert = new Alert(AlertType.ERROR, "Please enter new bike name.",  ButtonType.OK);
					alert.showAndWait();
					return;
				}
				try {
					OrderDAO.insertNewBike(data);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					if (data.getSqlError()) {
			    		Alert alert = new Alert(AlertType.ERROR, "Error: Unable to add new bike.\n" + data.getSqlErrorString(),  ButtonType.OK);
						alert.showAndWait();
			    		
			    		data.setSqlError(false);
			    	}
					e.printStackTrace();
				}
				
				
			}
			else {
				data.setOrderBike(cbBikes.getSelectionModel().getSelectedItem().getBikeName());
				data.setBikeId(cbBikes.getSelectionModel().getSelectedItem().getBikeId());
			}
			
			
			
			data.setOrderNote(txtComment.getText());
			data.setOrderPrivNote(txtPrivNote.getText());

			if (dateBox.getValue() != null) {				
				data.setOrderDate(dateBox.getValue().toString());
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, "Date cannot be empty.",  ButtonType.OK);
					alert.showAndWait();
				if (alert.getResult() == ButtonType.OK) {
				    dateBox.setValue(LocalDate.parse(data.getOrderDate()));
				    return;
				}
			}
			
			data.setOrderFname(data.getSelectedCustomer().getFirstName());
			data.setOrderLname(data.getSelectedCustomer().getLastName());
			data.setOrderStatusKey(data.getStatusKeys().get(cbStatus.getSelectionModel().getSelectedIndex()));
			
			
			try {
				OrderDAO.insertOrder(data);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.setNewOrder(false);
			
			//switch window
			Main m = new Main();
	    	m.changeTitle("Bike Shop Manager - Work Orders");
	        m.changeScene("WorkOrders.fxml");
			
	 }

	
	 
	 public void saveOrder(ActionEvent event ) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("\n------------------SAVING ORDER after edit------------------------------------");
		
		
			// delete removed items from db
			if (removedItemsId.size() > 0 && itemsEdited == false) {
				OrderDAO.removeItems(data.getOrderId(), removedItemsId);
			}

			
			// if new bike then it must be inserted into db and data.bikeId needs to be set
			if (cbBikes.getSelectionModel().getSelectedIndex() == 0) {
				int count = 0;
				
				if (txtBike.getText().isBlank()) {
					Alert alert = new Alert(AlertType.ERROR, "Please enter new bike name.",  ButtonType.OK);
					alert.showAndWait();
					return;
				}
				// check if bike already exists
				for (Bike b : data.getCustomerBikes()) {
					System.out.println(b.getBikeName() + " " + txtBike.getText());
					System.out.println(b.getBikeName().compareTo(txtBike.getText()));
					if (b.getBikeName().compareTo(txtBike.getText()) == 0) {
						Alert alert = new Alert(AlertType.ERROR, "Bike already exists.",  ButtonType.OK);
						alert.showAndWait();
						break;
					}
					else {
						count++;
					}
				}				
				if (count == data.getCustomerBikes().size()) {
					data.setOrderBike(txtBike.getText());
					OrderDAO.insertNewBike(data);
					System.out.println("added bike : " + data.getOrderBike() + " with bikeId :" + data.getBikeId());
				}
				else {
					return;
				}
				
			}
			else {
				data.setOrderBike(cbBikes.getSelectionModel().getSelectedItem().toString());
				data.setBikeId(cbBikes.getSelectionModel().getSelectedItem().getBikeId());
//				System.out.println(data.getOrderBike() + data.getBikeId().toString());
			}
			
			
			
			if (dateBox.getValue() != null) {
				
				data.setOrderDate(dateBox.getValue().toString());
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, "Date cannot be empty.",  ButtonType.OK);
					alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
				    dateBox.setValue(LocalDate.parse(data.getOrderDate()));
				    return;
				}
			}
			
			data.setOrderStatus(cbStatus.getValue());
			data.setOrderNote(txtComment.getText());
			data.setOrderPrivNote(txtPrivNote.getText());
			
			
//			String name = lblCustName.getText();
//			String nameA[] = name.split(" ");
			
			// ohhh boy..
			if (data.getOrderFname() == null) {
				data.setOrderFname(data.getEditCustomer().getFirstName());
				data.setOrderLname(data.getEditCustomer().getLastName());
			}
			data.setOrderStatusKey(data.getStatusKeys().get(cbStatus.getSelectionModel().getSelectedIndex()));
			
			// if Items edited, then run special update Order query with a loop for the items.
			// otherwise only run regular update order query
			if (itemsEdited) {
				DButils.dbExecuteUpdateOrderAndItems(data);
			}
			else {
				OrderDAO.updateOrder(data);
			}
			
			// switch window
			data.setBikeId(null);
			Main m = new Main();
	    	m.changeTitle("Bike Shop Manager - Work Orders");
	        m.changeScene("WorkOrders.fxml");

		 }
	 
	 public void populateFields() throws IOException, ClassNotFoundException, SQLException {
		 
		 orderInfo = OrderDAO.getOrderInfo(data.getOrderId());
//		 this.bikes = OrderDAO.getCustomerBikes(data.getCustId());
		 data.setCustomerBikes(OrderDAO.getCustomerBikes(Integer.parseInt(orderInfo.get(9))));
		 data.setOrderItems(OrderDAO.getOrderItems(data.getOrderId()));
		 
//		 System.out.println(orderInfo.toString());
//		 int i = 0;
//		 for ( i = 0; i< orderInfo.size();i++) {
//			 System.out.println(i + "  " + orderInfo.get(i));
//		 }
		 
		 // Date
		 String date = orderInfo.get(0);
		 dateBox.setValue(LocalDate.parse(date.substring(0, 10)));
		 data.setOrderDate(date.substring(0, 10));
		 
		 // Name
		 lblCustName.setText(orderInfo.get(1) + " " + orderInfo.get(10));
		 data.setOrderFname(orderInfo.get(1));
		 data.setOrderLname(orderInfo.get(10));
		 
		 
		 // Status
		 data.setOrderStatus(orderInfo.get(4));
		 cbStatus.setItems(data.getStatuses());
		 cbStatus.setValue(orderInfo.get(4));
		 data.setOrderStatusKey(orderInfo.get(5)); 
		 

		// add bikes combobox
		 cbBikes.setItems(data.getCustomerBikes());
		 data.setBikeId(Integer.parseInt(orderInfo.get(7)));
		 
		 Bike currentBike = null;
		 Integer i = 0;
		 for (Bike b : data.getCustomerBikes()) {
			 if (b.getBikeId().compareTo(data.getBikeId()) == 0 ){
				 currentBike = b;
				 data.setSelectedBikeIndex(i);
				 data.setOrderBike(b.getBikeName());
				 break;
			 }
			 i++;
		 }

		 cbBikes.getSelectionModel().select(i);
		 
		 // temporary work around
		 if ( orderInfo.get(7) == null) {
			 data.setBikeId(0);
		 }
		 		 
		 // Comment
		 txtComment.setText(orderInfo.get(8));
		 data.setOrderNote(orderInfo.get(8));
		 
		 txtPrivNote.setText(orderInfo.get(11));
		 data.setOrderPrivNote(orderInfo.get(11));
		 
		 // change the text colour of the checkbox is there is some private note
		 if (txtPrivNote.getText() != null && txtPrivNote.getText().isEmpty() == false) {
			 chkPrivNote.setStyle("-fx-text-fill: orange; -fx-font-size: 16.5px;");
		 }
		 
		 // Cust ID
		 data.setCustId(Integer.parseInt(orderInfo.get(9)));
		 
		 
		 
		 
		 // Table Setup
		 colDesc.setCellValueFactory(cellData -> cellData.getValue().itemDescProperty());
		 colPrice.setCellValueFactory(cellData -> cellData.getValue().itemPriceProperty());
		 colQty.setCellValueFactory(cellData -> cellData.getValue().itemQtyProperty());
		 colTotal.setCellValueFactory(cellData -> cellData.getValue().itemTotalProperty());

		 tblItems.setItems(data.getOrderItems());
		 
		 
		 lblOrderTotal.setText(String.format("%.2f", data.getOrderTotal()));
	    	
	    }
	 
	 // when you create a new work order
	 public void populateFieldsNew() throws IOException, ClassNotFoundException, SQLException {
		 
		 data.setCustomerBikes(OrderDAO.getCustomerBikes(data.getSelectedCustomer().getCustId()));
		 data.setOrderItems(FXCollections.observableArrayList());
		 data.setOrderTotal(0f);
		 
		 // Date
		 dateBox.setValue(LocalDate.now());
		 data.setOrderDate(LocalDate.now().toString());
		 
		 // Name
		 lblCustName.setText(data.getSelectedCustomer().getFirstName() + " " + data.getSelectedCustomer().getLastName());
		 data.setOrderFname(data.getSelectedCustomer().getFirstName());
		 data.setOrderLname(data.getSelectedCustomer().getLastName());
		 
		 // Status
		 data.setOrderStatus(data.getStatuses().get(0));
		 cbStatus.setItems(data.getStatuses());
		 cbStatus.setValue(data.getStatuses().get(0));
		 data.setOrderStatusKey(data.getStatusKeys().get(0)); 
		 
		 // Add items combobox
//		 cbAllItems.setItems(data.getAllItemsString());
		 
		 // add bikes combobox
		 cbBikes.setItems(data.getCustomerBikes());
		 cbBikes.setValue(data.getCustomerBikes().get(0));
		 
		 // Bike
		 txtBike.setText("");
		 
//		 data.setBikeId(Integer.parseInt(orderInfo.get(7)));
		 
		 // Comment
		 txtComment.setText("");
		 
		 // Cust ID
		 data.setCustId(data.getSelectedCustomer().getCustId());
 
		 // Table Setup
		 colDesc.setCellValueFactory(cellData -> cellData.getValue().itemDescProperty());
		 colPrice.setCellValueFactory(cellData -> cellData.getValue().itemPriceProperty());
		 colQty.setCellValueFactory(cellData -> cellData.getValue().itemQtyProperty());
		 colTotal.setCellValueFactory(cellData -> cellData.getValue().itemTotalProperty());

		 tblItems.setItems(data.getOrderItems());
		 
		 lblOrderTotal.setText(String.format("%.2f", data.getOrderTotal()));
	    	
	    }
	 
	
 // open item editing screen
    @FXML
    private void editItem (MouseEvent actionEvent) throws Exception {
    	Item selectedItem = tblItems.getSelectionModel().getSelectedItem();
    	Integer i = tblItems.getSelectionModel().getSelectedIndex();
    	
    	if(actionEvent.getButton().equals(MouseButton.PRIMARY)){
    			
    			if(actionEvent.getClickCount() == 2 && selectedItem != null ) {
            	
//            	System.out.println("item Selected double click");
            	
            	// saving the selected item
            	Item item = new Item();
            	item.setItemId(selectedItem.getItemId());
            	item.setItemDesc(selectedItem.getItemDesc());
            	item.setItemPrice(selectedItem.getItemPrice());
            	item.setItemQty(selectedItem.getItemQty());
            	item.setItemLb(selectedItem.getItemLb());
            	item.setItemCost(selectedItem.getItemCost());
            	item.setItemTotal(item.getItemPrice() * item.getItemQty());
            	data.setNewItem(item);
            	
            	
            	// Edit Item Window
            	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemEdit.fxml"));
            	Parent rootItem = loaderItem.load();
            	
            	Stage stage = new Stage();
            	stage.initModality(Modality.APPLICATION_MODAL);
            	stage.setScene(new Scene(rootItem));
            	stage.setTitle("Edit Item");
            	
            	// show and wait for updated item values in data singleton
            	stage.showAndWait();
            	
            	lblOrderTotal.setText(String.format("%.2f",data.getOrderTotal())); 
            	
            	data.getOrderItems().set(i,data.getNewItem());
            	
            	itemsEdited = true;
	
            	System.out.println("back in work order " + data.getNewItem().toString());
            	
            }
            
            else if (actionEvent.getClickCount() == 2 && selectedItem == null) {
            	System.out.println("Can implement add item function");
            }
    	}
    }
    
 // add Item to table
//    @FXML
//    private void addItem (ActionEvent event) throws Exception {
//    	int i = cbAllItems.getSelectionModel().getSelectedIndex();
//    	if ( i == -1) {
//    		return;
//    	}
//    	data.setSelectedCbItem(data.getAllItems().get(i));
//    	data.getAllItems().get(i).setItemQty(1);
//    	data.getAllItems().get(i).setItemTotal(data.getAllItems().get(i).getItemPrice());
//    	
//    	data.getSelectedCbItem().setItemQty(1);
//
//    	
//    	int itemIndex = this.containsItem(data.getOrderItems(), data.getSelectedCbItem());
//    	if (itemIndex != -1) {
//    		lblItemInList.setVisible(true);
//    		return;
////    		data.getOrderItems().get(itemIndex).setItemQty(data.getOrderItems().get(itemIndex).getItemQty() + 1);
//    	}
//    	else {
//    		lblItemInList.setVisible(false);
//    		data.setOrderTotal(data.getOrderTotal() + data.getSelectedCbItem().getItemTotal());
//    		
////    		data.getOrderItems().add(data.getSelectedCbItem());
//    		data.getOrderItems().add(data.getAllItems().get(i));
//    		
//    		System.out.println("adding " + data.getAllItems().get(i));
//    		
//    		lblOrderTotal.setText(String.format("%.2f",data.getOrderTotal())); 
//    		
//    		itemsEdited = true;
//    	}
//
//    }
    
 // add Item to table
    @FXML
    private void addItemFromItemsList (ActionEvent event) throws Exception {
    	
    	data.setNewItem(null);
    	// Item Window
    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemsListAddItemToOrder.fxml"));
    	Parent rootItem = loaderItem.load();    	
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.setScene(new Scene(rootItem));
    	stage.setTitle("Select Item");    	
    	
    	// show and wait for updated item values in data singleton
    	data.setAddingItemFromItemsList(true);
    	stage.showAndWait();
    	data.setAddingItemFromItemsList(false);
    	
    	if (data.getNewItem() != null) {
	    	// check if item is already in the list
	    	int itemIndex = this.containsItem(data.getOrderItems(), data.getNewItem());
	    	if (itemIndex != -1) {
	    		lblItemInList.setVisible(true);
	    		return;
	    	}
	    	// if not in list then add to order items
	    	else {
	    		itemsEdited = true;
	    		lblItemInList.setVisible(false);
	    		data.getOrderItems().add(data.getNewItem());
	    		data.setOrderTotal(data.getOrderTotal()+data.getNewItem().getItemTotal());
	    		lblOrderTotal.setText(String.format("%.2f",data.getOrderTotal())); 
	    	}
    	}

    }
    // Create new Item
    @FXML
    private void newItem (ActionEvent event) throws Exception {
    	
    	// Edit Item Window
    	FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("ItemNew.fxml"));
    	Parent rootItem = loaderItem.load();
    	
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.setScene(new Scene(rootItem));
    	stage.setTitle("Create New Item");
    	
    	// show and wait for updated item values in data singleton
    	stage.showAndWait();
    	
    	itemsEdited = true;
    	// if item was added successfully then update the items selection box and add it to table
    	lblOrderTotal.setText(String.format("%.2f",data.getOrderTotal())); 
    	if (data.getNewItem() != null) {
    		
//    		cbAllItems.setItems(data.getAllItemsString());
    		tblItems.refresh();
    		System.out.println("new item " + data.getNewItem().toString());
    	}
    	
    }
    public void deleteItem(ActionEvent event ) throws IOException, ClassNotFoundException, SQLException {
		 
	 	if (tblItems.getSelectionModel().getSelectedItem() != null) {
		 	Integer itemId = tblItems.getSelectionModel().getSelectedItem().getItemId();
		 	Integer itemNum = tblItems.getSelectionModel().getSelectedIndex();

		 	
		 	 data.setOrderTotal(data.getOrderTotal() - data.getOrderItems().get(itemNum).getItemTotal());
		 	 removedItemsId.add(data.getOrderItems().get(itemNum).getItemId());
		 	 data.getOrderItems().remove(data.getOrderItems().get(itemNum));
			 
			 tblItems.setItems(data.getOrderItems());
			 
			 
			 lblOrderTotal.setText(String.format("%.2f",data.getOrderTotal()));
	 	}

    }
    public void bikeSelection(Bike newValue) {
    	if (newValue != null) {
	    	if (newValue.getBikeName().equalsIgnoreCase("New Bike")) {
	    		txtBike.setVisible(true);
	    		menuBikeInfo.setDisable(true);
	    		txtBike.clear();
	    		data.setBikeId(null);
	    		btnBikeInfo.setDisable(true);
	    	}
	    	else {
	    		txtBike.setVisible(false);
	    		menuBikeInfo.setDisable(false);
	    		txtBike.setText(newValue.getBikeName());
	    		data.setBikeId(newValue.getBikeId());
	    		btnBikeInfo.setDisable(false);
	    		data.setSelectedBikeIndex(cbBikes.getSelectionModel().getSelectedIndex());	
	    	}
    	}
    	
    	
    }
    // open Work Order on double click of order in the list
    @FXML
    private void openCustomersBtn (ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to discard changes?",  ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
		    return;
		}

    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersTable.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Customers");
    	m.changeScene(root);               

    }
    
 // show customer information
    @FXML
    private void showCustomerInfo (ActionEvent event) throws Exception {
    	Integer originalBikeSelection = cbBikes.getSelectionModel().getSelectedIndex();
    	// if we are editing an existing order, we have an order Id
    	if (data.getNewOrder() == false) {
//			ObservableList<String> orderInfo = OrderDAO.getOrderInfo(data.getOrderId());
			// set customer
//			 Customer orderCustomer = new Customer();
//			 orderCustomer.setCustId(data.getCustId());
//			 orderCustomer.setEmail(orderInfo.get(3));
//			 orderCustomer.setFirstName(orderInfo.get(1));
//			 orderCustomer.setLastName(orderInfo.get(10));
//			 orderCustomer.setPhone(orderInfo.get(2));
			 data.setEditCustomer(OrderDAO.getCustomer(data.getCustId()));
//			 data.setEditCustomer(orderCustomer);

    	}
    	else {
    		// set customer
			 data.setEditCustomer(data.getSelectedCustomer());	
    	}
    	
    	data.setEditingCustomer(true);
		// Edit Item Window
		FXMLLoader loaderItem = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
		Parent rootItem = loaderItem.load();		
		Stage stage = new Stage();
		stage.setResizable(false);
//		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(rootItem));
		
		// show and wait for updated item values in data singleton
		stage.showAndWait(); 
		
		// update the choicebox
		cbBikes.setItems(data.getCustomerBikes());
		cbBikes.getSelectionModel().select(originalBikeSelection);
		
		// update name in case it has changed
		lblCustName.setText(data.getEditCustomer().getFirstName() + " " + data.getEditCustomer().getLastName());
		
		if (data.getNewOrder() == false ) {
			orderInfo.set(3, data.getEditCustomer().getEmail());
			orderInfo.set(2, data.getEditCustomer().getPhone());
			orderInfo.set(1, data.getEditCustomer().getFirstName());
			orderInfo.set(10, data.getEditCustomer().getLastName());
		}
		else {
			data.setSelectedCustomer(data.getEditCustomer());
		}

    }
    
 // open Work Order on double click of order in the list
    @FXML
    private void openReportBtn (ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to discard changes?",  ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
		    return;
		}

    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Reports");
    	m.changeScene(root);               
            
    	
    }
    
    // view Items List
    @FXML
    private void viewItemsList (ActionEvent event) throws Exception {
    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to discard changes?",  ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
		    return;
		}
    	Main m = new Main();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemsList.fxml"));
    	Parent root = loader.load();
    	m.changeTitle("Bike Shop Manager - Items");
    	m.changeScene(root);
    	
    }
    
// // make the fields editable. got stuck on updating the total
//    @FXML
//    private void editableCols(){    	
//    	colDesc.setCellFactory(TextFieldTableCell.forTableColumn());
//    	colPrice.setCellFactory(TextFieldTableCell.<Item, Number>forTableColumn(new NumberStringConverter()));
//	
//    	colDesc.setOnEditCommit(event->event.getTableView().getItems().get(event.getTablePosition().getRow()).setItemDesc(event.getNewValue()) );
//    	colPrice.setOnEditCommit(event->event.getTableView().getItems().get(event.getTablePosition().getRow()).setItemPrice(event.getNewValue().floatValue()) );
//   	
//    	
//        /* Allow for the values in each cell to be changeable */
//    	tblItems.setEditable(true); 
//    }
    
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException, IOException {
//		 ObservableList<Item> items = OrderDAO.getOrderItems(data.getOrderId());
//		 bikes = OrderDAO.getCustomerBikes(data.getCustId());
		 data.setCreatingNewBike(false);
		 lblItemInList.setVisible(false);
		 txtPrivNote.setVisible(false);
		 txtComment.setPrefWidth(2000);
		 
		 if (data.getNewOrder()) {
			 populateFieldsNew();
			 btnBikeInfo.setDisable(true);
			 
			 cbBikes.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> bikeSelection(newValue));
		 }
		 else {
			 txtBike.setVisible(false);
			 btnBikeInfo.setDisable(false);
			 populateFields();
			 cbBikes.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> bikeSelection(newValue));
		 }
		 
		// show the private note when checking the box, otherwise it stays hidden by default.
		 chkPrivNote.selectedProperty().addListener((ov, oldValue, newValue) -> {
			 if (newValue == true) {
				 txtPrivNote.setVisible(true);
				 txtComment.setPrefWidth(200);
			 }
			 else {
				 txtPrivNote.setVisible(false);
				 txtPrivNote.setPrefWidth(1.0);
				 txtComment.setPrefWidth(2000);
			 } 
		});
		 
		 // Hide all the buttons for receipt mode
		 receipt_mode.selectedProperty().addListener((ov, oldValue, newValue) -> {
			 if (newValue == true) {
				 
				// TODO change this to a regular button instead of checkbox. no use for this feature anymore.
				 /*
				 txtPrivNote.setVisible(false);
				 txtPrivNote.setPrefWidth(1.0);
				 txtComment.setPrefWidth(3000);
				 
				 chkPrivNote.setVisible(false);
				 btnCustomerInfo.setVisible(false);
				 btnBikeInfo.setVisible(false);
				 btnAddItem.setVisible(false);
				 btnRemoveItem.setVisible(false);
				 btnNewItem.setVisible(false);
				 vBoxItemBtns.setVisible(false);
				 
				 tblItems.resizeColumn(colQty, 50);
				 tblItems.resizeColumn(colTotal, 50);
				 tblItems.resizeColumn(colPrice, 50);
				 tblItems.resizeColumn(colDesc, 200);
				 */
				 
				 // create output file on desktop "order.csv"
				 Receipt.print_order_to_file(data);
				 
				 
				 // run this script to run macros from BSMRECEIPT.xlsm
				 try {
					Runtime.getRuntime().exec("wscript C:\\Users\\LT\\Desktop\\receipt.vbs");
					receipt_mode.setSelected(false);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
			 else {
				 chkPrivNote.setSelected(false);
				 chkPrivNote.setVisible(true);
//				 txtPrivNote.setVisible(true);
				 txtComment.setPrefWidth(3000);
				 btnCustomerInfo.setVisible(true);
				 btnBikeInfo.setVisible(true);
				 btnAddItem.setVisible(true);
				 btnRemoveItem.setVisible(true);
				 btnNewItem.setVisible(true);
				 vBoxItemBtns.setVisible(true);
				 
			 } 
		});
		
	 }
}
