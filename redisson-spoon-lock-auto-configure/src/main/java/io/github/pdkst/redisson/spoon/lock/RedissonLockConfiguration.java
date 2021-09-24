package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.invoker.RedissonLockInvoker;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * RedissonLockInvoker配置，使用redisson
 *
 * @author pdkst
 * @since 2021/5/29
 */
@ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "type")
public class RedissonLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockInvoker.class)
    public LockInvoker lockInvoker(RedissonClientProvider redissonClientProvider, RedissonLockProperties redissonLockProperties) {
        final String lockPrefix = redissonLockProperties.getLockPrefix();
        return new RedissonLockInvoker(lockPrefix, redissonClientProvider);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClientProvider.class)
    public RedissonClientProvider redissonClientProvider(RedissonClient redissonClient) {
        return () -> redissonClient;
    }

}
