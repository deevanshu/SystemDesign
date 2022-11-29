package com.systemdesign.ObserverDesign_Pattern;

import java.util.ArrayList;
import java.util.List;

public class IphoneStockObservableImplementation implements StockObservable{

	public List<NotificationObserver> observerList = new ArrayList<>();
	int dataCount = 0;


	@Override
	public void add(NotificationObserver observer) {
		observerList.add(observer);

	}

	@Override
	public void remove(NotificationObserver observer) {
		observerList.remove(observer);

	}

	@Override
	public void setStockCount(int data) {
		
		dataCount = dataCount + data;	
		notifySubscribers();
	}

	@Override
	public int getStockCount() {
		return dataCount;
	}

	@Override
	public void notifySubscribers() {

		for(NotificationObserver observer : observerList) {

			observer.update();
		}
	}
}
