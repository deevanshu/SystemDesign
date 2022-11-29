package com.systemdesign.CarRental;

public class Vehicle {

	int vehicleNo ;
	int vehicleId;
	VehicleType vehicleType;
	int kmDriven;
	int average;
	int cc;
	Status status;
	
	public int getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(int vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public int getKmDriven() {
		return kmDriven;
	}
	public void setKmDriven(int kmDriven) {
		this.kmDriven = kmDriven;
	}
	public int getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
