package application;

import java.sql.Date;

import javafx.beans.property.*;

public class Customer  {
	

	@Override
	public String toString() {
		return "Customer [custId= " + custId.get() + ", firstName= " + firstName.get() + ", lastName= " + lastName.get() + ", phone= " + phone.get()
				+ ", email= " + email.get() + "]";
	}


	//Declare Customer data
    private SimpleIntegerProperty custId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private String note;
    private Date creationDate;
//    private SimpleObjectProperty<Date> custDate;


	//Constructor
	public Customer() {
		
		this.custId = new SimpleIntegerProperty();
		this.firstName = new SimpleStringProperty();
		this.lastName = new SimpleStringProperty();
		this.phone = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
	}
	 // iD
    public Integer getCustId() {
        return custId.get();
    }
    public void setCustId(Integer f){
        this.custId.set(f);
    }
    public SimpleIntegerProperty custIdProperty(){
        return custId;
    }
    
    // first name
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String OrderId){
        this.firstName.set(OrderId);
    }
    public SimpleStringProperty firstNameProperty(){
        return firstName;
    }
    // last name
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String OrderId){
        this.lastName.set(OrderId);
    }
    public SimpleStringProperty lastNameProperty(){
        return lastName;
    }
   
    // Phone
    public String getPhone() {
        return phone.get();
    }
    public void setPhone(String OrderId){
        this.phone.set(OrderId);
    }
    public SimpleStringProperty phoneProperty(){
        return phone;
    }
    
    // Phone
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String OrderId){
        this.email.set(OrderId);
    }
    public SimpleStringProperty emailProperty(){
        return email;
    }
    
    
	public int compareId(Customer i1, Customer i2) {		
		return Integer.compare(i1.getCustId(),i2.getCustId());
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}	
	
}




