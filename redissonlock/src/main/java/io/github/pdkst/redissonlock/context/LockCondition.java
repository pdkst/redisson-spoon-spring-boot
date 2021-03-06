package io.github.pdkst.redissonlock.context;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pdkst
 * @since 2021/3/6
 */
@Data
@AllArgsConstructor
public class LockCondition {
    private String expression;
    private long timeout;
    private long leaseTime;
}
