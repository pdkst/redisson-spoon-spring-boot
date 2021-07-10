package io.github.pdkst.redisson.spoon.spel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.expression.MethodBasedEvaluationContext;

import java.lang.reflect.Method;

/**
 * SpringEL表达式上下文，和{@link MethodBasedEvaluationContext}对比的话，保存method变量获取，保存方法对象直接获取
 *
 * @author pdkst
 * @since 2021/6/8
 */
@Getter
@Setter
public class MethodEvaluationRoot {
    /** Root对象 */
    private Object root;
    private Class<?> clazz;
    /** method，被执行的方法 */
    private Method method;
    /** 方法实际参数 */
    private Object[] args;

    public MethodEvaluationRoot(Object root, Class<?> clazz, Method method, Object[] args) {
        this.root = root;
        this.clazz = clazz;
        this.method = method;
        this.args = args;
    }
}
