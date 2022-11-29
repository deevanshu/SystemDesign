package com.systemdesign.ParkingLot;
import java.util.List;
import java.util.Map;

public class ParkingLot {

	private String nameOfParkingLot;
	private Address address;
	private List<ParkingFloor> parkingFloors;

	private static ParkingLot parkingLot=null; // for singleton instance

	private  ParkingLot(String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors) {
		this.nameOfParkingLot = nameOfParkingLot;
		this.address = address;
		this.parkingFloors = parkingFloors;
	}

	public static ParkingLot getInstance (String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors) {
		if(parkingLot == null){
			parkingLot = new ParkingLot(nameOfParkingLot,address,parkingFloors);
		}
		return parkingLot;
	}

	public void addFloors(String name, Map<ParkingSlotType, Map<String,ParkingSlot>> parkSlots){
		ParkingFloor parkingFloor = new ParkingFloor(name,parkSlots);
		parkingFloors.add(parkingFloor);
	}

	public void removeFloors(ParkingFloor parkingFloor){
		parkingFloors.remove(parkingFloor);
	}

	public Ticket assignTicket(Vehicle vehicle){

		//to assign ticket we need parking slot for this vehicle

		ParkingSlot parkingSlot = getParkingSlotForVehicleAndPark(vehicle);
		if(parkingSlot == null) return null;
		Ticket parkingTicket = createTicketForSlot(parkingSlot,vehicle);
		//persist ticket to database
		return parkingTicket;
	}

	private ParkingSlot getParkingSlotForVehicleAndPark(Vehicle vehicle) {
		ParkingSlot parkingSlot=null;
		for(ParkingFloor floor : parkingFloors){
			parkingSlot = floor.getRelevantSlotForVehicleAndPark(vehicle);
			if(parkingSlot!= null) break;
		}

		return parkingSlot;
	}

	private Ticket createTicketForSlot(ParkingSlot parkingSlot, Vehicle vehicle) {
		return Ticket.createTicket(vehicle,parkingSlot);
	}

	public double scanAndPay(Ticket ticket){
		long endTime = System.currentTimeMillis();
		ticket.getParkingSlot().removeVehicle(ticket.getVehicle());
		int duration = (int) (endTime-ticket.getStartTime())/1000;
		double price = ticket.getParkingSlot().getParkingSlotType().getPriceForParking(duration);
		//persist record to database
		return price;
	}
}

class Address {
	String street;
	String block;
	String city;
	String state;
	String country;

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}

enum ParkingSlotType {
	TwoWheeler{
		public double getPriceForParking(long duration){
			return duration*0.05;
		}
	},
	Compact{
		public double getPriceForParking(long duration){
			return duration*0.075;
		}
	},
	Medium{
		public double getPriceForParking(long duration){
			return duration*0.09;
		}
	},
	Large{
		public double getPriceForParking(long duration){
			return duration*0.10;
		}
	};

	public abstract double getPriceForParking(long duration);
}

class Ticket {
	String ticketNumber;
	long startTime;
	long endTime;
	Vehicle vehicle;
	ParkingSlot parkingSlot;

	public static Ticket createTicket(Vehicle vehicle,ParkingSlot parkingSlot){

		Ticket ticket = new Ticket();
		ticket.setParkingSlot(parkingSlot);
		ticket.setStartTime(System.currentTimeMillis());
		ticket.setVehicle(vehicle);
		ticket.setTicketNumber(vehicle.getVehicleNumber()+System.currentTimeMillis());

		return ticket;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}

	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}
}

class Vehicle {

	String vehicleNumber;
	VehicleCategory vehicleCategory;

	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public VehicleCategory getVehicleCategory() {
		return vehicleCategory;
	}
	public void setVehicleCategory(VehicleCategory vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}
}

enum VehicleCategory {
	TwoWheeler, // 2 wheeler slot type 
	Hatchback, // compact slot type
	Sedan,     // medium   slot type
	SUV,       // medium   slot type
	Bus        // large   slot type
} 
