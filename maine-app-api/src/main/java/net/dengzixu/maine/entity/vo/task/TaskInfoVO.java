package net.dengzixu.maine.entity.vo.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskInfoVO(Long id,
                         String title,
                         String description,
                         @JsonProperty(value = "user_id") Long userId,
                         Integer status,
                         @JsonProperty(value = "end_time") String endTime,
                         @JsonProperty(value = "create_time") String createTime,
                         @JsonProperty(value = "setting") TaskSettingVO taskSettingVO) {
}