package net.dengzixu.maine.entity.dto;

import lombok.Data;

@Data
public class TakeRecordDTO {
    private Long taskID;
    private Integer recordStatus;
    private String recordCreateTime;
    private String taskTitle;
    private String taskDescription;
    private Integer taskStatus;
}
