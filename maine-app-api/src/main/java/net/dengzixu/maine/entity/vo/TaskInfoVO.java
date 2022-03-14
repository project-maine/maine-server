package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskInfoVO(Long id, String title, String description, Long userId, Integer status,
                         String endTime, String createTime,
                         @JsonProperty(value = "setting") TaskSettingVO taskSettingVO) {
}