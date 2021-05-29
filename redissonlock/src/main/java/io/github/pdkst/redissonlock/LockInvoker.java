package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockContext;

import java.util.concurrent.locks.Lock;

/**
 * 锁执行器，提供锁上下文
 *
 * @author pdkst
 * @since 2021/3/5
 */
public interface LockInvoker {
    /**
     * 获取锁上下文，执行 try(onLock)finally{onRelease}标准锁流程
     *
     * @param context 上下文
     * @return 对于一次执行的锁上下文，包涵锁信息
     */
    LockContext<? extends Lock> initContext(InvokerContext context);
}
