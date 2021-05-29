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
 * SpringEL表达式执行器
 *
 * @author pdkst
 * @see org.springframework.context.expression.CachedExpressionEvaluator
 * @see ParameterNameDiscoverer
 * @see MethodBasedEvaluationContext
 * @since 1.0
 */
public class ExpressionEvaluator extends CachedExpressionEvaluator {
    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /**
     * 创建执行上下文，包涵SpringEL表达式可用的值，默认提供方法和参数名字，使用默认{@link DefaultParameterNameDiscoverer}解析方法参数名字
     *
     * @param context 执行器上下文
     * @return EvaluationContext
     */
    public EvaluationContext createEvaluationContext(InvokerContext context) {
        // 获取真实执行方法
        Method targetMethod = getTargetMethod(context.getClazz(), context.getMethod());
        return new MethodBasedEvaluationContext(context, targetMethod, context.getArgs(), this.paramNameDiscoverer);
    }

    /**
     * 从上下文中解析表达式的值
     *
     * @param conditionExpression SpringEL表达式
     * @param context 上下文，root对象，#root指向此对象
     * @param evalContext 上下文对象，可以自定义EL表达式的值
     * @return 表达式求值结果
     */
    public String condition(String conditionExpression,
                            InvokerContext context,
                            EvaluationContext evalContext) {
        final AnnotatedElementKey elementKey = new AnnotatedElementKey(context.getMethod(), context.getClazz());
        return getExpression(this.conditionCache, elementKey, conditionExpression)
                .getValue(evalContext, String.class);
    }

    /**
     * 从上下文中解析表达式的值
     *
     * @param conditionExpression SpringEL表达式
     * @param context 上下文，root对象，#root指向此对象
     * @return 表达式求值结果
     * @see #condition(String, InvokerContext, EvaluationContext)
     */
    public String condition(String conditionExpression,
                            InvokerContext context) {
        return condition(conditionExpression, context, createEvaluationContext(context));
    }

    /**
     * 获取方法的实际方法，即被代理的方法，有缓存
     *
     * @param targetClass 实际类型
     * @param method 方法
     * @return 实际方法
     * @see AopUtils#getMostSpecificMethod(Method, Class)
     */
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