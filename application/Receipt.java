package application;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Receipt {

	public static void print_order_to_file(OrderDataSingleton data) {

		try {
			PrintWriter output = new PrintWriter("C:\\Users\\LT\\Desktop\\order.csv");
			
			output.println(data.getOrderFname() + " " + data.getOrderLname());
			output.println(data.getOrderBike());
			output.println(data.getOrderStatus());
			output.println(data.getOrderDate());
			output.println(data.getOrderId());
			output.println("");
			
			output.println("Name,Qty,Price,Total");
			for (Item item : data.getOrderItems()) {
				output.println(item.getItemDesc()+","+item.getItemQty()+","+item.getItemPrice());
			}
			
			output.println("-----");
			output.print(data.getOrderNote());
			
			
		    output.close();
			System.out.println("finished printing to file");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
