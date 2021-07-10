package io.github.pdkst.redisson.spoon.lock;

import org.redisson.api.RedissonClient;

/**
 * @author pdkst
 * @since 2021/7/10
 */
@FunctionalInterface
public interface RedissonClientProvider {
    /**
     * 提供RedissonClient实例
     *
     * @return RedissonClient
     */
    RedissonClient getRedissonClient();
}
