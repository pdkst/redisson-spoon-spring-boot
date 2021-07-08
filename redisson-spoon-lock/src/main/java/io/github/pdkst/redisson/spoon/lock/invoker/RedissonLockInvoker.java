package io.github.pdkst.redisson.spoon.lock.invoker;

import io.github.pdkst.redisson.spoon.lock.LockInvoker;
import io.github.pdkst.redisson.spoon.lock.context.InvokerContext;
import io.github.pdkst.redisson.spoon.lock.context.LockCondition;
import io.github.pdkst.redisson.spoon.lock.context.RedissonLockContext;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 分布式锁执行器
 *
 * @author pdkst
 * @since 2021/3/5
 */
@RequiredArgsConstructor
public class RedissonLockInvoker implements LockInvoker {
    private final String prefix;
    private final RedissonClient redissonClient;

    @Override
    public RedissonLockContext initContext(InvokerContext context) {
        final String parseValue = context.parseValue();
        final RLock lock = redissonClient.getLock(prefix + ":" + parseValue);
        final LockCondition lockCondition = context.getLockCondition();
        return new RedissonLockContext(lock, lockCondition.getTimeout(), lockCondition.getLeaseTime());
    }
}
