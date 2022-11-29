package com.systemdesign.CarRental;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static List<Vehicle> vehicles;
	private static Location location;
	private static List<User> users;
	private static List<Store>stores ;

	public static void main(String args[]) {

		users = addUser();
		vehicles = addVehicle();
		stores = addStore();

		location = new Location();
		location.pinCode = 247001;
		location.state = "UP";
		location.city ="SRE";
		location.address = "C8";


		// 1: User search store based on Location
		vehicleRentalSystem  rentalSystem = new vehicleRentalSystem(users, stores);
		User mainUser = users.get(0);
		Store store = rentalSystem.getStore(location);

		// 2: get All Vehicles u r interested in (based upon filters)
		List<Vehicle> vehicleList =  store.getVehInventory().getVehicle();
		Vehicle selectedVehicle = null ;
		for(Vehicle vehicle : vehicleList) {
			
			if(vehicle.getVehicleType()==VehicleType.CAR) {
				selectedVehicle = vehicle;
			}
		}
		
		// 3: Reserving the Particular Vehicle 
		Reservation reservation  =  new Reservation();
		int reservtionId = reservation.createReservation(mainUser, selectedVehicle);
		
		// 4: Generate The Bill
		Bill bill = new Bill(reservation);
		
		// 5: Make Payment
		Payment payment = new Payment();
		payment.payBill(bill);
		
		// 6: Update Reservation , then complete Reservation based upon reservation Id 
		
		// 7: store.completeReservation(Reservation object);
	}
	private static List<User> addUser() {

		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(100);
		user1.setDrivingLicesne(12345);

		users.add(user1);
		return users;
	}

	private static List<Store> addStore() {

		List<Store> stores = new ArrayList<>();
		Store store = new Store();
		store.storeId=1;
		store.setStoreLocation(location);

		VehicleInverntoryMgmt vehicleInverntoryMgmt =  new VehicleInverntoryMgmt();
		vehicleInverntoryMgmt.setVehicle(vehicles);

		store.setVehInventory(vehicleInverntoryMgmt);

		return stores;
	}

	private static List<Vehicle> addVehicle() {

		List<Vehicle> vehicles = new ArrayList<>();

		Vehicle vehicle1 = new Car();
		vehicle1.setVehicleId(1);
		vehicle1.setVehicleType(VehicleType.CAR);

		vehicles.add(vehicle1);

		return vehicles;
	}
}
