package io.github.pdkst.redissonlock.context;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author pdkst
 */
public class RedissonLockContext extends LockContext<RLock> {

    public RedissonLockContext(RLock lock, long timeout, long waitTime) {
        super(lock, timeout, waitTime);
    }

    @Override
    public boolean onLock() throws InterruptedException {
        return lock.tryLock(timeout, leaseTime, TimeUnit.MICROSECONDS);
    }

    @Override
    public void onRelease() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}