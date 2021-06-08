package io.github.pdkst.redissonlock.spel;

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
     * @param root root对象
     * @param clazz 类型
     * @param method 方法
     * @param args 执行参数
     * @return EvaluationContext
     */
    public MethodReferenceEvaluationContext createEvaluationContext(Object root, Class<?> clazz, Method method, Object[] args) {
        // 获取真实执行方法
        final Method targetMethod = getTargetMethod(clazz, method);
        return new MethodReferenceEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
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
                            Method method,
                            EvaluationContext evalContext) {
        final AnnotatedElementKey elementKey = new AnnotatedElementKey(method, null);
        final Expression expression = getExpression(this.conditionCache, elementKey, conditionExpression);
        return expression.getValue(evalContext, String.class);
    }

    /**
     * 从上下文中解析表达式的值
     *
     * @param conditionExpression SpringEL表达式
     * @param root 上下文，root对象，#root指向此对象
     * @return 表达式求值结果
     * @see #condition(String, Method, EvaluationContext)
     */
    public String condition(String conditionExpression,
                            Object root,
                            Class<?> clazz,
                            Method method,
                            Object[] args) {
        final Method targetMethod = getTargetMethod(clazz, method);
        final MethodReferenceEvaluationContext evaluationContext = this.createEvaluationContext(root, clazz, method, args);
        return condition(conditionExpression, targetMethod, evaluationContext);
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