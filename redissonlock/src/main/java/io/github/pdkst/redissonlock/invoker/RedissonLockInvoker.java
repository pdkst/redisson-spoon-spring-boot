package io.github.pdkst.redissonlock.invoker;

import io.github.pdkst.redissonlock.LockInvoker;
import io.github.pdkst.redissonlock.RedissonLock;
import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.RedissonLockContext;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author pdkst
 * @since 2021/3/5
 */
@RequiredArgsConstructor
public class RedissonLockInvoker implements LockInvoker {
    private final String prefix;
    private final RedissonClient redissonClient;

    @Override
    public RedissonLockContext initContext(InvokerContext context) {
        final String parseValue = context.parse();
        final RLock lock = redissonClient.getLock(prefix + ":" + parseValue);
        final RedissonLock redissonLock = context.getRedissonLock();
        return new RedissonLockContext(lock, redissonLock.timeout(), redissonLock.leaseTime());
    }
}
