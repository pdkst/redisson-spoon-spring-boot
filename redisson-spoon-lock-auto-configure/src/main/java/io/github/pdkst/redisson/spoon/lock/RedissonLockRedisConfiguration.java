package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.invoker.RedissonLockInvoker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.Set;

/**
 * RedissonLockInvoker配置，使用redisson
 *
 * @author pdkst
 * @since 2021/5/29
 */
@ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "type")
public class RedissonLockRedisConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockInvoker.class)
    public LockInvoker lockInvoker(RedissonClient redissonClient, RedissonLockProperties redissonLockProperties) {
        return new RedissonLockInvoker(redissonLockProperties.getPrefix(), redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(Config redissonLockConfig) {
        return Redisson.create(redissonLockConfig);
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public Config redissonLockConfig() {
        return new Config();
    }

    @Bean
    @ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "type", havingValue = "single")
    @ConditionalOnMissingBean(RedissonClient.class)
    public Config singleRedissonConfig(RedissonLockProperties redissonLockProperties, Config redissonLockConfig) {
        redissonLockConfig.useSingleServer()
                .setAddress(redissonLockProperties.getAddress())
                .setPassword(redissonLockProperties.getPassword());
        return redissonLockConfig;
    }

    @Bean
    @ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "type", havingValue = "replica")
    @ConditionalOnMissingBean(RedissonClient.class)
    public Config masterRedissonConfig(RedissonLockProperties redissonLockProperties, Config redissonLockConfig) {
        final Set<String> replicaAddress = redissonLockProperties.getReplicaAddress();
        redissonLockConfig.useMasterSlaveServers()
                .setMasterAddress(redissonLockProperties.getAddress())
                .setSlaveAddresses(replicaAddress);
        return redissonLockConfig;
    }

    @Bean
    @ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "type", havingValue = "cluster")
    @ConditionalOnMissingBean(RedissonClient.class)
    public Config clusterRedissonConfig(RedissonLockProperties redissonLockProperties, Config redissonLockConfig) {
        redissonLockConfig.useClusterServers()
                .setPassword(redissonLockProperties.getPassword())
                .addNodeAddress(redissonLockProperties.getNodeAddress());
        return redissonLockConfig;
    }
}
