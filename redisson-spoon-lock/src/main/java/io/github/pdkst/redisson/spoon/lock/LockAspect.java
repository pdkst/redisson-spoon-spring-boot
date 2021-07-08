package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.context.ProcessorContext;
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

    /**
     * 环绕通知，只拦截方法，具体逻辑交给{@link LockProcessor}执行
     *
     * @param pjp 切点
     * @param lock 锁对象配置
     * @return 方法返回值
     * @throws Throwable 可能抛出的所有异常
     * @throws IllegalStateException 加锁失败会抛出此异常
     */
    @Around("@annotation(lock)")
    public Object doAround(ProceedingJoinPoint pjp, RedissonLock lock) throws Throwable {
        final ProcessorContext context = new ProcessorContext(pjp, lock);
        return lockProcessor.process(context);
    }

}
