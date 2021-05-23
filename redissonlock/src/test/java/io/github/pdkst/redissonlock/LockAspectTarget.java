package io.github.pdkst.redissonlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@Slf4j
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LockAspectTarget {
    public LockAspectTarget() {
        log.info("LockAspectTarget construct...");
    }

    @RedissonLock("#lockName")
    public void aopTarget(String lockName) {
        log.info("lock name = {}", lockName);
    }
}
