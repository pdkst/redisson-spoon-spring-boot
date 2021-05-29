package io.github.pdkst.redissonlock.invoker;

import io.github.pdkst.redissonlock.LockInvoker;
import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockCondition;
import io.github.pdkst.redissonlock.context.LockContext;
import lombok.AllArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * Java核心AQS锁执行器，只包涵单机锁信息，如果lockSupplier提供的锁为单例则相当于Synchronized
 *
 * @author pdkst
 * @since 2021/3/5
 */
@AllArgsConstructor
public class ConcurrentCoreLockInvoker implements LockInvoker {
    private final Supplier<Lock> lockSupplier;

    @Override
    public LockContext<Lock> initContext(InvokerContext context) {
        final LockCondition lockCondition = context.getLockCondition();
        return new LockContext<>(lockSupplier.get(), lockCondition.getTimeout(), lockCondition.getLeaseTime());
    }
}
