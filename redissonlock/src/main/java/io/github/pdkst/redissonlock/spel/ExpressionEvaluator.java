package io.github.pdkst.redissonlock.spel;

import io.github.pdkst.redissonlock.context.InvokerContext;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pdkst
 */
public class ExpressionEvaluator extends CachedExpressionEvaluator {
    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public EvaluationContext createEvaluationContext(InvokerContext context) {

        Method targetMethod = getTargetMethod(context.getClazz(), context.getMethod());
        return new MethodBasedEvaluationContext(context, targetMethod, context.getArgs(), this.paramNameDiscoverer);
    }

    public String condition(String conditionExpression,
                            InvokerContext context,
                            EvaluationContext evalContext) {
        final AnnotatedElementKey elementKey = new AnnotatedElementKey(context.getMethod(), null);
        return getExpression(this.conditionCache, elementKey, conditionExpression)
                .getValue(evalContext, String.class);
    }

    public String condition(String conditionExpression,
                            InvokerContext context) {
        return condition(conditionExpression, context, createEvaluationContext(context));
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}