package net.dengzixu.maine.entity.vo.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ParticipantVO(@JsonProperty(value = "user_id") Long userID,
                            @JsonProperty(value = "user_name") String userName,
                            @JsonProperty(value = "serial_id") String serialID,
                            @JsonProperty(value = "record_status") Integer recordStatus,
                            @JsonProperty(value = "take_time") String takeTime) {
}
