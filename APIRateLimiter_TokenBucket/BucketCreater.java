package com.systemdesign.APIRateLimiter_TokenBucket;

import java.util.HashMap;

public class BucketCreater {

	HashMap<Integer , TokenBucket> bucket ; 
	
	public BucketCreater(int id , int capacity) {
		bucket = new HashMap<>();
		bucket.put(id, new TokenBucket(capacity , 10));
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
