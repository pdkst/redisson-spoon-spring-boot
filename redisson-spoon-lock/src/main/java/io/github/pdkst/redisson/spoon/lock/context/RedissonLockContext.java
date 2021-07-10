package io.github.pdkst.redisson.spoon.lock.context;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * redisson lock context
 *
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
    public boolean onRelease() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
            return true;
        }
        return false;
    }
}