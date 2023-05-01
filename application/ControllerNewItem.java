package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerNewItem {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	@FXML
	 private TextField txtName;
	 @FXML
	 private TextField txtPrice;
	 @FXML
	 private TextField txtCost;
	 @FXML
	 private Button btnCancel;
	 @FXML
	 private Button btnSave;
	 @FXML
	 private CheckBox chkLabour;
	 @FXML
	 private TextField txtQty;
	 @FXML
	 private CheckBox chkMisc;
	 @FXML
	 private Label lblQty;
	 Stage stage;

	//Constructor
		public ControllerNewItem() throws ClassNotFoundException{
			

		}
		
	public Boolean containsItem(ObservableList<Item> Items, Item item) {
		 int i = 0;
		 	for (Item itemInList : Items) {
		 		if (itemInList.compareName(itemInList, item)) {
		 			return true;
		 		}
		 		i++;
		 	}
		 	return false;
	    	
	    }
	public Boolean containsItem(ObservableList<Item> Items, String txtDesc) {
		 	for (Item itemInList : Items) {
		 		if (itemInList.getItemDesc().equalsIgnoreCase(txtDesc)) {
		 			return true;
		 		}
		 	}
		 	return false;	
	    }
	
	 public void cancel(ActionEvent event ) throws IOException {
		 
		 stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		 stage.close();
	 }
	 
	
	 // save item to DB.  
	 // itemId wont be available until after db insertion. Or you can use length of AllItems +1.
	public void saveItem(ActionEvent event )  {
		data.setNewItem(null);
		Item item = new Item();
		
		if ( data.getNewItemFromItemsList() == true) {
			System.out.println( "_new item being created from items list_" );
			
			if( txtName.getText().isBlank() == false) {
				
				// do not allow duplicate items to be added
				if (this.containsItem(data.getAllItems(), txtName.getText())) {
					Alert alert = new Alert(AlertType.ERROR, "Item with same name already exists",  ButtonType.OK);
					alert.showAndWait();
					return;
				}
				
				
				try {
					if(txtName.getText().length() > 150) {
			        	Alert alert = new Alert(AlertType.WARNING, "Item Description is too long. Max 150 characters.",  ButtonType.OK);
						alert.showAndWait();
						return;
			        }
					item.setItemDesc(txtName.getText());
					item.setItemPrice(Float.parseFloat(txtPrice.getText()));
					item.setItemCost(Float.parseFloat(txtCost.getText()));
					
					if (chkLabour.isSelected())					
						item.setItemLb(1);
					else {
						item.setItemLb(0);
					}
					
					// item created successfully
					System.out.println("item created " + item.toString());
				
					// push item to DB Items table and get its ID
					Integer newItemId = OrderDAO.insertItem(item);				
					
					// set id and add to data aLlItems and Order Items 
					item.setItemId(newItemId);
					data.setNewItem(item);
					data.getAllItems().add(item);
					data.getAllItemsString().add(item.getItemDesc() + "  $" + item.getItemPrice());
					
					System.out.println("New " + item.toString());
					
					// leave window
					(((Node) event.getSource())).getScene().getWindow().hide();
					
					return;
					
				}
				catch(Exception e) {
					Alert alert = new Alert(AlertType.ERROR, "Price & Cost must be numeric.",  ButtonType.OK);
					alert.showAndWait();
					return;
				}
			}
			
		}
		else {
			System.out.println( "_new item being created from work order_" );
			if( txtName.getText().length() != 0) {
				try {
					if(txtName.getText().length() > 150) {
			        	Alert alert = new Alert(AlertType.WARNING, "Item Description is too long. Max 150 characters.",  ButtonType.OK);
						alert.showAndWait();
						return;
			        }
					item.setItemDesc(txtName.getText());
					item.setItemPrice(Float.parseFloat(txtPrice.getText()));
					item.setItemCost(Float.parseFloat(txtCost.getText()));
					item.setItemQty(Float.parseFloat(txtQty.getText()));
					item.setItemTotal(item.getItemPrice() * item.getItemQty());					
					data.setOrderTotal(data.getOrderTotal() + item.getItemTotal());
					
					if (chkLabour.isSelected())					
						item.setItemLb(1);
					else {
						item.setItemLb(0);
					}

					
					// item created successfully
					System.out.println("item created " + item.toString());
					
					// check if the work order contains item, do not allow insertion.

					if (this.containsItem(data.getOrderItems(), item)) {
						
						//Warn that an item with the same name already exists.
						Alert alert = new Alert(AlertType.WARNING, "Item exists in work order.",  ButtonType.OK);
						alert.showAndWait();
						
						return;
					}
					
					// check if the item already exists, warn user that it does.			
					if (this.containsItem(data.getAllItems(), item)) {
						
						//Warn that an item with the same name already exists.
						Alert alert = new Alert(AlertType.WARNING, "Item with the same name alread exists."
								+ "\n Do you wish to add a duplicate item?",  ButtonType.YES, ButtonType.NO);
						alert.showAndWait();
						
						if(alert.getResult() == ButtonType.NO) {
							System.out.println("_-__ Declined to add duplicate item in DB_-__");
							return;
						}
					}
					
					// Misc Items do not get inserted into the items list. itemId of 0 signifies a Misc item.
					Integer newItemId = 0;
					if (chkMisc.isSelected() == false) {
						// push item to DB Items table and get its ID
						newItemId = OrderDAO.insertItem(item);
						item.setItemId(newItemId);
						data.setNewItem(item);
						data.getAllItems().add(item);
						data.getAllItemsString().add(item.getItemDesc() + "  $" + item.getItemPrice());
					}
					
					// set id and add to data aLlItems and Order Items 

					item.setItemId(newItemId);
					
					data.getOrderItems().add(item);	
		
					System.out.println("New " + item.toString());
					
					// leave window
					(((Node) event.getSource())).getScene().getWindow().hide();
					
					return;
					
				}
				catch(Exception e) {
					Alert alert = new Alert(AlertType.ERROR, "Price & Cost must be numeric.",  ButtonType.OK);
					alert.showAndWait();
					return;
				}
			}
			
		}
		
		

		System.out.println("_-__ NOT SAVEd_-__");
		
		return;
		    	
	 }
	@FXML
	public void selectAllText(MouseEvent event) {
		final TextField source = (TextField) event.getSource();
		source.selectAll();		
	}

	 
	 public void populateFields() throws ClassNotFoundException, SQLException  {
		 if ( data.getNewItemFromItemsList() == true) {
			 chkMisc.setVisible(false);
			 lblQty.setVisible(false);
			 txtQty.setVisible(false);
		 }
	}
	  
	 
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
//		 ObservableList<Item> items = OrderDAO.getOrderItems(data.getOrderId());
		 
//		 System.out.println("\nItemId: " + data.getItemId());
//		 System.out.println("ItemDesc " + data.getItemDesc());
//		 System.out.println("ItemPrice: " + data.getItemPrice());
//		 System.out.println("ItemQty: " + data.getItemQty());
		 populateFields();
	 }
}
