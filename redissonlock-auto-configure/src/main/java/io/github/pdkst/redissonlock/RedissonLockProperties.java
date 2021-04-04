package io.github.pdkst.redissonlock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author pdkst
 * @since 2021/4/4
 */
@Data
@ConfigurationProperties(prefix = RedissonLockProperties.REDISSON_LOCK_PREFIX)
public class RedissonLockProperties {
    public static final String REDISSON_LOCK_PREFIX = "pdkst.redissonlock";
    private boolean enable;
    private String prefix;
    private String type;
    private String address;
    private Set<String> slaveAddress;
    private String[] nodeAddress;
    private String password;
}
