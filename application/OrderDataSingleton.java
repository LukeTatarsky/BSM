package application;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

public class OrderDataSingleton {
	
	private static final OrderDataSingleton instance = new OrderDataSingleton();
	
	
	
	private Integer bikeId;
	private Integer custId;	
	
	// --------------- Work Order data -----------------
	private Integer orderId;
	private Float orderTotal;
	private String orderNote;
	private String orderPrivNote;
	private String orderDate;
	private String orderBike;
	private String orderFname;
	private String orderLname;
	private String orderStatus;
	private String orderStatusKey;
	private Boolean newOrder;
	private Bike orderBikeObj;
	private Integer selectedBikeIndex;
//	private Integer selectedOrderIndex;
//	private Order selectedOrder;
	// -----------------------------------------------
	
	// --------------- Item  data -----------------
	private Integer itemId;
	private Float itemPrice;
	private Float itemQty;
	private Float itemTotal;
	private String itemDesc;
	private Integer itemIndex;
	// -----------------------------------------------
	
	// --------------- Customer data -----------------
	private String customerFname;
	private String customerLname;
	private String customerEmail;
	private String customerPhone;
	private Customer newCustomer;
	private Customer editCustomer;
	private Boolean editingCustomer;
	private Customer selectedCustomer;
	private Integer editingIndex;
	// -----------------------------------------------
	
	// --------------- Bike data -----------------
	private Boolean creatingNewBike;
	// -----------------------------------------------
	
	// these get populated once after successful login
	private ObservableList<Item> allItems;
	private ObservableList<String> allItemsString;
	private ObservableList<Item> orderItems;
	private ObservableList<String> statuses;
	private ObservableList<String> statusKeys;
	private ObservableList<Customer> customers;
	private ObservableList<Customer> allCustomers;
	private ObservableList<Bike> customerBikes;
	
	private Item newItem;
	private Item selectedCbItem;
	
	private String sqlErrorString;
	private Boolean sqlError;
	
	private Boolean newItemFromItemsList;
	private Boolean AddingItemFromItemsList;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String keyword;
	private Boolean activeOrders;
	
	private OrderDataSingleton() {
		this.setSqlError(false);
		this.setKeyword("");
		this.setAddingItemFromItemsList(false);
		
	}
	

	
	public Integer getBikeId() {
		return bikeId;
	}

