package io.github.pdkst.redissonlock.context;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 锁上下文
 *
 * @author pdkst
 * @since 2021/3/5
 */
@AllArgsConstructor
public class LockContext<T extends Lock> {
    protected final T lock;
    protected final long timeout;
    protected final long leaseTime;

    public boolean onLock() throws InterruptedException {
        return lock.tryLock(leaseTime, TimeUnit.MILLISECONDS);
    }

    public void onRelease() {
        lock.unlock();
    }
}
