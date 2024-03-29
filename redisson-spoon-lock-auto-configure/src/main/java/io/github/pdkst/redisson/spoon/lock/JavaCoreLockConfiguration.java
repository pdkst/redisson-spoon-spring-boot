package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.invoker.ConcurrentCoreLockInvoker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认核心锁，非公平锁， dont copy xxx
 *
 * @author pdkst
 * @since 2021/5/29
 */
@ConditionalOnMissingClass("org.redisson.api.RedissonClient")
@ConditionalOnProperty(havingValue = "core", name = "mode", matchIfMissing = true, prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX)
public class JavaCoreLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockInvoker.class)
    public LockInvoker lockInvoker() {
        final ReentrantLock reentrantLock = new ReentrantLock(false);
        return new ConcurrentCoreLockInvoker(() -> reentrantLock);
    }
}
