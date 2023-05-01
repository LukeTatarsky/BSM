package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;


import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;



public class DButils {
	private static OrderDataSingleton data = OrderDataSingleton.getInstance();
	
    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
  //Connection
    private static Connection conn = null;
    
    //Connection String
    private static final String address = "jdbc:mysql://192.168.0.1";  // router
    private static final String uName = "null";
    private static final String pass = "null";
	

	public static void dbConnect() throws SQLException, ClassNotFoundException {
		
		//Connection conn = null;
//		PreparedStatement psQuery = null;
//		ResultSet rsResults = null;
		/*
        //Setting Oracle JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
        System.out.println("Oracle JDBC Driver Registered!");
		*/
		// get connection
		try {
			conn = DriverManager.getConnection(address, uName, pass);
			
			
		} catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;		
			} 
		}
	
	//Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
    
    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedRowSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs = null;
        
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: \n" + queryStmt + "\n");
            //Create statement
            stmt = conn.createStatement();
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(resultSet);
            
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
            
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }
    

    
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
           	data.setSqlError(true);
           	data.setSqlErrorString(e.getMessage());            
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
    //DB Execute for order update with item updates
    public static void dbExecuteUpdateOrderAndItems(OrderDataSingleton data) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        
        String sqlStmt = "UPDATE `WorkOrders` \nSET \t`bikeId`='" + data.getBikeId() 
    	+ "', \n\t`note`='" + data.getOrderNote().replaceAll("'", "") 
    	+ "', \n\t`custId`='" + data.getCustId() 
      	+ "', \n\t`dateTime`='" +  data.getOrderDate() + " 00:00:00"
		+ "', \n\t`statusKey`='" + data.getOrderStatusKey()
    	+ "' \nWHERE  `orderId`='" + data.getOrderId() + "';";
        System.out.println("__________"+ sqlStmt);

        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();            
            //Run executeUpdate operation with given sql statement
            stmt.addBatch(sqlStmt);
            
//            System.out.println("__________");
            sqlStmt = "DELETE FROM `ItemsSold` WHERE  `orderId`= " + data.getOrderId() + ";";  
            
            System.out.println("__________"+ sqlStmt);            
            stmt.addBatch(sqlStmt);
            
//            System.out.println("__________");
            for (Item item : data.getOrderItems()) {
            	
            	sqlStmt = String.format("INSERT INTO `ItemsSold` VALUES ('%s','%s','%s','%s','%s','%s','%s');", 
            			data.getOrderId().toString(),
            			item.getItemId().toString(),
            			item.getItemQty().toString(),
            			item.getItemPrice().toString(),
            			item.getItemDesc().replaceAll("'", ""),
            			item.getItemCost().toString(),
            			item.getItemLb());
            	System.out.println("__________"+ sqlStmt);
            	
            	stmt.addBatch(sqlStmt);            	
            }
            stmt.executeBatch();
            
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
  //DB Execute for order update with item updates
    public static void dbExecuteInsertOrderAndItems(OrderDataSingleton data) throws SQLException, ClassNotFoundException {

    	//Declare statement as null
        Statement stmt = null;

        try {

            // get Order Id          
            String getIDStmnt = "SELECT max(orderId) FROM WorkOrders";
            ResultSet rs = DButils.dbExecuteQuery(getIDStmnt);
            rs.next();
            data.setOrderId(rs.getInt(1) +1 );
            rs.close();
            System.out.println("__________"+ data.getOrderId().toString());
            
            String sqlStmt = String.format("INSERT INTO `WorkOrders` VALUES ('%s','%s','%s','%s','%s','%s','%s');", 
            		data.getOrderDate(),
            		data.getOrderId(),
            		data.getCustId(),
            		data.getBikeId(),
            		data.getOrderStatusKey(),
            		data.getOrderNote().replaceAll("'", ""),
            		data.getOrderPrivNote().replaceAll("'", ""));
            
            System.out.println("__________"+ sqlStmt);
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            
            //Create Statement
            stmt = conn.createStatement();  
            
            // add insert into workorders
            stmt.addBatch(sqlStmt);   
            
            sqlStmt = "DELETE FROM `ItemsSold` WHERE  `orderId`= " + data.getOrderId() + ";";  
            
            System.out.println("__________"+ sqlStmt);            
            stmt.addBatch(sqlStmt);
            
//            System.out.println("__________");
            for (Item item : data.getOrderItems()) {
            	
            	sqlStmt = String.format("INSERT INTO `ItemsSold` VALUES ('%s','%s','%s','%s','%s','%s','%s');", 
            			data.getOrderId().toString(),
            			item.getItemId().toString(),
            			item.getItemQty().toString(),
            			item.getItemPrice().toString(),
            			item.getItemDesc().replaceAll("'", ""),
            			item.getItemCost().toString(),
            			item.getItemLb());
            	System.out.println("__________"+ sqlStmt);
            	
            	stmt.addBatch(sqlStmt);            	
            }
            stmt.executeBatch();
            
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
  //DB Execute for order deletion
    public static void dbExecuteDeleteOrder(OrderDataSingleton data) throws SQLException, ClassNotFoundException {
    	//Declare statement as null
        Statement stmt = null;
        
      //Connect to DB (Establish Oracle Connection)
        dbConnect();
      //Create Statement
        stmt = conn.createStatement(); 
        
        String  deleteStmnt = "DELETE FROM `WorkOrders` WHERE  `orderId`=" + data.getOrderId() + ";";
        System.out.println(deleteStmnt);
        stmt.addBatch(deleteStmnt); 
        
        deleteStmnt = "DELETE FROM `ItemsSold` WHERE  `orderId`= " + data.getOrderId() + ";"; 
        System.out.println(deleteStmnt);
        stmt.addBatch(deleteStmnt); 
        
      
        
       // execute
        stmt.executeBatch();
        
      //Close connection
        dbDisconnect();
        
    }
}
