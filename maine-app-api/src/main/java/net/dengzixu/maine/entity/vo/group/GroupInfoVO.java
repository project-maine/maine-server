package net.dengzixu.maine.entity.vo.group;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GroupInfoVO(Long id,
                          String name,
                          String description,
                          @JsonProperty(value = "user_id") Long userID,
                          Integer status,
                          @JsonProperty(value = "create_time") String createTime,
                          @JsonProperty(value = "modify_time") String modifyTime) {
}
