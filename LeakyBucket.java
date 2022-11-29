package com.systemdesign.APIRateLimiter_LeakyBucket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LeakyBucket implements RateLimiter{

	BlockingQueue<Integer> queue ;  // As it's safe for multithreaded env. and if capacity given while creatng blockng queue it won't 
                                	// allow request greater then given capacity to enter into queue.
	public LeakyBucket(int capacity) {

		this.queue = new LinkedBlockingQueue<>(capacity);
	}

	public boolean grantAccess() {
		if(queue.remainingCapacity() > 0 ) {
			queue.add(1);
			return true;
		}
		return false;
	}
}
