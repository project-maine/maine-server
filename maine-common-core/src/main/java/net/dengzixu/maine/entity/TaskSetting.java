package net.dengzixu.maine.entity;

import lombok.Data;

@Data
public class TaskSetting {
    private Long taskID;
    private byte[] setting;

    private String createTime;
    private String modifyTime;
}
