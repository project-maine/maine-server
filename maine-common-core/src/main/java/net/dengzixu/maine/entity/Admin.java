package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class Admin {
    private Long id;
    private String name;
    private String password;
    private String createTime;
    private String modifyTime;
}
