package io.github.pdkst.redissonlock.context;

import io.github.pdkst.redissonlock.RedissonLock;
import io.github.pdkst.redissonlock.spel.ExpressionEvaluator;
import io.github.pdkst.redissonlock.spel.MethodReferenceEvaluationContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author pdkst
 * @since 2021/3/5
 */
@Data
@AllArgsConstructor
public class InvokerContext {
    private final Object object;
    private final Class<?> clazz;
    private final Method method;
    private Object[] args;
    private final LockCondition lockCondition;
    private final ExpressionEvaluator evaluator;
    private final MethodReferenceEvaluationContext methodReferenceEvaluationContext;

    public InvokerContext(final ProceedingJoinPoint joinPoint, final RedissonLock redissonLock) {
        this(joinPoint, redissonLock, new ExpressionEvaluator());
    }

    public InvokerContext(final ProcessorContext context) {
        this(context.getProceedingJoinPoint(), context.getLock(), new ExpressionEvaluator());
    }

    public InvokerContext(final ProceedingJoinPoint joinPoint, final RedissonLock redissonLock, ExpressionEvaluator evaluator) {
        this.lockCondition = new LockCondition(redissonLock.value(), redissonLock.timeout(), redissonLock.leaseTime());
        this.object = joinPoint.getTarget();
        this.args = joinPoint.getArgs();
        this.clazz = object != null ? object.getClass() : null;
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        this.method = signature.getMethod();
        this.evaluator = evaluator;
        this.methodReferenceEvaluationContext = evaluator.createEvaluationContext(object, clazz, method, args);
    }

    public String parseValue() {
        return evaluator.condition(lockCondition.getExpression(), method, methodReferenceEvaluationContext);
    }
}
