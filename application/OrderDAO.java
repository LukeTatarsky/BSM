package application;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


/* ----------------------------------------------------------------------------------------------------- */
/*
 * 					NEED TO FIX ALL STATEMENTS TO USE PARAMETERS
 * 
 * 				1) safety  prepared statement instead of statement
 * 				2) entering ' into any of the string boxes brakes the query
*/

public class OrderDAO {
	
	/* ----------------------------------------------------------------------------------------------------- */
    /*
     * 					THIS SECTION HANDLES MAIN TABLE QUERIES
    */
	
	ObservableList<Order> ordList;
	//*******************************
    // Search a Work Order
    //*******************************
    public static ObservableList<Order> search (String key, String startDate, String endDate, Boolean activeOrders) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
    	String selectStmt = null;
//    	System.out.println(startDate.length());
//    	System.out.println(endDate.length());
//    	System.out.println(key.length());
    	
    	// only start date
    	if (startDate.length() > 2 && endDate.length() == 2){
    		if (activeOrders == true) {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE statusKey != 4 AND orderId IN (";
    		} 
    		else {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE orderId IN (";
    		}
    		
    		selectStmt +=
    				 "SELECT orderId FROM AllWrkOrd_search_func WHERE ("
	        		+ " orderId LIKE " + key
	        		+ " OR FirstName LIKE " + key
	        		+ " OR LastName LIKE " + key
	        		+ " OR phone LIKE " + key
	        		+ " OR email LIKE " + key
	        		+ " OR BikeName LIKE " + key
	        		+ " OR OrderStatus LIKE " + key
	        		+ ") AND CreationDate >= " + startDate + ") ORDER BY CreationDate desc,  orderId desc";
    	}
    	// only end date
    	else if (startDate.length() == 2 && endDate.length() > 2){
    		if (activeOrders == true) {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE statusKey != 4 AND orderId IN (";
    		} 
    		else {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE orderId IN (";
    		}
    		selectStmt +=
    				 "SELECT orderId FROM AllWrkOrd_search_func WHERE ("
	        		+ " orderId LIKE " + key
	        		+ " OR FirstName LIKE " + key
	        		+ " OR LastName LIKE " + key
	        		+ " OR phone LIKE " + key
	        		+ " OR email LIKE " + key
	        		+ " OR BikeName LIKE " + key
	        		+ " OR OrderStatus LIKE " + key
	        		+ ") AND CreationDate <= " + endDate + ") ORDER BY CreationDate desc,  orderId desc";
    	}
    	// start date and end date
    	else if (startDate.length() != 2 && endDate.length() != 2){
    		if (activeOrders == true) {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE statusKey != 4 AND orderId IN (";
    		} 
    		else {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE orderId IN (";
    		}
    		selectStmt +=
    				 "SELECT orderId FROM AllWrkOrd_search_func WHERE ("
	        		+ " orderId LIKE " + key
	        		+ " OR FirstName LIKE " + key
	        		+ " OR LastName LIKE " + key
	        		+ " OR phone LIKE " + key
	        		+ " OR email LIKE " + key
	        		+ " OR BikeName LIKE " + key
	        		+ " OR OrderStatus LIKE " + key
	        		+ ") AND CreationDate BETWEEN " + startDate + " AND " + endDate + ") ORDER BY CreationDate desc,  orderId desc";
    	}
    	// only keyword
    	else if (startDate.length() == 2 && endDate.length() == 2 && key.length() > 4){
    		if (activeOrders == true) {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE statusKey != 4 AND orderId IN (";
    		} 
    		else {
    			selectStmt = "SELECT * FROM AllWrkOrd_search WHERE orderId IN (";
    		}
    		selectStmt +=
    				 "SELECT orderId FROM AllWrkOrd_search_func WHERE ("
	        		+ "orderId LIKE " + key
	        		+ " OR FirstName LIKE " + key
	        		+ " OR LastName LIKE " + key
	        		+ " OR phone LIKE " + key
	        		+ " OR email LIKE " + key
	        		+ " OR BikeName LIKE " + key
	        		+ " OR OrderStatus LIKE " + key + ")) ORDER BY CreationDate desc,  orderId desc";
    	}
    	else {
    		if (activeOrders == true) {
    			selectStmt = "SELECT * FROM AllWorkOrders WHERE statusKey != 4";
    		} 
    		else {
    			selectStmt = "SELECT * FROM AllWorkOrders";
    		}

    		
    	}
        //Execute SELECT statement
        try {
        	//Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);

            
            //Send ResultSet and get list
            ObservableList<Order> ordList  = getAllOrdersFromResultSet(rsOrders);
//            System.out.println(ordList.get(2).getEmail());
            //Return employee object
            rsOrders.close();
            return ordList;
            
        } catch (SQLException e) {
            System.out.println("While searching for " + key + "id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    

    //*******************************
    //SELECT all Work Orders
    //*******************************
    public static ObservableList<Order> showOrders() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM AllWorkOrders";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the  method to get object
            ObservableList<Order> ordList  = getAllOrdersFromResultSet(rsOrders);
            //Return object list
            return ordList;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    
  //*******************************
    //SELECT all Work Orders
    //*******************************
    public static ObservableList<Order> getOrdersForReport() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM AllWorkOrders WHERE CreationDate > '2023-01-01' ORDER BY CreationDate desc";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the  method to get object
            ObservableList<Order> ordList  = getAllOrdersFromResultSet(rsOrders);
            //Return object list
            return ordList;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    
    //Use ResultSet from DB as parameter and set Object's attributes and return object.
    private static ObservableList<Order> getAllOrdersFromResultSet(ResultSet rs) throws SQLException
    {	
    	ObservableList<Order> orderList = FXCollections.observableArrayList();
    	
    	
    	while  (rs.next()) {
        	Order ord = new Order();

        	ord.setOrderDate(rs.getDate(1));
        	ord.setOrderId(rs.getInt(2));
        	ord.setFirstName(rs.getString(3));
        	ord.setLastName(rs.getString(4));
        	ord.setNumItems(rs.getInt(5));  
        	ord.setOrderTotal(rs.getFloat(6));
        	ord.setOrderStatus(rs.getString(7));  
        	ord.setOrderCost(rs.getFloat(8));

        	orderList.add(ord);
        }
      //return orderList (ObservableList of Orders)
        return orderList;
    }
    
  
  //*******************************
    // Get Statuses
    //*******************************
    public static ObservableList<String> getStatuses() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  `statusKey`,  `statusDesc` FROM `StatusType` ORDER BY `statusKey`";
        ObservableList<String> statuses = FXCollections.observableArrayList();
        
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs = DButils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the  method to get object
            while(rs.next()) {
            
            	statuses.add(rs.getString(2) + " " + rs.getString(1));
            }
            rs.close();
  
            return statuses;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    //*******************************
    // Get Items
    //*******************************
    public static ObservableList<Item> getItems() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT  * FROM `Items` ORDER BY descr";
        ObservableList<Item> items = FXCollections.observableArrayList();
        
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs = DButils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the  method to get object
            while(rs.next()) {
            
            	Item item = new Item();
            	
            	item.setItemId(rs.getInt(1));
            	item.setItemDesc(rs.getString(2));
            	item.setItemPrice(rs.getFloat(3));
            	item.setItemLb(rs.getInt(4));
            	item.setItemCost(rs.getFloat(5));
            	item.setItemQty(1);
            	item.setItemTotal(rs.getFloat(3));
            	
            	items.add(item);
            	System.out.println("(DAO) " +item.toString());
            }
            rs.close();
  
            return items;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    
    /* ----------------------------------------------------------------------------------------------------- */
    /*
     * 					THIS SECTION HANDLES WORK ORDER QUERIES
    */
    
    //*******************************
    //SELECT order Info
    //*******************************
    public static ObservableList<String> getOrderInfo(Integer orderId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
    	String selectStmt = "SELECT W.DateTime 'Timestamp',\r\n"
        		+ " W.orderId 'Order ID',\r\n"
        		+ " C.fName 'First Name',\r\n"
        		+ "	C.lName 'Last Name',\r\n"
        		+ "	C.phone 'Phone',\r\n"
        		+ "	C.email 'Email',\r\n"
        		+ "	ST.statusDesc 'Order Status',\r\n"
        		+ "	ST.statusKey,\r\n"
        		+ "	B.bName 'Bike',\r\n"
        		+ "	B.bikeId,\r\n"
        		+ "	W.note,\r\n"
        		+ "	C.custId,\r\n"
        		+ "	W.privNote\r\n"
        		+ "	FROM WorkOrders W\r\n"
        		+ "	left JOIN Customers C ON C.custId = W.custId\r\n"
        		+ "	left JOIN StatusType ST ON ST.statusKey = W.statusKey\r\n"
        		+ "	left JOIN Bikes B ON B.bikeId = W.bikeId\r\n"
        		+ "	WHERE W.orderId = '" + orderId + "' LIMIT 1";

        ObservableList<String> info = FXCollections.observableArrayList();
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);
            //Send ResultSet to the  method to get object
            
            rsOrders.next();

    		info.add(rsOrders.getString(1)); // date
    		info.add(rsOrders.getString(3)); // First name
    		info.add(rsOrders.getString(5)); // phone
    		info.add(rsOrders.getString(6)); // email
    		info.add(rsOrders.getString(7)); // status
    		info.add(rsOrders.getString(8)); // statuskey
    		info.add(rsOrders.getString(9)); // bike
    		info.add(rsOrders.getString(10)); // bikeId
    		info.add(rsOrders.getString(11)); // note          		
    		info.add(rsOrders.getString(12)); // custId
    		info.add(rsOrders.getString(4) ); // Last name
    		info.add(rsOrders.getString(13)); // private note
    		
    		
    		rsOrders.close();
    		
            return info;
            
            
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    

    //*************************************
    //Update Work Order
    //*************************************
    public static void updateOrder (OrderDataSingleton data) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	
    	String updateStmt = "UPDATE `WorkOrders` \nSET \t" 
    	+ " \n\t`note`='" + data.getOrderNote() 
    	+ "', \n\t`privNote`='" + data.getOrderPrivNote() 
    	+ "', \n\t`custId`='" + data.getCustId() 
      	+ "', \n\t`dateTime`='" +  data.getOrderDate() + " 00:00:00"
		+ "', \n\t`statusKey`='" + data.getOrderStatusKey()
		+ "', \n\t`bikeId`='" + data.getBikeId()
    	+ "' \nWHERE  `WorkOrders`.`orderId`='" + data.getOrderId() + "';";
    	
//    	String updateStmt = "UPDATE `Bikes`, `WorkOrders` \nSET \t`Bikes`.`bName`='" + data.getOrderBike() 
//    	+ "', \n\t`WorkOrders`.`note`='" + data.getOrderNote() 
//    	+ "', \n\t`custId`='" + data.getCustId() 
//      	+ "', \n\t`dateTime`='" +  data.getOrderDate() + " 00:00:00"
//		+ "', \n\t`statusKey`='" + data.getOrderStatusKey()
//    	+ "' \nWHERE  \n\t`Bikes`.`bikeId`='" + data.getBikeId() + "' AND `WorkOrders`.`orderId`='" + data.getOrderId() + "';";

        System.out.println(updateStmt);
        DButils.dbExecuteUpdate(updateStmt);
        

    }
    
  //*************************************
    // Insert new Work Order
    //*************************************
    public static void insertOrder (OrderDataSingleton data) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement

    	DButils.dbExecuteInsertOrderAndItems(data);

    }
    //*************************************
    // delete Work Order
    //*************************************
    public static void deleteOrder (OrderDataSingleton data) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement

    	DButils.dbExecuteDeleteOrder(data);

    }
    
    
    /* ----------------------------------------------------------------------------------------------------- */
    /*
     * 					THIS SECTION HANDLES BIKE QUERIES
    */
    
    // gets the bike name and id for work order bike selection.
    public static ObservableList<Bike> getCustomerBikes(Integer custId) throws SQLException, ClassNotFoundException {
    	
    	String selectStmt = " SELECT bikeId, bName FROM Bikes WHERE bikeCustId = " + custId + ";";
    	ObservableList<Bike> bikes = FXCollections.observableArrayList();
    	//Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs = DButils.dbExecuteQuery(selectStmt);
            
            Bike bike = new Bike();

        	bike.setBikeId(0); // Bike ID
        	bike.setBikeName("New Bike"); // Bike NAme
        	bike.setBikeCustId(custId); // Cust ID
 
        	bikes.add(bike);
        	
            //Send ResultSet to the  method to get object
            while(rs.next()) {
            	
            	bike = new Bike();

            	bike.setBikeId(rs.getInt(1)); // Bike ID
            	bike.setBikeName(rs.getString(2)); // Bike NAme
            	bike.setBikeCustId(custId); // Cust ID
     
            	bikes.add(bike);
            	
            	System.out.println("(DAO) Bike " + bike.toString());
            }
            rs.close();         
            return bikes;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    	
    }
    
    // gets the bike information for the bike info page.
    // bikeId, name , custId has already been set before
    public static Bike getBikeInfo(Bike bike) throws SQLException, ClassNotFoundException {
    	
    	String selectStmt = " SELECT * FROM Bikes WHERE bikeId = " + bike.getBikeId() + ";";
    	//Execute SELECT statement
    	 System.out.println(selectStmt);
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs = DButils.dbExecuteQuery(selectStmt);
            rs.first();
            bike.setBikeName(rs.getString(2));
        	bike.setSerial(rs.getString(5));
        	bike.setSize(rs.getString(6));
            bike.setChain(rs.getString(7));
            bike.setRearDer(rs.getString(8));
            bike.setFrontDer(rs.getString(9));
            bike.setRearTire(rs.getString(10));
            bike.setFrontTire(rs.getString(11));
            bike.setRearWheel(rs.getString(12));
            bike.setFrontWheel(rs.getString(13));
            bike.setRearBrake(rs.getString(14));
            bike.setFrontBrake(rs.getString(15));
            bike.setRearPads(rs.getString(16));
            bike.setFrontPads(rs.getString(17));
            bike.setCassette(rs.getString(18));
            
            	
            System.out.println("(DAO) Bike " + bike.toString());
            
            rs.close(); 
            
            return bike;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    	
    }
    
    // Updates bike information.
    public static void updateBikeInfo(Bike bike) throws SQLException, ClassNotFoundException {
    	
    	//Declare a UPDATE statement
    	String updateStmt = "UPDATE `Bikes` SET ";
    	
    	updateStmt += " `bName`= '" + bike.getBikeName() + "'";
    	
    	if (bike.getSerial() == null) {
    		updateStmt += ", `serial`= ''"; 
    	} else { updateStmt += ", `serial`= '" + bike.getSerial() + "'";}  	
    	
    	
    	if (bike.getSize() == null) {
    		updateStmt += ", `size`= ''";
    	} else { updateStmt += ", `size`= '" + bike.getSize() + "'";}
    	
    	if (bike.getChain() == null) {
    		updateStmt += ", `chain`= ''";
    	} else { updateStmt += ", `chain`= '" + bike.getChain() + "'";}
    	
    	if (bike.getRearDer() == null) {
    		updateStmt += ", `rearDer`= ''";
    	} else { updateStmt += ", `rearDer`= '" + bike.getRearDer() + "'";}
    	
    	if (bike.getFrontDer() == null) {
    		updateStmt += ", `frontDer`= ''";
    	} else { updateStmt += ", `frontDer`= '" + bike.getFrontDer() + "'";}
    	
    	if (bike.getRearTire() == null) {
    		updateStmt += ", `rearTire`= ''";
    	} else { updateStmt += ", `rearTire`= '" + bike.getRearTire() + "'";}
    	
    	if (bike.getFrontTire() == null) {
    		updateStmt += ", `frontTire`= ''";
    	} else { updateStmt += ", `frontTire`= '" + bike.getFrontTire() + "'";}

    	if (bike.getRearWheel() == null) {
    		updateStmt += ", `rearWheel`= ''";
    	} else { updateStmt += ", `rearWheel`= '" + bike.getRearWheel() + "'";}

    	if (bike.getFrontWheel() == null) {
    		updateStmt += ", `frontWheel`= ''";
    	} else { updateStmt += ", `frontWheel`= '" + bike.getFrontWheel() + "'";}

    	if (bike.getRearBrake() == null) {
    		updateStmt += ", `rearBrake`= ''";
    	} else { updateStmt += ", `rearBrake`= '" + bike.getRearBrake() + "'";}

    	if (bike.getFrontBrake() == null) {
    		updateStmt += ", `frontBrake`= ''";
    	} else { updateStmt += ", `frontBrake`= '" + bike.getFrontBrake() + "'";}

    	if (bike.getRearPads() == null) {
    		updateStmt += ", `rearPads`= ''";
    	} else { updateStmt += ", `rearPads`= '" + bike.getRearPads() + "'";}
    	
    	if (bike.getFrontPads() == null) {
    		updateStmt += ", `frontPads`= ''";
    	} else { updateStmt += ", `frontPads`= '" + bike.getFrontPads() + "'";}
    	
    	if (bike.getCassette() == null) {
    		updateStmt += ", `cassette`= ''";
    	} else { updateStmt += ", `cassette`= '" + bike.getCassette() + "'";}
    	
    	updateStmt += " WHERE  `bikeId`= "+ bike.getBikeId()+";";

        System.out.println(updateStmt);
        
        DButils.dbExecuteUpdate(updateStmt);

    }
    
    // when creating a new bike, first get a new Id, then insert the bike into list.
    public static void insertNewBike(OrderDataSingleton data) throws ClassNotFoundException, SQLException {
    	String selectStmt = "SELECT max(bikeId) FROM Bikes";
    	ResultSet rs = DButils.dbExecuteQuery(selectStmt);
    	rs.next();
    	data.setBikeId(rs.getInt(1) + 1);
    	rs.close();
    	
    	String updateStmt = "INSERT INTO `Bikes` (`bikeId`, `bName`, `bikeCustId`) "
    			+"VALUES ('" + data.getBikeId() + "', '" + data.getOrderBike() + "', '" + data.getCustId() + "');";
    	
    	DButils.dbExecuteUpdate(updateStmt);
    	
    	
    }
    
    
    /* ----------------------------------------------------------------------------------------------------- */
    /*
     * 					THIS SECTION HANDLES ITEM QUERIES
    */
  //*******************************
    //SELECT order items
    //*******************************
    public static ObservableList<Item> getOrderItems(Integer orderId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
//    	String selectStmt = "CALL `GetOrderItemsSold`('" + orderId + "')";
    	String selectStmt = " SELECT\r\n"
    			+ "	S.itemId \"Item ID\",\r\n"
    			+ "	S.soldName \"Item Description\",\r\n"
    			+ "	S.quantity \"Quantity\",\r\n"
    			+ "	S.soldPrice \"Item Price\",	\r\n"
    			+ "	S.quantity * S.soldPrice AS Subtotal,\r\n"
    			+ " S.cost, S.labour"
    			+ "	FROM ItemsSold S \r\n"
    			+ "	WHERE S.orderId = '"+ orderId + "'  ORDER BY `labour` desc, `Item Description`;";
        ObservableList<Item> items = FXCollections.observableArrayList();
        OrderDataSingleton data = OrderDataSingleton.getInstance();
        data.setOrderTotal((float) 0.0);
        
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs = DButils.dbExecuteQuery(selectStmt);

            //Send ResultSet to the  method to get object
            while(rs.next()) {
            	
            	Item item = new Item();

            	item.setItemDesc(rs.getString(2)); // item Name
            	item.setItemQty(rs.getFloat(3)); // item Qty
            	item.setItemPrice(rs.getFloat(4)); // item Price
            	item.setItemId(rs.getInt(1)); // item Id
            	item.setItemTotal(rs.getFloat(5)); // Total
            	item.setItemCost(rs.getFloat(6)); // cost
            	item.setItemLb(rs.getInt(7));
            	data.setOrderTotal(data.getOrderTotal() + item.getItemTotal());
            	items.add(item);
            	
            	System.out.println("(DAO) Order " + item.toString());
            }
            rs.close();         
            return items;
            
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }
    }
    
    public static ObservableList<Item> getAllItems(ResultSet rs) throws SQLException{
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		// loop through row in the result set
		while(rs.next()) {
			Item item = new Item();
			item.setItemId(rs.getInt(1));
			item.setItemDesc(rs.getString(2));
			item.setItemPrice(rs.getFloat(3));
			item.setItemLb(rs.getInt(4));
			item.setItemCost(rs.getFloat(5));
			
			itemList.add(item);
		}
		return itemList;
	}
    
    public static ObservableList<Item> findItems (String key) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
    	String selectStmt = null;

		selectStmt = "SELECT * FROM Items WHERE"
        		+ " descr LIKE " + key + ";";

        //Execute SELECT statement
        try {
        	//Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);

            
            //Send ResultSet and get list
            ObservableList<Item> ordList  = getAllItems(rsOrders);
//            System.out.println(ordList.get(2).getEmail());
            //Return employee object
            rsOrders.close();
            return ordList;
            
        } catch (SQLException e) {
            System.out.println("While searching for " + key + "id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    //*************************************
    //Insert new item and return its new id 
    //*************************************
    public static Integer insertItem (Item item) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	Integer itemId = null;
    	String updateStmt = "INSERT INTO `Items` "
    			+ "(`descr`, `price`, `labour`, `cost`) "
    			+ "VALUES ('"+ item.getItemDesc() +"', '"+ item.getItemPrice() 
    			+"', '"+item.getItemLb()+"', '"+ item.getItemCost()+"');";

        System.out.println(updateStmt);
        
        DButils.dbExecuteUpdate(updateStmt);
        
        updateStmt = "SELECT max(itemId) FROM Items";
        ResultSet rs = DButils.dbExecuteQuery(updateStmt);
        rs.next();

        itemId = rs.getInt(1);
        rs.close();
        
		return itemId;

    }
    //*************************************
    //DELETE Items from Work Order
    //
    //*************************************
    public static void removeItems (Integer orderId, List<Integer> removedItemsId) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	String idList = "(";
    	
    	for (int i = 0; i < removedItemsId.size(); i++) {
    			if (i != removedItemsId.size() - 1) {
    				idList += removedItemsId.get(i) + ",";
    			}
    			else {
    				idList += removedItemsId.get(i) + ")";
    			}
    		}
    	System.out.println(idList);

        String updateStmt = "   DELETE FROM ItemsSold\n" +
                        "      WHERE orderId = '" + orderId + "'\n" +
                        "    AND itemId in " + idList.toString() + ";";

        System.out.println(updateStmt);
          DButils.dbExecuteUpdate(updateStmt);
    }
    
  //*************************************
    //Delete Item
    //*************************************
    public static void deleteItem (Integer itemId) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	String updateStmt = "DELETE FROM `Items` WHERE  `itemId`=" + itemId +";";

        System.out.println(updateStmt);
        
        DButils.dbExecuteUpdate(updateStmt);

		return;

    }
    //*************************************
    //Update Item
    //*************************************
    public static void updateItem (Item item) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	String updateStmt = "UPDATE `Items` SET `descr`='"+item.getItemDesc()
    					+"', `price`="+ item.getItemPrice()
    					+", `labour`="+ item.getItemLb() 
    					+", `cost`="+item.getItemCost() 
    					+" WHERE  `itemId`="+item.getItemId()+";";

        System.out.println(updateStmt);
        
        DButils.dbExecuteUpdate(updateStmt);

		return;

    }
    
    
    /* ----------------------------------------------------------------------------------------------------- */
    /*
     * 					THIS SECTION HANDLES CUSTOMER QUERIES
    */
    
	  //*******************************
    //SELECT all customers in DB
    //*******************************
	
	public static ObservableList<Customer> showCustomers() throws SQLException, ClassNotFoundException{
		// Select statement
		String selectStmt = "SELECT * FROM Customers ORDER BY fName, lName";
		// execute statement
		try {
			//get ResultSet fromdbExecuteQuery method
			ResultSet rsCustomers = DButils.dbExecuteQuery(selectStmt);
			// set ResultSet to the method to get customer object
			ObservableList<Customer> cstmList = getAllCustomers(rsCustomers);
			// return customer object list
			return cstmList;
			
		} catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }	
		
	}
	public static Customer getCustomer(Integer custId) throws SQLException, ClassNotFoundException{
		// Select statement
		String selectStmt = "SELECT * FROM Customers WHERE custId = '" + custId + "';";
		// execute statement
		try {
			//get ResultSet fromdbExecuteQuery method
			ResultSet rsCustomers = DButils.dbExecuteQuery(selectStmt);
			// set ResultSet to the method to get customer object
			ObservableList<Customer> cstmList = getAllCustomers(rsCustomers);
			// return customer object list
			return cstmList.get(0);
			
		} catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            //Return exception
            throw e;
        }	
		
	}
	
	public static ObservableList<Customer> getAllCustomers(ResultSet rs) throws SQLException{
		ObservableList<Customer> customerList = FXCollections.observableArrayList();
		// loop through row in the result set
		while(rs.next()) {
			Customer customer = new Customer();
			customer.setCustId(rs.getInt(1));
			customer.setFirstName(rs.getString(2));
			customer.setLastName(rs.getString(3));
			customer.setPhone(rs.getString(4));
			customer.setEmail(rs.getString(5));
			customer.setCreationDate(rs.getDate(6));
			customer.setNote(rs.getString(7));
			
			customerList.add(customer);
		}
		return customerList;
	}
	
	//******************************************
    //Insert new customer and return its new id 
    //******************************************
    public static Integer insertCustomer (Customer customer) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	Integer customerId = null;
    	java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
    	String updateStmt = "INSERT INTO `Customers` "
    			+ "(`fname`, `lname`, `email`, `phone`, `createdDate`, `note`) "
    			+ "VALUES ('"+ customer.getFirstName() +"', '"+ customer.getLastName() 
    			+"', '"+customer.getEmail()+"', '"+ customer.getPhone()+"', '"+date+"', '" + customer.getNote().toString() + "');";

        System.out.println(updateStmt);
        
        DButils.dbExecuteUpdate(updateStmt);
        
        updateStmt = "SELECT max(custId) FROM Customers";
        ResultSet rs = DButils.dbExecuteQuery(updateStmt);
        rs.next();

        customerId = rs.getInt(1);
        rs.close();
        
		return customerId;

    }
    
