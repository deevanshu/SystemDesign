package com.systemdesign.APIRateLimiter_TokenBucket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String args[]) {

		BucketCreater bucketObject = new BucketCreater(1 , 10);

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(new Runnable() {

			// Override the run method
			public void run()
			{
				for(int i = 0 ; i < 12 ; i ++) {
					bucketObject.accessApplication(1);
				}
			}
		});

		executorService.shutdown();
	}

	//	@Override
	//	public void run() {
	//		
	//		System.out.println("here");
	//	}
}
