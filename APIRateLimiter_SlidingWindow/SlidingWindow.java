package com.systemdesign.APIRateLimiter_SlidingWindow;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindow implements RateLimiter{

	ConcurrentLinkedQueue<Long> queue ;  
	int bucketCapacity;
	int timeWindowInSeconds ; 

	public SlidingWindow(int bucketCapacity , int timeWindowInSeconds) {

		this.bucketCapacity = bucketCapacity;
		this.timeWindowInSeconds = timeWindowInSeconds;
		this.queue = new ConcurrentLinkedQueue<>();  // Non blocking nd thread safe
	}


	public boolean grantAccess() {

		long currentTime = System.currentTimeMillis();
		refreshBucket();
		if(queue.size() < bucketCapacity ) {
			queue.offer(currentTime); // inserts at tail
			return true;
		}
		return false;
	}

	private void refreshBucket() {

		if(queue.isEmpty()){
			return ;
		}
		long calculatedTime = (System.currentTimeMillis() - queue.peek())/1000;  // first entry time in sec

		while(calculatedTime >= timeWindowInSeconds) {  // Remove all the entries which have crossed given time say del entries which are older than 1 min
			// from the current/latest entry if timewindow is 1 min 
			queue.poll() ;
			if(queue.isEmpty()) {
				break;
			}
			calculatedTime = (System.currentTimeMillis() - queue.peek())/1000;
		}
	}
}
