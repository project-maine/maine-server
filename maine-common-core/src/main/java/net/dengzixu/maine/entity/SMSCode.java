package net.dengzixu.maine.entity;

import lombok.Data;

@Deprecated
@Data
public class SMSCode {
    private Long traceId;

    private String phone;
    private String code;
    private String expireTime;

    private String createTime;
}
