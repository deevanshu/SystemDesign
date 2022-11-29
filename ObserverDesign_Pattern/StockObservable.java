package com.systemdesign.ObserverDesign_Pattern;

public interface StockObservable {

	public void add(NotificationObserver observer);

	public void remove(NotificationObserver observer);

	public void setStockCount(int data);

	public int getStockCount();
	
	public void notifySubscribers();
}
