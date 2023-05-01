package application;

import java.io.IOException;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

public class ControllerChart {

	@FXML
    private Button btnReturn;
    @FXML
    private LineChart<String, Float> chart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private Label lblCost;

    @FXML
    private Label lblProfit;

    @FXML
    private Label lblRevenue;
	
	
	 @FXML
	 private RadioButton rbExistingItem;
	 
	 private XYChart.Series<String, Float> seriesRevenue;
//	 private XYChart.Series<String, Float> seriesCost;
	 
	 private Float revenue;
	 private Float cost;
	 private Float profit;
	 
	 OrderDataSingleton data = OrderDataSingleton.getInstance();
	 
	//Constructor
		public ControllerChart() throws ClassNotFoundException{
			this.seriesRevenue = new Series<String, Float>();
			this.seriesRevenue.setName("Revenue");
//			this.seriesCost = new Series<String, Float>();
//			this.seriesCost.setName("Cost");
			this.revenue = 0f;
			this.cost = 0f;
			this.profit = 0f;
		}
		
	
	 public void cancel(ActionEvent event ) throws IOException {
		 
		 Main m = new Main();
		 m.changeTitle("Bike Shop Manager - Work Orders");
	     m.changeScene("WorkOrders.fxml");
	 }
	 

	

	public void getRevenueData() throws ClassNotFoundException, SQLException {
		ObservableList<Order> orders = OrderDAO.getOrdersForReport();
//		for ( Order order : orders) {			
//			Integer lastIndex = this.seriesRevenue.getData().size() - 1;			
//			if (lastIndex > 0 && this.seriesRevenue.getData().get(lastIndex).getXValue().equals(order.getOrderDate().toString())) {				
//				this.seriesRevenue.getData().get(lastIndex).setYValue(this.seriesRevenue.getData().get(lastIndex).getYValue() + order.getOrderTotal());
////				this.seriesCost.getData().get(lastIndex).setYValue(this.seriesCost.getData().get(lastIndex).getYValue() + order.getOrderCost());
//			}			
//			else {
//				this.seriesRevenue.getData().add(new XYChart.Data(order.getOrderDate().toString(), order.getOrderTotal()));
////				this.seriesCost.getData().add(new XYChart.Data(order.getOrderDate().toString(), order.getOrderCost()));
//			}
//			
//			this.revenue += order.getOrderTotal();
//			this.cost += order.getOrderCost();			
//		}	
		
		
		
		// sum up order totals that are on the same day
		for ( int i = orders.size() - 1  ; i >= 0 ; i--) {			
			Integer lastIndex = this.seriesRevenue.getData().size() - 1;			
			if (lastIndex > 0 && this.seriesRevenue.getData().get(lastIndex).getXValue()
									.equals(orders.get(i).getOrderDate().toString())) {	
				
				this.seriesRevenue.getData().get(lastIndex).setYValue(this.seriesRevenue.getData().get(lastIndex).getYValue() + orders.get(i).getOrderTotal());
//				this.seriesCost.getData().get(lastIndex).setYValue(this.seriesCost.getData().get(lastIndex).getYValue() + order.getOrderCost());
			}			
			else {
				this.seriesRevenue.getData().add(new XYChart.Data(orders.get(i).getOrderDate().toString(), orders.get(i).getOrderTotal()));
//				this.seriesCost.getData().add(new XYChart.Data(order.getOrderDate().toString(), order.getOrderCost()));
			}
			
			this.revenue += orders.get(i).getOrderTotal();
			this.cost += orders.get(i).getOrderCost();			
		}	
		this.profit = this.revenue - this.cost;
	}

	 
	 public void populateFields() throws ClassNotFoundException, SQLException  {
		 
		 
		 
		 getRevenueData();
		
		 lblRevenue.setText(String.format("%.2f", this.revenue));
		 lblCost.setText(String.format("%.2f", this.cost));
		 lblProfit.setText(String.format("%.2f", this.profit));

//		 xAxis.setLabel("Date");
//		 xAxis.tickLabelRotationProperty().set(20);
//		 xAxis.setTickLabelRotation(-30);
//		 yAxis.setLabel("$$");
		 chart.setLegendVisible(false);
		 chart.legendSideProperty().set(Side.TOP);

		 chart.getData().addAll(this.seriesRevenue);
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
	    
	    
	    // view Items List
	    @FXML
	    private void viewItemsList (ActionEvent event) throws Exception {
	    	
	    	Main m = new Main();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemsList.fxml"));
	    	Parent root = loader.load();
	    	m.changeTitle("Bike Shop Manager - Items");
	    	m.changeScene(root);
	    	
	    }

    private void editItem (MouseEvent actionEvent)  {

    }
	 
   
 
	 @FXML
	 private void initialize () throws ClassNotFoundException, SQLException {
		 
		 populateFields();
	 }
}
