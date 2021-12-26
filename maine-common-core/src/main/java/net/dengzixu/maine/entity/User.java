package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class User {
    private Long traceId;

    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private Integer status;

    private String createTime;
    private String modifyTime;
}
