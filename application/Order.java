package application;

import java.sql.Date;


import javafx.beans.property.*;

public class Order {
	
	//Declare Work Order Table Columns
    private SimpleObjectProperty<Integer> order_id;
    private SimpleObjectProperty<Float> order_total;
    private IntegerProperty num_items;
    private SimpleObjectProperty<Date> order_date;
    private StringProperty first_name;
    private StringProperty last_name;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty order_status;
    private SimpleObjectProperty<Float> orderCost;
//    private StringProperty note;


	//Constructor
	public Order() {
		this.order_id = new SimpleObjectProperty<>();
		this.order_total = new SimpleObjectProperty<>();
		this.num_items = new SimpleIntegerProperty();
		this.order_date = new SimpleObjectProperty<>();
		this.first_name = new SimpleStringProperty();
		this.last_name = new SimpleStringProperty();
		this.phone = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.order_status = new SimpleStringProperty();
		this.orderCost = new SimpleObjectProperty<>();
//		this.note = new SimpleStringProperty();
	}

    // order_id
    public int getOrderId() {
        return order_id.get();
    }
    public void setOrderId(int OrderId){
        this.order_id.set(OrderId);
    }
    public SimpleObjectProperty<Integer> OrderIdProperty(){
        return order_id;
    }
	
    
    // order_total
    public Float getOrderTotal() {
        return order_total.get();
    }
    public void setOrderTotal(float f){
        this.order_total.set(f);
    }
    public SimpleObjectProperty<Float> OrderTotalProperty(){
        return order_total;
    }
    
    // num_items
    public int getNumItems() {
        return num_items.get();
    }
    public void setNumItems(int NumItems){
        this.num_items.set(NumItems);
    }
    public IntegerProperty NumItemsProperty(){
        return num_items;
    }
    
    // order_date
    public Object getOrderDate(){
        return order_date.get();
    }
    public void setOrderDate(Date orderDate){
        this.order_date.set(orderDate);
    }
    public SimpleObjectProperty<Date> OrderDateProperty(){
        return order_date;
    }
    
    // first_name
    public String getFirstName() {
        return first_name.get();
    }
    public void setFirstName (String FirstName){
        this.first_name.set(FirstName);
    }
    public StringProperty FirstNameProperty() {
        return first_name;
    }
    
    // last_name
    public String getLastName() {
        return last_name.get();
    }
    public void setLastName (String LastName){
        this.last_name.set(LastName);
    }
    public StringProperty LastNameProperty() {
        return last_name;
    }
    
    // phone
    public String getPhone () {
        return phone.get();
    }
    public void setPhone (String phone){
        this.phone.set(phone);
    }
    public StringProperty PhoneProperty() {
        return phone;
    }
    
    // email
    public String getEmail () {
        return email.get();
    }
    public void setEmail (String email){
        this.email.set(email);
    }
    public StringProperty emailProperty() {
        return email;
    }

    // order_status
    public String getOrderStatus () {
        return order_status.get();
    }
    public void setOrderStatus (String OrderStatus){
        this.order_status.set(OrderStatus);
    }
    public StringProperty OrderStatusProperty() {
        return order_status;
    }
    
 // order_Cost
    public Float getOrderCost() {
        return orderCost.get();
    }
    public void setOrderCost(float f){
        this.orderCost.set(f);
    }
    public SimpleObjectProperty<Float> OrderCostProperty(){
        return order_total;
    }
    
    
    
// // order_note
//    public String getNoteStatus () {
//        return note.get();
//    }
//    public void setNoteStatus (String OrderStatus){
//        this.note.set(OrderStatus);
//    }
//    public StringProperty noteStatusProperty() {
//        return note;
//    }

    
    
}