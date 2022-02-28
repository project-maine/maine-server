package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private String userId;
    private String createTime;
    private String modifyTime;
}
