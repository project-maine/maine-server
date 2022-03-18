package net.dengzixu.maine.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskVO(Long id,
                     String title,
                     String description,
                     @JsonProperty(value = "user_id") Long userID,
                     Integer status) {
}
