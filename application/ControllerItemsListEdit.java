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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerItemsListEdit {
	
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



	 private Boolean safeToSave;
	 
	 Stage stage;
	 
	//Constructor
		public ControllerItemsListEdit() throws ClassNotFoundException{
			
			safeToSave = true;

		}
		
	public Boolean containsItem(ObservableList<Item> Items, Item item) {
		 int i = 0;
		 	for (Item itemInList : Items) {
		 		if (itemInList.compareId(itemInList, item) == 0) {
		 			return true;
		 		}
		 		i++;
		 	}
		 	return false;
	    	
	    }
	
	 public void cancel(ActionEvent event ) throws IOException {
		 
		 stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		 stage.close();
	 }
	 


	public void saveItem(ActionEvent event )  {
		
//		System.out.println("_-___-__"+data.getNewItem());
		
		
		
		if (safeToSave == false){
//			System.out.println("_-_ITEM   IN    LIST__-__");
			return;
		}
		
		
		Item newItem = new Item();
		
		newItem.setItemId(data.getNewItem().getItemId());
		
		if (txtName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR, "Item description should not be blank. \nResetting values.",  ButtonType.OK);
			alert.showAndWait();
			txtName.setText(data.getNewItem().getItemDesc());
			return;
		}
		
		newItem.setItemDesc(txtName.getText());
		
		
		newItem.setItemQty(1);
		
		
		if (chkLabour.isSelected())					
			newItem.setItemLb(1);
		else {
			newItem.setItemLb(0);
		}
		
		
		try {
			newItem.setItemPrice(Float.parseFloat(txtPrice.getText()));
			newItem.setItemCost(Float.parseFloat(txtCost.getText()));
			newItem.setItemTotal(newItem.getItemPrice());
			data.setNewItem(newItem);
			
			data.getAllItems().set(data.getItemIndex(), newItem);
			
			OrderDAO.updateItem(newItem);
			
			data.getAllItemsString().set(data.getItemIndex(), newItem.getItemDesc() + "  $"+ newItem.getItemPrice());
			
			//exit the item window
			(((Node) event.getSource())).getScene().getWindow().hide();
			
		}
		catch(Exception e){
			
			txtCost.setText(data.getNewItem().getItemCost().toString());
			txtPrice.setText(data.getNewItem().getItemPrice().toString());
			Alert alert = new Alert(AlertType.ERROR, "Price & Cost must be numeric. \nResetting values.",  ButtonType.OK);
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
		 
		 System.out.println("--edit "+ item.toString() );
		 txtName.setText(item.getItemDesc().toString());
		 txtCost.setText(item.getItemCost().toString());
		 txtPrice.setText(item.getItemPrice().toString());
		 
		 if (item.getItemLb().equals(1)) {

			 chkLabour.setSelected(true);
		 }
		 

	}
	 

    private void editItem (MouseEvent actionEvent)  {

    }
	 
	 
	 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
		 
		 populateFields();
	 }
}
