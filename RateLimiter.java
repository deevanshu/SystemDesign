package com.systemdesign.APIRateLimiter_LeakyBucket;

public interface RateLimiter {

	public boolean grantAccess();
}
