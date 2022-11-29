package com.systemdesign.APIRateLimiter_TokenBucket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket {

	private int bucketCapacity;
	private int refreshRate;
	private AtomicInteger currentCapacity; // for multi threaded env. 
	private AtomicLong    lastUpdatedTime; // for multi threaded env. 
	
	public TokenBucket(int bucketCapacity , int refreshRate) {
		
		this.bucketCapacity = bucketCapacity ; 
		this.refreshRate = refreshRate ;
		this.currentCapacity = new AtomicInteger(bucketCapacity) ;
		this.lastUpdatedTime = new AtomicLong(System.currentTimeMillis());
	}
	
	public boolean grantAccess() {
		
		refreshBucket();
		if(currentCapacity.get() > 0 ) {
			currentCapacity.decrementAndGet();
			return true;
		}

		return false;
	}

	private void refreshBucket() {
		long currentTime = System.currentTimeMillis();
		int additionalToken =(int)((currentTime - lastUpdatedTime.get() ) /1000 ) * (bucketCapacity / refreshRate); 
		int currCapacity = Math.min ( currentCapacity.get() + additionalToken , bucketCapacity ) ;
		currentCapacity.getAndSet(currCapacity);
		lastUpdatedTime.getAndSet(currentTime);
		
	}
}
