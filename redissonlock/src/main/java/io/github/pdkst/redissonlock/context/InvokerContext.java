package io.github.pdkst.redissonlock.context;

import io.github.pdkst.redissonlock.RedissonLock;
import io.github.pdkst.redissonlock.spel.ExpressionEvaluator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author pdkst
 * @since 2021/3/5
 */
@Data
@AllArgsConstructor
public class InvokerContext {
    private Object object;
    private Class<?> clazz;
    private Method method;
    private Object[] args;
    private RedissonLock redissonLock;

    public String parse() {
        final ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return evaluator.condition(redissonLock.value(), this);
    }
}
