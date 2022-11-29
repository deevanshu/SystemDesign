package com.systemdesign.APIRateLimiter_SlidingWindow;

import java.util.HashMap;

public class BucketCreater {

	HashMap<Integer , SlidingWindow> bucket ; 

	public BucketCreater(int id , int capacity) {
		bucket = new HashMap<>();
		bucket.put(id, new SlidingWindow(capacity , 60));
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
