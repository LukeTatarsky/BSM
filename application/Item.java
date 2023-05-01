package application;

import javafx.beans.property.*;

public class Item  {
	

	//Declare Work Order Table Columns
    private SimpleFloatProperty itemPrice;
    private SimpleFloatProperty itemCost;
    private SimpleFloatProperty itemQty;
    private SimpleFloatProperty itemTotal;
    private SimpleStringProperty itemDesc;
    private SimpleIntegerProperty itemId;
    private SimpleIntegerProperty itemLb;

//    private StringProperty note;


	//Constructor
	public Item() {
		this.itemPrice = new SimpleFloatProperty();
		this.itemCost = new SimpleFloatProperty();
		this.itemQty = new SimpleFloatProperty();
		this.itemTotal = new SimpleFloatProperty();
		this.itemDesc = new SimpleStringProperty();
		this.itemId = new SimpleIntegerProperty();
		this.itemLb = new SimpleIntegerProperty();

//		this.note = new SimpleStringProperty();
	}

    // itemDesc
    public String getItemDesc() {
        return itemDesc.get();
    }
    public void setItemDesc(String OrderId){
        this.itemDesc.set(OrderId);
    }
    public SimpleStringProperty itemDescProperty(){
        return itemDesc;
    }
	
    
    // total
    public Float getItemTotal() {
        return itemTotal.get();
    }
    public void setItemTotal(float f){
        this.itemTotal.set(f);
    }
    public SimpleFloatProperty itemTotalProperty(){
        return itemTotal;
    }
    
 // iD
    public Integer getItemId() {
        return itemId.get();
    }
    public void setItemId(Integer f){
        this.itemId.set(f);
    }
    public SimpleIntegerProperty itemIdProperty(){
        return itemId;
    }
    
    
    // Price
    public Float getItemPrice() {
        return itemPrice.get();
    }
    public void setItemPrice(float f){
        this.itemPrice.set(f);
    }
    public SimpleFloatProperty itemPriceProperty(){
        return itemPrice;
    }
    
    
    // Cost
    public Float getItemCost() {
		return itemCost.get();
	}
    
	public void setItemCost(float f) {
		this.itemCost.set(f);
	}
	public SimpleFloatProperty itemCostProperty(){
		return itemCost;
	}
    
	
	
    // qty
    public Float getItemQty() {
        return itemQty.get();
    }
    public void setItemQty(float f){
        this.itemQty.set(f);
    }
    public SimpleFloatProperty itemQtyProperty(){
        return itemQty;
    }
    
    // Labour
    public Integer getItemLb() {
        return itemLb.get();
    }
    public void setItemLb(Integer f){
        this.itemLb.set(f);
    }
    public SimpleIntegerProperty itemLbProperty(){
        return itemLb;
    }

	@Override
	public String toString() {
		return "Item [Price= " + itemPrice.get() + ", Cost= "+ itemCost.get() + ", Qty= " + itemQty.get() + ", Total= " + itemTotal.get() + ", Desc= "
				+ itemDesc.get() + ", Id= " + itemId.get() + ", Lb= " + itemLb.get() + "]";
	}

	
	public int compareId(Item i1, Item i2) {		
		return Integer.compare(i1.getItemId(),i2.getItemId());
	}
	
	
	public Boolean compareName(Item i1, Item i2) {		
		return i1.getItemDesc().equalsIgnoreCase(i2.getItemDesc());
	}

	
	
}