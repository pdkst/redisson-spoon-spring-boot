package io.github.pdkst.redisson.spoon.lock.invoker;

import io.github.pdkst.redisson.spoon.lock.LockInvoker;
import io.github.pdkst.redisson.spoon.lock.LockProcessor;
import io.github.pdkst.redisson.spoon.lock.context.InvokerContext;
import io.github.pdkst.redisson.spoon.lock.context.LockContext;
import io.github.pdkst.redisson.spoon.lock.context.ProcessorContext;
import lombok.AllArgsConstructor;

/**
 * 默认锁执行器，一般情况下提供LockInvoker的标准流程
 *
 * @author pdkst
 * @since 2021/5/23
 */
@AllArgsConstructor
public class DefaultLockProcessor implements LockProcessor {
    final LockInvoker lockInvoker;

    @Override
    public Object process(ProcessorContext context) throws Throwable {

        // 获取锁执行上下文
        final InvokerContext invokerContext = new InvokerContext(context);
        final LockContext<?> lockContext = lockInvoker.initContext(invokerContext);
        try {
            // 尝试获取，如果tryLock则需要自定义扩展重写onLock方法即可
            if (lockContext.onLock()) {
                return context.getProceedingJoinPoint().proceed();
            }
        } finally {
            // 一般需要判断
            lockContext.onRelease();
        }
        throw new IllegalStateException("lock fail！");
    }
}
