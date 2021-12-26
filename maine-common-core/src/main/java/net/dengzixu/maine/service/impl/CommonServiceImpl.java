package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.mapper.CommonMapper;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.utils.AliyunSMS;
import net.dengzixu.maine.utils.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommonServiceImpl implements CommonService {
    private final AliyunSMS aliyunSMS;
    private final CommonMapper commonMapper;

    @Autowired
    public CommonServiceImpl(AliyunSMS aliyunSMS, CommonMapper commonMapper) {
        this.aliyunSMS = aliyunSMS;
        this.commonMapper = commonMapper;
    }

    @Override
    public void sendSMS(String phone) {
        // 生成验证码
        String SMSCode = RandomGenerator.nextSMSCode();

        // 验证码写入数据库
        commonMapper.saveSMSCode(phone, SMSCode);

        // 发送验证码
        aliyunSMS.send(phone, "紫旭网络", "SMS_227160113", new HashMap<>() {{
            put("code", SMSCode);
        }});
    }

    @Override
    public boolean verifySMS(String phone, String code) {
        if (null == commonMapper.getSMSCode(phone, code, false)) {
            return false;
        }

        commonMapper.deleteSMSCode(phone, code);

        return true;
    }
}
