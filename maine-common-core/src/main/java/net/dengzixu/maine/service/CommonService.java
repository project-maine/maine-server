package net.dengzixu.maine.service;

public interface CommonService {
    void sendSMS(String phone);

    boolean verifySMS(String phone,String code);
}
