package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerItem {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	
	 @FXML
	 private RadioButton rbExistingItem;
	 @FXML
	 private RadioButton rbNewItem;	 
	 @FXML
	 private TextField txtDesc;
	 @FXML
	 private ComboBox<String> cbItems;
	 @FXML
	 private TextField txtName;
	 @FXML
	 private TextField txtPrice;
	 @FXML
	 private TextField txtQty;
	 @FXML
	 private Button btnCancel;
	 @FXML
	 private Button btnSave;
	 @FXML
	 private Label lblWarning;
	 @FXML
	 private CheckBox chkShowCost;	
	 @FXML
	 private TextField txtCost;
	 @FXML
	 private CheckBox chkLabour;
	 
	 private Boolean safeToSave;
	 Stage stage;
	 
	//Constructor
		public ControllerItem() throws ClassNotFoundException{

			data.setSelectedCbItem(data.getNewItem());
			safeToSave = true;

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
	public Boolean containsItemTxt(ObservableList<Item> Items, String TxtDesc) {

		 	for (Item itemInList : Items) {
		 		if (itemInList.getItemDesc().equalsIgnoreCase(TxtDesc)) {
		 			return true;
		 		}

		 	}
		 	return false;
	    	
	    }
	
	 public void cancel(ActionEvent event ) throws IOException {
		 stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		 stage.close();
		 
	 }
	 
	 // when you click on the combo box
	 public void selectCbItem(ActionEvent event ) throws IOException {
		 
		 data.setSelectedCbItem(data.getAllItems().get(cbItems.getSelectionModel().getSelectedIndex()));	

		 txtPrice.setText(data.getSelectedCbItem().getItemPrice().toString());
		 txtDesc.setText(data.getSelectedCbItem().getItemDesc().toString());
		 txtCost.setText(data.getSelectedCbItem().getItemCost().toString());
		 
		 if(data.getSelectedCbItem().getItemLb() == 1) {
			 chkLabour.setSelected(true);
		 }
		 else {
			 chkLabour.setSelected(false);
		 }
//		 System.out.println("select Box Item"+ data.getSelectedCbItem());
//		 if (this.containsItem(data.getOrderItems(),data.getSelectedCbItem()) 
//				 && data.getNewItem().getItemId().equals(data.getSelectedCbItem().getItemId()) == false){
//				lblWarning.setVisible(true);
//				safeToSave = false;
////				System.out.println("_-_ITEM   IN    LIST__-__");
//			}
//		 else {
//			 lblWarning.setVisible(false);
//			 safeToSave = true;
//		 }
	 }

	public void saveItem(ActionEvent event )  {
		
//		System.out.println("_-___-__"+data.getNewItem());
		
		Item cbItem = data.getSelectedCbItem();
		
		// check if item exists
		if ((this.containsItemTxt(data.getOrderItems(),txtDesc.getText()) 
				|| this.containsItem(data.getOrderItems(),data.getSelectedCbItem()))
				&& data.getNewItem().getItemId().equals(data.getSelectedCbItem().getItemId()) == false) {
			lblWarning.setVisible(true);
			safeToSave = false;

			}
		else {
			 lblWarning.setVisible(false);
			 safeToSave = true;
		}
		
		
		if (safeToSave == false){
//			System.out.println("_-_ITEM   IN    LIST__-__");
			return;
		}
		
		
		Item newItem = new Item();
        if(txtDesc.getText().length() > 150) {
        	Alert alert = new Alert(AlertType.WARNING, "Item Description is too long. Max 150 characters.",  ButtonType.OK);
			alert.showAndWait();
			return;
        }
		newItem.setItemDesc(txtDesc.getText());
		newItem.setItemPrice(cbItem.getItemPrice());
		newItem.setItemId(cbItem.getItemId());
		if(chkLabour.isSelected()) {
			newItem.setItemLb(1);
		}
		else {
			newItem.setItemLb(0);
		}
		
		newItem.setItemQty(1);
		
		try {
			data.setOrderTotal(data.getOrderTotal() - data.getNewItem().getItemTotal());
			newItem.setItemQty(Float.parseFloat(txtQty.getText()));
			newItem.setItemPrice(Float.parseFloat(txtPrice.getText()));
			newItem.setItemCost(Float.parseFloat(txtCost.getText()));
			newItem.setItemTotal(newItem.getItemPrice() * newItem.getItemQty());
			
			data.setOrderTotal(data.getOrderTotal()+newItem.getItemTotal());
			data.setNewItem(newItem);
			//exit the item window
			stage = (Stage)((Button) event.getSource()).getScene().getWindow(); // better
			stage.close();
			
		}
		catch(Exception e){
			
			txtQty.setText(cbItem.getItemQty().toString());
			txtPrice.setText(cbItem.getItemPrice().toString());
			Alert alert = new Alert(AlertType.ERROR, "Price & Quantity must be numeric. \nResetting values.",  ButtonType.OK);
			alert.showAndWait();
		

			
		}
		    	
	 }
	@FXML
	public void selectAllText(MouseEvent event) {
		final TextField source = (TextField) event.getSource();
		source.selectAll();
		
	}

	 
	 public void populateFields() throws ClassNotFoundException, SQLException  {
		 Item item = data.getNewItem();
		 
		 txtDesc.setText(item.getItemDesc());
		 txtQty.setText(item.getItemQty().toString());
		 txtPrice.setText(item.getItemPrice().toString());
		 txtCost.setText(item.getItemCost().toString());
		 
		 if(item.getItemLb() == 1) {
			 chkLabour.setSelected(true);
		 }
		 else {
			 chkLabour.setSelected(false);
		 }
		 
		 txtCost.setVisible(false);
		 
		 cbItems.setItems(data.getAllItemsString());
//		 cbItems.setPromptText(data.getNewItem().getItemDesc());
//		 cbItems.getSelectionModel().selectFirst();
		
	}
	 

    private void editItem ()  {

    }
	 
	 
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {

		 
		 // show the cost when checking the box, otherwise it stays hidden by default.
		 chkShowCost.selectedProperty().addListener((ov, oldValue, newValue) -> {
			 if (newValue == true) {
				 txtCost.setVisible(true);
			 }
			 else {
				 txtCost.setVisible(false);
			 } 
		});
		 
		 populateFields();
	 }
}
