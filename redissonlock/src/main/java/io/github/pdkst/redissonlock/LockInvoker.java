package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockContext;

import java.util.concurrent.locks.Lock;

/**
 * @author pdkst
 * @since 2021/3/5
 */
public interface LockInvoker {
    LockContext<? extends Lock> initContext(InvokerContext context);
}
