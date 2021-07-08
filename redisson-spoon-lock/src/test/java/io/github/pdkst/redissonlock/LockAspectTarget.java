package io.github.pdkst.redissonlock;

import io.github.pdkst.redisson.spoon.lock.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@Slf4j
@Component
public class LockAspectTarget {
    public LockAspectTarget() {
        log.info("LockAspectTarget construct...");
    }

    @RedissonLock("#root.args[0]")
    public void rootTarget(String lockName) {
        log.info("lock name = {}", lockName);
    }

    @RedissonLock("#lockName")
    public void paramNameTarget(String lockName) {
        log.info("lock name = {}", lockName);
    }
}
