package io.github.pdkst.redissonlock.invoker;

import io.github.pdkst.redissonlock.LockInvoker;
import io.github.pdkst.redissonlock.LockProcessor;
import io.github.pdkst.redissonlock.context.InvokerContext;
import io.github.pdkst.redissonlock.context.LockContext;
import io.github.pdkst.redissonlock.context.ProcessorContext;
import lombok.AllArgsConstructor;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@AllArgsConstructor
public class DefaultLockProcessor implements LockProcessor {
    final LockInvoker lockInvoker;

    @Override
    public Object process(ProcessorContext context) throws Throwable {

        // 参数名发现器
        final InvokerContext invokerContext = new InvokerContext(context);
        final LockContext<?> lockContext = lockInvoker.initContext(invokerContext);
        try {
            if (lockContext.onLock()) {
                return context.getProceedingJoinPoint().proceed();
            }
        } finally {
            lockContext.onRelease();
        }
        throw new IllegalStateException("lock fail！");
    }
}
