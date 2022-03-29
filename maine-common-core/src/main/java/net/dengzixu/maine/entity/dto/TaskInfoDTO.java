package net.dengzixu.maine.entity.dto;

import lombok.Data;
import net.dengzixu.maine.entity.TaskSettingItem;

@Data
public class TaskInfoDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Integer status;
    private String endTime;
    private String createTime;
    private TaskSettingItem taskSettingItem;
}
