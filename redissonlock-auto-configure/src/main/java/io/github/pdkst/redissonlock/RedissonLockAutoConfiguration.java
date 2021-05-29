package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.invoker.DefaultLockProcessor;
import io.github.pdkst.redissonlock.invoker.RedissonLockInvoker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Redisson Lock 自动配置类
 *
 * @author pdkst
 * @since 2021/3/5
 */
@Configuration
@EnableConfigurationProperties(RedissonLockProperties.class)
@ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedissonLockAutoConfiguration {

    @Bean
    public LockAspect lockAspect(LockProcessor lockProcessor) {
        return new LockAspect(lockProcessor);
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultLockProcessor lockAspect(LockInvoker lockInvoker) {
        return new DefaultLockProcessor(lockInvoker);
    }

    @Bean
    @ConditionalOnMissingBean(LockInvoker.class)
    @ConditionalOnBean(RedissonClient.class)
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
        final Set<String> slaveAddress = redissonLockProperties.getSlaveAddress();
        redissonLockConfig.useMasterSlaveServers()
                .setMasterAddress(redissonLockProperties.getAddress())
                .setSlaveAddresses(slaveAddress);
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
