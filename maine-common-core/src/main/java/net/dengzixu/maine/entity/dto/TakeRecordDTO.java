package net.dengzixu.maine.entity.dto;

public record TakeRecordDTO(Long taskID,
                            Integer recordStatus,
                            String recordCreateTime,
                            String taskTitle,
                            String taskDescription,
                            Integer taskStatus) {
}
