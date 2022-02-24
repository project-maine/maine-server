package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;

    private String password;
    private Integer passwordStatus;

    private String phone;
    private Integer phoneStatus;

    private String email;
    private Integer emailStatus;

    private Integer status;

    private String createTime;
    private String modifyTime;
}