	public void setBikeId(Integer bikeId) {
		this.bikeId = bikeId;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderBike() {
		return orderBike;
	}

	public void setOrderBike(String orderBike) {
		this.orderBike = orderBike;
	}

	public String getOrderFname() {
		return orderFname;
	}

	public void setOrderFname(String orderFname) {
		this.orderFname = orderFname;
	}

	public String getOrderLname() {
		return orderLname;
	}

	public void setOrderLname(String orderLname) {
		this.orderLname = orderLname;
	}

	public static OrderDataSingleton getInstance() {
		return instance;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	
	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Float getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(Float orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusKey() {
		return orderStatusKey;
	}

	public void setOrderStatusKey(String orderStatusKey) {
		this.orderStatusKey = orderStatusKey;
	}

	public Integer getItemId() {
		return Integer.valueOf(itemId);
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Float getItemQty() {
		return itemQty;
	}

	public void setItemQty(Float itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public ObservableList<Item> getAllItems() {
		return allItems;
	}

	public void setAllItems(ObservableList<Item> allItems) {
		this.allItems = allItems;
	}

	public ObservableList<Item> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(ObservableList<Item> orderItems) {
		this.orderItems = orderItems;
	}

	public ObservableList<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(ObservableList<String> statuses) {
		this.statuses = statuses;
	}

	public ObservableList<String> getStatusKeys() {
		return statusKeys;
	}

	public void setStatusKeys(ObservableList<String> statusKeys) {
		this.statusKeys = statusKeys;
	}

	public Item getNewItem() {
		return newItem;
	}

	public void setNewItem(Item newItem) {
		this.newItem = newItem;
	}

	public Float getItemTotal() {
		return itemQty * itemPrice;
	}

	public Item getSelectedCbItem() {
		return selectedCbItem;
	}

	public void setSelectedCbItem(Item selectedCbItem) {
//		System.out.println(selectedCbItem.toString());
		this.selectedCbItem = new Item();
		this.selectedCbItem.setItemDesc(selectedCbItem.getItemDesc());
		this.selectedCbItem.setItemPrice(selectedCbItem.getItemPrice());
		this.selectedCbItem.setItemQty(selectedCbItem.getItemQty());
		this.selectedCbItem.setItemId(selectedCbItem.getItemId());
		this.selectedCbItem.setItemLb(selectedCbItem.getItemLb());
		this.selectedCbItem.setItemTotal(selectedCbItem.getItemPrice());
		this.selectedCbItem.setItemCost(selectedCbItem.getItemCost());
		
	}

	public ObservableList<String> getAllItemsString() {
		return allItemsString;
	}

	public void setAllItemsString(ObservableList<String> allItemsString) {
		this.allItemsString = allItemsString;
	}



	public Integer getItemIndex() {
		return itemIndex;
	}



	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}



	public Boolean getNewItemFromItemsList() {
		return newItemFromItemsList;
	}



	public void setNewItemFromItemsList(Boolean newItemFromItemsList) {
		this.newItemFromItemsList = newItemFromItemsList;
	}


	// ----------- Customer data methods ---------------------------------------
	//-------------------------------------------------------------------------
	public ObservableList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ObservableList<Customer> customers) {
		this.customers = customers;
	}
	
	public String getCustomerFname() {
		return customerFname;
	}
	
	public void setCustomerFname(String fname) {
		this.customerFname = fname;
	}
	
	public String getCustomerLname() {
		return customerLname;
	}
	
	public void setCustomerLname(String lname) {
		this.customerLname = lname;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public void setCustomerEmail(String email) {
		this.customerEmail = email;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	
	public void setCustomerPhone(String phone) {
		this.customerPhone = phone;
	}
	
	public Customer getNewCustomer() {
		return newCustomer;
	}
	
	public void setNewCustomer(Customer newCustomer) {
		this.newCustomer = newCustomer;
	}
	
	public ObservableList<Customer> getAllCustomers() {
		return allCustomers;
	}

	public void setAllCustomers(ObservableList<Customer> allCustomers) {
		this.allCustomers = allCustomers;
	}



	public Customer getEditCustomer() {
		return editCustomer;
	}



	public void setEditCustomer(Customer editCustomer) {
		this.editCustomer = editCustomer;
	}



	public Boolean getEditingCustomer() {
		return editingCustomer;
	}



	public void setEditingCustomer(Boolean editingCustomer) {
		this.editingCustomer = editingCustomer;
	}



	public Integer getEditingIndex() {
		return editingIndex;
	}



	public void setEditingIndex(Integer editingIndex) {
		this.editingIndex = editingIndex;
	}



	public Boolean getNewOrder() {
		return newOrder;
	}



	public void setNewOrder(Boolean newOrder) {
		this.newOrder = newOrder;
	}



	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}



	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}



	public ObservableList<Bike> getCustomerBikes() {
		return customerBikes;
	}



	public void setCustomerBikes(ObservableList<Bike> customerBikes) {
		this.customerBikes = customerBikes;
	}



	public Boolean getAddingItemFromItemsList() {
		return AddingItemFromItemsList;
	}



	public void setAddingItemFromItemsList(Boolean addingItemFromItemsList) {
		AddingItemFromItemsList = addingItemFromItemsList;
	}



	public LocalDate getStartDate() {
		return startDate;
	}



	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	public LocalDate getEndDate() {
		return endDate;
	}



	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}



	public Bike getOrderBikeObj() {
		return orderBikeObj;
	}



	public void setOrderBikeObj(Bike orderBikeObj) {
		this.orderBikeObj = orderBikeObj;
	}



	public Integer getSelectedBikeIndex() {
		return selectedBikeIndex;
	}



	public void setSelectedBikeIndex(Integer selectedBikeIndex) {
		this.selectedBikeIndex = selectedBikeIndex;
	}



	public Boolean getCreatingNewBike() {
		return creatingNewBike;
	}



	public void setCreatingNewBike(Boolean creatingNewBike) {
		this.creatingNewBike = creatingNewBike;
	}



	public String getOrderPrivNote() {
		return orderPrivNote;
	}



	public void setOrderPrivNote(String orderPrivNote) {
		this.orderPrivNote = orderPrivNote;
	}



	public String getSqlErrorString() {
		return sqlErrorString;
	}



	public void setSqlErrorString(String sqlErrorString) {
		this.sqlErrorString = sqlErrorString;
	}



	public Boolean getSqlError() {
		return sqlError;
	}



	public void setSqlError(Boolean sqlError) {
		this.sqlError = sqlError;
	}



	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



	public Boolean getActiveOrders() {
		return activeOrders;
	}



	public void setActiveOrders(Boolean activeOrders) {
		this.activeOrders = activeOrders;
	}


//
//	public Integer getSelectedOrderIndex() {
//		return selectedOrderIndex;
//	}
//
//
//
//	public void setSelectedOrderIndex(Integer selectedOrderIndex) {
//		this.selectedOrderIndex = selectedOrderIndex;
//	}
//
//
//
//	public Order getSelectedOrder() {
//		return selectedOrder;
//	}
//
//
//
//	public void setSelectedOrder(Order selectedOrder) {
//		this.selectedOrder = selectedOrder;
//	}
	



}
