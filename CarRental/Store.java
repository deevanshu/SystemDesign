package com.systemdesign.CarRental;

import java.util.List;

public class Store {

	int storeId;
	VehicleInverntoryMgmt vehInventory ;
	Location storeLocation;
	List<Reservation> reservation;
	
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public VehicleInverntoryMgmt getVehInventory() {
		return vehInventory;
	}
	public void setVehInventory(VehicleInverntoryMgmt vehInventory) {
		this.vehInventory = vehInventory;
	}
	public Location getStoreLocation() {
		return storeLocation;
	}
	public void setStoreLocation(Location storeLocation) {
		this.storeLocation = storeLocation;
	}
	public List<Reservation> getReservation() {
		return reservation;
	}
	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}
	
	// Complete Reservation based upon reservation object 
}
