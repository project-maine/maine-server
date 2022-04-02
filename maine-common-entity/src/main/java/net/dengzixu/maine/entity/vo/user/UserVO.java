package net.dengzixu.maine.entity.vo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserVO(Long id,
                     String name,
                     @JsonProperty(value = "password_status")
                     Integer passwordStatus,
                     String phone,
                     @JsonProperty(value = "phone_status")
                     Integer phoneStatus,
                     String email,
                     @JsonProperty(value = "email_status")
                     Integer emailStatus,
                     Integer status,
                     @JsonProperty(value = "create_time")
                     String createTime) {
}
