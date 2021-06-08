package io.github.pdkst.redissonlock.spel;

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
public class MethodReferenceEvaluationContext extends MethodBasedEvaluationContext {
    /** Root对象 */
    private final Object rootObject;
    /** method，被执行的方法 */
    private final Method method;
    /** 方法实际参数 */
    private final Object[] arguments;

    public MethodReferenceEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        this.rootObject = rootObject;
        this.method = method;
        this.arguments = arguments;
    }
}
