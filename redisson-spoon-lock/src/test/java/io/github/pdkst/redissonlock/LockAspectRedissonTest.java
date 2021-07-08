package io.github.pdkst.redissonlock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author pdkst
 * @since 2021/5/23
 */
@EnableAspectJAutoProxy
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {LockAspectTarget.class, LockAspect.class, RedissonLockConfiguration.class})
public class LockAspectRedissonTest {
    @Autowired
    LockAspectTarget target;
    @MockBean
    RedissonClient redissonClient;

    @Test
    void testRoot() {
        target.rootTarget("testLockName");
    }

    @Test
    void testParamName() {
        target.paramNameTarget("testLockName");
    }

}