# redissonlock-spring-boot-starter

redissonlock-spring-boot-starter

### 说明

需要开启AOP功能，依靠注解快速实现锁（默认包括redisson分布式锁）

### 使用说明

```java
@Slf4j
@Component
public class LockAspectTarget {
    public LockAspectTarget() {
        log.info("LockAspectTarget construct...");
    }

    @RedissonLock("#root.args[0]")
    public void rootTarget(String lockName) {
        log.info("lock name = {}", lockName);
    }

    @RedissonLock("#lockName")
    public void paramNameTarget(String lockName) {
        log.info("lock name = {}", lockName);
    }
}

```

- `#root`对象指向`io.github.pdkst.redissonlock.context.InvokerContext`
- `#lockName`指向参数名字（示例参数名字是`lockName`）
- `#method` 指向当前方法`java.lang.reflect.Method`类型

