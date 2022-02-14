package com.nc.component.redis.client;

import com.nc.component.redis.enums.LockType;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/24
 * @package: com.nc.component.service
 */
public interface RedisLockClient {

    boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    void unLock(String lockName, LockType lockType);

}
