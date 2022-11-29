package com.systemdesign.APIRateLimiter_TokenBucket;

public interface RateLimiter {

	public boolean grantAccess();
}
