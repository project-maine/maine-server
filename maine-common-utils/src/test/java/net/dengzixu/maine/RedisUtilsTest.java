package net.dengzixu.maine;

import net.dengzixu.maine.utils.RandomGenerator;
import net.dengzixu.maine.utils.RedisUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisUtilsTest {
    private final RedisUtils redisUtils;

    @Autowired
    public RedisUtilsTest(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Test
    public void testRedisUtils() throws InterruptedException {
        String smsCode = RandomGenerator.nextSMSCode();

        redisUtils.setKey("sms_code:18041168758", smsCode, 5);

        Assertions.assertTrue(redisUtils.hasKey("sms_code:18041168758"));
        Assertions.assertFalse(redisUtils.hasKey("sms_code:18041168759"));

        String readSMSCode = redisUtils.getKey("sms_code:18041168758");
        Assertions.assertEquals(smsCode, readSMSCode);

        Thread.sleep(5 * 1000);

        Assertions.assertFalse(redisUtils.hasKey("sms_code:18041168758"));
    }
}
