package com.systemdesign.APIRateLimiter_LeakyBucket;

import java.util.HashMap;

public class BucketCreater {

	HashMap<Integer , LeakyBucket> bucket ; 
	
	public BucketCreater(int id , int capacity) {
		bucket = new HashMap<>();
		bucket.put(id, new LeakyBucket(capacity));
	}
	
	public void accessApplication(int id) {
		
		if(bucket.get(id).grantAccess()) {
			
			System.out.println("Application Accessed");
		}
		else {
			
			System.out.println("Access Denied , No Capacity");
		}
	}
}
