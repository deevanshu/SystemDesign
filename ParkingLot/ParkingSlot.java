package PARKING__LOT;

public class ParkingSlot {

	String name;
	boolean isAvailable = true;
	Vehicle vehicle;
	ParkingSlotType parkingSlotType;

	public ParkingSlot(String name, ParkingSlotType parkingSlotType) {
		this.name = name;
		this.parkingSlotType = parkingSlotType;
	}

	protected void addVehicle(Vehicle vehicle){
		this.vehicle = vehicle;
		this.isAvailable=false;
	}
	
	public void removeVehicle() {
	    this.vehicle = null;
	    this.isAvailable = true;
	}
	
	public ParkingSlotType getParkingSlotType() {
		return this.parkingSlotType;
	}


}