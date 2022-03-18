package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JoinedGroupVO(@JsonProperty(value = "group_id") Long groupID,
                            @JsonProperty(value = "group_name") String groupName,
                            @JsonProperty(value = "group_description") String groupDescription,
                            @JsonProperty(value = "group_status") Integer groupStatus,
                            @JsonProperty(value = "join_time") String joinTime) {
}
