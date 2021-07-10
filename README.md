# RedissonLock-spring-boot-starter

redissonlock-spring-boot-starter

### 说明

分布式简单锁实现，一个注解开启分布式锁；

注：需要开启AOP功能，依靠注解快速实现锁（默认包括redisson分布式锁） 注2：应用到线上环境请确保了解关键逻辑，或经过详细测试

### 使用

maven项目：将`LEAST`替换成要使用的版本，比如`1.0-SNAPSHOT`

```xml

<dependency>
	<groupId>io.github.pdkst</groupId>
	<artifactId>redisson-spoon-lock-spring-boot-starter</artifactId>
	<version>LEAST</version>
</dependency>
```

然后在pom.xml文件目录执行mvn打包命令即可引入

```bash
mvn clean package -Dmaven.test.skip
```

### 配置

最小配置，引入即开启JavaCore

CoreLock功能受限，默认配置只有一个全局锁

如果Spring项目中存在RedissonClient实例则默认开启Redisson全局锁

```yaml
pdkst:
  redisson:
    spoon:
      lock:
        enable: true
        lock-prefix: pdkst:github:lock
```

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

- `#root`对象指向`io.github.pdkst.redisson.spoon.lock.context.InvokerContext`
- `#lockName`指向参数名字（示例参数名字是`lockName`）
- `#method` 指向当前方法`java.lang.reflect.Method`类型

