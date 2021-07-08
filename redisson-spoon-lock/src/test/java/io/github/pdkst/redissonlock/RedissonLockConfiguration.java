package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.invoker.DefaultLockProcessor;
import io.github.pdkst.redissonlock.invoker.RedissonLockInvoker;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@AllArgsConstructor
public class RedissonLockConfiguration {
    final RedissonClient redissonClient;

    @Bean
    public LockProcessor lockProcessor() {
        return new DefaultLockProcessor(new RedissonLockInvoker("testRedissonPrefix", redissonClient));
    }
}
