package com.systemdesign.ObserverDesign_Pattern;

public class MobileObserverImple implements NotificationObserver{

	int mobileNo;
	StockObservable observable;

	public MobileObserverImple(int mobileNo , StockObservable observable) {

		this.mobileNo = mobileNo ; 
		this.observable = observable;
	}

	@Override
	public void update() {

		int data = observable.getStockCount();
		sendEmail(mobileNo , "Product in stock Mob : "+ data);
	}

	private void sendEmail(int emailId , String mess) {

		System.out.println(mess);
	}
}
