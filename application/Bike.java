package application;

import javafx.beans.property.*;

public class Bike  {
	
	private SimpleStringProperty bikeName;  
	private SimpleIntegerProperty bikeId;      
    private SimpleIntegerProperty bikeCustId;
    private String serial;
    private String size;
    private String chain;
    private String rearDer;
    private String frontDer;
    private String rearTire;
    private String frontTire;
    private String rearWheel;
    private String frontWheel;
    private String rearBrake;
    private String frontBrake;
    private String rearPads;
    private String frontPads;
    private String cassette;

	//Constructor
	public Bike() {
		this.bikeName = new SimpleStringProperty();
		this.bikeId = new SimpleIntegerProperty();
		this.bikeCustId = new SimpleIntegerProperty();

	}
	
	
    public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getRearDer() {
		return rearDer;
	}

	public void setRearDer(String rearDer) {
		this.rearDer = rearDer;
	}

	public String getFrontDer() {
		return frontDer;
	}

	public void setFrontDer(String frontDer) {
		this.frontDer = frontDer;
	}

	public String getRearTire() {
		return rearTire;
	}

	public void setRearTire(String rearTire) {
		this.rearTire = rearTire;
	}

	public String getFrontTire() {
		return frontTire;
	}

	public void setFrontTire(String frontTire) {
		this.frontTire = frontTire;
	}

	public String getRearWheel() {
		return rearWheel;
	}

	public void setRearWheel(String rearWheel) {
		this.rearWheel = rearWheel;
	}

	public String getFrontWheel() {
		return frontWheel;
	}

	public void setFrontWheel(String frontWheel) {
		this.frontWheel = frontWheel;
	}

	public String getRearBrake() {
		return rearBrake;
	}

	public void setRearBrake(String rearBrake) {
		this.rearBrake = rearBrake;
	}

	public String getFrontBrake() {
		return frontBrake;
	}

	public void setFrontBrake(String frontBrake) {
		this.frontBrake = frontBrake;
	}

	public String getRearPads() {
		return rearPads;
	}

	public void setRearPads(String rearPads) {
		this.rearPads = rearPads;
	}

	public String getFrontPads() {
		return frontPads;
	}

	public void setFrontPads(String frontPads) {
		this.frontPads = frontPads;
	}

	

    // Bike Name
    public String getBikeName() {
        return bikeName.get();
    }
    public void setBikeName(String name){
        this.bikeName.set(name);
    }
    public SimpleStringProperty bikeNameProperty(){
        return bikeName;
    }
	
    
    // iD
    public Integer getBikeId() {
        return bikeId.get();
    }
    public void setBikeId(Integer f){
        this.bikeId.set(f);
    }
    public SimpleIntegerProperty bikeIdProperty(){
        return bikeId;
    }
    
 // iD
    public Integer getBikeCustId() {
        return bikeCustId.get();
    }
    public void setBikeCustId(Integer f){
        this.bikeCustId.set(f);
    }
    public SimpleIntegerProperty bikeCustIdProperty(){
        return bikeCustId;
    }

    // used to populate the bike selection choice box.
    @Override
    public String toString() {
        return bikeName.get();
    }


	public String getCassette() {
		return cassette;
	}


	public void setCassette(String cassette) {
		this.cassette = cassette;
	}

	
	
}