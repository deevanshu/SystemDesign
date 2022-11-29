package com.systemdesign.APIRateLimiter_LeakyBucket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String args[]) {

		BucketCreater bucketObject = new BucketCreater(1 , 10);

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<Integer> future = executorService.submit(new Callable<Integer>() {
			// Override the run method
			public Integer call()
			{
				for(int i = 0 ; i < 12 ; i ++) {
					bucketObject.accessApplication(1);
				}
				return 999;
			}
		});
		
		try {
			int ans = future.get();
			System.out.println(ans);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	//	@Override
	//	public void run() {
	//		
	//		System.out.println("here");
	//	}
}
