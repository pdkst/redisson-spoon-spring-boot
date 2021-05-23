package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.ProcessorContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 通过AOP对方法进行拦截
 *
 * @author pdkst
 * @since 2021/3/5
 */
@Aspect
@RequiredArgsConstructor
public class LockAspect {
    final LockProcessor lockProcessor;

    @Around("@annotation(lock)")
    public Object doAround(ProceedingJoinPoint pjp, RedissonLock lock) throws Throwable {
        final ProcessorContext context = new ProcessorContext(pjp, lock);
        return lockProcessor.process(context);
    }

}
