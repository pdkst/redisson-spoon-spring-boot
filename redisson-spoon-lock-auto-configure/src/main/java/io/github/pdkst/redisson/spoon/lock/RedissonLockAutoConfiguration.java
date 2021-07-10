package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.invoker.DefaultLockProcessor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static io.github.pdkst.redisson.spoon.lock.RedissonLockAutoConfiguration.OnRedissonLockCondition;

/**
 * Redisson Lock 自动配置类
 *
 * @author pdkst
 * @since 2021/3/5
 */
@Configuration
@EnableConfigurationProperties(RedissonLockProperties.class)
@ConditionalOnProperty(prefix = RedissonLockProperties.REDISSON_LOCK_CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(LockProcessor.class)
@Conditional(OnRedissonLockCondition.class)
@Import({RedissonLockConfiguration.class, JavaCoreLockConfiguration.class})
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


    static class OnRedissonLockCondition extends AnyNestedCondition {
        OnRedissonLockCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        /**
         * dont copy xxx
         */
        @ConditionalOnMissingBean(RedissonClient.class)
        static class CoreLockProperty {

        }

        @ConditionalOnBean(RedissonClient.class)
        static class RedissonLockProperty {
            //可以分拆多个
        }
    }

}
