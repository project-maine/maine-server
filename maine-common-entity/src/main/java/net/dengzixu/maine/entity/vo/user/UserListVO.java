package net.dengzixu.maine.entity.vo.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserListVO(@JsonProperty(value = "user_list") List<UserVO> userList) {
}