  //*************************************
    //Update Customer Data
    //*************************************
    public static void updateCustomer (Customer customer) throws SQLException, ClassNotFoundException {
        //Declare a UPDATE statement
    	System.out.println(customer.toString());
    	String updateStmt = "UPDATE `Customers` SET `fName`= '" + customer.getFirstName() 
    	+ "', `lName`='"+customer.getLastName()
    	+ "', `phone`='"+customer.getPhone()
    	+ "', `email`='"+customer.getEmail()
    	+ "', `note`='"+customer.getNote().toString()
    	+ "' WHERE  `custId`="+customer.getCustId()+";";

        System.out.println(updateStmt);
        DButils.dbExecuteUpdate(updateStmt);
        

    }
    public static ObservableList<Customer> findCustomer (String key) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
    	String selectStmt = null;

		selectStmt = "SELECT * FROM Customers WHERE"
        		+ " fName LIKE " + key
        		+ " OR lName LIKE " + key
        		+ " OR phone LIKE " + key
        		+ " OR note LIKE " + key
        		+ " OR email LIKE " + key + ";";

        //Execute SELECT statement
        try {
        	//Get ResultSet from dbExecuteQuery method
            ResultSet rsOrders = DButils.dbExecuteQuery(selectStmt);

            
            //Send ResultSet and get list
            ObservableList<Customer> ordList  = getAllCustomers(rsOrders);
//            System.out.println(ordList.get(2).getEmail());
            //Return employee object
            rsOrders.close();
            return ordList;
            
        } catch (SQLException e) {
            System.out.println("While searching for " + key + "id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    
    

}   
   