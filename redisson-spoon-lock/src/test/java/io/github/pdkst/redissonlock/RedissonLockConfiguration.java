package io.github.pdkst.redissonlock;

import io.github.pdkst.redisson.spoon.lock.LockProcessor;
import io.github.pdkst.redisson.spoon.lock.RedissonClientProvider;
import io.github.pdkst.redisson.spoon.lock.invoker.DefaultLockProcessor;
import io.github.pdkst.redisson.spoon.lock.invoker.RedissonLockInvoker;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@AllArgsConstructor
public class RedissonLockConfiguration {
    final RedissonClientProvider redissonClientProvider;

    @Bean
    public LockProcessor lockProcessor() {
        return new DefaultLockProcessor(new RedissonLockInvoker("testRedissonPrefix", redissonClientProvider));
    }
}
