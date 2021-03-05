package io.github.pdkst.redissonlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pdkst
 * @since 2021/3/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedissonLock {
    String value() default "none";

    /**
     * 请求锁时间
     * @return 毫秒
     */
    long timeout() default 30000;

    /**
     * 锁剩余时间
     * @return 毫秒
     */
    long leaseTime() default 30000;
}
