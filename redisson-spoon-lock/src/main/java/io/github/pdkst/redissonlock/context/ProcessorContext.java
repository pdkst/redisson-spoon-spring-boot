package io.github.pdkst.redissonlock.context;

import io.github.pdkst.redissonlock.RedissonLock;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@Data
@RequiredArgsConstructor
public class ProcessorContext {
    final ProceedingJoinPoint proceedingJoinPoint;
    final RedissonLock lock;
}
