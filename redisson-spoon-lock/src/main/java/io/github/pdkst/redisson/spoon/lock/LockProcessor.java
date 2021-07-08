package io.github.pdkst.redisson.spoon.lock;

import io.github.pdkst.redisson.spoon.lock.context.ProcessorContext;

/**
 * 执行切点的具体方法，重写可能需要注意原先逻辑
 *
 * @author pdkst
 * @since 2021/5/23
 */
public interface LockProcessor {
    /**
     * 切点执行方法
     *
     * @param context 切点上下文
     * @return 方法执行返回值
     * @throws Throwable 异常列表
     */
    Object process(ProcessorContext context) throws Throwable;
}
