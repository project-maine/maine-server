package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class TaskCode {
    private Long taskID;
    private String code;
    private String expireTime;
    private String createTime;
    private String modifyTime;
}
