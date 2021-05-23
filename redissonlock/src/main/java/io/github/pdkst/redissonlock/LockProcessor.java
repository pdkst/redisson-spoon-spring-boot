package io.github.pdkst.redissonlock;

import io.github.pdkst.redissonlock.context.ProcessorContext;

/**
 * @author pdkst
 * @since 2021/5/23
 */
public interface LockProcessor {
    Object process(ProcessorContext context) throws Throwable;
}
