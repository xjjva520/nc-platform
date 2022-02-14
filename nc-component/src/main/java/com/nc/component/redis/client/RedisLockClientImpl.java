package com.nc.component.redis.client;

import com.nc.component.redis.enums.LockType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/24
 * @package: com.nc.component.service
 */
public class RedisLockClientImpl implements RedisLockClient {

    private RedissonClient redissonClient;

    public boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        RLock lock = this.getLock(lockName, lockType);
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    public void unLock(String lockName, LockType lockType) {
        RLock lock = this.getLock(lockName, lockType);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }

    }

    private RLock getLock(String lockName, LockType lockType) {
        RLock lock;
        if (LockType.REENTRANT == lockType) {
            lock = this.redissonClient.getLock(lockName);
        } else {
            lock = this.redissonClient.getFairLock(lockName);
        }

        return lock;
    }

    public RedisLockClientImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
