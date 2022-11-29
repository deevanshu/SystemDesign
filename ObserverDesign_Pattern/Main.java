package com.systemdesign.ObserverDesign_Pattern;
public class Main {

	public static void main(String args[]) {

		StockObservable observableObject = new IphoneStockObservableImplementation();

		NotificationObserver observer1 =  new EmailOberverImple("abc@gmail.com" , observableObject);
		NotificationObserver observer2 =  new EmailOberverImple("abcd@gmail.com" , observableObject);
		NotificationObserver observer3 =  new MobileObserverImple(12345 ,observableObject);
		
		observableObject.add(observer1);
		observableObject.add(observer2);
		observableObject.add(observer3);
		
		observableObject.setStockCount(10);
		observableObject.remove(observer2);
		observableObject.setStockCount(8);
	}
}
