package io.github.pdkst.redisson.spoon.lock.context;

import io.github.pdkst.redisson.spoon.lock.RedissonLock;
import io.github.pdkst.redisson.spoon.spel.ExpressionEvaluator;
import io.github.pdkst.redisson.spoon.spel.MethodEvaluationRoot;
import io.github.pdkst.redisson.spoon.spel.TypedMethodBasedEvaluationContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author pdkst
 * @since 2021/3/5
 */
@Data
@AllArgsConstructor
public class InvokerContext {
    private final LockCondition lockCondition;
    private final ExpressionEvaluator evaluator;
    private final TypedMethodBasedEvaluationContext<MethodEvaluationRoot> methodReferenceEvaluationContext;
    private final MethodEvaluationRoot methodEvaluationRoot;


    public InvokerContext(final ProceedingJoinPoint joinPoint, final RedissonLock redissonLock) {
        this(joinPoint, redissonLock, new ExpressionEvaluator());
    }

    public InvokerContext(final ProcessorContext context) {
        this(context.getProceedingJoinPoint(), context.getLock(), new ExpressionEvaluator());
    }

    public InvokerContext(final ProceedingJoinPoint joinPoint, final RedissonLock redissonLock, ExpressionEvaluator evaluator) {
        this.lockCondition = new LockCondition(redissonLock.value(), redissonLock.timeout(), redissonLock.leaseTime());
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Object target = joinPoint.getTarget();
        Class<?> clazz = target != null ? target.getClass() : null;
        this.evaluator = evaluator;
        this.methodReferenceEvaluationContext = evaluator.createEvaluationContext(target, clazz, signature.getMethod(), joinPoint.getArgs());
        this.methodEvaluationRoot = this.methodReferenceEvaluationContext.getEvaluationRoot();
    }

    public String parseValue() {
        return evaluator.condition(lockCondition.getExpression(), methodEvaluationRoot.getMethod(), methodReferenceEvaluationContext);
    }
}
