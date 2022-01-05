package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.exception.common.SMSCodeErrorException;
import net.dengzixu.maine.exception.common.SMSCodeSendFrequentlyException;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.utils.AliyunSMS;
import net.dengzixu.maine.utils.RandomGenerator;
import net.dengzixu.maine.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommonServiceImpl implements CommonService {
    private final AliyunSMS aliyunSMS;
    private final RedisUtils redisUtils;

    @Autowired
    public CommonServiceImpl(AliyunSMS aliyunSMS, RedisUtils redisUtils) {
        this.aliyunSMS = aliyunSMS;
        this.redisUtils = redisUtils;
    }

    @Override
    public void sendSMS(String phone) {
        String CODE_KEY = "sms:code:" + phone;
        String LIMIT_KEY = "sms:limit:" + phone;

        // 生成验证码
        String smsCode = RandomGenerator.nextSMSCode();

        // 判断短信是否发送过于频繁
        if (redisUtils.hasKey(LIMIT_KEY)) {
            throw new SMSCodeSendFrequentlyException();
        }

        // redis 存入验证码
        redisUtils.setKey(CODE_KEY, smsCode, 5 * 60);

        // redis 限制发送速率
        redisUtils.setKey(LIMIT_KEY, "LIMIT", 60);

        // 发送验证码
        aliyunSMS.send(phone, "紫旭网络", "SMS_227160113", new HashMap<>() {{
            put("code", smsCode);
        }});
    }

    @Override
    public boolean verifySMS(String phone, String code) {
        String KEY = "sms:code:" + phone;

        // 判断验证码是否存在
        if (!redisUtils.hasKey(KEY)) {
            throw new SMSCodeErrorException();
        }

        // 判断验证码是否正确
        if (!code.equals(redisUtils.getKey(KEY))) {
            throw new SMSCodeErrorException();
        }

        // 删除已使用的验证码
        redisUtils.getAndDeleteKey(KEY);

        return true;
    }
}
