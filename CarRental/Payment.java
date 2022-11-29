package com.systemdesign.CarRental;

public class Payment {

	Bill bill;
	
	public boolean payBill(Bill bill) {
		bill.isPaid = true;
		return true;
	}
}
