package net.dengzixu.maine.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.maine.config.MaineConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AliyunSMS {
    private final MaineConfig maineConfig;

    private final DefaultProfile profile;
    private final IAcsClient client;

    @Autowired
    public AliyunSMS(MaineConfig maineConfig) {
        this.maineConfig = maineConfig;

        this.profile = DefaultProfile.getProfile("cn-hangzhou",
                maineConfig.AliyunSMS().AccessKeyId(),
                maineConfig.AliyunSMS().AccessSecret());
        client = new DefaultAcsClient(profile);
    }


    public boolean send(String phone, String signName, String templateCode, String templateParam) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称
        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
        request.putQueryParameter("SignName", signName);//短信签名名称
        request.putQueryParameter("TemplateCode", templateCode);//短信模板ID
        request.putQueryParameter("TemplateParam", templateParam);//短信模板变量对应的实际值

        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean send(String phone, String signName, String templateCode, Map<String, Object> templateParam) {
        try {
            String templateParamString = new ObjectMapper().writeValueAsString(templateParam);
            return this.send(phone, signName, templateCode, templateParamString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
