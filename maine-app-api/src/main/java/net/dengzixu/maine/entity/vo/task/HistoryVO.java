package net.dengzixu.maine.entity.vo.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HistoryVO(@JsonProperty(value = "task_id") Long taskID,
                        @JsonProperty(value = "record_status") Integer recordStatus,
                        @JsonProperty(value = "record_creat_time") String recordCreateTime,
                        @JsonProperty(value = "task_title") String taskTitle,
                        @JsonProperty(value = "task_description") String taskDescription,
                        @JsonProperty(value = "task_status") Integer taskStatus) {
}
