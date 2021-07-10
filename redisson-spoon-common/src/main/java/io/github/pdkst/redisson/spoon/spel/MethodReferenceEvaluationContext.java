package io.github.pdkst.redisson.spoon.spel;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * SpringEL表达式上下文，和{@link MethodBasedEvaluationContext}对比的话，保存method变量获取，保存方法对象直接获取
 *
 * @author pdkst
 * @since 2021/6/8
 */
@Getter
@Builder
public class MethodReferenceEvaluationContext extends MethodBasedEvaluationContext {
    /** Root对象 */
    private final Object root;
    private final Class<?> clazz;
    /** method，被执行的方法 */
    private final Method method;
    /** 方法实际参数 */
    private final Object[] arguments;

    public MethodReferenceEvaluationContext(Object root, Class<?> clazz, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(root, method, arguments, parameterNameDiscoverer);
        this.root = root;
        this.clazz = clazz;
        this.method = method;
        this.arguments = arguments;
    }
}
