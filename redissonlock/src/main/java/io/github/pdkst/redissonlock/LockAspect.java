package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockContext;
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

    final LockInvoker lockInvoker;

    @Around("@annotation(lock)")
    public Object doAround(ProceedingJoinPoint pjp, RedissonLock lock) throws Throwable {
        // 参数名发现器
        final InvokerContext invokerContext = new InvokerContext(pjp, lock);
        final LockContext<?> lockContext = lockInvoker.initContext(invokerContext);
        try {
            if (lockContext.onLock()) {
                return pjp.proceed();
            }
        } finally {
            lockContext.onRelease();
        }
        throw new IllegalStateException("lock fail！");
    }

}
