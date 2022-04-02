package net.dengzixu.maine.entity.bo.admin;

import javax.validation.constraints.NotBlank;

public record AdminLoginBO(@NotBlank(message = "用户名不能为空")
                           String name,
                           @NotBlank(message = "用户名不能为空")
                           String password) {
}
