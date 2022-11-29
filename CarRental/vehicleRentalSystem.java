package com.systemdesign.CarRental;

import java.util.List;

public class vehicleRentalSystem {

	List<User> usersList;
	List<Store> storesList;
	
	public vehicleRentalSystem(List<User> users, List<Store> stores) {
		this.usersList = users;
		this.storesList = stores;
	}
	
	
	public Store getStore(Location location) {
		
		return storesList.get(0);
	}
	
	
	// ADD USERS / REMOVE USERS
	
	// ADD STORES / REMOVE STORES
}
