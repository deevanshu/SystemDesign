package com.systemdesign.CarRental;

public class Bill {

	Boolean isPaid;
	double Amount;
	Reservation reservation;
	
	public Bill(Reservation reservation) {
		
		this.reservation = reservation;
		this.Amount = calculateBillAmount();
		this.isPaid = false;
	}
	public double calculateBillAmount() {
		
		return 20;	
		
	}
}
