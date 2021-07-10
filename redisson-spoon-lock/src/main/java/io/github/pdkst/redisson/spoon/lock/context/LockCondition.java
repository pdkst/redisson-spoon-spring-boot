package io.github.pdkst.redisson.spoon.lock.context;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pdkst
 * @since 2021/3/6
 */
@Data
@AllArgsConstructor
public class LockCondition {
    /**
     * EL表达式
     */
    private String expression;
    /**
     * 等待时间
     */
    private long timeout;
    /**
     * 运行时间
     */
    private long leaseTime;
}
