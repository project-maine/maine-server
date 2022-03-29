package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class TaskRecord {
    private Long id;
    private String serialID;
    private Long userId;
    private Long taskId;
    private String createTime;
    private String modifyTime;
}
