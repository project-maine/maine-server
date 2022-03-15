package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GroupNumberVO(@JsonProperty(value = "user_id") Long userID,
                            @JsonProperty(value = "user_name") String userName,
                            @JsonProperty(value = "join_time") String joinTime) {
}
