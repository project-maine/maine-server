package net.dengzixu.maine;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.dengzixu.maine.utils.AliyunSMS;
import net.dengzixu.maine.utils.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AliyunSMSTest {
    private final static String SIGN_NAME = "紫旭网络";
    private final static String TEMPLATE_CODE = "SMS_227160113";

    private final AliyunSMS aliyunSMS;

    @Autowired
    public AliyunSMSTest(AliyunSMS aliyunSMS) {
        this.aliyunSMS = aliyunSMS;
    }


    @Test
    void testSendSMS() {
        Map<String, Object> map = new HashMap<>();

        map.put("code", RandomGenerator.nextSMSCode());

        aliyunSMS.send("15524860632", SIGN_NAME, TEMPLATE_CODE, map);
    }
}
