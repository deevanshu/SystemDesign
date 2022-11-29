package com.systemdesign.CarRental;

import java.sql.Date;

public class Reservation {

	int reservationId;
	User user;
	Vehicle vehicle;
	Date bookingDate;
	Date bookFrom;
	Date boookedTill;
	ReservationStatus resevationStatus;
    Location loction ;

	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public ReservationStatus getResevationStatus() {
		return resevationStatus;
	}
	public void setResevationStatus(ReservationStatus resevationStatus) {
		this.resevationStatus = resevationStatus;
	}
	public Location getLoction() {
		return loction;
	}
	public void setLoction(Location loction) {
		this.loction = loction;
	}
	public int getId() {
		return reservationId;
	}
	public void setId(int reservationId) {
		this.reservationId = reservationId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getBookFrom() {
		return bookFrom;
	}
	public void setBookFrom(Date bookFrom) {
		this.bookFrom = bookFrom;
	}
	public Date getBoookedTill() {
		return boookedTill;
	}
	public void setBoookedTill(Date boookedTill) {
		this.boookedTill = boookedTill;
	}

	public  int createReservation(User user , Vehicle vehicle) {
		
		reservationId = 12;
		this.user = user;
		this.vehicle = vehicle;
		resevationStatus = ReservationStatus.SCHEDULLED;
		
		return reservationId;
	}
	
	// UPDATE RESERVATION METHOD
}
