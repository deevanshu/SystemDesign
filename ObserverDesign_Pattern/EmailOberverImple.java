package com.systemdesign.ObserverDesign_Pattern;

public class EmailOberverImple implements NotificationObserver{

	String emailId;
	StockObservable observable;

	public EmailOberverImple(String emailId , StockObservable observable) {

		this.emailId = emailId ; 
		this.observable = observable;
	}

	@Override
	public void update() {

		int data = observable.getStockCount();
		sendEmail(emailId , "Product in stock Email with quantity : "+ data);
	}

	private void sendEmail(String emailId , String mess) {

		System.out.println(mess);
	}
}
