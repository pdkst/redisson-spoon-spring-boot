package io.github.pdkst.redisson.spoon.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedissonSpoonLock配置
 *
 * @author pdkst
 * @since 2021/4/4
 */
@Data
@ConfigurationProperties(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX)
public class RedissonLockProperties {
    public static final String REDISSON_LOCK_CONFIG_PREFIX = "pdkst.redisson.spoon.lock";
    public static final String REDISSON_LOCK_REDIS_DEFAULT_PREFIX = "pdkst:redisson:spoon:lock:";
    /**
     * 是否开启，默认开
     */
    private boolean enable;

    /**
     * 锁对象redis key前缀
     */
    private String lockPrefix = REDISSON_LOCK_REDIS_DEFAULT_PREFIX;
}
