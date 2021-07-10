package io.github.pdkst.redisson.spoon.lock.context;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 锁上下文，保存锁信息，在方法执行前后调用
 *
 * @author pdkst
 * @since 2021/3/5
 */
@AllArgsConstructor
public class LockContext<T extends Lock> {
    protected final T lock;
    protected final long timeout;
    protected final long leaseTime;

    /**
     * 加锁时执行
     *
     * @return 返回true则加锁成功，执行真实方法
     */
    public boolean onLock() throws InterruptedException {
        return lock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放锁时执行，finally，onLock执行失败也会执行 一般需要判断 是否获得锁
     *
     * @return 如果解锁返回true，本地锁总是返回true
     */
    public boolean onRelease() {
        lock.unlock();
        return true;
    }

    public T getLock() {
        return this.lock;
    }
}
