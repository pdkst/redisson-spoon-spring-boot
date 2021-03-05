package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

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
        // 方法签名
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 参数名发现器
        final Object target = pjp.getTarget();
        final Class<?> clazz = target != null ? target.getClass() : null;
        final Method method = signature.getMethod();
        final InvokerContext invokerContext = new InvokerContext(target, clazz, method, pjp.getArgs(), lock);
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
