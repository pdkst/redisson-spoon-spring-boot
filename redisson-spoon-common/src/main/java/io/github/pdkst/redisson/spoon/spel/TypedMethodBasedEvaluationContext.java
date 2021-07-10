package io.github.pdkst.redisson.spoon.spel;

import lombok.Getter;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * @author pdkst
 * @since 2021/7/10
 */
@Getter
public class TypedMethodBasedEvaluationContext<T extends MethodEvaluationRoot> extends MethodBasedEvaluationContext {
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private final T evaluationRoot;

    public TypedMethodBasedEvaluationContext(T rootObject) {
        super(rootObject, rootObject.getMethod(), rootObject.getArgs(), PARAMETER_NAME_DISCOVERER);
        this.evaluationRoot = rootObject;
    }
}
