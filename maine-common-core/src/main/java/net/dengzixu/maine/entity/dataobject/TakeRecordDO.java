package net.dengzixu.maine.entity.dataobject;

import lombok.Data;

@Data
public class TakeRecordDO {
    private Long taskID;
    private Integer recordStatus;
    private String recordCreateTime;
    private String taskTitle;
    private String taskDescription;
    private Integer taskStatus;
}
