package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerBikeInfo {
	
	@FXML
    private Button btnDiscard;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField txtBike;
    @FXML
    private TextField txtChain;
    @FXML
    private TextField txtFBrake;
    @FXML
    private TextField txtFDer;
    @FXML
    private TextField txtFPads;
    @FXML
    private TextField txtFTire;
    @FXML
    private TextField txtFWheel;
    @FXML
    private TextField txtOwner;
    @FXML
    private TextField txtRBrake;
    @FXML
    private TextField txtRDer;
    @FXML
    private TextField txtRPads;
    @FXML
    private TextField txtRTire;
    @FXML
    private TextField txtRWheel;
    @FXML
    private TextField txtSerial;
    @FXML
    private TextField txtSize;
    @FXML
    private TextField txtCassette;
    
    
    OrderDataSingleton data = OrderDataSingleton.getInstance();
    Stage stage;
    
    @FXML
    void cancel(ActionEvent event) {
    	
    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to discard any changes?",  ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			stage = (Stage)((Button) event.getSource()).getScene().getWindow();
			stage.close();
		}
    	
    }

    @FXML
    void saveInfo(ActionEvent event) throws ClassNotFoundException, SQLException {
    	Bike bike = data.getOrderBikeObj();
    	
    	// do not allow blank bike names
    	if (txtBike.getText().isBlank()) {
    		Alert alert = new Alert(AlertType.WARNING, "Bike Name cannot be blank.",  ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
    	
    	bike.setBikeName(txtBike.getText());
    	bike.setSerial(txtSerial.getText());
    	
    	bike.setSize(txtSize.getText());
    	bike.setChain(txtChain.getText());
    	bike.setRearDer(txtRDer.getText());
    	bike.setFrontDer(txtFDer.getText());
    	bike.setRearTire(txtRTire.getText());
    	bike.setFrontTire(txtFTire.getText());
    	bike.setRearWheel(txtRWheel.getText());
    	bike.setFrontWheel(txtFWheel.getText());
    	bike.setRearBrake(txtRBrake.getText());
    	bike.setFrontBrake(txtFBrake.getText());
    	bike.setRearPads(txtRPads.getText());
    	bike.setFrontPads(txtFPads.getText());
    	bike.setCassette(txtCassette.getText());
    	
    	OrderDAO.updateBikeInfo(bike);
    	data.setOrderBikeObj(bike);
    	
    	//quit when done
    	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		stage.close();
    	
    }
	 
    
    private void populateFields() throws ClassNotFoundException, SQLException {
    	
    	Bike bike = OrderDAO.getBikeInfo(data.getOrderBikeObj());
    	
    	txtOwner.setText(data.getOrderFname() + " " + data.getOrderLname());
    	txtBike.setText(bike.getBikeName());
    	txtSerial.setText(bike.getSerial());
    	txtSize.setText(bike.getSize());
    	txtChain.setText(bike.getChain());
    	txtRDer.setText(bike.getRearDer());
    	txtFDer.setText(bike.getFrontDer());
    	txtRTire.setText(bike.getRearTire());
    	txtFTire.setText(bike.getFrontTire());
    	txtRWheel.setText(bike.getRearWheel());
    	txtFWheel.setText(bike.getFrontWheel());
    	txtRBrake.setText(bike.getRearBrake());
    	txtFBrake.setText(bike.getFrontBrake());
    	txtRPads.setText(bike.getRearPads());
    	txtFPads.setText(bike.getFrontPads());
    	txtCassette.setText(bike.getCassette());
    	
    	
    }
    
    private void populateNewBike() throws ClassNotFoundException, SQLException {
    	
    	Bike bike = new Bike();
    	txtOwner.setText(data.getOrderFname() + " " + data.getOrderLname());

    	
    }
 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
		 if (data.getCreatingNewBike()) {
			 populateNewBike();
			 data.setCreatingNewBike(false);
		 }
		 else {
			 populateFields();
		 }
	 }
}
