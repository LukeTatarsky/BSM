package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerLogin {
	
	OrderDataSingleton data = OrderDataSingleton.getInstance();
	
    public ControllerLogin() {
    	
    }

    @FXML
    private Button button;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

//    private ObservableList<String> keys;

    public void userLogin(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, InterruptedException {
    	
        if (checkLogin()) {
        	
        	wronglogin.setText("Loading..");
        	
        	wronglogin.setVisible(true);
	        
        	// load preliminary data.
        	
	        data.setAllItems(OrderDAO.getItems());
			data.setAllCustomers(OrderDAO.showCustomers());
	        
	        data.setAddingItemFromItemsList(false);
	        
	        data.setNewItemFromItemsList(false);
	    	data.setStatuses(OrderDAO.getStatuses());
	    	ObservableList<String> statuses = data.getStatuses();
	    	ObservableList<String> allItemsString = FXCollections.observableArrayList();
	    	data.setStatusKeys(FXCollections.observableArrayList());
	    	ObservableList<String> keys = data.getStatusKeys();
	    	
	    	for (Item item : data.getAllItems()) {
	    		allItemsString.add(item.getItemDesc() + "  $" + item.getItemPrice());
			 }
	    	data.setAllItemsString(allItemsString);

			 String stat[] = {"",""}; 
			 for (int i = 0; i <= statuses.size() -1; i++) {
				 stat =  statuses.get(i).split(" ");
				 statuses.set(i, stat[0]);
				 keys.add(stat[1]);
			 }
	    	
	    	Main m = new Main();
	    	m.changeTitle("Bike Shop Manager - Work Orders");
	        m.changeScene("WorkOrders.fxml");
	        
        }
        else {
        	
        	wronglogin.setText("Wrong username or password!");
        	wronglogin.setVisible(true);
        }

    }
    
    // copy of above but for key event
    public void userLoginKey() throws IOException, ClassNotFoundException, SQLException, InterruptedException {
    	
        if (checkLogin()) {
        	
        	wronglogin.setText("Loading..");
        	
        	wronglogin.setVisible(true);
	        
        	// load preliminary data.
        	
	        data.setAllItems(OrderDAO.getItems());
			data.setAllCustomers(OrderDAO.showCustomers());
	        data.setNewItemFromItemsList(false);
	    	data.setStatuses(OrderDAO.getStatuses());
	    	ObservableList<String> statuses = data.getStatuses();
	    	ObservableList<String> allItemsString = FXCollections.observableArrayList();
	    	data.setStatusKeys(FXCollections.observableArrayList());
	    	ObservableList<String> keys = data.getStatusKeys();
	    	
	    	for (Item item : data.getAllItems()) {
	    		allItemsString.add(item.getItemDesc() + "  $" + item.getItemPrice());
			 }
	    	data.setAllItemsString(allItemsString);

			 String stat[] = {"",""}; 
			 for (int i = 0; i <= statuses.size() -1; i++) {
				 stat =  statuses.get(i).split(" ");
				 statuses.set(i, stat[0]);
				 keys.add(stat[1]);
			 }
	    	
	    	Main m = new Main();
	    	m.changeTitle("Bike Shop Manager - Work Orders");
	        m.changeScene("WorkOrders.fxml");
	        
        }
        else {
        	
        	wronglogin.setText("Wrong username or password!");
        	wronglogin.setVisible(true);
        }

    }
    

    private Boolean checkLogin() throws IOException, ClassNotFoundException, SQLException {
        
        
        if(username.getText().toString().equals("admin") && password.getText().toString().equals("321517")) {
        	
        	return true;
        	
        }
		return false;
    }
    @FXML
    private void enterKey(KeyEvent event) throws ClassNotFoundException, IOException, SQLException, InterruptedException {
    	if (event.getCode() == KeyCode.ENTER) {
    		userLoginKey();
    	}
    }
    
    
    // @ Frank Obedi    
    public void hideError(MouseEvent actionEvent) throws Exception{			
		
    	if(actionEvent.getButton().equals(MouseButton.PRIMARY)) {
    		if(wronglogin.isVisible()) {
    			wronglogin.setVisible(false);
        		System.out.println("Visible");
        		
        	}
    	}	
	}


}