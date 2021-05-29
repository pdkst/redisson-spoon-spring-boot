package io.github.pdkst.redissonlock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author pdkst
 * @since 2021/4/4
 */
@Data
@ConfigurationProperties(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX)
public class RedissonLockProperties {
    public static final String REDISSON_LOCK_CONFIG_PREFIX = "pdkst.redissonlock";
    public static final String REDISSON_LOCK_REDIS_DEFAULT_PREFIX = "pdkst:redissonlock:";
    private boolean enable;
    private String prefix = REDISSON_LOCK_REDIS_DEFAULT_PREFIX;
    private RedissonMode type = RedissonMode.single;
    private String address = "redis://localhost:6379";
    private Set<String> replicaAddress;
    private String[] nodeAddress;
    private String password;

    static enum RedissonMode {
        /**
         * 单机
         */
        single,
        /**
         * 主从
         */
        replica,
        /**
         * 集群
         */
        cluster
    }
}
